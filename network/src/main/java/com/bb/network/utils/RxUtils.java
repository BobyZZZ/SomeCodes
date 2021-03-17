package com.bb.network.utils;

import com.bb.network.bean.BaseResponse;
import com.bb.network.exceptionHandler.ResponseErrorHandler;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/15
 * Time: 18:58
 */
public class RxUtils {
    public static <T> ObservableTransformer<T, T> ioToMain() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Function<Throwable, Observable<T>> handleError() {
        return new ResponseErrorHandler<>();
    }
}
