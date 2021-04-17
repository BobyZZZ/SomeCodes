package com.bb.module_bookshelf.mvp.view;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bb.module_bookshelf.R;
import com.bb.module_bookshelf.mvp.contract.LikedNovelFragmentContract;
import com.bb.module_bookshelf.mvp.presenter.LikedNovelFragmentPresenter;
import com.bb.module_common.base.BaseMvpFragment;
import com.bb.module_common.utils.ResUtils;
import com.bb.module_novelmanager.arouter.RouterManager;
import com.bb.module_novelmanager.entity.NovelDetails;
import com.bb.module_common.utils.GlideUtils;
import com.bb.uilib.adapter.base.BaseRvAdapter;
import com.bb.uilib.adapter.base.BaseVH;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/21
 * Time: 0:27
 */
public class LikedNovelFragment extends BaseMvpFragment<LikedNovelFragmentPresenter> implements LikedNovelFragmentContract.IView {
    public static String TAG = "LikedNovelFragment";

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
/*                //打开小说内容阅读页面
                Intent intent = ReadingActivity.createIntent(getContext(), data.getNovelId(), null);
                startActivity(intent);*/
                RouterManager.getInstance().toNovelReading(data.getNovelId(),null);
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
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshFavorite();
    }

    public void refreshFavorite() {
        setRefreshing(true);
        mPresenter.getAllLiked();
    }

    @Override
    public void updateLikedNovelList(List<NovelDetails> allLikedNovel) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            setRefreshing(false);
        }
        mLikedNovelAdapter.setNewData(allLikedNovel);
    }

    public void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }

    class LikedNovelAdapter extends BaseRvAdapter<NovelDetails> {
        public LikedNovelAdapter(int layoutId) {
            super(layoutId);
        }

        @Override
        protected void convert(BaseVH holder, final NovelDetails data) {
            ImageView ivCover = holder.getView(R.id.iv_novel_cover, ImageView.class);
            if (!TextUtils.isEmpty(data.coverUrl)) {
                GlideUtils.load(data.coverUrl, ivCover);
            }

            holder.setText(R.id.tv_novel_name, ResUtils.getString(R.string.novel_name,data.name));
            holder.setText(R.id.tv_novel_author, data.getAuthor());
            holder.setText(R.id.tv_novel_introduction, data.introduction);
            ivCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
/*                    //点击封面图片进入详情页面
                    Intent intent = NovelDetailActivity.createIntent(getContext(), data.getNovelId());
                    startActivity(intent);*/
                    RouterManager.getInstance().toNovelDetail(data.getNovelId());
                }
            });
        }
    }
}
