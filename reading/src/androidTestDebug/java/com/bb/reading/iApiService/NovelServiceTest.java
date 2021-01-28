package com.bb.reading.iApiService;

import android.util.Log;

import com.bb.network.observer.BaseObserver;
import com.bb.reading.entity.NovelDetails;
import com.bb.reading.entity.PageData;
import com.bb.reading.entity.SearchResult;
import com.bb.reading.mvp.callback.BaseCallback;
import com.bb.reading.mvp.modle.NovelSortModel;
import com.bb.reading.network.NovelService;
import com.bb.reading.network.RetrofitManager;
import com.bb.reading.utils.log.LongLogUtils;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/17
 * Time: 22:11
 */
public class NovelServiceTest {
    String TAG = "NovelServiceTest";

    private NovelService mNovelService;
    private String mNovelIndex;

    @Before
    public void init() {
        mNovelIndex = "http://www.biquge.info/10_10582/";
        mNovelService = RetrofitManager.getInstance().createRs(NovelService.class);
    }

    @Test
    public void getCategory() {
        mNovelService.getCategory(mNovelIndex);

    }

    @Test
    public void getNovelDetails() {
        mNovelService.getNovelDetails(mNovelIndex)
                .subscribe(new BaseObserver<NovelDetails>() {
                    @Override
                    protected void onSuccess(NovelDetails novelDetails) {
                        LongLogUtils.d(TAG, "onSuccess() called with: novelDetails = [" + novelDetails + "]");
                    }

                    @Override
                    protected void onFail(Throwable e) {

                    }
                });
    }

    @Test
    public void searchNovel() {
        String searchKey = "剑来";
        mNovelService.searchNovel(searchKey)
                .subscribe(new Observer<SearchResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchResult searchResult) {
                        String string = searchResult.getResults().toString();
                        Log.d(TAG, "onNext() called with: searchResult = [" + string + "]");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError() called with: e = [" + e + "]");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Test
    public void getNovelBySort() {
        int type = NovelService.NovelType.TYPE_XUANHUAN;
        int page = 1;
        mNovelService.getNovelBySort(type, page)
                .subscribe(new Consumer<PageData>() {
                    @Override
                    public void accept(PageData pageData) throws Exception {
                        Log.d(TAG, "onSuccess: ");
                    }
                });
    }
}