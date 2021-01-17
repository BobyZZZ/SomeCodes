package com.bb.reading.mvp.contract;

import androidx.fragment.app.Fragment;

import com.bb.reading.entity.HomePageBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/16
 * Time: 21:49
 */
public interface TabViewPagerFragmentContract {
    interface IModel {
    }

    interface IView {
        void setTabsAndFragments(List<Fragment> fragmentList, String[] titles);
    }

    interface IPresenter {
        void initTabsAndFragments();
        void onError(Throwable e);
    }
}
