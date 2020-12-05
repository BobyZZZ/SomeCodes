package com.bb.network.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/15
 * Time: 17:34
 */
public class CommonResponseInterceptor implements Interceptor {
    String TAG = "ResponseInterceptor";
    @Override
    public Response intercept(Chain chain) throws IOException {
        long start = System.currentTimeMillis();
        Response proceed = chain.proceed(chain.request());
        Log.e(TAG, "intercept cost time: " + (System.currentTimeMillis() - start));
        return proceed;
    }
}
