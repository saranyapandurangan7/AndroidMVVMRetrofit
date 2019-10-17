package com.saranya.androidmvvm.app;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.mContext = getApplicationContext();
    }
    public static Context getAppContext() {
        return MyApplication.mContext;
    }
}
