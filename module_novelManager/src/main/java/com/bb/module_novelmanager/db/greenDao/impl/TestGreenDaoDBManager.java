package com.bb.module_novelmanager.db.greenDao.impl;


import com.bb.module_novelmanager.db.greenDao.BaseDB;
import com.bb.module_novelmanager.entity.TestGreenDaoEntity;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/8
 * Time: 0:20
 */
public class TestGreenDaoDBManager extends BaseDB<TestGreenDaoEntity,String> {
    public TestGreenDaoDBManager(AbstractDao<TestGreenDaoEntity, String> dao) {
        super(dao);
    }
}
