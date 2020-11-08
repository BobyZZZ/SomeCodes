package com.ist.fishtoucher.db.greenDao.beanManager;

import com.ist.fishtoucher.db.greenDao.base.BaseBeanManager;
import com.ist.fishtoucher.entity.NovelCategory;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/8
 * Time: 21:28
 */
public class NovelCategoryDBManager extends BaseBeanManager<NovelCategory,String> {

    public NovelCategoryDBManager(AbstractDao<NovelCategory, String> dao) {
        super(dao);
    }
}
