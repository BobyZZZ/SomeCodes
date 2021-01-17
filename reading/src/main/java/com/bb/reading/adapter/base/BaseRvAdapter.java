package com.bb.reading.adapter.base;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/16
 * Time: 0:04
 */
public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseVH> {
    private final int mLayoutId;
    protected List<T> mDatas;

    public BaseRvAdapter(int layoutId) {
        mLayoutId = layoutId;
    }

    public void setNewData(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BaseVH.create(parent, mLayoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseVH holder, int position) {
        T data = getItemData(position);
        convert(holder, data);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(getItemData(position), position);
            }
        });
    }

    protected T getItemData(int position) {
        return mDatas.get(position);
    }

    protected abstract void convert(BaseVH holder, T data);

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    protected void onItemClick(T data, int position) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(data,position);
        }
    }

    private OnItemClickListener<T> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T data, int position);
    }
}
