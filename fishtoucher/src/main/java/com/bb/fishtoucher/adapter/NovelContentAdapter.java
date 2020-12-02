package com.bb.fishtoucher.adapter;

import android.view.View;

import com.bb.fishtoucher.mvp.presenter.MainPresenter;
import com.bb.fishtoucher.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ist.fishtoucher.R;
import com.bb.fishtoucher.entity.NovelChapterContent;
import com.bb.fishtoucher.entity.NovelChapterInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class NovelContentAdapter extends BaseQuickAdapter<NovelChapterContent, BaseViewHolder> implements LoadMoreModule, OnLoadMoreListener {
    String TAG = "NovelContentAdapter";

    private final MainPresenter mPresenter;

    public NovelContentAdapter(int layoutResId, MainPresenter presenter) {
        super(layoutResId);
        this.mPresenter = presenter;
    }

    public NovelContentAdapter(int layoutResId, List<NovelChapterContent> data, MainPresenter presenter) {
        super(layoutResId, data);
        this.mPresenter = presenter;
    }

    @Override
    protected void convert(@NotNull final BaseViewHolder viewHolder, final NovelChapterContent novelChapterContent) {
        String text = novelChapterContent.getContent();
        LogUtils.d(TAG, "convert getBookName: " + novelChapterContent.getChapterName());
        LogUtils.d(TAG, "convert getChapterNumber: " + novelChapterContent.getChapterNumber());
        viewHolder.setText(R.id.tv_novel_title, novelChapterContent.getChapterName());
        viewHolder.setText(R.id.tv_novel_content, text);
    }

    class VH extends BaseViewHolder {

        public VH(@NotNull View view) {
            super(view);
        }
    }
    private OnChapterClickListener mOnChapterClickListener;

    public void setOnChapterClickListener(OnChapterClickListener mOnChapterClickListener) {
        this.mOnChapterClickListener = mOnChapterClickListener;
    }

    public interface OnChapterClickListener {

        void onclick(NovelChapterInfo chapter, int chapterNumber);
    }
    public void initAutoLoadMoreEvent() {
        BaseLoadMoreModule loadMoreModule = getLoadMoreModule();
        loadMoreModule.setOnLoadMoreListener(this);
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMore();
    }
}
