package com.ist.fishtoucher.mvp.presenter;

import android.util.Log;

import com.ist.fishtoucher.base.BasePresenter;
import com.ist.fishtoucher.mvp.contract.MainContract;
import com.ist.fishtoucher.entity.NovelCategory;
import com.ist.fishtoucher.entity.NovelChapterInfo;
import com.ist.fishtoucher.iApiService.NovelService;
import com.ist.fishtoucher.mvp.modle.MainModel;
import com.ist.fishtoucher.utils.LogUtils;
import com.ist.fishtoucher.utils.RxUtils;
import com.ist.fishtoucher.utils.SPUtils;
import com.ist.fishtoucher.mvp.view.MainActivity;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class MainPresenter extends BasePresenter<MainActivity> implements MainContract.IMainPresenter {
    String TAG = getClass().getSimpleName();

    private MainModel mModel;
    private String mNovelID;
    private int mChapterNumberLoaded = 0;//当前以缓存到第几章
    private int mCurrentReading = 0;//当前正在阅读的章节
    private List<NovelCategory.Chapter> mChapters;
    private Observer<ResponseBody> mNovelContentObserver;

    public MainPresenter(String novelID) {
        this.mNovelID = novelID;
        LogUtils.d(TAG, "MainPresenter mNovelID: " + mNovelID);
        mModel = new MainModel();
        mNovelContentObserver = new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String bodyStr = responseBody.string();
                    NovelChapterInfo novelChapterInfo = new NovelChapterInfo(bodyStr);
                    //已缓存章节
                    mChapterNumberLoaded = novelChapterInfo.getChapterNumber();
                    mView.loadContentSuccessAndToDisplay(novelChapterInfo, novelChapterInfo.getChapterNumber(), false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "read onError: " + e.getMessage());
                mView.onError(e);
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void getCategory(String novelIndex) {
        Observable<ResponseBody> category = mModel.getCategory(novelIndex);
        category.compose(RxUtils.<ResponseBody>rxScheduers()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String body = responseBody.string();
                    NovelCategory novelCategory = new NovelCategory(body);
                    mChapters = novelCategory.getChapters();
                    mView.updateCategory(novelCategory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void read(String novelID, final int chapterNumber,boolean resetData) {
        read(novelID, getChapterIndexFromCategory(chapterNumber),resetData);
    }

    public void read(String novelID, final int chapterNumber) {
        read(novelID, getChapterIndexFromCategory(chapterNumber), false);
    }

    /**
     * @param novelID    哪本小说
     * @param chapterID  该章节的href
     */
    public void read(String novelID, String chapterID) {
        read(novelID,chapterID,false);
    }

    /**
     * @param novelID    哪本小说
     * @param chapterID  该章节的href
     * @param resetData  是否需要重新设置数据
     */
    public void read(final String novelID, final String chapterID, final boolean resetData) {
        mView.loading();
        LogUtils.d(TAG, "read: " + chapterID);

        Observer<ResponseBody> observer = new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String bodyStr = responseBody.string();
                    NovelChapterInfo novelChapterInfo = new NovelChapterInfo(bodyStr);
                    //已缓存章节
                    mChapterNumberLoaded = novelChapterInfo.getChapterNumber();
                    mView.loadContentSuccessAndToDisplay(novelChapterInfo, novelChapterInfo.getChapterNumber(),resetData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "read onError: " + e.getMessage());
                mView.onError(e);
            }

            @Override
            public void onComplete() {

            }
        };
        Observable<ResponseBody> chapterObservable = mModel.getChapter(novelID, chapterID);
        chapterObservable.compose(RxUtils.<ResponseBody>rxScheduers()).subscribe(observer);
    }

    @Override
    public void loadMore() {
        LogUtils.d(TAG, "auto loadMore chapter: " + (getChapterNumberHaveLoaded() + 1) + "章");
        read(mNovelID, getChapterNumberHaveLoaded() + 1);
    }

    private int findChapterNumber(String chapterIndex) {
        if (mChapters != null && !mChapters.isEmpty()) {
            for (int i = 0; i < mChapters.size(); i++) {
                if (mChapters.get(i).getUrl().equals(chapterIndex)) {
                    return i + 1;
                }
            }
        }
        return -1;
    }

    @Deprecated
    private long getChapterUrl(String novelIndex, int chapter) {
        long result = NovelService.DIYI_XULIE_FIRST_CHAPTER_INDEX + chapter - 1;
        switch (novelIndex) {
            case NovelService.DIYI_XULIE_NOVEL_INDEX:
                result = NovelService.DIYI_XULIE_FIRST_CHAPTER_INDEX + (chapter - 1) * 2;
                break;
            case NovelService.SIYANG_HUMAN_NOVEL_INDEX:
                result = NovelService.SIYANG_HUMAN_FIRST_CHAPTER_INDEX + chapter - 1;
                break;
        }
        return result;
    }

    /**
     * 先获取目录，然后从目录中获取准确的地址进行访问
     *
     * @param chapter
     * @return
     */
    private String getChapterIndexFromCategory(int chapter) {
        if (mChapters != null && !mChapters.isEmpty()) {
            return mChapters.get(chapter - 1).getUrl();
        }
        return null;
    }

    public int getChapterNumberHaveLoaded() {
        return mChapterNumberLoaded;
    }

    public int getCurrentReading() {
        return mCurrentReading;
    }

    public void setCurrentReading(int currentReading) {
        this.mCurrentReading = currentReading;
        SPUtils.putInt(SPUtils.KEY_LAST_READ, currentReading);
    }
}
