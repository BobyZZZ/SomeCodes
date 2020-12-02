package com.bb.fishtoucher.db.greenDao.beanManager;

import com.bb.fishtoucher.db.greenDao.base.BaseBeanManager;
import com.bb.fishtoucher.entity.TestGreenDaoEntity;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/8
 * Time: 0:20
 */
public class TestGreenDaoDBManager extends BaseBeanManager<TestGreenDaoEntity,String> {
    public TestGreenDaoDBManager(AbstractDao<TestGreenDaoEntity, String> dao) {
        super(dao);
    }
}
