package com.snapperbay4453.jsonfeed.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.snapperbay4453.jsonfeed.databases.LocalDatabase;
import com.snapperbay4453.jsonfeed.models.Feed;
import com.snapperbay4453.jsonfeed.models.FeedDao;
import com.snapperbay4453.jsonfeed.repositories.FeedRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FeedViewModel extends AndroidViewModel {
    private FeedRepository feedRepository;
    private LiveData<List<Feed>> feeds;

    public FeedViewModel(@NonNull Application application) {
        super(application);
        feedRepository = new FeedRepository(application);
        feeds = feedRepository.selectAll();
    }

    public void insert(Feed feed) {
        feedRepository.insert(feed);
    }

    public void update(Feed feed) {
        feedRepository.update(feed);
    }

    public void delete(Feed feed) {
        feedRepository.delete(feed);
    }

    public void deleteAll() {
        feedRepository.deleteAll();
    }

    public LiveData<List<Feed>> selectAll() {
        return feeds;
    }
}
