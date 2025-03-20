package com.example.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class BackgroundService extends Service {
    private static final String CHANNEL_ID = "BackgroundServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BackgroundService", "Service Created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("BackgroundService", "Service Started");

        // Ensure the service runs in the foreground
        startForegroundService();

        // Return START_STICKY to restart the service if killed
        return START_STICKY;
    }

    private void startForegroundService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Background Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }

            Notification notification = new Notification.Builder(this, CHANNEL_ID)
                  //  .setContentTitle("Service Running")
                  //  .setContentText("Background process is active")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();

            startForeground(1, notification);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BackgroundService", "Service Stopped");
        // Restart the service if it gets killed
        Intent restartService = new Intent(this, BackgroundService.class);
        startService(restartService);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
