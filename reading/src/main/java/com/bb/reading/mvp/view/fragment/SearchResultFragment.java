package com.bb.reading.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.bb.reading.mvp.view.activity.SearchResultActivity;
import com.bb.reading.utils.log.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
    private String mSearchKey;

    public static SearchResultFragment newInstance(String searchKey) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SearchResultActivity.KEY_SEARCHKEY, searchKey);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mSearchKey = arguments.getString(SearchResultActivity.KEY_SEARCHKEY);
        }
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
        if (!TextUtils.isEmpty(mSearchKey)) {
            mPresenter.search(mSearchKey);
        } else {
            showToast(R.string.search_key_could_not_empty);
        }
    }

    @Override
    public void updateResults(SearchResult searchResult) {
        mAdapter.setNewInstance(searchResult.getResults());
    }
}
