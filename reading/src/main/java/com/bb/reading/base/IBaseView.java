package com.bb.reading.base;

import android.content.DialogInterface;

public interface IBaseView<P extends BasePresenter> {
    P createPresenter();
    void onError(Throwable throwable);
}
