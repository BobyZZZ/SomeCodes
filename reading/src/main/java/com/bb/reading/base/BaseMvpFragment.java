package com.bb.reading.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * Created by Boby on 2019/6/17.
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements BaseView{
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
