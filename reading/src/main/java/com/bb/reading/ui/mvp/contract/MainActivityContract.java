package com.bb.reading.ui.mvp.contract;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public interface MainActivityContract {
    interface IMainModel {

    }

    interface IMainView {
        void setTab(ArrayList<Fragment> fragmentList, String[] titles);
    }

    interface IMainPresenter {
        void initTabsAndFragments();
    }
}
