package com.bb.reading.mvp.modle;

import android.util.Log;

import com.bb.network.observer.BaseObserver;
import com.bb.reading.entity.NovelDetails;
import com.bb.reading.mvp.contract.NovelDetailActivityContract;
import com.bb.reading.mvp.presenter.NovelDetailActivityPresenter;
import com.bb.reading.network.NovelService;
import com.bb.reading.network.RetrofitManager;
import com.bb.reading.utils.RxUtils;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/18
 * Time: 0:47
 */
public class NovelDetailActivityModel implements NovelDetailActivityContract.IModel {
    private final NovelDetailActivityPresenter mPresenter;
    String TAG = "NovelDetailActivityModel";
    private final NovelService mNovelService;

    public NovelDetailActivityModel(NovelDetailActivityPresenter novelDetailActivityPresenter) {
        mPresenter = novelDetailActivityPresenter;
        mNovelService = RetrofitManager.getInstance().createRs(NovelService.class);
    }

    @Override
    public void getDetailData(String novelIndex) {
        Log.d(TAG, "getDetailData() called with: novelIndex = [" + novelIndex + "]");
        mNovelService.getNovelDetails(novelIndex)
                .compose(RxUtils.rxScheduers())
                .subscribe(new BaseObserver<NovelDetails>() {
                    @Override
                    protected void onSuccess(NovelDetails novelDetails) {
                        Log.d(TAG, "onSuccess() called with: novelDetails = [" + novelDetails + "]");
                        mPresenter.onDetailDataSuccess(novelDetails);
                    }

                    @Override
                    protected void onFail(Throwable e) {

                    }
                });
    }
}
