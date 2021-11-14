package com.snapperbay4453.jsonfeed.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.snapperbay4453.jsonfeed.databases.LocalDatabase;
import com.snapperbay4453.jsonfeed.models.Feed;
import com.snapperbay4453.jsonfeed.models.FeedDao;

import java.util.List;

public class FeedRepository {
    private FeedDao feedDao;
    private LiveData<List<Feed>> feeds;

    public FeedRepository(Application application) {
        feedDao = LocalDatabase.getInstance(application).feedDao();
        feeds = feedDao.selectAll();
    }

    public LiveData<List<Feed>> selectAll() {
        return feeds;
    }

    public List<Feed> selectAllSync() {
        return feedDao.selectAllSync();
    }

    public Feed selectByIdSync(int id) {
        return feedDao.selectByIdSync(id).get(0);
    }

    public void insert(Feed feed) {
        new InsertAsyncTask(feedDao).execute(feed);
    }

    public void update(Feed feed) {
        new UpdateAsyncTask(feedDao).execute(feed);
    }

    public void delete(Feed feed) {
        new DeleteAsyncTask(feedDao).execute(feed);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(feedDao).execute();
    }

    private static class InsertAsyncTask extends AsyncTask<Feed, Void, Void> {
        private FeedDao feedDao;

        private InsertAsyncTask(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(Feed... feeds) {
            feedDao.insert(feeds[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Feed, Void, Void> {
        private FeedDao feedDao;

        private UpdateAsyncTask(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(Feed... feeds) {
            feedDao.update(feeds[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Feed, Void, Void> {
        private FeedDao feedDao;

        private DeleteAsyncTask(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(Feed... feeds) {
            feedDao.delete(feeds[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Feed, Void, Void> {
        private FeedDao feedDao;

        private DeleteAllAsyncTask(FeedDao feedDao) {
            this.feedDao = feedDao;
        }

        @Override
        protected Void doInBackground(Feed... feeds) {
            feedDao.deleteAll();
            return null;
        }
    }
}