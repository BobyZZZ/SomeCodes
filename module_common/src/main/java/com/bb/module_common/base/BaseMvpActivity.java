package com.bb.module_common.base;

import com.bb.module_common.R;
import com.bb.module_common.utils.log.LogUtils;
import com.bb.network.exceptionHandler.ExceptionHandler;

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
