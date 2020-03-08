package com.example.appproject;

import android.app.Application;
import android.content.Context;

import com.example.appproject.network.NetWorkManager;

public class RxApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        // Init Retrofit
        NetWorkManager.getInstance().init();
    }

    public static Context getContext() {
        return mContext;
    }
}
