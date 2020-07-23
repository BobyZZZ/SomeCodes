package com.ist.flyframeview.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ist.flyframeview.AnimationUtils;
import com.ist.flyframeview.R;

public class TvAdapter<VH> extends RecyclerView.Adapter {
    String TAG = "TvAdapter";
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
//        Log.d(TAG, "onCreateViewHolder: ");
        final TvAdapter.VH holder = new TvAdapter.VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent,false));
        holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    AnimationUtils.scale(holder.itemView, 1, 1.4f, 300);
                    parent.requestLayout();//需要重新刷新布局
                } else {
                    AnimationUtils.scale(holder.itemView, 1.4f, 1f, 300);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Log.d(TAG, "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class VH extends RecyclerView.ViewHolder {
        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
