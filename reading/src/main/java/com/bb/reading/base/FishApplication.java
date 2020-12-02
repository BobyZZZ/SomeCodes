package com.bb.reading.base;

import android.app.Application;
import android.content.Context;

import com.bb.reading.constant.GlobalConstant;
import com.bb.reading.db.DaoHelper;

public class FishApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        initConfig();
        DaoHelper.getInstance().init(this);
    }

    private void initConfig() {
        GlobalConstant.setFishMode(false);
    }
}
