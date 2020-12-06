package com.bb.uilib.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        initData();
        initView();
    }

    protected void initData() {

    }

    protected void initView() {

    }

    protected abstract int getLayoutRes();
}