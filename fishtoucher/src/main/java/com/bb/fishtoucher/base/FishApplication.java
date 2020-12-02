package com.bb.fishtoucher.base;

import android.app.Application;
import android.content.Context;

import com.bb.fishtoucher.constant.GlobalConstant;
import com.bb.fishtoucher.db.DaoHelper;

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
