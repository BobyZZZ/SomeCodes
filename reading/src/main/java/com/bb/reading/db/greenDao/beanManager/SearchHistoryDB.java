package com.bb.reading.db.greenDao.beanManager;

import com.bb.reading.db.greenDao.base.BaseDB;
import com.bb.reading.entity.SearchHistory;

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
