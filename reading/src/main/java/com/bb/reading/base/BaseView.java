package com.bb.reading.base;

import android.content.DialogInterface;

public interface BaseView<P extends BasePresenter> {
    P createPresenter();

    void showToast(String info);

    void showLoading(DialogInterface.OnCancelListener onCancelListener);

    void hideLoading();

    void onError(Throwable throwable);
}
