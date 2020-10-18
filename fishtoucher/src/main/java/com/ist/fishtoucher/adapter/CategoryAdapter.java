package com.ist.fishtoucher.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ist.fishtoucher.R;
import com.ist.fishtoucher.entity.NovelCategory;
import com.ist.fishtoucher.mvp.presenter.MainPresenter;

import org.jetbrains.annotations.NotNull;


public class CategoryAdapter extends BaseQuickAdapter<NovelCategory.Chapter, BaseViewHolder> {

    private final MainPresenter mPresenter;

    public CategoryAdapter(int layoutResId, MainPresenter presenter) {
        super(layoutResId);
        this.mPresenter = presenter;
    }

    @Override
    protected void convert(@NotNull final BaseViewHolder viewHolder, final NovelCategory.Chapter chapter) {
        viewHolder.itemView.setSelected(mPresenter != null
                && mPresenter.getCurrentChapterNumber() - 1 == viewHolder.getAdapterPosition() ? true : false);
        viewHolder.setText(R.id.tv_chapter_name, chapter.getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnChapterClickListener != null) {
                    mOnChapterClickListener.onclick(chapter, viewHolder.getAdapterPosition());
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
        void onclick(NovelCategory.Chapter chapter, int chapterNumber);
    }
}
