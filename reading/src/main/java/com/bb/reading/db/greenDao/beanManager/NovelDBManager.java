package com.bb.reading.db.greenDao.beanManager;

import android.util.Log;

import com.bb.reading.entity.DaoSession;
import com.bb.reading.entity.NovelChapterInfo;
import com.bb.reading.entity.NovelChapterInfoDao;
import com.bb.reading.entity.NovelDetails;
import com.bb.reading.entity.SearchResult;
import com.bb.reading.utils.log.LogUtils;

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
    public LikedNovelDB mLikedNovelDB;

    private NovelDBManager(DaoSession dao) {
        mCategoryDB = new CategoryDB(dao.getNovelChapterInfoDao());
        mLikedNovelDB = new LikedNovelDB(dao.getNovelDetailsDao());
    }

    public static NovelDBManager getInstance(DaoSession dao) {
        if (sInstance == null) {
            synchronized (NovelDBManager.class) {
                sInstance = new NovelDBManager(dao);
            }
        }
        return sInstance;
    }

    /**
     * 根据小说id获取目录
     *
     * @param novelIndex 某小说
     * @return
     */
    public List<NovelChapterInfo> getCategory(String novelIndex) {
        List<NovelChapterInfo> cache = mCategoryDB.query(NovelChapterInfoDao.Properties.NovelID.eq(novelIndex));
/*        LongLogUtils.i(TAG, "get " + novelIndex + "'s Category cache in thread: " + Thread.currentThread().getName()
                + ", have cache: " + cache);*/
        return cache;
    }

    /**
     * 保存小说目录
     *
     * @param novelCategory
     * @return
     */
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
//        LongLogUtils.i(TAG, "save cache: " + novelCategory);
        mCategoryDB.insertOrReplace(novelCategory);
        return true;
    }

    /**
     * 获取所有本地收藏小说
     */
    public List<NovelDetails> getAllLikedNovel() {
        List<NovelDetails> novelDetails = mLikedNovelDB.loadAll();
        LogUtils.d(TAG, "getAllLikedNovel() called");
        return novelDetails;
    }

    /**
     * 收藏小说
     */
    public long saveLikedNovel(NovelDetails novelDetails) {
        long insertOrReplace = mLikedNovelDB.insertOrReplace(novelDetails);
        LogUtils.d(TAG, "saveLikedNovel() called : " + insertOrReplace);
        return insertOrReplace;
    }

    /**
     * 收藏小说
     */
    public boolean saveLikedNovel(SearchResult.Item item) {
        NovelDetails novelDetails = new NovelDetails();
        novelDetails.setNovelId(item.novelId);
        novelDetails.setName(item.getName());
        long insertOrReplace = mLikedNovelDB.insertOrReplace(novelDetails);
        LogUtils.d(TAG, "saveLikedNovel() called : " + insertOrReplace);
        return insertOrReplace > 0;
    }

    /**
     * 取消收藏小说
     */
    public boolean deleteLikedNovel(SearchResult.Item item) {
        NovelDetails load = mLikedNovelDB.load(item.novelId);
        if (load == null) {
            return false;
        }
        mLikedNovelDB.delete(load);
        return true;
    }

    public boolean isAlreadyLiked(String novelId) {
        NovelDetails load = mLikedNovelDB.load(novelId);
        return load != null;
    }
}
