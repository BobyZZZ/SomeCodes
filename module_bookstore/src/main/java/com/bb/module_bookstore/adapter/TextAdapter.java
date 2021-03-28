package com.bb.module_bookstore.adapter;

import com.bb.module_bookstore.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/2/9
 * Time: 14:07
 */
public class TextAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int mLastSelect = -1;
    private int mCurrentSelect = 0;

    public TextAdapter(int layoutResId) {
        super(layoutResId);
    }

    public void setItemSelected(int position) {
        if (position == mCurrentSelect) {
            return;
        }

        mLastSelect = mCurrentSelect;
        mCurrentSelect = position;

        if (mLastSelect != -1) {
            notifyItemChanged(mLastSelect);
        }
        if (mCurrentSelect != -1) {
            notifyItemChanged(position);
        }
    }

    public TextAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    public void onBindViewHolder(@NotNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setSelected(position == mCurrentSelect);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_text, s);
    }
}
