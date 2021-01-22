package com.bb.reading.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bb.reading.R;
import com.bb.reading.base.BaseMvpActivity;
import com.bb.reading.entity.SearchHistory;
import com.bb.reading.mvp.contract.SearchActivityContract;
import com.bb.reading.mvp.presenter.SearchActivityPresenter;
import com.bb.reading.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Boby on 2019/6/21.
 */

public class SearchActivity extends BaseMvpActivity<SearchActivityPresenter> implements SearchActivityContract.IView {
    String TAG = "SearchActivity";

    String[] mTips = new String[]{"三体", "爆裂鼓手", "魔方游戏", "海贼王", "两小无猜", "海上钢琴师"};
    private EditText mEtSearch;
    private TextView mClean;
    private FlowLayout mFlTips;
    private FlowLayout mFlHistorys;
    private View mRoot;
    private TextView mTvTrip;
    private TextView mTvHistory;

/*    @BindView(R.id.iv_blur_picture)
    ImageView mImageView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.mask)
    View mMask;
    @BindView(R.id.view_status_bar)
    View statuBar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;*/

    private String mKey;
//    private SearchCommentRVAdapter mAdapter;

    public static void startSearchResultActivityForResult(Fragment fragment, String key) {
        Intent intent = new Intent(fragment.getContext(), SearchActivity.class);
        intent.putExtra("key", key);
        fragment.startActivityForResult(intent, 0);
    }

    public static void startSearchResultActivity(Context context, String key) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("key", key);
        context.startActivity(intent);
    }

    @Override
    public SearchActivityPresenter createPresenter() {
        return new SearchActivityPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        mEtSearch = findViewById(R.id.et_search);
        mClean = findViewById(R.id.tv_clear);
        mFlTips = findViewById(R.id.ll_trip);
        mFlHistorys = findViewById(R.id.ll_history);
        mRoot = findViewById(R.id.root_layout);
        mTvTrip = findViewById(R.id.tv_trip);
        mTvHistory = findViewById(R.id.tv_history);

        mFlHistorys.setRevert(true);
        mFlHistorys.setMaxLinesCount(5);

/*        mMask.setVisibility(View.VISIBLE);

        mAdapter = new SearchCommentRVAdapter(null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        */

        initListener();
    }

    protected void initListener() {
        mEtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEtSearch.setHint(null);
                } else {
                    mEtSearch.setHint(R.string.search_text);
                }
            }
        });
        mEtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        if (!TextUtils.isEmpty(getSearchKey())) {
                            mPresenter.search(getSearchKey());
                        } else {
                            showToast(R.string.search_key_could_not_empty);
                        }
                    }
                }
                return false;
            }
        });
        mClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtSearch.clearFocus();
                mPresenter.cleanHistory();
            }
        });
        mFlHistorys.setOnTagClickedListener(new FlowLayout.OnTagClickedListener() {
            @Override
            public void onClick(String text, int index) {
                mPresenter.search(text);
            }
        });
        mFlTips.setOnTagClickedListener(new FlowLayout.OnTagClickedListener() {
            @Override
            public void onClick(String text, int index) {
                mPresenter.search(text);
            }
        });

/*        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMoreComment();
            }
        }, mRecyclerView);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchInfo.Comment comment = ((SearchCommentRVAdapter) adapter).getData().get(position);
                DetailPageActivity.startDetailPageActivity(SearchActivity.this, new DetailPageBean(comment.getId(), comment.getArticle(), comment.getWriter(), comment.getContent()));
            }
        });*/
/*        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //verticalOffset:完全打开时为0,收起时为-appBarLayout.getTotalScrollRange()
                mTvTitle.setAlpha(-verticalOffset * 1.0f / appBarLayout.getTotalScrollRange());
            }
        });*/
    }

    @Override
    protected void process() {
        /**
         * 加载推荐
         */
        ArrayList<SearchHistory> others = new ArrayList<>();
        for (int i = 0; i < mTips.length; i++) {
            SearchHistory other = new SearchHistory(mTips[i], mTips[i]);
            others.add(other);
        }
        mFlTips.setData(others);

        mPresenter.refreshHistory();
    }

    @Override
    public void updateHistory(List<SearchHistory> histories) {
        if (histories == null) {
            mFlHistorys.removeAll();
        } else {
            mFlHistorys.setData(histories);
        }
    }

    @Override
    public String getSearchKey() {
        return mEtSearch.getText().toString();
    }
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.getHistory();
    }*/

    @Override
    public void onError(Throwable throwable) {

    }

    public void onSearchError(Throwable e) {
//        mAdapter.loadMoreEnd();
    }

    public void onSearchSuccess(/*SearchInfo searchInfo*/) {
        Log.e("onSearchError", "onSearchSuccess: "/* + searchInfo*/);
/*        List<SearchInfo.Comment> comments = searchInfo.getComments();
        if (mAdapter.getData() == null || mAdapter.getData().size() == 0) {
            //第一次加载
            String imageUrl = searchInfo.getImageUrl();
            if (TextUtils.isEmpty(imageUrl)) {
                //没有图片就不用加载图片
                mMask.setVisibility(View.GONE);
                //根据主题设置颜色
                int type = SharePreferenceUtils.getInt(Constant.THEME, Constant.THEME_DAY);
                mImageView.setBackgroundColor(type == Constant.THEME_DAY ? ResUtils.getColor(R.color.colorPrimary) : ResUtils.getColor(R.color.status_bar_night));
                ;
            } else {
                GlideUtils.blur(mImageView, "http://" + searchInfo.getImageUrl(), 15, new Runnable() {
                    @Override
                    public void run() {
                        AlphaAnimation alpha = new AlphaAnimation(1f, 0f);
                        alpha.setDuration(200);
                        alpha.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mMask.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        alpha.start();
                        mMask.clearAnimation();
                        mMask.setAnimation(alpha);
                    }
                });
            }
            mAdapter.setNewData(comments);//设置评论数据
            mAdapter.setNotDoAnimationCount(3);
            addHeaderView(searchInfo);//添加头部，用于展示简介和相关作品
        } else {
            mAdapter.addData(comments);
            mMask.setVisibility(View.GONE);
        }

        //根据评论条数判断是否还有更多评论可拉取
        if (comments != null && comments.size() < Constant.COMMENT_PAGE_SIZE) {
            mAdapter.loadMoreEnd();
        } else {
            mAdapter.loadMoreComplete();
        }*/
    }

    public void addHeaderView(/*SearchInfo searchInfo*/) {
/*        View headerView;
        if (SharePreferenceUtils.getInt(Constant.THEME, Constant.THEME_DAY) == Constant.THEME_DAY) {
            headerView = LayoutInflater.from(this).inflate(R.layout.header_detail_info, null);
        } else {
            headerView = LayoutInflater.from(this).inflate(R.layout.header_detail_info_night, null);
        }

        mAdapter.removeAllHeaderView();
        mAdapter.addHeaderView(headerView);
        TextView mTvJianJie = headerView.findViewById(R.id.tv_jianjie);
        TextView mTvTagJianJie = headerView.findViewById(R.id.tv_tag_jianjie);
        RecyclerView mRvOtherWriter = headerView.findViewById(R.id.rv_other_writer);
        View noWriterText = headerView.findViewById(R.id.tv_no_others_tips_writer);

        RecyclerView mRvOther = headerView.findViewById(R.id.rv_other);
        View noOthers = headerView.findViewById(R.id.tv_no_others_tips);

        if (TextUtils.isEmpty(searchInfo.getJianJie())) {
            mTvTagJianJie.setVisibility(View.GONE);
            mTvJianJie.setVisibility(View.GONE);
        } else {
            mTvJianJie.setVisibility(View.VISIBLE);
            mTvJianJie.setText(searchInfo.getJianJie());//简介
        }*/
/*
        //相关作品
        List<SearchInfo.RelativeArticle> relativeArticles = searchInfo.getRelativeArticles();
        if (relativeArticles != null && relativeArticles.size() > 0) {
            noOthers.setVisibility(View.GONE);
            mRvOther.setVisibility(View.VISIBLE);

            OtherRVAdapter adapter = new OtherRVAdapter(R.layout.item_other, relativeArticles);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRvOther.setLayoutManager(layoutManager);
            mRvOther.setAdapter(adapter);

            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.tv_other_name) {
                        SearchInfo.RelativeArticle article = (SearchInfo.RelativeArticle) adapter.getData().get(position);
                        SearchActivity.startSearchResultActivity(SearchActivity.this, article.getName());
                    }
                }
            });
        } else {
            noOthers.setVisibility(View.VISIBLE);
            mRvOther.setVisibility(View.GONE);
        }
        //相关作者
        List<SearchInfo.RelativeWriter> relativeWriters = searchInfo.getRelativeWriters();
        if (relativeWriters != null && relativeWriters.size() > 0) {
            noWriterText.setVisibility(View.GONE);
            mRvOtherWriter.setVisibility(View.VISIBLE);

            OtherRVAdapter adapter = new OtherRVAdapter(R.layout.item_other, relativeWriters);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRvOtherWriter.setLayoutManager(layoutManager);
            mRvOtherWriter.setAdapter(adapter);

            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.tv_other_name) {
                        SearchInfo.RelativeWriter writer = (SearchInfo.RelativeWriter) adapter.getData().get(position);
                        SearchActivity.startSearchResultActivity(SearchActivity.this, writer.getName());
                    }
                }
            });
        } else {
            noWriterText.setVisibility(View.VISIBLE);
            mRvOtherWriter.setVisibility(View.GONE);
        }*/
    }
}
