package com.bb.module_novelmanager.network.api;

import com.bb.module_novelmanager.network.NovelService;
import com.bb.module_novelmanager.network.interceptor.ChangeUrlInterceptor;
import com.bb.network.NetworkApiServer;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/3/28
 * Time: 19:50
 */
public class NovelServiceBQGApi extends NetworkApiServer {
    private static NovelServiceBQGApi mInstance;

    public static NovelServiceBQGApi getInstance() {
        if (mInstance ==null) {
            synchronized (NovelServiceBQGApi.class) {
                if (mInstance == null) {
                    mInstance = new NovelServiceBQGApi();
                }
            }
        }
        return mInstance;
    }

    @Override
    public String getFormal() {
        return NovelService.NORVEL_BASE_URL;
    }

    @Override
    public String getTest() {
        return NovelService.NORVEL_BASE_URL;
    }

    @Override
    protected OkHttpClient createOKhttpClient() {
        OkHttpClient oKhttpClient = super.createOKhttpClient();
        return oKhttpClient.newBuilder()
                .addNetworkInterceptor(new ChangeUrlInterceptor())
                .build();
    }
/*
    @Override
    protected Interceptor getInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return httpLoggingInterceptor;
    }*/
}
