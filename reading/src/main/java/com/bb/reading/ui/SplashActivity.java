package com.bb.reading.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.bb.module_common.utils.StatusBarUtils;
import com.bb.reading.BuildConfig;
import com.bb.reading.R;
import com.bb.reading.ui.mvp.view.activity.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusBarUtils.setStatusBar(this, Color.TRANSPARENT,true,true);

        initFastEnterHomeWay();
        countDownToMainActivity();
    }

    /**
     * 点击快速进入主页
     */
    private void initFastEnterHomeWay() {
        if (BuildConfig.DEBUG) {
            findViewById(R.id.flash_text_logo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHandler.removeCallbacks(mToMainPageRunnable);
                    jumpToMainPage();
                }
            });
        }
    }

    private void countDownToMainActivity() {
        mHandler.postDelayed(mToMainPageRunnable, 2900);
    }

    private Runnable mToMainPageRunnable = new Runnable() {
        @Override
        public void run() {
            jumpToMainPage();
        }
    };

    private void jumpToMainPage() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}