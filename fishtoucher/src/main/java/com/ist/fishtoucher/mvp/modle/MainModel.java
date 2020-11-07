package com.ist.fishtoucher.mvp.modle;


import android.util.Log;

import com.ist.fishtoucher.entity.NovelCategory;
import com.ist.fishtoucher.entity.NovelChapterInfo;
import com.ist.fishtoucher.mvp.callback.BaseCallback;
import com.ist.fishtoucher.mvp.contract.MainContract;
import com.ist.fishtoucher.iApiService.NovelService;
import com.ist.fishtoucher.utils.RetrofitManager;
import com.ist.fishtoucher.utils.RxUtils;


import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class MainModel implements MainContract.IMainModel {
    String TAG = "MainModel";
    private NovelService mNovelService;

    public MainModel() {
        mNovelService = RetrofitManager.getInstance().createRs(NovelService.class);
    }

    @Override
    public void getCategory(String novelIndex, final BaseCallback baseCallback) {
        mNovelService.getCategory(novelIndex)
        .compose(RxUtils.<ResponseBody>rxScheduers()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String body = responseBody.string();
                    NovelCategory novelCategory = new NovelCategory(body);
                    baseCallback.onSuccess(novelCategory);
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
    public void getChapter(String novelIndex, String chapterIndex, final BaseCallback baseCallback) {
        mNovelService.getChapter(novelIndex,chapterIndex)
                .compose(RxUtils.<ResponseBody>rxScheduers())
                .subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String bodyStr = responseBody.string();
                    NovelChapterInfo novelChapterInfo = new NovelChapterInfo(bodyStr);
                    baseCallback.onSuccess(novelChapterInfo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "read onError: " + e.getMessage());
                baseCallback.onError(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void test2(final BaseCallback callback2) {
        mNovelService.getCategory(NovelService.JIAN_LAI_NOVEL_INDEX)
                .compose(RxUtils.<ResponseBody>rxScheduers())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        try {
                            String body = responseBody.string();
                            NovelCategory novelCategory = new NovelCategory(body);
                            List<NovelCategory.Chapter> mChapters = novelCategory.getChapters();

                            callback2.onSuccess(mChapters);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
