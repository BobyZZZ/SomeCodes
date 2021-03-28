package com.bb.module_common.base;

public interface IBaseView<P extends BasePresenter> {
    P createPresenter();
    void onError(Throwable throwable);
}
