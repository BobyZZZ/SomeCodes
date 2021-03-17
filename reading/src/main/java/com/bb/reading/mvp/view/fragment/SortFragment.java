package com.bb.reading.mvp.view.fragment;

import android.view.View;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bb.reading.R;
import com.bb.reading.adapter.rv.SortNovelAdapter;
import com.bb.reading.base.BaseMvpListFragment;
import com.bb.reading.entity.NovelInfo;
import com.bb.reading.mvp.contract.SortFragmentContract;
import com.bb.reading.mvp.presenter.SortFragmentPresenter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/28
 * Time: 1:03
 */
public class SortFragment extends BaseMvpListFragment<SortFragmentPresenter, SortNovelAdapter> implements SortFragmentContract.IView,
        OnLoadMoreListener, TabLayout.BaseOnTabSelectedListener {

    protected String TAG = "SortFragment";
    private BaseLoadMoreModule mLoadMoreModule;
    private TabLayout mTabLayout;

    public static SortFragment newInstance() {
        SortFragment fragment = new SortFragment();
        return fragment;
    }

    @Override
    public SortFragmentPresenter createPresenter() {
        return new SortFragmentPresenter();
    }

    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);
        loadSuccess(true);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mTabLayout = new TabLayout(getContext());
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected SortNovelAdapter createAdapter() {
        return new SortNovelAdapter(R.layout.item_sort_novel, new ArrayList<>());
    }

    @Override
    protected void initListener() {
        mTabLayout.addOnTabSelectedListener(this);

        mLoadMoreModule = mAdapter.getLoadMoreModule();
        mLoadMoreModule.setOnLoadMoreListener(this);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getNewNovels(mTabLayout.getSelectedTabPosition());
            }
        });
    }

    @Override
    protected void process() {
        mPresenter.process();
    }

    @Override
    public void initTypes(String[] types) {
        mTabLayout.removeAllTabs();
        for (int i = 0; i < types.length; i++) {
            TabLayout.Tab tab = mTabLayout.newTab();
            tab.setText(types[i]);
            mTabLayout.addTab(tab, i == 0);
        }

        //添加头部
        mAdapter.addHeaderView(mTabLayout);
    }

    @Override
    public void addNovels(List<NovelInfo> novels, boolean hasMore, boolean cleanOldData) {
        if (cleanOldData) {
            mAdapter.setNewInstance(new ArrayList<>());
        }
        mAdapter.addData(novels);
        loadSuccess(hasMore);
    }

    //刷新ui
    private void loadSuccess(boolean hasMore) {
        if (!hasMore) {
            mLoadMoreModule.setEnableLoadMore(false);
        }
        if (mLoadMoreModule.isLoading()) {
            mLoadMoreModule.loadMoreComplete();
        }
        setRefreshing(false);
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        mPresenter.getNewNovels(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
