package com.bb.reading.mvp.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bb.reading.R;
import com.bb.reading.adapter.viewpager.TabFragmentPagerAdapter;
import com.bb.reading.base.BaseMvpFragment;
import com.bb.reading.mvp.contract.TabViewPagerFragmentContract;
import com.bb.reading.mvp.presenter.TabViewPagerFragmentPresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/15
 * Time: 21:47
 */
public class TabViewPagerFragment extends BaseMvpFragment<TabViewPagerFragmentPresenter> implements TabViewPagerFragmentContract.IView {
    String TAG = "TabViewPagerFragment";
    private static final String KEY_TYPE = "type";
    public static final int TYPE_SHUCHENG = 1;//书城
    public static final int TYPE_SORT = 10;//分类页面，书城里的一个子页面

    private int mType;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabFragmentPagerAdapter mPagerAdapter;

    public static TabViewPagerFragment newInstance(int type) {
        TabViewPagerFragment tabViewPagerFragment = new TabViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type);
        tabViewPagerFragment.setArguments(bundle);
        return tabViewPagerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(KEY_TYPE);
        TAG += "_" + mType;
    }

    @Override
    public TabViewPagerFragmentPresenter createPresenter() {
        return new TabViewPagerFragmentPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_viewpager;
    }

    @Override
    protected void initView(View view) {
        mTabLayout = view.findViewById(R.id.tabLayout);
        mViewPager = view.findViewById(R.id.viewPager);

        //tablayout + viewpager
        mTabLayout.setupWithViewPager(mViewPager);
        mPagerAdapter = new TabFragmentPagerAdapter(getContext(), getChildFragmentManager(), "书城Adapter");
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void process() {
        mPresenter.initTabsAndFragments();
    }

    @Override
    public void setTabsAndFragments(List<Fragment> fragmentList, String[] titles) {
        mPagerAdapter.setData(fragmentList,titles);
    }

    public int getType() {
        return mType;
    }
}
