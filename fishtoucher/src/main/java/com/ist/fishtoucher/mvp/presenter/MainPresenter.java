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
    private int mCurrentChapterNumber = 0;//当前阅读第几章
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
//                    mView.displayContent(novelChapterInfo.getBookName() + "\r\n" + novelChapterInfo.getContent(), mCurrentChapterNumber);
                    //当前章节
                    mCurrentChapterNumber = novelChapterInfo.getChapterNumber();
                    mView.displayContent(novelChapterInfo, mCurrentChapterNumber);
                    SPUtils.putInt(SPUtils.KEY_LAST_READ, mCurrentChapterNumber);
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

    @Override
    public void read(String novelIndex, final int chapterNumber) {
//        Observable<ResponseBody> chapterObservable = mModel.getChapter(novelIndex, getChapterUrl(novelIndex, chapterIndex));
        mView.loading();
        read(novelIndex, getChapterIndexFromCategory(chapterNumber), chapterNumber);
    }

    /**
     * @param novelIndex    哪本小说
     * @param chapterIndex  该章节的href
     * @param chapterNumber 第几章，不确定时传-1；
     */
    public void read(String novelIndex, String chapterIndex, int chapterNumber) {
        LogUtils.d(TAG, "read: " + chapterIndex + ",chapterNumber: " + chapterNumber);
/*        int currentChapterNumber = chapterNumber;
        if(chapterNumber == -1 && (currentChapterNumber = findChapterNumber(chapterIndex)) != -1) {
            mCurrentChapterNumber = currentChapterNumber;
        } else {
            mCurrentChapterNumber = currentChapterNumber;
        }*/
        Observable<ResponseBody> chapterObservable = mModel.getChapter(novelIndex, chapterIndex);
        chapterObservable.compose(RxUtils.<ResponseBody>rxScheduers()).subscribe(mNovelContentObserver);
    }

    @Override
    public void loadMore() {
        LogUtils.d(TAG, "auto loadMore chapter: " + (getCurrentChapterNumber() + 1) + "章");
        read(mNovelID, getCurrentChapterNumber() + 1);
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

    public int getCurrentChapterNumber() {
        return mCurrentChapterNumber;
    }
}
