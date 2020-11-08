package com.ist.fishtoucher.db.greenDao.beanManager;

import com.ist.fishtoucher.entity.DaoSession;
import com.ist.fishtoucher.entity.NovelCategory;
import com.ist.fishtoucher.iApiService.NovelService;
import com.ist.fishtoucher.utils.LogUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/8
 * Time: 0:20
 */
public class NovelDBManager implements NovelService {
    String TAG = "NovelDBManager";

    private static NovelDBManager sInstance;
    public final NovelCategoryDBManager mNovelCategoryDBManager;

    private NovelDBManager(DaoSession dao) {
        mNovelCategoryDBManager = new NovelCategoryDBManager(dao.getNovelCategoryDao());
    }

    public static NovelDBManager getInstance(DaoSession dao) {
        if (sInstance == null) {
            synchronized (NovelDBManager.class) {
                sInstance = new NovelDBManager(dao);
            }
        }
        return sInstance;
    }

    @Override
    public Observable<ResponseBody> getChapter(String novel_index, String chapter_href) {
        return Observable.empty()
                .observeOn(Schedulers.io())
                .map(new Function<Object, ResponseBody>() {
                    @Override
                    public ResponseBody apply(Object o) throws Exception {
                        LogUtils.d(TAG, "getChapter in thread: " + Thread.currentThread().getName());
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ResponseBody> getCategory(String novelIndex) {
/*        return Observable.just(novelIndex)
                .observeOn(Schedulers.io())
                .map(new Function<String, ResponseBody>() {
                    @Override
                    public ResponseBody apply(String string) throws Exception {
                        NovelCategory cache = mNovelCategoryDao.load(string);
                        LogUtils.d(TAG,"get " + string + "'s Category in thread: " + Thread.currentThread().getName()
                        + "\n cache: " + cache);
                        if (cache != null) {
                            ResponseBody responseBodyString = ResponseBody.create(MediaType.parse("text"), cache.getResponseBody());
                            return responseBodyString;
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());*/

        NovelCategory cache = mNovelCategoryDBManager.load(novelIndex);
        LogUtils.d(TAG, "get " + novelIndex + "'s Category in thread: " + Thread.currentThread().getName()
                + ", have cache: " + (cache != null));
        if (cache != null) {
            ResponseBody responseBodyString = ResponseBody.create(MediaType.parse("from_db"), cache.getResponseBody());
            Observable<ResponseBody> bodyObservable = Observable.just(responseBodyString);
            return bodyObservable;
        }
        return null;
    }

    public void saveCategory(NovelCategory novelCategory) {
        LogUtils.d(TAG, "save cache: novelCategory");
        mNovelCategoryDBManager.insertOrReplace(novelCategory);
        LogUtils.w(TAG, "after saveCategory: " + mNovelCategoryDBManager.loadAll());

    }
}
