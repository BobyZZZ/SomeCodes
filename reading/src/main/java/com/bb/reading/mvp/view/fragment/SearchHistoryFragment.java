package com.bb.reading.mvp.view.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bb.reading.R;
import com.bb.reading.base.BaseMvpFragment;
import com.bb.reading.entity.SearchHistory;
import com.bb.reading.mvp.contract.SearchHistoryFragmentContract;
import com.bb.reading.mvp.presenter.SearchActivityPresenter;
import com.bb.reading.mvp.presenter.SearchHistoryFragmentPresenter;
import com.bb.reading.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boby on 2019/6/20.
 */

public class SearchHistoryFragment extends BaseMvpFragment<SearchHistoryFragmentPresenter> implements SearchHistoryFragmentContract.IView {
    String TAG = "SearchHistoryFragment";
    String[] mTips = new String[]{"剑来", "猫腻", "无限沉沦", "绝世唐门", "斗罗大陆", "老子能召唤万物"};
    private List<SearchHistory> mHistories;
    private TextView mClean;
    private FlowLayout mFlTrips;
    private FlowLayout mFlHistorys;
    private View mRoot;
    private TextView mTvTrip;
    private TextView mTvHistory;
    private SearchActivityPresenter mSearchActivityPresenter;

    public static SearchHistoryFragment newInstance() {
        SearchHistoryFragment fragment = new SearchHistoryFragment();
        return fragment;
    }

    public void setOthersPresenter(SearchActivityPresenter searchActivityPresenter) {
        mSearchActivityPresenter = searchActivityPresenter;
    }

    @Override
    public SearchHistoryFragmentPresenter createPresenter() {
        SearchHistoryFragmentPresenter presenter = new SearchHistoryFragmentPresenter();
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_history;
    }

    @Override
    protected void initView(View view) {
        mClean = view.findViewById(R.id.tv_clear);
        mFlTrips = view.findViewById(R.id.ll_trip);
        mFlHistorys = view.findViewById(R.id.ll_history);
        mRoot = view.findViewById(R.id.root_layout);
        mTvTrip = view.findViewById(R.id.tv_trip);
        mTvHistory = view.findViewById(R.id.tv_history);

        mFlHistorys.setMaxLinesCount(2);
    }

    @Override
    protected void initListener() {
        mClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.cleanHistory();
            }
        });
        mFlHistorys.setOnTagClickedListener(new FlowLayout.OnTagClickedListener() {
            @Override
            public void onClick(String text, int index) {
                mSearchActivityPresenter.search(mHistories.get(index).getName());
            }
        });
        mFlTrips.setOnTagClickedListener(new FlowLayout.OnTagClickedListener() {
            @Override
            public void onClick(String text, int index) {
                mSearchActivityPresenter.search(mTips[index]);
            }
        });
    }

    @Override
    protected void process() {
        Log.d(TAG, "process() called");
        mPresenter.refreshHistory();
        ArrayList<SearchHistory> others = new ArrayList<>();
        for (int i = 0; i < mTips.length; i++) {
            SearchHistory other = new SearchHistory(mTips[i], mTips[i]);
            others.add(other);
        }
        mFlTrips.setData(others);
    }

    @Override
    public void updateHistory(List<SearchHistory> histories) {
        mHistories = histories;
        if (histories == null) {
            mFlHistorys.removeAll();
        } else {
            mFlHistorys.setData(histories);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.refreshHistory();
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
