package com.bb.module_bookshelf;

import com.bb.module_common.base.BaseApplication;
import com.bb.module_novelmanager.db.greenDao.DaoHelper;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/3/28
 * Time: 13:10
 */
public class BookshelfApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        DaoHelper.getInstance().init(this);
    }
}
