package com.bb.network.observer;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/15
 * Time: 18:19
 */
public abstract class BaseObserver<T> implements Observer<T> {
    public BaseObserver() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFail(e);
    }

    @Override
    public void onComplete() {

    }

    protected abstract void onSuccess(T t);

    protected abstract void onFail(Throwable e);

    /*****************************************************************************************************/
    /**
     * mvp：p层调用m层时作接口回调
     * @param <T>
     */
    public interface P2MCallback<T> {
        void onSuccess(T t);

        void onFail(Throwable e);
    }
}
