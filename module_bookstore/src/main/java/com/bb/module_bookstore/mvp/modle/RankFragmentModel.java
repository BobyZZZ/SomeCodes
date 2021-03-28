package com.bb.module_bookstore.mvp.modle;

import com.bb.module_bookstore.mvp.contract.RankFragmentContract;
import com.bb.network.observer.BaseObserver;
import com.bb.module_novelmanager.entity.RankPageDataFruitBean;
import com.bb.module_novelmanager.network.NovelService;
import com.bb.module_novelmanager.network.RetrofitManager;
import com.bb.module_common.utils.RxUtils;
import com.bb.module_common.utils.log.LogUtils;

import io.reactivex.functions.Function;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/2/9
 * Time: 16:49
 */
public class RankFragmentModel implements RankFragmentContract.IModel {
    String TAG = "RankFragmentModel";

    private final RankFragmentContract.IPresenter mPresenter;
    private final NovelService mNovelService;

    public RankFragmentModel(RankFragmentContract.IPresenter presenter) {
        mPresenter = presenter;
        mNovelService = RetrofitManager.getInstance().createRs(NovelService.class);
    }


    @Override
    public void getRankPageData() {
        mNovelService.getRankData()
                .map(new Function<RankPageDataFruitBean, RankPageDataFruitBean.RankPageData>() {
                    @Override
                    public RankPageDataFruitBean.RankPageData apply(RankPageDataFruitBean fruitBean) throws Exception {
                        return fruitBean.filter();
                    }
                })
                .compose(RxUtils.<RankPageDataFruitBean.RankPageData>rxScheduers())
                .onErrorResumeNext(RxUtils.<RankPageDataFruitBean.RankPageData>handleError())
                .subscribe(new BaseObserver<RankPageDataFruitBean.RankPageData>() {
                    @Override
                    protected void onSuccess(RankPageDataFruitBean.RankPageData rankPageData) {
                        LogUtils.d(TAG, rankPageData.toString());
                        mPresenter.onGetRankPageDataSuccess(rankPageData);
                    }

                    @Override
                    protected void onFail(Throwable e) {
                        LogUtils.e(TAG, e.toString());
                        mPresenter.onError(e);
                    }
                });
    }
}
