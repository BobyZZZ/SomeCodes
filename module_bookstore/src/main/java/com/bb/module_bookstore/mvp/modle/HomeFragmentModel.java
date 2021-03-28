package com.bb.module_bookstore.mvp.modle;

import com.bb.module_bookstore.mvp.contract.HomeFragmentContract;
import com.bb.module_bookstore.mvp.presenter.HomeFragmentPresenter;
import com.bb.module_common.utils.RxUtils;
import com.bb.network.observer.BaseObserver;
import com.bb.module_novelmanager.entity.HomePageBean;
import com.bb.module_novelmanager.network.NovelService;
import com.bb.module_novelmanager.network.RetrofitManager;

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
                .compose(RxUtils.<HomePageBean>rxScheduers())
                .onErrorResumeNext(RxUtils.<HomePageBean>handleError())
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
