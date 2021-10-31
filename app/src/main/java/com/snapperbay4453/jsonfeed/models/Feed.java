package com.snapperbay4453.jsonfeed.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "feeds")
public class Feed {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "timestamp")
    private long timestamp;

    @ColumnInfo(name = "interval")
    private int interval;

    @ColumnInfo(name = "data")
    private String data;

    public Feed(@NonNull String name, @NonNull String url, String description) {
        this.name = name;
        this.url = url;
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getInterval() {
        return interval;
    }
    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return "Feed{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static DiffUtil.ItemCallback<Feed> DIFF_CALLBACK = new  DiffUtil.ItemCallback<Feed>() {
        @Override
        public boolean areItemsTheSame(@NonNull Feed oldItem, @NonNull Feed newItem) {
            return oldItem.id == newItem.id;
        }
        @Override
        public boolean areContentsTheSame(@NonNull Feed oldItem, @NonNull Feed newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        Feed feed = (Feed) obj;
        return feed.id == this.id
                && feed.url == this.url
                && feed.description == this.description
                && feed.timestamp == this.timestamp
                && feed.interval == this.interval
                && feed.data == this.data;
    }

}
