package com.example.appproject.network;

import android.util.Log;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkManager {

    private static final String TAG = "NetWorkManager";
    private static NetWorkManager mInstance;
    private static Retrofit sRetrofit;

    private static final String URL = "https://api.ooopn.com/image/beauty/";

    public static NetWorkManager getInstance() {
        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        return mInstance;
    }

    public void init() {
        // Init okHttp object
        Log.i(TAG, "init: ");
        OkHttpClient client = new OkHttpClient.Builder().build();

        // Init retrofit
        sRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return sRetrofit;
    }
}
