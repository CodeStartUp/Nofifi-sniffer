package com.example.notification;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Start the background service safely
        Intent serviceIntent = new Intent(getApplicationContext(), BackgroundService.class);
        getApplicationContext().startService(serviceIntent);

        return Result.success();
    }
}
