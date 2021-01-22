package com.bb.reading.db.greenDao.beanManager;

import com.bb.reading.db.greenDao.base.BaseDB;
import com.bb.reading.entity.TestGreenDaoEntity;

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
