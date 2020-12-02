package com.bb.reading.db.greenDao.beanManager;

import com.bb.reading.db.greenDao.base.BaseBeanManager;
import com.bb.reading.entity.NovelCategory;

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
