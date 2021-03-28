package com.bb.module_noveldetail.mvp.modle;

import android.text.TextUtils;

import com.bb.module_common.utils.RxUtils;
import com.bb.module_noveldetail.mvp.contract.NovelDetailActivityContract;
import com.bb.module_novelmanager.entity.NovelDetails;
import com.bb.module_novelmanager.network.NovelService;
import com.bb.module_novelmanager.network.RetrofitManager;
import com.bb.module_common.utils.log.LogUtils;
import com.bb.network.observer.BaseObserver;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
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
        getDetailData(novelIndex, false);
    }

    /**
     * @param novelIndex
     * @param sync       同步操作
     */
    public void getDetailData(final String novelIndex, boolean sync) {
        LogUtils.d(TAG, "getDetailData() called with: novelIndex = [" + novelIndex + "],sync = [" + sync + "]");
        Observable<NovelDetails> observable = mNovelService.getNovelDetails(novelIndex);
        if (!sync) {
            //for show
            observable
                    .compose(RxUtils.<NovelDetails>rxScheduers())
                    .onErrorResumeNext(RxUtils.<NovelDetails>handleError())
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
                            mPresenter.onError(e);
                        }
                    });
        } else {
            //for add liked,db operate
            observable.onErrorResumeNext(RxUtils.<NovelDetails>handleError())
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
                            mPresenter.onError(e);
                        }
                    });
        }
    }
}
