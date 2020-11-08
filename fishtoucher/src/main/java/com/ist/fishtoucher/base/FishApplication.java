package com.ist.fishtoucher.base;

import android.app.Application;
import android.content.Context;

import com.ist.fishtoucher.constant.GlobalConstant;
import com.ist.fishtoucher.db.DaoHelper;

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
