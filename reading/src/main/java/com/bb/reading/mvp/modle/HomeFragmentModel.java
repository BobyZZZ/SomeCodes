package com.bb.reading.mvp.modle;

import android.util.Log;

import com.bb.network.observer.BaseObserver;
import com.bb.reading.entity.HomePageBean;
import com.bb.reading.network.NovelService;
import com.bb.reading.network.RetrofitManager;
import com.bb.reading.mvp.contract.HomeFragmentContract;
import com.bb.reading.mvp.presenter.HomeFragmentPresenter;
import com.bb.reading.utils.RxUtils;

/**
 * Created by Boby on 2019/6/19.
 */

public class HomeFragmentModel implements HomeFragmentContract.IModel {
    String TAG = "HomeFragmentModel";
    HomeFragmentPresenter mPresenter;
    private final NovelService mNovelService;

    public HomeFragmentModel(HomeFragmentPresenter presenter) {
        mNovelService = RetrofitManager.getInstance().createRs(NovelService.class);
        this.mPresenter = presenter;
//        this.type = presenter.getView().getType();
    }

    @Override
    public void getHomeData() {
        mNovelService.getHomeData()
                .compose(RxUtils.rxScheduers())
                .subscribe(new BaseObserver<HomePageBean>() {
                    @Override
                    protected void onSuccess(HomePageBean homePageBean) {
//                        Log.d(TAG, "onSuccess() called with: homePageBean = [" + homePageBean + "]");
                        mPresenter.onHomeData(homePageBean);
                    }

                    @Override
                    protected void onFail(Throwable e) {
                        mPresenter.onError(e);
                    }
                });
    }
}
