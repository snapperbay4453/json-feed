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

import com.jayway.jsonpath.JsonPath;
import com.snapperbay4453.jsonfeed.activities.MainActivity;
import com.snapperbay4453.jsonfeed.models.Feed;
import com.snapperbay4453.jsonfeed.repositories.FeedRepository;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FetcherService extends Service {

    private List<FetcherThread> fetcherThreads;
    private FeedRepository feedRepository = new FeedRepository(getApplication());
    private List<Feed> feeds;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class FetcherThread extends Thread {
        boolean isRun = true;
        Feed feed;
        public FetcherThread(Feed feed) {
            this.feed = feed;
        }
        public void stopForever() {
            synchronized (this) {
                this.isRun = false;
            }
        }
        public String fetch(String urlString) throws Exception {
        String response = "";
            URL url = new URL(urlString);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();

            if (httpUrlConnection.getResponseCode() == 200) {
                InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"), 8);
                StringBuilder stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                response = stringBuilder.toString();
            } else {
                throw new Exception("데이터를 가져오는 중 오류가 발생했습니다.");
            }
            return response;
        }
        private String filter(String jsonString, String filter) throws Exception {
            if ((filter == null) || (filter.equals(""))) { return jsonString; }
            else {
                String filteredString = JsonPath.parse(jsonString).read(filter).toString();
                return filteredString;
            }
        }
        public void run() {
            while (isRun) {
                try {
                    String jsonString = fetch(feed.getUrl());
                    feed.setData(filter(jsonString, feed.getFilter()));
                } catch (Exception exception) {
                    feed.setData(exception.getMessage());
                } finally {
                    feedRepository.update(feed);
                }
                try {
                    Thread.sleep( feed.getRefreshInterval() * 1000 );
                } catch (Exception exception) {}
            }
        }
    }

    private void start() {
        fetcherThreads = new ArrayList<>();
        feeds = feedRepository.selectAllSync();
        for (Feed feed: feeds) {
            FetcherThread fetcherThread = new FetcherThread(feed);
            fetcherThreads.add(fetcherThread);
            fetcherThread.start();
        }
    }

    private void stop() {
        for (FetcherThread fetcherThread: fetcherThreads) {
            fetcherThread.stopForever();
        }
        fetcherThreads = null;
    }

    private void restart() {
        stop();
        start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String command = intent.getExtras().getString("command");
        if (command.equals("start")) {
            start();
        } else if (command.equals("stop")) {
            stop();
        } else if (command.equals("restart")) {
            restart();
        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        return START_STICKY;
    }

}