package com.bb.reading.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bb.reading.R;
import com.bb.reading.adapter.base.BaseRvAdapter;
import com.bb.reading.adapter.rv.BannerAdapter;
import com.bb.reading.adapter.rv.snaphelper.BannerSnapHelper;
import com.bb.reading.base.BaseMvpFragment;
import com.bb.reading.entity.HomePageBean;
import com.bb.reading.entity.PageData;
import com.bb.reading.mvp.contract.HomeFragmentContract;
import com.bb.reading.mvp.presenter.HomeFragmentPresenter;
import com.bb.reading.mvp.view.activity.NovelDetailActivity;
import com.bb.reading.view.SortNovelListView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/15
 * Time: 22:03
 * 首页
 */
public class HomeFragment extends BaseMvpFragment<HomeFragmentPresenter> implements HomeFragmentContract.IView {
    String TAG = "HomeFragment";

    private ViewGroup mSortNovelListContainer;
    private RecyclerView mBanner;
    private BannerAdapter mBannerAdapter;
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
        mBannerAdapter = new BannerAdapter(R.layout.item_banner);
        mBanner.setAdapter(mBannerAdapter);
        //轮播效果
        PagerSnapHelper pagerSnapHelper = new BannerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mBanner);
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

        mBannerAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener<PageData.TopNovel>() {
            @Override
            public void onItemClick(PageData.TopNovel data, int position) {
                Intent intent = NovelDetailActivity.createIntent(getContext(), data.getNovelDetailUrl());
                startActivity(intent);
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
        mBannerAdapter.setNewData(bannerData);
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

    }
}
