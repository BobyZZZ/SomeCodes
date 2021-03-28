package com.bb.reading.base;

import com.bb.module_common.base.BaseApplication;
import com.bb.module_novelmanager.constant.GlobalConstant;
import com.bb.module_novelmanager.db.greenDao.DaoHelper;

public class FishApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
        DaoHelper.getInstance().init(this);
    }

    private void initConfig() {
        GlobalConstant.setFishMode(false);
    }
}
