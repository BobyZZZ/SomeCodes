package com.bb.reading.ui.mvp.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.bb.reading.R;
import com.bb.module_bookstore.adapter.TabFragmentPagerAdapter;
import com.bb.module_common.base.BaseFragment;
import com.bb.module_booksearch.mvp.view.SearchActivity;
import com.google.android.material.tabs.TabLayout;

/**
 * Created by Boby on 2019/6/17.
 */

public class MainTabFragment extends BaseFragment {
    String TAG = "TabFragment";

    TabLayout mTabLayout;
    ViewPager mViewPager;
    /**
     * 书架
     */
    public static final int TAB_SHUJIA = 0;
    /**
     * 书城
     */
    public static final int TAB_SHUCHENG = 10;
    /**
     * 我的
     */
    public static final int TAB_ME = 20;
    private int type;

    private TabFragmentPagerAdapter mAdapter;

    public static MainTabFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        MainTabFragment fragment = new MainTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public int getType() {
        return type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        type = arguments.getInt("type", 0);
        setTAG(type);
        super.onCreate(savedInstanceState);
    }

    private void setTAG(int type) {
        switch (type) {
            case TAB_SHUJIA:
                TAG = "书架";
                break;
            case TAB_SHUCHENG:
                TAG = "书城";
                break;
            case TAB_ME:
                TAG = "我的";
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

    @Override
    protected void initView(View view) {
        Log.d(TAG, "initView() called with: view = [" + view + "]");
/*        mAdapter = new TabFragmentPagerAdapter(getContext(), getChildFragmentManager(), "ChildFragmentPagerAdapter");
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);*/

        TextView tvTest = view.findViewById(R.id.tv_place_hold);
        tvTest.setText(TAG);
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.startSearchActivity(getContext());
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void process() {
    }

/*    @Override
    public void setTab(ArrayList<Fragment> fragmentList, String[] titles) {
        Log.d(TAG, "setTab() called with: fragmentList = [" + fragmentList + "], titles = [" + titles + "]");
*//*        mAdapter.setData(fragmentList, titles);
        mViewPager.setCurrentItem(0);*//*
    }*/
}
