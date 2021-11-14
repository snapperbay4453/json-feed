package com.snapperbay4453.jsonfeed.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.snapperbay4453.jsonfeed.models.Feed;

import java.util.List;

@Dao
public interface FeedDao {
    @Query("SELECT * FROM feeds")
    LiveData<List<Feed>> selectAll();

    @Query("SELECT * FROM feeds")
    List<Feed> selectAllSync();

    @Query("SELECT * FROM feeds WHERE id=:id")
    List<Feed> selectByIdSync(int id);

    @Insert
    void insert(Feed feed);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Feed feed);

    @Delete
    void delete(Feed feed);

    @Query("DELETE FROM feeds")
    void deleteAll();
}
