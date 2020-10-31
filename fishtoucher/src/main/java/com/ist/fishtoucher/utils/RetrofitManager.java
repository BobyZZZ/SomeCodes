package com.ist.fishtoucher.utils;

import android.util.Log;

import com.ist.fishtoucher.iApiService.NovelService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private Retrofit mRetrofit;

    private RetrofitManager() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.v("loggingInterceptor",message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()/*.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                LogUtils.v("LogInterceptor", "request:" + request);
                LogUtils.v("LogInterceptor", "System.nanoTime():" + System.nanoTime());
                Response response = chain.proceed(request);
                LogUtils.v("LogInterceptor", "request:" + request);
                LogUtils.v("LogInterceptor", "System.nanoTime():" + System.nanoTime());
                return response;
            }
        })*/.addNetworkInterceptor(loggingInterceptor).connectTimeout(15, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(NovelService.NORVEL_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitManager getInstance() {
        return Holder.mInstance;
    }

    private static class Holder {
        private static RetrofitManager mInstance = new RetrofitManager();
    }

    public <T> T createRs(Class<T> clazz) {
        return mRetrofit == null ? null : mRetrofit.create(clazz);
    }
}
