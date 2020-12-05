package com.bb.network;

import android.util.Log;

import com.bb.network.bean.BaseResponse;
import com.bb.network.environment.INetworkEnvironment;
import com.bb.network.exceptionHandler.ExceptionHandler;
import com.bb.network.exceptionHandler.ResponseErrorHandler;
import com.bb.network.interceptor.CommonRequestInterceptor;
import com.bb.network.interceptor.CommonResponseInterceptor;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/15
 * Time: 16:32
 */
public abstract class NetworkApiServer implements INetworkEnvironment {
    static String TAG = "NetworkApiServer";
    private static Map<String, Retrofit> mRetrofitMap = new HashMap<>();
    private static INetworkRequestInfo mNetworkRequestInfo;
    private String mBaseUrl;

    protected void init(INetworkRequestInfo networkRequestInfo) {
        mNetworkRequestInfo = networkRequestInfo;
    }

    public <T> T getService(Class<T> clazz) {
        return getService(clazz,getBaseUrl());
    }
        /**
         * 根据baseurl获取api接口
         * @param clazz
         * @param <T>
         * @return
         */
    public <T> T getService(Class<T> clazz,String baseUrl) {
        if (baseUrl != null) {
            return getRetrofit(baseUrl).create(clazz);
        } else {
            return getRetrofit(getBaseUrl()).create(clazz);
        }
    }

    private void initBaseUrl() {
        if (mBaseUrl == null) {
            if (mNetworkRequestInfo != null && mNetworkRequestInfo.isDebug()) {
                mBaseUrl = getTest();
            }
            mBaseUrl = getFormal();
            Log.d(TAG, "initBaseUrl: " + mBaseUrl);
        }
    }

    private String getBaseUrl() {
        initBaseUrl();
        return mBaseUrl;
    }

    private Retrofit getRetrofit(String baseUrl) {
        Retrofit retrofit = mRetrofitMap.get(baseUrl);
        if (retrofit != null) {
            return retrofit;
        }

        retrofit = new Retrofit.Builder().client(createOKhttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mRetrofitMap.put(baseUrl, retrofit);
        return retrofit;
    }

    protected OkHttpClient createOKhttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS);
        if (getInterceptor() != null) {
            builder.addInterceptor(getInterceptor());
        }
        builder.addInterceptor(new CommonRequestInterceptor(mNetworkRequestInfo));
        builder.addInterceptor(new CommonResponseInterceptor());
        if (mNetworkRequestInfo != null && mNetworkRequestInfo.isDebug()) {
            builder.addInterceptor(getLoggingInterceptor());
        }
        return builder.build();
    }

    protected Interceptor getInterceptor() {
        return null;
    }

    /**
     * 添加日志拦截器
     *
     * @return
     */
    private static Interceptor getLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    /**
     * 线程切换，并抛出统一异常（有异常情况下）
     *
     * @param <T>
     * @return
     */
    public <T> ObservableTransformer<T, T> ioToMainWithHandlerError() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
                if (getServerExceptionHandler() != null) {
                    observable = (Observable<T>) observable.map(getServerExceptionHandler());
                }
                observable = observable.onErrorResumeNext(new ResponseErrorHandler<T>());
                return observable;
            }
        };
    }

    /**
     * 自定义处理服务器返回错误结果，抛出公共异常ResponseThrowable，方便上层统一错误处理异常
     *
     * @param <T>
     * @return
     */
    public <T> Function<T, T> getServerExceptionHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(T t) throws Exception {
                if (t instanceof BaseResponse && ((BaseResponse) t).getCode() != BaseResponse.RESPONSE_OK) {
                    throw new ExceptionHandler.ResponseThrowable(ExceptionHandler.Error.UNKNOWN, "服务器数据请求失败"
                            , new IllegalArgumentException());
                }
                return t;
            }
        };
    }
}
