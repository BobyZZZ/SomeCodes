package com.bb.reading.mvp.view.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bb.reading.R;

import com.bb.reading.adapter.viewpager.TabFragmentPagerAdapter;
import com.bb.reading.annotation.BindView;
import com.bb.reading.base.BaseMvpActivity;
import com.bb.reading.mvp.contract.MainActivityContract;
import com.bb.reading.mvp.presenter.MainActivityPresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends BaseMvpActivity<MainActivityPresenter> implements MainActivityContract.IMainView {
    String TAG = "MainActivity";
    @BindView(R.id.main_viewPager)
    public ViewPager mViewPager;
    @BindView(R.id.main_tabLayout)
    public TabLayout mTabLayout;

    TabFragmentPagerAdapter mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainActivityPresenter createPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    protected void initView() {
        //viewPager + tablayout
        mPagerAdapter = new TabFragmentPagerAdapter(this, getSupportFragmentManager(), "外层ViewPager的Adapter");
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(Integer.MAX_VALUE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void process() {
        getPresenter().initTabsAndFragments();
    }

/*    @Override
    protected void initTheme(int type) {
        switch (type) {
            case Constant.THEME_DAY:
                //日间模式
                mToolbar.setBackgroundColor(ResUtils.getColor(R.color.colorPrimary));
                mtvTitle.setTextColor(ResUtils.getColor(R.color.black));
                //设置图标
                Drawable drawable = getDrawable(R.drawable.nav_icon);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mtvMenu.setCompoundDrawables(drawable, null, null, null);

                mTabLayout.setBackgroundColor(ResUtils.getColor(R.color.white));
                mTabLayout.setTabTextColors(ResUtils.getColor(R.color.black_translate), ResUtils.getColor(R.color.black));
                mTabLayout.setSelectedTabIndicatorColor(ResUtils.getColor(R.color.colorAccent));
                break;
            case Constant.THEME_NIGHT:
                //夜间模式
                mToolbar.setBackgroundColor(ResUtils.getColor(R.color.status_bar_night));
                mtvTitle.setTextColor(ResUtils.getColor(R.color.white));
                //设置图标
                Drawable drawable2 = getDrawable(R.drawable.nav_icon_white);
                drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
                mtvMenu.setCompoundDrawables(drawable2, null, null, null);

                mTabLayout.setBackgroundColor(ResUtils.getColor(R.color.status_bar_night));
                mTabLayout.setTabTextColors(ResUtils.getColor(R.color.tab_gray), ResUtils.getColor(R.color.white));
                mTabLayout.setSelectedTabIndicatorColor(ResUtils.getColor(R.color.red_bg));
                break;
        }
    }*/

    @Override
    public void setTab(ArrayList<Fragment> fragmentList, String[] titles) {
        mPagerAdapter.setData(fragmentList, titles);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
