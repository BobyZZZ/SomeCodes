package com.ist.fishtoucher.base;

import android.app.Application;
import android.content.Context;

import com.ist.fishtoucher.constant.GlobalConstant;

public class FishApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        initConfig();
    }

    private void initConfig() {
        GlobalConstant.setFishMode(false);
    }
}
