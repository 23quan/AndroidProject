package com.example.androidproject;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    //全局唯一的上下文Context
    private static Context mContext;
    //全局BaseApplication
    private static BaseApplication instance;

    //全局唯一的上下文context
    public static Context getContext() {
        return mContext;
    }

    //全局BaseApplication
    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        instance = this;
    }
}
