package com.bb.reading.base;

import com.bb.network.exceptionHandler.ExceptionHandler;
import com.bb.reading.R;
import com.bb.reading.utils.log.LogUtils;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements IBaseView<P> {
    String TAG = "BaseMvpActivity";
    protected P mPresenter;

    @Override
    protected void initOthers() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        LogUtils.e(TAG, "onError: " + throwable);
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
