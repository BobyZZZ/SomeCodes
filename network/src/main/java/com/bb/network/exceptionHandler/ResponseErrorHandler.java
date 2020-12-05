package com.bb.network.exceptionHandler;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 异常处理统一出口，处理以下异常：
 * 1、服务器请求失败400、401等错误
 * 2、服务器返回BaseResponse中定义的错误code
 *
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/15
 * Time: 20:54
 */
public class ResponseErrorHandler<T> implements Function<Throwable, Observable<T>> {
    String TAG = "ResponseErrorHandler";
    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        throwable.printStackTrace();
        return Observable.error(ExceptionHandler.handleException(throwable));
    }
}
