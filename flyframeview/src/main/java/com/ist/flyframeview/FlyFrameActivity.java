package com.ist.flyframeview;

import android.view.View;

public class FlyFrameActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fly_frame;
    }

    @Override
    protected void initView(View inflate) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean hasFlyFrame() {
        return true;
    }
}
