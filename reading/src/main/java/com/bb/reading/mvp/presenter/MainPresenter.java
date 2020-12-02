package com.bb.reading.mvp.presenter;

import com.bb.reading.base.BasePresenter;
import com.bb.reading.utils.LogUtils;
import com.bb.reading.utils.NovelUtils;
import com.bb.reading.entity.NovelChapterInfo;
import com.bb.reading.mvp.callback.BaseCallback;
import com.bb.reading.mvp.contract.MainContract;
import com.bb.reading.entity.NovelChapterContent;
import com.bb.reading.mvp.modle.MainModel;
import com.bb.reading.mvp.view.MainActivity;

import java.util.List;

public class MainPresenter extends BasePresenter<MainActivity> implements MainContract.IMainPresenter {
    String TAG = getClass().getSimpleName();

    private MainModel mModel;
    private String mNovelID;
    /**
     * 记录当前已加载到的章节
     */
    private String mChapterIdLoaded;
    private List<NovelChapterInfo> mChapters;
    private boolean mFirstInit = true;//控制初次打开时自动加载

    public MainPresenter(String novelID) {
        this.mNovelID = novelID;
        LogUtils.d(TAG, "MainPresenter mNovelID: " + mNovelID);
        mModel = new MainModel();
    }

    @Override
    public void getCategory(String novelIndex) {
        mView.loading();
        mModel.getCategory(novelIndex, new BaseCallback() {
            @Override
            public <T> void onSuccess(T data) {
                List<NovelChapterInfo> novelCategory = (List<NovelChapterInfo>) data;
                mChapters = novelCategory;
                mView.updateCategory(novelCategory);

                if (mFirstInit) {//首次打开时自动打开上次观看章节
                    read(mNovelID, NovelUtils.getLastReadChapter(mNovelID,novelCategory.get(0).getChapterId()));
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e);
            }
        });
    }

    public void read(String novelID, final int chapterNumber, boolean resetData) {
        read(novelID, getChapterIndexFromCategory(chapterNumber), resetData);
    }

    public void read(String novelID, final int chapterNumber) {
        read(novelID, getChapterIndexFromCategory(chapterNumber), false);
    }

    /**
     * @param novelID   哪本小说
     * @param chapterID 该章节的href
     */
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
        mModel.getChapter(novelID, chapterID, new BaseCallback() {
            @Override
            public <T> void onSuccess(T data) {
                mView.hideLoading();
                NovelChapterContent novelChapterContent = (NovelChapterContent) data;
                //已缓存章节
                mChapterIdLoaded = novelChapterContent.getChapterId();
                mView.loadContentSuccessAndToDisplay(novelChapterContent, novelChapterContent.getChapterNumber(), resetData);
                if (mFirstInit) {//初次加载时，滚到上次阅读的位置
                    mFirstInit = false;
                    mView.recoverLastReadingState(novelChapterContent);
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e);
            }
        });
    }

    @Override
    public void loadMore() {
        read(mNovelID, getNextLoadChapterId());
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
     * @return  章节id
     */
    private String getNextLoadChapterId() {
        String nextChapterId = null;
        NovelChapterInfo nextLoadChapter = mView.getChapterInfoWithOffset(mChapterIdLoaded, 1);
        if (nextLoadChapter != null) {
            nextChapterId =  nextLoadChapter.getChapterId();
        }
        LogUtils.d(TAG, "auto loadMore chapter: " + nextChapterId);
        return nextChapterId;
    }

    public void saveCurrentReading(NovelChapterContent novelChapterContent) {
        saveCurrentReading(novelChapterContent,false);
    }
    public void saveCurrentReading(NovelChapterContent novelChapterContent,boolean resetReadingPosition) {
        NovelUtils.saveLastReadChapter(novelChapterContent.getNovelId(),novelChapterContent.getChapterId());
        if (resetReadingPosition) {
            NovelUtils.saveLastReadingPosition(novelChapterContent.getNovelId(),0);
        }
    }

    public void mytest2() {
        String text = "";
        BaseCallback baseCallback = new BaseCallback() {
            @Override
            public <T> void onSuccess(T data) {
                List<NovelChapterInfo> changeData = (List<NovelChapterInfo>) data;
                for (NovelChapterInfo chapter : changeData) {
                }
                LogUtils.i("zhouyc mytest2: ", changeData.toString());
                LogUtils.e("zhouyc mytest2: ", "==============================================");
            }
        };
        mModel.test2(baseCallback);
    }


}
