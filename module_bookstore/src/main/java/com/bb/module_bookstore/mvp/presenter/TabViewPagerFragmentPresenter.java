package com.bb.module_bookstore.mvp.presenter;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.bb.module_bookstore.R;
import com.bb.module_bookstore.mvp.contract.TabViewPagerFragmentContract;
import com.bb.module_bookstore.mvp.view.HomeFragment;
import com.bb.module_bookstore.mvp.view.RankFragment;
import com.bb.module_bookstore.mvp.view.SortFragment;
import com.bb.module_bookstore.mvp.view.TabViewPagerFragment;
import com.bb.module_common.base.BasePresenter;
import com.bb.module_common.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/16
 * Time: 21:48
 */
public class TabViewPagerFragmentPresenter extends BasePresenter<TabViewPagerFragment> implements TabViewPagerFragmentContract.IPresenter {
    String TAG = "TabViewPagerFragmentPresenter";
    private String[] mTabs;
    private List<Fragment> mFragments;


    public TabViewPagerFragmentPresenter() {
    }

    @Override
    public void initTabsAndFragments() {
        Log.d(TAG, "initTabsAndFragments() called with type: " + mView.getType());
        switch (mView.getType()) {
            case TabViewPagerFragment.TYPE_SHUCHENG:
                mTabs = ResUtils.getStringArray(R.array.shucheng_tabs);
                mFragments = new ArrayList<>();
                mFragments.add(HomeFragment.getInstance());//精选
//                mFragments.add(TabViewPagerFragment.newInstance(TabViewPagerFragment.TYPE_SORT));
                mFragments.add(SortFragment.newInstance());//分类
                mFragments.add(RankFragment.newInstance());//排行
                break;
/*            case TabViewPagerFragment.TYPE_SORT:
                mTabs = ResUtils.getStringArray(R.array.sort_tabs);
                mFragments.add(SortFragment.newInstance());
                break;*/
            default:
                mTabs = ResUtils.getStringArray(R.array.shucheng_tabs);
                mFragments = new ArrayList<>();
                for (String tab : mTabs) {
                    mFragments.add(HomeFragment.getInstance());
                }
        }
        mView.setTabsAndFragments(mFragments,mTabs);
    }

    @Override
    public void onError(Throwable e) {

    }
}
