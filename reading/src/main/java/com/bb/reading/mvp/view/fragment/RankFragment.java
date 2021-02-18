package com.bb.reading.mvp.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bb.reading.R;
import com.bb.reading.adapter.rv.RankExpandableAdapter;
import com.bb.reading.adapter.rv.TextAdapter;
import com.bb.reading.base.BaseMvpFragment;
import com.bb.reading.entity.RankPageDataFruitBean;
import com.bb.reading.mvp.contract.RankFragmentContract;
import com.bb.reading.mvp.presenter.RankFragmentPresenter;
import com.bb.reading.mvp.view.activity.NovelDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/2/9
 * Time: 13:59
 */
public class RankFragment extends BaseMvpFragment<RankFragmentPresenter> implements RankFragmentContract.IView {

    private RankExpandableAdapter mRankExpandableAdapter;
    private TextAdapter mRankPeriodsAdapter;
    private ExpandableListView mExpandableListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static Fragment newInstance() {
        RankFragment rankFragment = new RankFragment();
        return rankFragment;
    }

    @Override
    public RankFragmentPresenter createPresenter() {
        return new RankFragmentPresenter();
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rank;
    }

    @Override
    protected void initView(View view) {
        RecyclerView rvRankType = view.findViewById(R.id.rv_rank_type_list);
        mExpandableListView = view.findViewById(R.id.expandable_novel_list);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        //排行类型rv：最新、完结、推荐。。。
//        List<String> rankPeriods = Arrays.asList(ResUtils.getStringArray(R.array.rank_period_array));
        mRankPeriodsAdapter = new TextAdapter(R.layout.item_text);
        rvRankType.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRankType.setAdapter(mRankPeriodsAdapter);

        mRankExpandableAdapter = new RankExpandableAdapter();
        mExpandableListView.setAdapter(mRankExpandableAdapter);
    }

    @Override
    protected void initListener() {
        //下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                process();
            }
        });
        //时间榜选择
        mRankPeriodsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                onPeriodChanged(position);
            }
        });
        //小说点击事件
        mRankExpandableAdapter.setOnChildClickListener(new RankExpandableAdapter.OnChildClickListener() {
            @Override
            public void onClick(RankPageDataFruitBean.TypeRank.TimeTypeRank.Item novelItem) {
                Intent intent = NovelDetailActivity.createIntent(getContext(), novelItem.novelId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void process() {
        mPresenter.process();
    }

    @Override
    public void showRankData(RankPageDataFruitBean.RankPageData rankPageData) {
        List<RankPageDataFruitBean.TypeRank.TimeTypeRank> timeTypeRanks = rankPageData.typeRanks.get(0).timeTypeRanks;
        ArrayList<String> periods = new ArrayList<>();
        for (RankPageDataFruitBean.TypeRank.TimeTypeRank timeTypeRank : timeTypeRanks) {
            String period = timeTypeRank.period;
            periods.add(period);
        }
        mRankPeriodsAdapter.setNewInstance(periods);
        onPeriodChanged(mRankExpandableAdapter.mPeriodType);//默认选中第一个

        mRankExpandableAdapter.setGroupDatas(rankPageData.typeRanks);
        //展开全部
        int groupCount = mRankExpandableAdapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            mExpandableListView.expandGroup(i);
        }

        loadSuccess();
    }

    private void loadSuccess() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void onPeriodChanged(int position) {
        mRankPeriodsAdapter.setItemSelected(position);

        mRankExpandableAdapter.changePeriod(position);
    }
}
