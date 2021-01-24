package com.bb.reading.mvp.view.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bb.reading.R;
import com.bb.reading.adapter.base.BaseRvAdapter;
import com.bb.reading.adapter.base.BaseVH;
import com.bb.reading.base.BaseMvpFragment;
import com.bb.reading.entity.NovelDetails;
import com.bb.reading.mvp.contract.LikedNovelFragmentContract;
import com.bb.reading.mvp.presenter.LikedNovelFragmentPresenter;
import com.bb.reading.mvp.view.activity.ReadingActivity;
import com.bb.reading.utils.GlideUtils;
import com.bb.reading.utils.ResUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/21
 * Time: 0:27
 */
public class LikedNovelFragment extends BaseMvpFragment<LikedNovelFragmentPresenter> implements LikedNovelFragmentContract.IView {
    String TAG = "LikedNovelFragment";

    private RecyclerView mRvLikedNovel;
    private LikedNovelAdapter mLikedNovelAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static LikedNovelFragment newInstance() {
        LikedNovelFragment likedNovelFragment = new LikedNovelFragment();
        return likedNovelFragment;
    }

    @Override
    public LikedNovelFragmentPresenter createPresenter() {
        return new LikedNovelFragmentPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_liked_novel;
    }

    @Override
    protected void initView(View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mRvLikedNovel = view.findViewById(R.id.rv_liked_novel_list);

        mRvLikedNovel.setLayoutManager(new LinearLayoutManager(getContext()));
        mLikedNovelAdapter = new LikedNovelAdapter(R.layout.item_sort_novel_hottest);
        mRvLikedNovel.setAdapter(mLikedNovelAdapter);
    }

    @Override
    protected void initListener() {
        mLikedNovelAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener<NovelDetails>() {
            @Override
            public void onItemClick(NovelDetails data, int position) {
                //打开小说内容阅读页面
                Intent intent = ReadingActivity.createIntent(getContext(), data.getNovelId(), null);
                startActivity(intent);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh() called : ");
                mPresenter.getAllLiked();
            }
        });
    }

    @Override
    protected void process() {
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefreshing(true);
                mPresenter.getAllLiked();
            }
        }, 300);
    }

    @Override
    public void updateLikedNovelList(List<NovelDetails> allLikedNovel) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            setRefreshing(false);
        }
        mLikedNovelAdapter.setNewData(allLikedNovel);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    public void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    class LikedNovelAdapter extends BaseRvAdapter<NovelDetails> {
        public LikedNovelAdapter(int layoutId) {
            super(layoutId);
        }

        @Override
        protected void convert(BaseVH holder, NovelDetails data) {
            if (!TextUtils.isEmpty(data.coverUrl)) {
                GlideUtils.load(data.coverUrl, holder.getView(R.id.iv_novel_cover, ImageView.class));
            }
            holder.setText(R.id.tv_novel_name, ResUtils.getString(R.string.novel_name,data.name));
            holder.setText(R.id.tv_novel_author, data.getAuthor());
            holder.setText(R.id.tv_novel_introduction, data.introduction);
        }
    }
}
