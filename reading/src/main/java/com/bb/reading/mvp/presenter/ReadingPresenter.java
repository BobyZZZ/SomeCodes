package com.bb.reading.mvp.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.bb.reading.base.BasePresenter;
import com.bb.reading.utils.log.LogUtils;
import com.bb.reading.utils.NovelSpUtils;
import com.bb.reading.entity.NovelChapterInfo;
import com.bb.reading.mvp.callback.BaseCallback;
import com.bb.reading.mvp.contract.ReadingActivityContract;
import com.bb.reading.entity.NovelChapterContent;
import com.bb.reading.mvp.modle.ReadingModel;
import com.bb.reading.mvp.view.activity.ReadingActivity;

import java.util.List;

public class ReadingPresenter extends BasePresenter<ReadingActivity> implements ReadingActivityContract.IMainPresenter {
    String TAG = "ReadingPresenter";

    private ReadingModel mModel;
    /**
     * 记录当前已加载到的章节
     */
    private String mChapterIdLoaded;
    private List<NovelChapterInfo> mChapters;
    private boolean mFirstInit = true;//控制初次打开时自动加载

    public ReadingPresenter() {
        mModel = new ReadingModel();
//        mModel = new DynamicProxyInstance<MainContract.IMainModel>().create(MainContract.IMainModel.class,new NovelServiceCacheImpl(),new MainModel());
    }

    @Override
    public void getCategory(String novelIndex, boolean readCache) {
        mView.loadingStart();
        mModel.getCategory(novelIndex, readCache, new BaseCallback<List<NovelChapterInfo>>() {
            @Override
            public void onSuccess(List<NovelChapterInfo> novelCategory, boolean... fromCache) {
                Log.d(TAG, "getCategory onSuccess() called with: fromCache = [" + fromCache[0] + "]");
                mChapters = novelCategory;
                mView.updateCategory(novelCategory);

                if (mFirstInit) {//首次打开时自动打开上次观看章节
                    read(novelIndex, mView.getChapterID());
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e);
            }
        });
    }

    /**
     * @param novelID   哪本小说
     * @param chapterID 该章节的href
     */
    @Override
    public void read(String novelID, String chapterID) {
        read(novelID, chapterID, false);
    }

    /**
     * @param novelID   哪本小说
     * @param chapterID 该章节的href
     * @param resetData 是否需要重新设置数据
     */
    public void read(final String novelID, final String chapterID, final boolean resetData) {
        LogUtils.d(TAG, "read: " + chapterID);
        mModel.getChapter(novelID, chapterID, new BaseCallback<NovelChapterContent>() {
            @Override
            public void onSuccess(NovelChapterContent novelChapterContent, boolean... fromCache) {
                mView.loadingStop();
                //已缓存章节
                mChapterIdLoaded = novelChapterContent.getChapterId();
                mView.loadContentSuccessAndToDisplay(novelChapterContent, novelChapterContent.getChapterNumber(), resetData);
                if (mFirstInit) {//初次加载时，滚到上次阅读的位置
                    mFirstInit = false;
                }
                //和最后阅读的章节相同时才恢复到上次阅读的位置
                if (TextUtils.equals(NovelSpUtils.getLastReadChapter(novelID), chapterID)) {
                    mView.recoverLastReadingState(novelChapterContent);
                }
                NovelSpUtils.saveLastReadNovel(novelID);//记录最后阅读哪本小说
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e);
            }
        });
    }

    @Override
    public void loadMore() {
        read(mView.getNovelID(), getNextLoadChapterId());
    }

    /**
     * 先获取目录，然后从目录中获取准确的地址进行访问
     *
     * @param chapter
     * @return
     */
    private String getChapterIndexFromCategory(int chapter) {
        if (mChapters != null && !mChapters.isEmpty() && chapter > 0 && chapter < mChapters.size()) {
            return mChapters.get(chapter - 1).getChapterId();
        }
        return null;
    }

    /**
     * 获取下一章要加载的章节id
     *
     * @return 章节id
     */
    private String getNextLoadChapterId() {
        String nextChapterId = null;
        NovelChapterInfo nextLoadChapter = mView.getChapterInfoWithOffset(mChapterIdLoaded, 1);
        if (nextLoadChapter != null) {
            nextChapterId = nextLoadChapter.getChapterId();
        }
        LogUtils.d(TAG, "auto loadMore chapter: " + nextChapterId);
        return nextChapterId;
    }

    public void saveCurrentReading(NovelChapterContent novelChapterContent) {
        saveCurrentReading(novelChapterContent, false);
    }

    public void saveCurrentReading(NovelChapterContent novelChapterContent, boolean resetReadingPosition) {
        NovelSpUtils.saveLastReadChapter(novelChapterContent.getNovelId(), novelChapterContent.getChapterId());
        if (resetReadingPosition) {
            NovelSpUtils.saveLastReadingPosition(novelChapterContent.getNovelId(), 0);
        }
    }

    public void mytest2() {
        String text = "";
        BaseCallback baseCallback = new BaseCallback() {
            @Override
            public void onSuccess(Object data, boolean... fromCache) {
                List<NovelChapterInfo> changeData = (List<NovelChapterInfo>) data;
                for (NovelChapterInfo chapter : changeData) {
                }
                LogUtils.i("zhouyc mytest2: ", changeData.toString());
                LogUtils.e("zhouyc mytest2: ", "==============================================");
            }
        };
        mModel.test2(baseCallback);
    }

    @Override
    public void detachView() {
        mModel.onDestroy();
        super.detachView();
    }
}
