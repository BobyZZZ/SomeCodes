package com.bb.reading.ui.mvp.modle.proxy.cacheImpl;

import android.util.Log;

import com.bb.module_novelmanager.db.greenDao.DaoHelper;
import com.bb.module_novelmanager.db.greenDao.impl.NovelDBManager;
import com.bb.module_novelmanager.entity.NovelChapterContent;
import com.bb.module_novelmanager.entity.NovelChapterInfo;
import com.bb.module_noveldetail.callback.BaseCallback;
import com.bb.module_noveldetail.mvp.contract.ReadingActivityContract;

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
