package com.ist.fishtoucher.base;

import android.app.Application;
import android.content.Context;

public class FishApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
