package com.snapperbay4453.jsonfeed.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.snapperbay4453.jsonfeed.models.Feed;

import java.util.List;

@Dao
public interface FeedDao {
    @Query("SELECT * FROM feeds")
    LiveData<List<Feed>> selectAll();

    @Insert
    void insert(Feed user);

    @Delete
    void delete(Feed user);

    @Query("DELETE FROM feeds")
    void clear();
}
