package com.bb.reading.mvp.modle;


import android.text.TextUtils;
import android.util.Log;

import com.bb.reading.db.greenDao.beanManager.NovelDBManager;
import com.bb.reading.mvp.callback.BaseCallback;
import com.bb.reading.mvp.contract.MainContract;
import com.bb.reading.mvp.modle.proxy.DynamicProxyInstance;
import com.bb.reading.utils.LogUtils;
import com.bb.reading.utils.RetrofitManager;
import com.bb.reading.utils.RxUtils;
import com.bb.reading.db.DaoHelper;
import com.bb.reading.entity.NovelCategory;
import com.bb.reading.entity.NovelChapterContent;
import com.bb.reading.entity.NovelChapterInfo;
import com.bb.reading.iApiService.NovelService;


import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.ResponseBody;

public class MainModel implements MainContract.IMainModel {
    String TAG = "MainModel";
    private NovelService mNovelServiceReal;
    private final NovelDBManager mNovelDBManager;
    private DynamicProxyInstance mProxyInstance;

    public MainModel() {
        mNovelServiceReal = RetrofitManager.getInstance().createRs(NovelService.class);
        mNovelDBManager = DaoHelper.getInstance().getNovelDBManager();

        mProxyInstance = new DynamicProxyInstance();
//        mProxyInstance.setDBManager(mNovelDBManager);//是否保存数据
    }

    @Override
    public void getCategory(final String novelIndex, final BaseCallback baseCallback) {
        mProxyInstance.create(NovelService.class, mNovelServiceReal)
                .getCategory(novelIndex)
                .compose(RxUtils.<ResponseBody>rxScheduers())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            MediaType mediaType = responseBody.contentType();
                            String type = "";
                            if (mediaType != null) {
                                type = mediaType.type();
                            }
                            String body = responseBody.string();
                            List<NovelChapterInfo> novelCategory = NovelCategory.parse(novelIndex, body);
                            LogUtils.e(TAG, "getCategory: " + novelCategory);
                            if (novelCategory == null || novelCategory.isEmpty()) {
                                onError(new Exception("novelCategory is null"));
                                return;
                            }
                            baseCallback.onSuccess(novelCategory);
                            if (!TextUtils.isEmpty(type)) {
                                mNovelDBManager.saveCategory(new NovelCategory(novelIndex, body));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseCallback.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getChapter(final String novelIndex, final String chapterIndex, final BaseCallback baseCallback) {
        mNovelServiceReal.getChapter(novelIndex, chapterIndex)
                .compose(RxUtils.<ResponseBody>rxScheduers())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String bodyStr = responseBody.string();
                            NovelChapterContent novelChapterContent = new NovelChapterContent(novelIndex,chapterIndex,bodyStr);
                            baseCallback.onSuccess(novelChapterContent);
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
        mNovelServiceReal.getCategory(NovelService.JIAN_LAI_NOVEL_INDEX)
                .compose(RxUtils.<ResponseBody>rxScheduers())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        try {
                            String body = responseBody.string();
                            List<NovelChapterInfo> novelCategory = NovelCategory.parse(NovelService.JIAN_LAI_NOVEL_INDEX,body);

                            callback2.onSuccess(novelCategory);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
