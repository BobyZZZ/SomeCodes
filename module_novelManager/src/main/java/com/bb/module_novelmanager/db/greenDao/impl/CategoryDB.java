package com.bb.module_novelmanager.db.greenDao.impl;

import com.bb.module_novelmanager.db.greenDao.BaseDB;
import com.bb.module_novelmanager.entity.NovelChapterInfo;

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
