package com.saranya.androidmvvm.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.saranya.androidmvvm.dao.Converter;
import com.saranya.androidmvvm.model.UserContentResponse;

import java.util.List;

@Entity
public class Contents {

    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name = "title")
    private String title;



   /* @ColumnInfo(name = "rows")
    private String rows;*/

    private List<UserContentResponse.Contents> rows = null;


    @TypeConverters(Converter.class)
    public List<UserContentResponse.Contents> getRows() {
        return rows;
    }

    @TypeConverters(Converter.class)
    public void setRows(List<UserContentResponse.Contents> rows) {
        this.rows = rows;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
