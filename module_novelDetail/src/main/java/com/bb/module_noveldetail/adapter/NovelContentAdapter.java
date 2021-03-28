package com.bb.module_noveldetail.adapter;

import com.bb.module_noveldetail.R;
import com.bb.module_novelmanager.entity.NovelChapterContentFruitBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.bb.module_novelmanager.entity.NovelChapterInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class NovelContentAdapter extends BaseQuickAdapter<NovelChapterContentFruitBean, BaseViewHolder> implements LoadMoreModule {
    String TAG = "NovelContentAdapter";

    public NovelContentAdapter(int layoutResId) {
        super(layoutResId);
    }

    public NovelContentAdapter(int layoutResId, List<NovelChapterContentFruitBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull final BaseViewHolder viewHolder, final NovelChapterContentFruitBean novelChapterContent) {
        String text = novelChapterContent.content;
//        LogUtils.d(TAG, "convert getBookName: " + novelChapterContent.getChapterName());
//        LogUtils.d(TAG, "convert getChapterNumber: " + novelChapterContent.getChapterNumber());
        viewHolder.setText(R.id.tv_novel_title, novelChapterContent.chapterName);
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
