package com.bb.reading.db.greenDao.beanManager;

import com.bb.reading.entity.DaoSession;
import com.bb.reading.entity.NovelChapterInfo;
import com.bb.reading.entity.NovelChapterInfoDao;
import com.bb.reading.utils.LongLogUtils;

import java.util.List;


/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/8
 * Time: 0:20
 */
public class NovelDBManager {
    String TAG = "NovelDBManager";

    private static NovelDBManager sInstance;
    public CategoryDB mCategoryDB;

    private NovelDBManager(DaoSession dao) {
        NovelChapterInfoDao novelChapterInfoDao = dao.getNovelChapterInfoDao();
        mCategoryDB = new CategoryDB(novelChapterInfoDao);
    }

    public static NovelDBManager getInstance(DaoSession dao) {
        if (sInstance == null) {
            synchronized (NovelDBManager.class) {
                sInstance = new NovelDBManager(dao);
            }
        }
        return sInstance;
    }

    public List<NovelChapterInfo> getCategory(String novelIndex) {
        List<NovelChapterInfo> cache = mCategoryDB.query(NovelChapterInfoDao.Properties.NovelID.eq(novelIndex));
        LongLogUtils.i(TAG, "get " + novelIndex + "'s Category in thread: " + Thread.currentThread().getName()
                + ", have cache: " + cache);
        return cache;
    }

    public boolean saveCategory(List<NovelChapterInfo> novelCategory) {
        if (novelCategory == null || novelCategory.isEmpty()) {
            return false;
        }
        //先删除
        List<NovelChapterInfo> cache = getCategory(novelCategory.get(0).getNovelID());
        if (!cache.isEmpty()) {
            for (NovelChapterInfo t : cache) {
                mCategoryDB.delete(t);
            }
        }

        //再保存
        LongLogUtils.i(TAG, "save cache: " + novelCategory);
        mCategoryDB.insertOrReplace(novelCategory);
        return true;
    }
}
