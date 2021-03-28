package com.bb.module_novelmanager.db.greenDao.impl;


import com.bb.module_novelmanager.db.greenDao.BaseDB;
import com.bb.module_novelmanager.entity.NovelDetails;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/8
 * Time: 21:28
 * 目录数据库
 */
public class LikedNovelDB extends BaseDB<NovelDetails,String> {

    public LikedNovelDB(AbstractDao<NovelDetails,String> dao) {
        super(dao);
    }
}
