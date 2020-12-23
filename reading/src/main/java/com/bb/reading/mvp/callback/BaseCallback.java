package com.bb.reading.mvp.callback;

/**
 * 普通接口，泛型方法
 */
public abstract class BaseCallback<T> {
    public abstract void onSuccess(T data,boolean... fromCache);
    public void onError(Throwable e){
    }
}
