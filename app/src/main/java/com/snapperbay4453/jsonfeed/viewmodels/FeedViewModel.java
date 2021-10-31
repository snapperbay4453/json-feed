package com.snapperbay4453.jsonfeed.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.snapperbay4453.jsonfeed.databases.LocalDatabase;
import com.snapperbay4453.jsonfeed.models.Feed;
import com.snapperbay4453.jsonfeed.models.FeedDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FeedViewModel extends AndroidViewModel {

    private FeedDao feedDao;
    private ExecutorService executorService;

    public FeedViewModel(@NonNull Application application) {
        super(application);
        feedDao = LocalDatabase.getInstance(application).feedDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Feed>> getAll(){
        return feedDao.selectAll();
    }

    public void save(Feed feed){
        executorService.execute(() -> feedDao.insert(feed));
    }

    public void remove(Feed feed){
        executorService.execute(() -> feedDao.delete(feed));
    }

    public void delete(Feed feed){
        executorService.execute(() -> feedDao.delete(feed));
    }

    public void nuke(){
        executorService.execute(() -> feedDao.clear());
    }

}