package com.saranya.androidmvvm.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.saranya.androidmvvm.db.Contents;

import java.util.List;

@Dao
public interface UserContentDao {
    @Query("SELECT * FROM Contents")
    List<Contents> getAll();

    @Insert
    void insert(Contents contents);

    @Delete
    void delete(Contents contents);

    @Update
    void update(Contents contents);
}
