package com.bb.module_novelmanager;

import android.util.Log;

import com.bb.module_novelmanager.utils.JsoupUtils;
import com.bb.network.observer.BaseObserver;
import com.bb.module_novelmanager.entity.NovelChapterContentFruitBean;
import com.bb.module_novelmanager.entity.NovelDetails;
import com.bb.module_novelmanager.entity.PageData;
import com.bb.module_novelmanager.entity.RankPageDataFruitBean;
import com.bb.module_novelmanager.entity.SearchResult;
import com.bb.module_novelmanager.network.NovelService;
import com.bb.module_novelmanager.network.RetrofitManager;
import com.bb.module_common.utils.log.LongLogUtils;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

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

    @Test
    public void getRankData() {
        mNovelService.getRankData()
                .subscribe(new Consumer<RankPageDataFruitBean>() {
                    @Override
                    public void accept(RankPageDataFruitBean rankPageData) throws Exception {
                        Log.d(TAG, "开始过滤: ");
                        RankPageDataFruitBean.RankPageData rankPageData1 = rankPageData.filter();
                        Log.d(TAG, "结束过滤: ");
                    }
                });
    }

    @Test
    public void getNovelChapterDetails() {
        mNovelService.getNovelChapterDetails("/10/10489/4535761.html")
                .subscribe(new BaseObserver<NovelChapterContentFruitBean>() {
                    @Override
                    protected void onSuccess(NovelChapterContentFruitBean novelChapterContentFruitBean) {
                        String contentBeforeFilter = novelChapterContentFruitBean.content;
                        Log.d(TAG, "getNovelChapterDetails before filter: " + contentBeforeFilter.length() + "\n" + contentBeforeFilter);
                        String contentAfterFilter = JsoupUtils.parseContentToAndroidText(contentBeforeFilter);
                        contentAfterFilter = null;
//                        contentAfterFilter = contentBeforeFilter.replaceAll("\\s{8}", "\t");
                        contentAfterFilter = contentBeforeFilter.replaceAll("\\s{5}", "\n\t");
                        Log.d(TAG, "getNovelChapterDetails after filter: " + contentAfterFilter.length() + "\n" + contentAfterFilter);
                    }

                    @Override
                    protected void onFail(Throwable e) {
                        Log.e(TAG, "onFail: ");
                    }
                });
    }
}