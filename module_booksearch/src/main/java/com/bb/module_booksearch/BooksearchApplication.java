package com.bb.module_booksearch;

import com.bb.module_common.base.BaseApplication;
import com.bb.module_novelmanager.db.greenDao.DaoHelper;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/3/28
 * Time: 13:15
 */
public class BooksearchApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        DaoHelper.getInstance().init(this);
    }
}
