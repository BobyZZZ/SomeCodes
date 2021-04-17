package com.bb.uilib.operateMenu;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.uilib.adapter.base.BaseRvAdapter;
import com.bb.uilib.adapter.base.BaseVH;
import com.bb.uilib.utils.ExpandCloseAnimUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/4/17
 * Time: 20:08
 */
public abstract class OperateMenuAdapter<T> extends BaseRvAdapter<T> implements IExpandClose {

    private ExpandCloseAnimUtils expandCloseAnimUtils;

    public OperateMenuAdapter(int layoutId) {
        super(layoutId);
    }

    public OperateMenuAdapter(int layoutId, List<T> datas) {
        super(layoutId, datas);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseVH holder, int position) {
        super.onBindViewHolder(holder, position);
        if (position == getItemCount() - 1) {
            //最后一个设置长按事件控制展开或关闭
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    expandCloseAnimUtils.toggle();
                    return true;
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        expandCloseAnimUtils = new ExpandCloseAnimUtils(recyclerView);
    }

    @Override
    public void expand() {
        expandCloseAnimUtils.expand();
    }

    @Override
    public void close() {
        expandCloseAnimUtils.close();
    }

    @Override
    public void toggle() {
        expandCloseAnimUtils.toggle();
    }
}
