package com.bb.module_novelmanager.db.greenDao.impl;
import com.bb.module_common.utils.log.LogUtils;
import com.bb.module_novelmanager.entity.DaoSession;
import com.bb.module_novelmanager.entity.NovelChapterInfo;
import com.bb.module_novelmanager.entity.NovelChapterInfoDao;
import com.bb.module_novelmanager.entity.NovelDetails;
import com.bb.module_novelmanager.entity.SearchResult;
import com.bb.module_novelmanager.network.NovelService;
import com.bb.module_novelmanager.network.RetrofitManager;
import com.bb.module_novelmanager.network.api.NovelServiceBQGApi;
import com.bb.network.exceptionHandler.ResponseErrorHandler;
import com.bb.network.observer.BaseObserver;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


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
    private final NovelService novelService;

    private NovelDBManager(DaoSession dao) {
        mCategoryDB = new CategoryDB(dao.getNovelChapterInfoDao());
        mLikedNovelDB = new LikedNovelDB(dao.getNovelDetailsDao());
        novelService = NovelServiceBQGApi.getInstance().getService(NovelService.class);
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
    public void saveLikedNovel(final String novelId, final Runnable... callback) {
        novelService.getNovelDetails(novelId)
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new ResponseErrorHandler<NovelDetails>())
                .map(new Function<NovelDetails,NovelDetails>() {
                    @Override
                    public NovelDetails apply(NovelDetails novelDetails) throws Exception {
                        novelDetails.novelId = novelId;
                        saveLikedNovel(novelDetails);
                        return novelDetails;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NovelDetails>() {
                    @Override
                    protected void onSuccess(NovelDetails novelDetails) {
                        if (callback[0] != null) {
                            callback[0].run();
                        }
                    }

                    @Override
                    protected void onFail(Throwable e) {
                    }
                });

    }

    /**
     * 更新小说（根据@Id）
     * @param novelDetails
     */
    public void updateLikedNovel(NovelDetails novelDetails) {
        Observable.just(novelDetails)
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<NovelDetails>() {
                    @Override
                    protected void onSuccess(NovelDetails novelDetails) {
                        mLikedNovelDB.update(novelDetails);
                    }

                    @Override
                    protected void onFail(Throwable e) {

                    }
                });
    }
    /**
     * 更新小说（根据@Id）
     * @param novelDetails
     */
    public void updateLikedNovels(List<NovelDetails> novelDetails) {
        Observable.just(novelDetails)
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<List<NovelDetails>>() {
                    @Override
                    protected void onSuccess(List<NovelDetails> novelDetails) {
                        mLikedNovelDB.update(novelDetails);
                    }

                    @Override
                    protected void onFail(Throwable e) {

                    }
                });
    }

    /**
     * 收藏小说
     */
    public long saveLikedNovel(NovelDetails novelDetails) {
        long insertOrReplace = mLikedNovelDB.insertOrReplace(novelDetails);
        LogUtils.d(TAG, "saveLikedNovel() called : " + insertOrReplace);
        return insertOrReplace;
    }

    public boolean deleteLikedNovel(String novelId) {
        NovelDetails load = mLikedNovelDB.load(novelId);
        if (load == null) {
            return false;
        }
        mLikedNovelDB.delete(load);
        return true;
    }

    /**
     * 取消收藏小说
     */
    public boolean deleteLikedNovel(SearchResult.Item item) {
        return deleteLikedNovel(item.novelId);
    }

    public boolean isAlreadyLiked(String novelId) {
        NovelDetails load = mLikedNovelDB.load(novelId);
        return load != null;
    }
}
