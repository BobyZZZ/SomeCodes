package com.bb.reading.mvp.modle;

import android.text.TextUtils;
import android.util.Log;

import com.bb.network.observer.BaseObserver;
import com.bb.reading.entity.NovelDetails;
import com.bb.reading.mvp.contract.NovelDetailActivityContract;
import com.bb.reading.mvp.presenter.NovelDetailActivityPresenter;
import com.bb.reading.network.NovelService;
import com.bb.reading.network.RetrofitManager;
import com.bb.reading.utils.RxUtils;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/18
 * Time: 0:47
 */
public class NovelDetailActivityModel<P extends NovelDetailActivityContract.IPresenter> implements NovelDetailActivityContract.IModel {
    private final P mPresenter;
    String TAG = "NovelDetailActivityModel";
    private final NovelService mNovelService;

    public NovelDetailActivityModel(P novelDetailActivityPresenter) {
        mPresenter = novelDetailActivityPresenter;
        mNovelService = RetrofitManager.getInstance().createRs(NovelService.class);
    }

    @Override
    public void getDetailData(String novelIndex) {
        getDetailData(novelIndex,false);
    }

    /**
     * @param novelIndex
     * @param sync 同步操作
     */
    public void getDetailData(String novelIndex, boolean sync) {
        Log.d(TAG, "getDetailData() called with: novelIndex = [" + novelIndex + "],sync = [" + sync + "]");
        Observable<NovelDetails> observable = mNovelService.getNovelDetails(novelIndex);
        if (!sync) {
            //for show
            observable
                    .compose(RxUtils.rxScheduers())
                    .onErrorResumeNext(com.bb.network.utils.RxUtils.handleError())
                    .subscribe(new BaseObserver<NovelDetails>() {
                        @Override
                        protected void onSuccess(NovelDetails novelDetails) {
                            if (TextUtils.isEmpty(novelDetails.novelId)) {
                                novelDetails.novelId = novelIndex;
                            }
                            mPresenter.onDetailDataSuccess(novelDetails);
                        }

                        @Override
                        protected void onFail(Throwable e) {

                        }
                    });
        } else {
            //for add liked,db operate
            observable.onErrorResumeNext(com.bb.network.utils.RxUtils.handleError())
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new BaseObserver<NovelDetails>() {
                        @Override
                        protected void onSuccess(NovelDetails novelDetails) {
                            if (TextUtils.isEmpty(novelDetails.novelId)) {
                                novelDetails.novelId = novelIndex;
                            }
                            mPresenter.onDetailDataSuccess(novelDetails);
                        }

                        @Override
                        protected void onFail(Throwable e) {

                        }
                    });
        }
    }
}
