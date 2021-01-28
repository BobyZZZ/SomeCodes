package com.bb.reading.mvp.presenter;

import androidx.fragment.app.Fragment;

import com.bb.reading.R;
import com.bb.reading.base.BasePresenter;
import com.bb.reading.mvp.contract.MainActivityContract;
import com.bb.reading.mvp.view.activity.MainActivity;
import com.bb.reading.mvp.view.fragment.LikedNovelFragment;
import com.bb.reading.mvp.view.fragment.MainTabFragment;
import com.bb.reading.mvp.view.fragment.TabViewPagerFragment;
import com.bb.reading.utils.ResUtils;

import java.util.ArrayList;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/15
 * Time: 0:00
 */
public class MainActivityPresenter extends BasePresenter<MainActivity> implements MainActivityContract.IMainPresenter {
    private String[] mTitles;//标签
    private ArrayList<Fragment> mFragments;
    @Override
    public void initTabsAndFragments() {
        mTitles = ResUtils.getStringArray(R.array.main_activity_tabs);
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            switch (i) {
                case 0:
                    mFragments.add(LikedNovelFragment.newInstance());
                    break;
                case 1:
                    mFragments.add(TabViewPagerFragment.newInstance(TabViewPagerFragment.TYPE_SHUCHENG));
                    break;
                case 2:
                    mFragments.add(MainTabFragment.newInstance(MainTabFragment.TAB_ME));
                    break;
                default:
                    mFragments.add(MainTabFragment.newInstance(MainTabFragment.TAB_SHUJIA));
            }
        }
        mView.setTab(mFragments,mTitles);
    }
}
