// 참조: https://shihis123.tistory.com/entry/Android-Background-ServiceThread-백그라운드-서비스-유지하기Notification [Gomdori]

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

public class BackgroundService extends Service {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private Handler handler;
    private BackgroundNotificationServiceThread backgroundNotificationServiceThread;
    private Notification notification;
    private String notificationChannelId = "JSON_FEED_BACKGROUND_SERVICE";
    private int notificationId = 1;

    public BackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class BackgroundNotificationServiceThread extends Thread {
        boolean isRun = true;
        public void stopForever() {
            synchronized (this) {
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.cancel(notificationId);
                this.isRun = false;
            }
        }
        private void createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        notificationChannelId,
                        "JSON Feed 실행 알림",
                        NotificationManager.IMPORTANCE_DEFAULT
                );
                channel.setDescription("JSON Feed 실행 알림입니다.");
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        }
        public void createNotification() {
            Context backgroundServiceContext = BackgroundService.this;
            Intent mainIntent = new Intent(backgroundServiceContext, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    backgroundServiceContext, 0, mainIntent, 0
            );
            NotificationCompat.Builder notificationBuilder;
            notificationBuilder = new NotificationCompat.Builder(backgroundServiceContext, notificationChannelId);
            notification = notificationBuilder
                    .setSmallIcon(android.R.drawable.btn_star)
                    .setContentTitle("JSON Feed")
                    .setContentText("JSON Feed is running.")
                    .setContentIntent(pendingIntent)
                    .build();
            notification.defaults = Notification.DEFAULT_SOUND;
            // notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(backgroundServiceContext);
            notificationManagerCompat.notify(notificationId, notification);
        }
        public void run() {
            createNotificationChannel();
            while (isRun) {
                createNotification();
                try {
                    Thread.sleep( 1000 );
                } catch (Exception e) {}
            }
        }
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

    private void initializeSharedPreferences() {
        boolean isExist = sharedPreferences.contains("background_service_status");
        if(!isExist) {
            sharedPreferencesEditor.putBoolean("background_service_status", false);
        }
        sharedPreferencesEditor.commit();
        handler.post(new ToastRunnable("Background service initialized sharedPreferences."));
    }

    private void toggleBackgroundServiceStatus() {
        boolean backgroundServiceStatus = sharedPreferences.getBoolean("background_service_status", false);
        sharedPreferencesEditor.putBoolean("background_service_status", !backgroundServiceStatus);
        sharedPreferencesEditor.commit();
        handler.post(new ToastRunnable(String.valueOf(sharedPreferences.getBoolean("background_service_status", false))));

        if(!backgroundServiceStatus) {
            backgroundNotificationServiceThread = new BackgroundNotificationServiceThread();
            backgroundNotificationServiceThread.start();
        } else {
            backgroundNotificationServiceThread.stopForever();
            backgroundNotificationServiceThread = null;
        }
    }

    @Override
    public void onCreate() {
        sharedPreferences = getApplicationContext().getSharedPreferences("application_preferences", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String command = intent.getExtras().getString("command");
        if (command.equals("initialize")) {
            initializeSharedPreferences();
        } else if (command.equals("toggle_background_service")) {
            toggleBackgroundServiceStatus();
        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        return START_STICKY;
    }

}