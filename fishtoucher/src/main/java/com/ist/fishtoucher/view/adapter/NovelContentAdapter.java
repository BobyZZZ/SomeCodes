package com.ist.fishtoucher.view.adapter;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ist.fishtoucher.R;
import com.ist.fishtoucher.entity.NovelCategory;
import com.ist.fishtoucher.entity.NovelChapterInfo;
import com.ist.fishtoucher.presenter.MainPresenter;
import com.ist.fishtoucher.utils.LogUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class NovelContentAdapter extends BaseQuickAdapter<NovelChapterInfo, BaseViewHolder> implements LoadMoreModule, OnLoadMoreListener {
    String TAG = "NovelContentAdapter";

    private final MainPresenter mPresenter;

    public NovelContentAdapter(int layoutResId, MainPresenter presenter) {
        super(layoutResId);
        this.mPresenter = presenter;

    }

    public NovelContentAdapter(int layoutResId, List<NovelChapterInfo> data, MainPresenter presenter) {
        super(layoutResId, data);
        this.mPresenter = presenter;
    }

    @Override
    protected void convert(@NotNull final BaseViewHolder viewHolder, final NovelChapterInfo novelChapterInfo) {
        String text = novelChapterInfo.getContent();
        LogUtils.d(TAG, "convert getBookName: " + novelChapterInfo.getBookName());
        LogUtils.d(TAG, "convert getChapterNumber: " + novelChapterInfo.getChapterNumber());
        viewHolder.setText(R.id.tv_novel_title,novelChapterInfo.getBookName());
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

        void onclick(NovelCategory.Chapter chapter, int chapterNumber);
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
