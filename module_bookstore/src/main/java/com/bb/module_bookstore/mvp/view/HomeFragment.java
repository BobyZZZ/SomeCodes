package com.bb.module_bookstore.mvp.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bb.module_bookstore.R;
import com.bb.module_bookstore.adapter.BannerAdapter;
import com.bb.module_bookstore.adapter.VPBannerAdapter;
import com.bb.module_bookstore.rvHelper.BannerSnapHelper;
import com.bb.uilib.adapter.base.BasePagerAdapter;
import com.bb.uilib.adapter.base.BaseRvAdapter;
import com.bb.module_common.base.BaseMvpFragment;
import com.bb.module_novelmanager.arouter.RouterManager;
import com.bb.module_novelmanager.entity.HomePageBean;
import com.bb.module_novelmanager.entity.PageData;
import com.bb.module_bookstore.mvp.contract.HomeFragmentContract;
import com.bb.module_bookstore.mvp.presenter.HomeFragmentPresenter;
import com.bb.module_novelmanager.view.SortNovelListView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/15
 * Time: 22:03
 * 首页
 */
public class HomeFragment extends BaseMvpFragment<HomeFragmentPresenter> implements HomeFragmentContract.IView {
    protected String TAG = "HomeFragment";

    private ViewGroup mSortNovelListContainer;
    private ViewPager mBanner;
    private VPBannerAdapter mBannerAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static HomeFragment getInstance() {
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    public HomeFragmentPresenter createPresenter() {
        return new HomeFragmentPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        mSortNovelListContainer = view.findViewById(R.id.ll_container);
        mBanner = view.findViewById(R.id.banner);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        //banner
        mBannerAdapter = new VPBannerAdapter(R.layout.item_banner,mBanner);
        mBanner.setAdapter(mBannerAdapter);

        mBannerAdapter.setBgView(view.findViewById(R.id.fl_banner_container));
/*        //轮播效果
        PagerSnapHelper pagerSnapHelper = new BannerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mBanner);*/
    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh() called");
                process();
            }
        });

        mBannerAdapter.setOnItemClickListener(new BasePagerAdapter.OnItemClickListener<PageData.TopNovel>() {
            @Override
            public void onItemClick(int position, PageData.TopNovel data) {
//                Intent intent = NovelDetailActivity.createIntent(getContext(), data.getNovelDetailUrl());
//                startActivity(intent);
                RouterManager.getInstance().toNovelDetail(data.getNovelDetailUrl());
            }
        });
    }

    @Override
    protected void process() {
        mPresenter.process();
    }

    /**
     * 加载数据完成
     */
    public void loadDataFinish() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void setBannerData(List<PageData.TopNovel> bannerData) {
        mBannerAdapter.setDatas(bannerData);
    }

    @Override
    public void setNovelListData(List<HomePageBean.SortHotNovel> sortHotNovels) {
        if (sortHotNovels.isEmpty()) {
            return;
        }
        mSortNovelListContainer.removeAllViews();
        for (HomePageBean.SortHotNovel sortHotNovel : sortHotNovels) {
            SortNovelListView sortNovelListView = new SortNovelListView(getContext());
            sortNovelListView.setData(sortHotNovel);
            mSortNovelListContainer.addView(sortNovelListView);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);
        loadDataFinish();
    }
}
