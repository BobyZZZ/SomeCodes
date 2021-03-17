package com.bb.reading.mvp.modle;

import com.bb.network.observer.BaseObserver;
import com.bb.reading.entity.RankPageDataFruitBean;
import com.bb.reading.mvp.contract.RankFragmentContract;
import com.bb.reading.network.NovelService;
import com.bb.reading.network.RetrofitManager;
import com.bb.reading.utils.RxUtils;
import com.bb.reading.utils.log.LogUtils;

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
                .compose(RxUtils.rxScheduers())
                .onErrorResumeNext(com.bb.network.utils.RxUtils.handleError())
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
