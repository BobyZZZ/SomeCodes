package com.bb.network.interceptor;

import com.bb.network.INetworkRequestInfo;

import java.io.IOException;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/15
 * Time: 16:49
 */
public class CommonRequestInterceptor implements Interceptor {

    private final INetworkRequestInfo mNetworkRequestInfo;

    public CommonRequestInterceptor(INetworkRequestInfo networkRequestInfo) {
        mNetworkRequestInfo = networkRequestInfo;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (mNetworkRequestInfo != null) {
            builder.addHeader("appVersion", mNetworkRequestInfo.getAppVersionCode());
        }
        builder.addHeader("os","android");
        builder.addHeader("date",new Date(System.currentTimeMillis()).toString());
        Request request = builder.build();
        return chain.proceed(request);
    }
}
