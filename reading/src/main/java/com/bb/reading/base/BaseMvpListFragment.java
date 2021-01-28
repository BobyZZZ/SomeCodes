package com.bb.reading.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Boby on 2019/6/17.
 */

public abstract class BaseMvpListFragment<P extends BasePresenter,A extends RecyclerView.Adapter> extends ListFragment<A> implements BaseView {
    String TAG = "BaseMvpFragment";
    protected P mPresenter;

    /**
     * 在onCreate()后调用
     */
    @Override
    public abstract P createPresenter();

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
