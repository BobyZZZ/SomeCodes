package com.bb.reading.mvp.modle;

import android.util.Log;

import com.bb.network.observer.BaseObserver;
import com.bb.reading.entity.PageData;
import com.bb.reading.mvp.contract.SortFragmentContract;
import com.bb.reading.network.NovelService;
import com.bb.reading.network.RetrofitManager;
import com.bb.reading.utils.RxUtils;
import com.bb.reading.utils.log.LogUtils;
import com.bb.reading.utils.log.LongLogUtils;

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

    public void getNovelsBySort(int type, int page, boolean cleanOldData) {
        Log.d(TAG, "getNovelsBySort() called with: type = [" + type + "], page = [" + page + "], cleanOldData = [" + cleanOldData + "]");
        mNovelService.getNovelBySort(type, page)
                .compose(RxUtils.rxScheduers())
                .subscribe(new BaseObserver<PageData>() {
                    @Override
                    protected void onSuccess(PageData pageData) {
                        LongLogUtils.d(TAG, "getNovelsBySort onSuccess pageData : " + pageData.getNovels().size());
                        mPresenter.loadNovelSuccess(pageData,type,page,cleanOldData);
                    }

                    @Override
                    protected void onFail(Throwable e) {

                    }
                });
    }
}
