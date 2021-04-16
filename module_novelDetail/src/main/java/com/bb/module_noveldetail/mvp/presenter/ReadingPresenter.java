package com.bb.module_noveldetail.mvp.presenter;

import android.text.TextUtils;

import com.bb.module_common.base.BasePresenter;
import com.bb.module_noveldetail.mvp.view.ReadingActivity;
import com.bb.module_novelmanager.entity.NovelChapterContentFruitBean;
import com.bb.module_common.utils.log.LogUtils;
import com.bb.module_novelmanager.utils.NovelSpUtils;
import com.bb.module_novelmanager.entity.NovelChapterInfo;
import com.bb.module_noveldetail.callback.BaseCallback;
import com.bb.module_noveldetail.mvp.contract.ReadingActivityContract;
import com.bb.module_noveldetail.mvp.modle.ReadingModel;

import java.util.List;

public class ReadingPresenter extends BasePresenter<ReadingActivity> implements ReadingActivityContract.IMainPresenter {
    String TAG = "ReadingPresenter";

    private ReadingModel mModel;
    /**
     * 记录当前已加载到的章节
     */
    private String mChapterIdLoaded;
    private List<NovelChapterInfo> mNovelCategory;
    private boolean mFirstInit = true;//控制初次打开时自动加载

    public ReadingPresenter() {
        mModel = new ReadingModel();
//        mModel = new DynamicProxyInstance<MainContract.IMainModel>().create(MainContract.IMainModel.class,new NovelServiceCacheImpl(),new MainModel());
    }

    @Override
    public void getCategory(final String novelIndex, boolean readCache) {
        mView.loadingStart();
        mModel.getCategory(novelIndex, readCache, new BaseCallback<List<NovelChapterInfo>>() {
            @Override
            public void onSuccess(List<NovelChapterInfo> novelCategory, boolean... fromCache) {
                LogUtils.d(TAG, "getCategory onSuccess() called with: fromCache = [" + fromCache[0] + "]");
                mNovelCategory = novelCategory;
                mView.updateCategory(novelCategory);

                if (mFirstInit) {
                    //首次打开时自动打开加载
                    String id = mView.getChapterID();
                    if (TextUtils.isEmpty(id) && mNovelCategory!= null && !mNovelCategory.isEmpty()) {
                        id = NovelSpUtils.getLastReadChapter(mView.getNovelID(),mNovelCategory.get(0).getChapterId());
                        LogUtils.d(TAG, "打开最后一次阅读的章节或第一章");
                    }
                    read(novelIndex, id);
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
        LogUtils.d(TAG, "read target chapterId: " + chapterID);
        mModel.getChapter(novelID, chapterID, new BaseCallback<NovelChapterContentFruitBean>() {
            @Override
            public void onSuccess(NovelChapterContentFruitBean novelChapterContent, boolean... fromCache) {
                mView.loadingStop();
                //已缓存章节
                mChapterIdLoaded = novelChapterContent.chapterId;
                mView.loadContentSuccessAndToDisplay(novelChapterContent, hasMoreChapter(), resetData);
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

    /**
     * @return true:还有下一章；false：没有下一章了
     */
    public boolean hasMoreChapter() {
        return !isLastChapter(mChapterIdLoaded);
    }

    private boolean isLastChapter(String chapterId) {
        if (mNovelCategory == null || mNovelCategory.isEmpty()) {
            return true;
        }
        String lastChapter = mNovelCategory.get(mNovelCategory.size() - 1).getChapterId();
        return TextUtils.equals(lastChapter,chapterId);
    }

    public void saveCurrentReading(NovelChapterContentFruitBean novelChapterContent, boolean resetReadingPosition) {
        NovelSpUtils.saveLastReadChapter(novelChapterContent.novelId, novelChapterContent.chapterId);
        if (resetReadingPosition) {
            NovelSpUtils.saveLastReadingPosition(novelChapterContent.novelId, 0);
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
