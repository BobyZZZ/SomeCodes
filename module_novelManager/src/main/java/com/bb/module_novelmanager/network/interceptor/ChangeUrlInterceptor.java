package com.bb.module_novelmanager.network.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.bb.module_common.utils.log.LogUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/17
 * Time: 22:35
 */
public class ChangeUrlInterceptor implements Interceptor {
    String TAG = "ChangeUrlInterceptor";
    final String urlHeader = "needFilter";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originRequest = chain.request();
        String hasBaseUrl = originRequest.header(urlHeader);
        if (TextUtils.isEmpty(hasBaseUrl)) {
            return chain.proceed(originRequest);
        }
        Request.Builder newBuilder = originRequest.newBuilder();
        newBuilder.removeHeader(urlHeader);

        HttpUrl oldUrl = originRequest.url();
        List<String> pathSegments = oldUrl.pathSegments();
        String host = oldUrl.url().getHost();

        HttpUrl.Builder urlBuilder = oldUrl.newBuilder();
        for (int i = 0; i < pathSegments.size(); i++) {
            String pathSegment = pathSegments.get(i);
            if (pathSegment.contains(host)) {
                String replace = pathSegment.replace(host, "");
                urlBuilder.setPathSegment(i, replace);
            }
        }
        HttpUrl newUrl = urlBuilder.build();

        Request newRequest = newBuilder.url(newUrl).build();
        LogUtils.d(TAG, "intercept() called with: newRequest = [" + newRequest + "]");
        return chain.proceed(newRequest);
    }
}
