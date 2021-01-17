package com.bb.reading.mvp.modle;

import com.bb.network.observer.BaseObserver;
import com.bb.reading.entity.PageData;
import com.bb.reading.network.NovelService;
import com.bb.reading.mvp.callback.BaseCallback;
import com.bb.reading.mvp.contract.NovelSortContract;
import com.bb.reading.utils.log.LogUtils;
import com.bb.reading.network.RetrofitManager;
import com.bb.reading.utils.RxUtils;

import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/12/27
 * Time: 23:40
 */
public class NovelSortModel implements NovelSortContract.INovelSortModel {
    String TAG = "NovelSortModel";

    private final NovelService mNovelService;

    public NovelSortModel() {
        mNovelService = RetrofitManager.getInstance().createRs(NovelService.class);
    }

    @Override
    public void getNovelBySort(int sort, final BaseCallback<PageData> callback) {
        Observable<PageData> novelBySort = mNovelService.getNovelBySort(NovelService.NovelType.TYPE_XUANHUAN,1);
        novelBySort
                .compose(RxUtils.rxScheduers())
                .subscribe(new BaseObserver<PageData>() {
                    @Override
                    protected void onSuccess(PageData pageData) {
                        LogUtils.d(TAG, "getNovelBySort success: " + pageData);
                        callback.onSuccess(pageData);
                    }

                    @Override
                    protected void onFail(Throwable e) {
                        LogUtils.d(TAG, "getNovelBySort fail() called with: e = [" + e + "]");
                    }
                });
    }
}
