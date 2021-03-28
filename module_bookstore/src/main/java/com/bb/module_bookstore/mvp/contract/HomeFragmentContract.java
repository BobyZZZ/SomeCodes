package com.bb.module_bookstore.mvp.contract;

import com.bb.module_novelmanager.entity.HomePageBean;
import com.bb.module_novelmanager.entity.PageData;

import java.util.List;

public interface HomeFragmentContract {
    interface IModel {
        void getHomeData();
    }

    interface IView {
        void setBannerData(List<PageData.TopNovel> bannerData);
        void setNovelListData(List<HomePageBean.SortHotNovel> sortHotNovels);
    }

    interface IPresenter {
        void onHomeData(HomePageBean homePageBean);
        void onError(Throwable e);
    }
}
