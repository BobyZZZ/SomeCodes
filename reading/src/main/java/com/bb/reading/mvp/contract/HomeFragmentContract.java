package com.bb.reading.mvp.contract;

import androidx.fragment.app.Fragment;

import com.bb.reading.entity.HomePageBean;
import com.bb.reading.entity.PageData;

import java.util.ArrayList;
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
