package com.bb.reading.adapter.rv;

import android.view.View;

import com.bb.reading.mvp.presenter.ReadingPresenter;
import com.bb.reading.utils.log.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.bb.reading.R;
import com.bb.reading.entity.NovelChapterContent;
import com.bb.reading.entity.NovelChapterInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class NovelContentAdapter extends BaseQuickAdapter<NovelChapterContent, BaseViewHolder> implements LoadMoreModule {
    String TAG = "NovelContentAdapter";

    public NovelContentAdapter(int layoutResId) {
        super(layoutResId);
    }

    public NovelContentAdapter(int layoutResId, List<NovelChapterContent> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull final BaseViewHolder viewHolder, final NovelChapterContent novelChapterContent) {
        String text = novelChapterContent.getContent();
//        LogUtils.d(TAG, "convert getBookName: " + novelChapterContent.getChapterName());
//        LogUtils.d(TAG, "convert getChapterNumber: " + novelChapterContent.getChapterNumber());
        viewHolder.setText(R.id.tv_novel_title, novelChapterContent.getChapterName());
        viewHolder.setText(R.id.tv_novel_content, text);
    }

    private OnChapterClickListener mOnChapterClickListener;

    public void setOnChapterClickListener(OnChapterClickListener mOnChapterClickListener) {
        this.mOnChapterClickListener = mOnChapterClickListener;
    }

    public interface OnChapterClickListener {
        void onclick(NovelChapterInfo chapter, int chapterNumber);
    }

    /**
     * 加载更多
     * @param onLoadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        BaseLoadMoreModule loadMoreModule = getLoadMoreModule();
        loadMoreModule.setOnLoadMoreListener(onLoadMoreListener);
    }
}
