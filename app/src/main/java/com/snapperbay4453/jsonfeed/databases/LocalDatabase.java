package com.snapperbay4453.jsonfeed.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.snapperbay4453.jsonfeed.models.Feed;
import com.snapperbay4453.jsonfeed.models.FeedDao;

@Database(entities = {Feed.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract FeedDao feedDao();

    private static volatile LocalDatabase INSTANCE;

    public static LocalDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (LocalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalDatabase.class, "local_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
