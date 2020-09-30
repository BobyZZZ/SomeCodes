package com.ist.fishtoucher.base;

/**
 * 绑定View和解绑View的基类，其他功能方法另起一个接口进行定义
 * @param <V>
 */
public class BasePresenter<V extends BaseView> {
    protected V mView;

    public void attachView(V view){
        mView = view;
    }

    public void detachView(){
        mView = null;
    }
}
