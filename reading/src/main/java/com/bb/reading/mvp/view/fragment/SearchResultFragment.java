package com.bb.reading.mvp.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.reading.R;
import com.bb.reading.adapter.rv.NovelSearchResultAdapter;
import com.bb.reading.base.BaseMvpFragment;
import com.bb.reading.db.DaoHelper;
import com.bb.reading.db.greenDao.beanManager.NovelDBManager;
import com.bb.reading.entity.SearchResult;
import com.bb.reading.mvp.contract.SearchResultFragmentContract;
import com.bb.reading.mvp.presenter.SearchResultFragmentPresenter;
import com.bb.reading.mvp.view.activity.NovelDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/24
 * Time: 1:32
 */
public class SearchResultFragment extends BaseMvpFragment<SearchResultFragmentPresenter> implements SearchResultFragmentContract.IView {
    String TAG = "SearchResultFragment";

    private RecyclerView mRvSearchResult;
    private NovelSearchResultAdapter mAdapter;

    public static SearchResultFragment newInstance() {
        SearchResultFragment fragment = new SearchResultFragment();
        return fragment;
    }

    private SearchResultFragment() {

    }

    @Override
    public SearchResultFragmentPresenter createPresenter() {
        return new SearchResultFragmentPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected void initView(View view) {
        mRvSearchResult = view.findViewById(R.id.rv_search_result);

        mAdapter = new NovelSearchResultAdapter(R.layout.item_search_result);
        mAdapter.setPresenter(mPresenter);
        mRvSearchResult.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvSearchResult.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                NovelSearchResultAdapter novelSearchResultAdapter = (NovelSearchResultAdapter) adapter;
                SearchResult.Item item = novelSearchResultAdapter.getItem(position);
                Intent intent = NovelDetailActivity.createIntent(getContext(), item.novelId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void process() {

    }

    @Override
    public void updateResults(SearchResult searchResult) {
        mAdapter.setNewInstance(searchResult.getResults());
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
