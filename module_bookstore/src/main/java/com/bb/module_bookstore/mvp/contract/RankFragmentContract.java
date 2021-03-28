package com.bb.module_bookstore.mvp.contract;

import com.bb.module_novelmanager.entity.RankPageDataFruitBean;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/2/9
 * Time: 14:00
 */
public interface RankFragmentContract {
    interface IModel {
        void getRankPageData();
    }

    interface IView {
        void showRankData(RankPageDataFruitBean.RankPageData rankPageData);
    }

    interface IPresenter {
        void process();

        void onGetRankPageDataSuccess(RankPageDataFruitBean.RankPageData rankPageData);

        void onError(Throwable e);
    }
}
