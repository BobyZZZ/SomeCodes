package com.bb.reading.base;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView<P>{
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
}
