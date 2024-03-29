package com.bb.module_novelmanager.network;

import com.bb.module_common.utils.log.LogUtils;
import com.bb.module_novelmanager.BuildConfig;
import com.bb.module_novelmanager.network.interceptor.ChangeUrlInterceptor;

import java.util.concurrent.TimeUnit;

import me.ghui.fruit.converter.retrofit.FruitConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitManager {
    private Retrofit mRetrofit;

    private RetrofitManager() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.v("loggingInterceptor", message);
            }
        });
        HttpLoggingInterceptor.Level logLevel = BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC;
        loggingInterceptor.setLevel(logLevel);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .proxy(new Proxy(Proxy.Type.HTTP,new InetSocketAddress("192.168.1.102",808)))
                .addNetworkInterceptor(new ChangeUrlInterceptor())
                .addNetworkInterceptor(loggingInterceptor)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(NovelService.NORVEL_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(FruitConverterFactory.create())
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
