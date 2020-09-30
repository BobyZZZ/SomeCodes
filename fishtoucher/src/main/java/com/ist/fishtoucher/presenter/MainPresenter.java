package com.ist.fishtoucher.presenter;

import android.util.Log;

import com.ist.fishtoucher.base.BasePresenter;
import com.ist.fishtoucher.contract.MainContract;
import com.ist.fishtoucher.entity.NovelCategory;
import com.ist.fishtoucher.entity.NovelContent;
import com.ist.fishtoucher.iApiService.NovelService;
import com.ist.fishtoucher.model.MainModel;
import com.ist.fishtoucher.utils.RxUtils;
import com.ist.fishtoucher.utils.SPUtils;
import com.ist.fishtoucher.view.MainActivity;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class MainPresenter extends BasePresenter<MainActivity> implements MainContract.IMainPresenter {
    String TAG = getClass().getSimpleName();

    private MainModel mModel;
    private int mCurrentChapterIndex = 0;//当前阅读
    private List<String> mHrefs;


    public MainPresenter() {
        mModel = new MainModel();
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
                    mHrefs = novelCategory.getHrefs();
                    mView.updateCategory(novelCategory);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "getCategory onError: "+e.getMessage() );
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void read(String novelIndex, final int chapterIndex) {
        Log.d(TAG, "read: " + chapterIndex);
//        Observable<ResponseBody> chapterObservable = mModel.getChapter(novelIndex, getChapterUrl(novelIndex, chapterIndex));
        Observable<ResponseBody> chapterObservable = mModel.getChapter(novelIndex, getChapterIndexFromCategory(chapterIndex));
        chapterObservable.compose(RxUtils.<ResponseBody>rxScheduers()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String bodyStr = responseBody.string();
                    NovelContent novelContent = new NovelContent(bodyStr);
                    mView.displayContent(novelContent.getBookName() + "\r\n" + novelContent.getContent(),chapterIndex);
                    mCurrentChapterIndex = chapterIndex;
                    SPUtils.putInt(SPUtils.KEY_LAST_READ,mCurrentChapterIndex);
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
        });
    }

    /**
     * 通过计算得出，不准确
     * @param novelIndex
     * @param chapter
     * @return
     */
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
     * @param chapter
     * @return
     */
    private String getChapterIndexFromCategory(int chapter) {
        String result;
        if (mHrefs != null && !mHrefs.isEmpty()) {
            return mHrefs.get(chapter - 1);
        }
        return null;
    }

    public int getCurrentChapterIndex() {
        return mCurrentChapterIndex;
    }
}
