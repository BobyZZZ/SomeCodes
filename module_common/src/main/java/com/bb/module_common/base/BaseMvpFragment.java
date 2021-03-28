package com.bb.module_common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bb.module_common.R;
import com.bb.network.exceptionHandler.ExceptionHandler;
import com.bb.module_common.utils.log.LogUtils;

/**
 * Created by Boby on 2019/6/17.
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements IBaseView {
    protected String TAG = "BaseMvpFragment";
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

    @Override
    public void onError(Throwable throwable) {
        LogUtils.e(TAG, "onError: " + throwable + "," + (throwable instanceof ExceptionHandler.ResponseThrowable));
        if (throwable instanceof ExceptionHandler.ResponseThrowable) {
            ExceptionHandler.ResponseThrowable error = (ExceptionHandler.ResponseThrowable) throwable;
            switch (error.code) {
                case ExceptionHandler.Error.LOCAL_CACHE_ERROR:
                    break;
                default:
                    showToast(R.string.error_server);
                    hideLoading();
                    break;
            }
        }
    }
}
