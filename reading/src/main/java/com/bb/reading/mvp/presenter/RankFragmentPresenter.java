package com.bb.reading.mvp.presenter;

import com.bb.reading.base.BasePresenter;
import com.bb.reading.entity.RankPageDataFruitBean;
import com.bb.reading.mvp.contract.RankFragmentContract;
import com.bb.reading.mvp.modle.RankFragmentModel;
import com.bb.reading.mvp.view.fragment.RankFragment;

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

    }
}
