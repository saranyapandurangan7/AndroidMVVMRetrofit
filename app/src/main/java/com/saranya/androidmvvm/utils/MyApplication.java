package com.saranya.androidmvvm.utils;

import android.app.Application;

import com.saranya.androidmvvm.api.ApiComponent;
import com.saranya.androidmvvm.api.DaggerApiComponent;
import com.saranya.androidmvvm.api.UserContentService;
import com.saranya.androidmvvm.dimodule.ApiModule;
import com.saranya.androidmvvm.dimodule.AppModule;

public class MyApplication extends Application  {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
