package com.bb.reading.mvp.presenter;

import android.util.Log;

import com.bb.reading.base.BasePresenter;
import com.bb.reading.entity.HomePageBean;
import com.bb.reading.mvp.contract.HomeFragmentContract;
import com.bb.reading.mvp.modle.HomeFragmentModel;
import com.bb.reading.mvp.view.fragment.HomeFragment;

/**
 * Created by Boby on 2019/6/17.
 */

public class HomeFragmentPresenter extends BasePresenter<HomeFragment> implements HomeFragmentContract.IPresenter {
    String TAG = "HomeFragmentPresenter";

    private final HomeFragmentModel mHomeFragmentModel;
    public HomeFragmentPresenter() {
        mHomeFragmentModel = new HomeFragmentModel(this);
    }

    public void process() {
        mHomeFragmentModel.getHomeData();
    }

    @Override
    public void onHomeData(HomePageBean homePageBean) {
        if (mView != null) {
            mView.setBannerData(homePageBean.bannerNovels);
            mView.setNovelListData(homePageBean.sortHotNovels);

            mView.loadDataFinish();
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "onError() called with: e = [" + e + "]");
        if (mView != null) {
            mView.onError(e);
        }
    }
}
