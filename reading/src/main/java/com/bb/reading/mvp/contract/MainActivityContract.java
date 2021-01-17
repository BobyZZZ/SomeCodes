package com.bb.reading.mvp.contract;

import androidx.fragment.app.Fragment;

import com.bb.reading.entity.NovelChapterContent;
import com.bb.reading.entity.NovelChapterInfo;
import com.bb.reading.mvp.callback.BaseCallback;

import java.util.ArrayList;
import java.util.List;

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
