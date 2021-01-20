package com.bb.reading.db.greenDao.beanManager;

import com.bb.reading.db.greenDao.base.BaseBeanManager;
import com.bb.reading.entity.NovelChapterInfo;
import com.bb.reading.entity.NovelDetails;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/8
 * Time: 21:28
 * 目录数据库
 */
public class LikedNovelDB extends BaseBeanManager<NovelDetails,String> {

    public LikedNovelDB(AbstractDao<NovelDetails,String> dao) {
        super(dao);
    }
}
