package com.bb.module_common.adapter.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/3/30
 * Time: 22:58
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {

    private final int mLayoutId;
    protected List<T> mDatas;

    public BasePagerAdapter(@LayoutRes int layoutId) {
        this(layoutId, null);
    }

    public BasePagerAdapter(@LayoutRes int layoutId, List<T> datas) {
        mLayoutId = layoutId;
        mDatas = datas;
    }

    public void setDatas(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    protected T getItemData(int position) {
        if (mDatas != null && position < mDatas.size()) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(mLayoutId, container,false);
        container.addView(inflate);
        final T itemData = getItemData(position);
        onBindView(inflate,itemData,position);

        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position,itemData);
                }
            }
        });
        return inflate;
    }

    protected abstract void onBindView(View view, T itemData, int position);

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    private OnItemClickListener<T> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> mOnClickListener) {
        this.mOnItemClickListener = mOnClickListener;
    }

    public interface OnItemClickListener<D> {
        void onItemClick(int position,D data);
    }
}
