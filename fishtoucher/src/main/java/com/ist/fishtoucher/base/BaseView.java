package com.ist.fishtoucher.base;

public interface BaseView {
    void showToast(String info);

    void showProgress();

    void onError(Throwable throwable);
}
