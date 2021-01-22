package com.bb.reading.db.greenDao.beanManager;

import com.bb.reading.db.greenDao.base.BaseDB;
import com.bb.reading.entity.NovelChapterInfo;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/8
 * Time: 21:28
 * 目录数据库
 */
public class CategoryDB extends BaseDB<NovelChapterInfo,Long> {

    public CategoryDB(AbstractDao<NovelChapterInfo, Long> dao) {
        super(dao);
    }
}
