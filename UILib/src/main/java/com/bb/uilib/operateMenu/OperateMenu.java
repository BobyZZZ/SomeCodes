package com.bb.uilib.operateMenu;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.uilib.R;
import com.bb.uilib.adapter.base.BaseVH;

import java.util.ArrayList;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/4/17
 * Time: 19:10
 */
public class OperateMenu extends RecyclerView {
    String TAG = "OperateMenu";

    public OperateMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initAdapterAndLayoutManager();
    }

    private void initAdapterAndLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(layoutManager);

        DefaultOperateAdapter defaultOperateAdapter = new DefaultOperateAdapter();
        setAdapter(defaultOperateAdapter);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof IExpandClose) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    IExpandClose defaultOperateAdapter = (IExpandClose) adapter;
                    defaultOperateAdapter.close();
                }
            }, 0);
        }
    }

    /**
     * 默认Adapter
     */
    class DefaultOperateAdapter extends OperateMenuAdapter<OperateBean> {

        private final ArrayList<OperateBean> datas;

        public DefaultOperateAdapter() {
            super(R.layout.item_operate_default);
            datas = new ArrayList<>();
            datas.add(new OperateBean(R.drawable.ic_launcher_background, "分享"));
            datas.add(new OperateBean(R.drawable.ic_launcher_background, "收藏"));
            datas.add(new OperateBean(R.drawable.ic_launcher_background, "返回"));
            setNewData(datas);
        }

        @Override
        protected void convert(BaseVH holder, OperateBean operateBean) {
            holder.getView(R.id.iv, ImageView.class).setImageResource(operateBean.imgRes);
        }
    }
}
