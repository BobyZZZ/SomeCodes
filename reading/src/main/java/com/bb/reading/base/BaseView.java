package com.bb.reading.base;

import android.content.DialogInterface;

public interface BaseView<P extends BasePresenter> {
    P createPresenter();
    void onError(Throwable throwable);
}
