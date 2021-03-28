package com.bb.module_bookstore.mvp.modle;

import android.util.Log;

import com.bb.module_bookstore.mvp.contract.SortFragmentContract;
import com.bb.network.observer.BaseObserver;
import com.bb.module_novelmanager.entity.PageData;
import com.bb.module_novelmanager.network.NovelService;
import com.bb.module_novelmanager.network.RetrofitManager;
import com.bb.module_common.utils.RxUtils;
import com.bb.module_common.utils.log.LogUtils;
import com.bb.module_common.utils.log.LongLogUtils;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/28
 * Time: 21:09
 */
public class SortNovelModel implements SortFragmentContract.IModel {
    private final SortFragmentContract.IPresenter mPresenter;
    String TAG = "SortNovelModel";

    private final NovelService mNovelService;

    public SortNovelModel(SortFragmentContract.IPresenter presenter) {
        mPresenter = presenter;
        mNovelService = RetrofitManager.getInstance().createRs(NovelService.class);
    }

    @Override
    public void getNovelsBySort(int type, int page) {
        getNovelsBySort(type,page,false);
    }

    public void getNovelsBySort(final int type, final int page, final boolean cleanOldData) {
        Log.d(TAG, "getNovelsBySort() called with: type = [" + type + "], page = [" + page + "], cleanOldData = [" + cleanOldData + "]");
        mNovelService.getNovelBySort(type, page)
                .compose(RxUtils.<PageData>rxScheduers())
                .onErrorResumeNext(RxUtils.<PageData>handleError())
                .subscribe(new BaseObserver<PageData>() {
                    @Override
                    protected void onSuccess(PageData pageData) {
                        LongLogUtils.d(TAG, "getNovelsBySort onSuccess pageData : " + pageData.getNovels().size());
                        mPresenter.loadNovelSuccess(pageData,type,page,cleanOldData);
                    }

                    @Override
                    protected void onFail(Throwable e) {
                        LogUtils.e(TAG, "onFail: " + e);
                        mPresenter.onError(e);
                    }
                });
    }
}
