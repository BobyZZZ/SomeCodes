package com.bb.module_noveldetail.mvp.modle;

import android.util.Log;

import com.bb.module_novelmanager.entity.NovelCategory;
import com.bb.network.exceptionHandler.ExceptionHandler;
import com.bb.network.exceptionHandler.ResponseErrorHandler;
import com.bb.network.observer.BaseObserver;
import com.bb.module_novelmanager.db.greenDao.impl.NovelDBManager;
import com.bb.module_novelmanager.entity.NovelChapterContentFruitBean;
import com.bb.module_noveldetail.callback.BaseCallback;
import com.bb.module_noveldetail.mvp.contract.ReadingActivityContract;
import com.bb.module_common.utils.log.LogUtils;
import com.bb.module_novelmanager.network.RetrofitManager;
import com.bb.module_common.utils.RxUtils;
import com.bb.module_novelmanager.db.greenDao.DaoHelper;
import com.bb.module_novelmanager.entity.NovelChapterInfo;
import com.bb.module_novelmanager.network.NovelService;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ReadingModel implements ReadingActivityContract.IMainModel<NovelChapterContentFruitBean,List<NovelChapterInfo>> {
    String TAG = "MainModel";
    private NovelService mNovelServiceReal;
    private final NovelDBManager mNovelDBManager;
    private Map<Observable,Disposable> mDisposableList = new HashMap<>();

    public ReadingModel() {
        mNovelDBManager = DaoHelper.getInstance().getNovelDBManager();
        mNovelServiceReal = RetrofitManager.getInstance().createRs(NovelService.class);
    }

    public void onDestroy() {
        Log.d(TAG, "zhouyc onDestroy() called: " + mDisposableList.size());
        Iterator<Disposable> iterator = mDisposableList.values().iterator();
        while (iterator.hasNext()) {
            Disposable next = iterator.next();
            next.dispose();
            iterator.remove();
        }
    }

    @Override
    public void getChapter(final String novelIndex, final String chapterIndex, final BaseCallback<NovelChapterContentFruitBean> baseCallback) {
/*        mNovelServiceReal.getChapter(*//*novelIndex, *//*chapterIndex)
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
                        LogUtils.e(TAG, "read onError: " + e.getMessage());
                        baseCallback.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
        String url = novelIndex + chapterIndex;
        mNovelServiceReal.getNovelChapterDetails(url)
                .compose(RxUtils.<NovelChapterContentFruitBean>rxScheduers())
                .onErrorResumeNext(RxUtils.<NovelChapterContentFruitBean>handleError())
                .subscribe(new BaseObserver<NovelChapterContentFruitBean>() {
                    @Override
                    protected void onSuccess(NovelChapterContentFruitBean novelChapterContentFruitBean) {
                        novelChapterContentFruitBean.novelId = novelIndex;
                        novelChapterContentFruitBean.chapterId = chapterIndex;

                        novelChapterContentFruitBean.content = novelChapterContentFruitBean.content.replaceAll("\\s{5}","\n\t\t\t\t");
                        baseCallback.onSuccess(novelChapterContentFruitBean);
                    }

                    @Override
                    protected void onFail(Throwable e) {
                        LogUtils.e(TAG, "read onError: " + e.getMessage());
                        baseCallback.onError(e);
                    }
                });
    }

    @Override
    public void getCategory(final String novelIndex, final boolean readFromCache, final BaseCallback<List<NovelChapterInfo>> baseCallback) {
        final Observable<List<NovelChapterInfo>> serverObservable = mNovelServiceReal
                .getCategory(novelIndex)
                .map(new Function<ResponseBody, List<NovelChapterInfo>>() {
                    @Override
                    public List<NovelChapterInfo> apply(ResponseBody responseBody) throws Exception {
                        //解析服务器数据
                        String body = responseBody.string();
                        List<NovelChapterInfo> novelCategory = NovelCategory.parse(novelIndex, body);

                        LogUtils.d(TAG,"get " + novelIndex + "'s Category server data in thread: " + Thread.currentThread().getName()
                                + "server data 's length is : " + novelCategory.size());
                        return novelCategory;
                    }
                })
                .doOnNext(new Consumer<List<NovelChapterInfo>>() {
                    @Override
                    public void accept(List<NovelChapterInfo> novelChapterInfos) throws Exception {
                        //保存数据库
                        boolean saveCategoryCacheResult = mNovelDBManager.saveCategory(novelChapterInfos);
                        LogUtils.d(TAG, "doOnNext saveCategory " + novelIndex + " " + (saveCategoryCacheResult ? "success" : "fail") + " in thread: "
                                + Thread.currentThread().getName());
                    }
                })
                .onErrorResumeNext(new ResponseErrorHandler());

        Observable<List<NovelChapterInfo>> cacheObservable = Observable.just(novelIndex)
                .map(new Function<String, List<NovelChapterInfo>>() {
                    @Override
                    public List<NovelChapterInfo> apply(String s) throws Exception {
                        //读取数据库
                        List<NovelChapterInfo> categoryCache = mNovelDBManager.getCategory(s);

                        LogUtils.d(TAG,"get " + s + "'s Category cache in thread: " + Thread.currentThread().getName()
                        + "and cache's length is: " + categoryCache.size());
                        return categoryCache;
                    }
                })
                .doOnNext(new Consumer<List<NovelChapterInfo>>() {
                    @Override
                    public void accept(List<NovelChapterInfo> novelChapterInfos) throws Exception {
                        LogUtils.d(TAG,"doOnNext to check cache: " + novelChapterInfos);
                        if (novelChapterInfos.isEmpty()) {
                            ExceptionHandler.ResponseThrowable error = ExceptionHandler.ResponseThrowable.create(ExceptionHandler.Error.LOCAL_CACHE_ERROR
                                    , novelIndex + "'s cagetory cache is null", null);
                            throw error;
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends List<NovelChapterInfo>>>() {
                    @Override
                    public ObservableSource<? extends List<NovelChapterInfo>> apply(Throwable throwable) throws Exception {
                        LogUtils.e(TAG, Thread.currentThread().getName() + "---onErrorResumeNext: " + throwable);
                        baseCallback.onError(throwable);
                        return Observable.empty();
                    }
                })
                .observeOn(Schedulers.io());


        final Observable<List<NovelChapterInfo>> listObservable;
        if (readFromCache) {
            listObservable = Observable.concat(cacheObservable, serverObservable);
        } else {
            listObservable = serverObservable;
        }

        listObservable
                .compose(RxUtils.<List<NovelChapterInfo>>rxScheduers())
                .subscribe(new Observer<List<NovelChapterInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposableList.put(listObservable,d);
                        Log.d(TAG, "zhouyc onSubscribe: " + mDisposableList.size());
                    }

                    @Override
                    public void onNext(List<NovelChapterInfo> novelCategory) {
                        LogUtils.e(TAG, "onNext in thread: " + Thread.currentThread().getName() + "getCategory's size : " + novelCategory.size());
                        if (novelCategory == null || novelCategory.isEmpty()) {
                            onError(ExceptionHandler.ResponseThrowable.create(ExceptionHandler.Error.NETWORK_ERROR,
                                    "novelCategory is null", null));
                            return;
                        }
                        baseCallback.onSuccess(novelCategory, false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        baseCallback.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG,"getCategory onComplete");
                        mDisposableList.remove(listObservable);
                        Log.d(TAG, "zhouyc onComplete: " + mDisposableList.size());
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
