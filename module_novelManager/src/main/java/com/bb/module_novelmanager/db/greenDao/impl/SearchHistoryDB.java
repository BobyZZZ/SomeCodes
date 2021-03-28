package com.bb.module_novelmanager.db.greenDao.impl;

import com.bb.module_novelmanager.db.greenDao.BaseDB;
import com.bb.module_novelmanager.entity.SearchHistory;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/22
 * Time: 22:57
 */
public class SearchHistoryDB extends BaseDB<SearchHistory, String> {
    public SearchHistoryDB(AbstractDao<SearchHistory, String> dao) {
        super(dao);
    }
}
