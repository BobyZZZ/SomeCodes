package com.bb.module_booksearch.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.bb.module_booksearch.R;
import com.bb.module_booksearch.mvp.contract.SearchActivityContract;
import com.bb.module_booksearch.mvp.presenter.SearchActivityPresenter;
import com.bb.module_common.base.BaseMVPFragmentActivity;

/**
 * Created by Boby on 2019/6/21.
 */

public class SearchActivity extends BaseMVPFragmentActivity<SearchActivityPresenter> implements SearchActivityContract.IView {
    String TAG = "SearchActivity";

    private EditText mEtSearch;

    public static void startSearchResultActivityForResult(Fragment fragment, String key) {
        Intent intent = new Intent(fragment.getContext(), SearchActivity.class);
        intent.putExtra("key", key);
        fragment.startActivityForResult(intent, 0);
    }

    public static void startSearchActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    public SearchActivityPresenter createPresenter() {
        return new SearchActivityPresenter();
    }

    @Override
    protected int getOtherViewLayoutId() {
        return R.layout.layout_search_bar;
    }

    @Override
    protected void initOtherView(View otherView) {
        super.initOtherView(otherView);
        mEtSearch = otherView.findViewById(R.id.et_search);
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
    }

    @Override
    protected void process() {
        mPresenter.process();
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
