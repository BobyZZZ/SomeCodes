package com.bb.module_bookstore.mvp.presenter;

import com.bb.module_bookstore.mvp.modle.RankFragmentModel;
import com.bb.module_bookstore.mvp.view.RankFragment;
import com.bb.module_common.base.BasePresenter;
import com.bb.module_novelmanager.entity.RankPageDataFruitBean;
import com.bb.module_bookstore.mvp.contract.RankFragmentContract;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/2/9
 * Time: 14:01
 */
public class RankFragmentPresenter extends BasePresenter<RankFragment> implements RankFragmentContract.IPresenter {
    String TAG = "RankFragmentPresenter";

    private final RankFragmentModel mModel;
    public RankPageDataFruitBean.RankPageData mRankPageData;

    public RankFragmentPresenter() {
        mModel = new RankFragmentModel(this);
    }

    @Override
    public void process() {
        mModel.getRankPageData();
    }

    @Override
    public void onGetRankPageDataSuccess(RankPageDataFruitBean.RankPageData rankPageData) {
        mRankPageData = rankPageData;
        if (mView != null) {
            mView.showRankData(rankPageData);
        }
    }

    @Override
    public void onError(Throwable e) {
        mView.onError(e);
    }
}
