package com.ist.fishtoucher.view.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ist.fishtoucher.R;
import com.ist.fishtoucher.entity.NovelCategory;

import org.jetbrains.annotations.NotNull;


public class CategoryAdapter extends BaseQuickAdapter<NovelCategory.Chapter,BaseViewHolder> {

    public CategoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull final BaseViewHolder viewHolder, final NovelCategory.Chapter chapter) {
        viewHolder.setText(R.id.tv_chapter_name,chapter.getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChapterClickListener != null) {
                    mOnChapterClickListener.onclick(chapter,viewHolder.getAdapterPosition());
                }
            }
        });
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
        void onclick(NovelCategory.Chapter chapter,int chapterNumber);
    }
}
