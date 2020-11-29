package com.ist.fishtoucher.mvp.modle;


import android.text.TextUtils;
import android.util.Log;

import com.ist.fishtoucher.db.DaoHelper;
import com.ist.fishtoucher.db.greenDao.beanManager.NovelDBManager;
import com.ist.fishtoucher.entity.NovelCategory;
import com.ist.fishtoucher.entity.NovelChapterContent;
import com.ist.fishtoucher.entity.NovelChapterInfo;
import com.ist.fishtoucher.mvp.callback.BaseCallback;
import com.ist.fishtoucher.mvp.contract.MainContract;
import com.ist.fishtoucher.iApiService.NovelService;
import com.ist.fishtoucher.mvp.modle.proxy.DynamicProxyInstance;
import com.ist.fishtoucher.utils.LogUtils;
import com.ist.fishtoucher.utils.RetrofitManager;
import com.ist.fishtoucher.utils.RxUtils;


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
                        e.printStackTrace();
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
