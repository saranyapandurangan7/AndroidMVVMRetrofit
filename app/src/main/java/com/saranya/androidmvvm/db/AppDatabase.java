package com.saranya.androidmvvm.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.saranya.androidmvvm.dao.Converter;
import com.saranya.androidmvvm.dao.UserContentDao;

@Database(entities = {Contents.class}, version = 1)
@TypeConverters(Converter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserContentDao userContentDao();
}
