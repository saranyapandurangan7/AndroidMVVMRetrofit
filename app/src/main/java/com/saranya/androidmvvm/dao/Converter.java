package com.saranya.androidmvvm.dao;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saranya.androidmvvm.model.UserContentResponse;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converter {

    @TypeConverter
    public String fromCountryLangList(List<UserContentResponse.Contents> countryLang) {
        if (countryLang == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<UserContentResponse.Contents>>() {}.getType();
        String json = gson.toJson(countryLang, type);
        return json;
    }

    @TypeConverter
    public List<UserContentResponse.Contents> toCountryLangList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<UserContentResponse.Contents>>() {}.getType();
        List<UserContentResponse.Contents> countryLangList = gson.fromJson(countryLangString, type);
        return countryLangList;
    }
}
