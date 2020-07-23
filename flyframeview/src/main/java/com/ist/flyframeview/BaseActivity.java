package com.ist.flyframeview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ist.flyframeview.view.FlyFrameView;

public abstract class BaseActivity extends AppCompatActivity {

    private View mInflate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initViewInternal();

        initData();
    }

    private void initViewInternal() {
        hideActionBar();

        setupFlyFrame();//设置焦点移动框

        initView(LayoutInflater.from(this).inflate(getLayoutId(), (FrameLayout)findViewById(R.id.fl_container), true));
    }

    private void setupFlyFrame() {
        if (hasFlyFrame()) {
            FlyFrameView mFlyFrameView = findViewById(R.id.fly_view);
            initListener(mFlyFrameView);
        }
    }

    private void hideActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
    }

    private void initListener(final FlyFrameView flyFrameView) {
        flyFrameView.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                flyFrameView.moveToTarget(newFocus);
            }
        });
    }

    protected abstract void initView(View inflate);

    protected abstract void initData();

    protected boolean hasFlyFrame() {
        return false;
    }

    protected abstract int getLayoutId();
}
