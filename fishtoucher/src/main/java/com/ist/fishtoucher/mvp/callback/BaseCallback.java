package com.ist.fishtoucher.mvp.callback;

/**
 * 普通接口，泛型方法
 */
public abstract class BaseCallback {
    public abstract <T> void onSuccess(T data);
    public void onError(Throwable e){
    }
}
