package com.ist.flyframeview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ist.flyframeview.adapter.TvAdapter;

public class TvActivity extends BaseActivity {
    String TAG = "TvActivity";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tv;
    }

    @Override
    protected void initView(View inflate) {
        final RecyclerView rvTv = findViewById(R.id.rv_tv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rvTv.setLayoutManager(layoutManager);
        rvTv.setAdapter(new TvAdapter());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean hasFlyFrame() {
        return false;
    }
}
