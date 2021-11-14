package com.snapperbay4453.jsonfeed.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.snapperbay4453.jsonfeed.activities.MainActivity;
import com.snapperbay4453.jsonfeed.models.Feed;
import com.snapperbay4453.jsonfeed.repositories.FeedRepository;

import java.util.Date;
import java.util.List;

public class MainService extends Service {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private Handler handler;
    private Intent notificationServiceIntent;
    private Intent fetcherServiceIntent;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class ToastRunnable implements Runnable {
        String message;
        public ToastRunnable(String message) {
            this.message = message;
        }
        @Override
        public void run(){
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private void initialize() {
        sharedPreferencesEditor.putBoolean("service_status", false);
        sharedPreferencesEditor.commit();
        handler.post(new ToastRunnable("Service initialized sharedPreferences."));
    }

    private void toggle() {
        boolean serviceStatus = !sharedPreferences.getBoolean("service_status", false);
        sharedPreferencesEditor.putBoolean("service_status", serviceStatus);
        sharedPreferencesEditor.commit();
        handler.post(new ToastRunnable(String.valueOf(serviceStatus)));

        if (serviceStatus) {
            notificationServiceIntent.putExtra("command", "start");
            fetcherServiceIntent.putExtra("command", "start");
        } else {
            notificationServiceIntent.putExtra("command", "stop");
            fetcherServiceIntent.putExtra("command", "stop");
        }
        startService(notificationServiceIntent);
        startService(fetcherServiceIntent);
    }

    @Override
    public void onCreate() {
        sharedPreferences = getApplicationContext().getSharedPreferences("application_preferences", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        handler = new Handler();
        notificationServiceIntent = new Intent(this, NotificationService.class);
        fetcherServiceIntent = new Intent(this, FetcherService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String command = intent.getExtras().getString("command");
        if (command.equals("initialize")) {
            initialize();
        } else if (command.equals("toggle")) {
            toggle();
        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        return START_STICKY;
    }

}