package com.bb.reading.adapter.rv;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bb.reading.utils.log.LogUtils;
import com.bb.reading.utils.NovelSpUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.bb.reading.R;

import com.bb.reading.entity.NovelChapterInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class CategoryAdapter extends BaseQuickAdapter<NovelChapterInfo, BaseViewHolder> {
    String TAG = "CategoryAdapter";

    public CategoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull final BaseViewHolder viewHolder, final NovelChapterInfo chapter) {
        viewHolder.itemView.setSelected(chapter.getChapterId().equals(NovelSpUtils.getLastReadChapter()) ? true : false);
        viewHolder.setText(R.id.tv_chapter_name, chapter.getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChapterClickListener != null) {
                    mOnChapterClickListener.onclick(chapter, viewHolder.getAdapterPosition() + 1);
                }
            }
        });
    }

    /**
     * 滑动到当前阅读章节
     */
    public void scrollToCurrentReading() {
        int currentChapterNumber = -1;
        List<NovelChapterInfo> datas = getData();
        String lastReadChapter = NovelSpUtils.getLastReadChapter();
        LogUtils.d(TAG,"scrollToCurrentReading: " + lastReadChapter);
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getChapterId().equals(lastReadChapter)) {
                currentChapterNumber = i;
                break;
            }
        }
        if (currentChapterNumber > -1 && getItemCount() > currentChapterNumber) {
//                    mRvCategory.smoothScrollToPosition(currentChapterNumber);
//                    mRvCategory.scrollToPosition(currentChapterNumber);只滚动到显示出来，不置顶
            ((LinearLayoutManager) getRecyclerView().getLayoutManager()).scrollToPositionWithOffset(currentChapterNumber, 0);
            notifyDataSetChanged();
        } else {
            LogUtils.d(TAG, "cancel scrollToPosition: ");
        }
    }

    /**
     * 根据章节id获取章节bean
     * @param chapterId 章节id
     * @param offset 偏移量，如前一章或后一章
     * @return 章节bean
     */
    public NovelChapterInfo getChapterInfoWithOffset(String chapterId, int offset) {
        List<NovelChapterInfo> datas = getData();
        int currentIndex = getChapterPosition(chapterId);
        if (currentIndex == -1) {
            return null;
        }
        int targetIndex = currentIndex + offset;
        if (targetIndex < 0 || targetIndex >= getItemCount()) {
            return null;
        } else {
            return datas.get(targetIndex);
        }
    }

    /**
     * 根据章节id获取章节所处position
     * @param chapterId 章节id
     * @return  position
     */
    public int getChapterPosition(String chapterId) {
        List<NovelChapterInfo> datas = getData();
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getChapterId().equals(chapterId)) {
                return i;
            }
        }
        return -1;
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
}
