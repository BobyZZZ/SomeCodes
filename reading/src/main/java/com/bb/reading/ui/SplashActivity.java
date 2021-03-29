package com.bb.reading.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.bb.module_common.utils.StatusBarUtils;
import com.bb.reading.R;
import com.bb.reading.ui.mvp.view.activity.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusBarUtils.setStatusBar(this, Color.TRANSPARENT,true,true);

        countDownToMainActivity();
    }

    private void countDownToMainActivity() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2900);
    }
}