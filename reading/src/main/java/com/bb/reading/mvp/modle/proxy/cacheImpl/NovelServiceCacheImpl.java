package com.bb.reading.mvp.modle.proxy.cacheImpl;

import android.util.Log;

import com.bb.reading.db.DaoHelper;
import com.bb.reading.db.greenDao.beanManager.NovelDBManager;
import com.bb.reading.entity.NovelChapterContent;
import com.bb.reading.entity.NovelChapterInfo;
import com.bb.reading.mvp.callback.BaseCallback;
import com.bb.reading.mvp.contract.ReadingActivityContract;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/1
 * Time: 22:45
 */
public class NovelServiceCacheImpl implements ReadingActivityContract.IMainModel<NovelChapterContent, List<NovelChapterInfo>> {
    String TAG = "NovelServiceCacheImpl";
    private NovelDBManager mNovelDBManager;

    public NovelServiceCacheImpl() {
        mNovelDBManager = DaoHelper.getInstance().getNovelDBManager();
    }

    @Override
    public void getChapter(String novelIndex, String chapterIndex, BaseCallback<NovelChapterContent> baseCallback) {
        Log.d(TAG, "getChapter() called with: novelIndex = [" + novelIndex + "], chapterIndex = [" + chapterIndex + "], baseCallback = [" + baseCallback + "]");

    }

    @Override
    public void getCategory(String novelIndex, boolean readFromCache, BaseCallback<List<NovelChapterInfo>> baseCallback) {
        Log.d(TAG, "getCategory() called with: novelIndex = [" + novelIndex + "], readFromCache = [" + readFromCache + "], baseCallback = [" + baseCallback + "]");

    }
}
