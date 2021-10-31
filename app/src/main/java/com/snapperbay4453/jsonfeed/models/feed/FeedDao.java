package com.snapperbay4453.jsonfeed.models.feed;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FeedDao {
    @Query("SELECT * FROM feeds")
    LiveData<List<Feed>> selectAll();

    @Query("SELECT * FROM feeds WHERE id=:id")
    LiveData<List<Feed>> selectById(int id);

    @Query("SELECT * FROM feeds WHERE id IN (:ids)")
    LiveData<List<Feed>> selectByIds(int[] ids);

    @Insert
    void insertAll(Feed... users);

    @Delete
    void delete(Feed user);

    @Query("DELETE FROM feeds")
    void nuke();
}
