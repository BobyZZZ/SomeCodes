package com.bb.fishtoucher.utils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewUtils {
    /**
     * 计算最多同时显示多少个item
     * 限制：使用垂直LinearLayoutManager
     * @param recyclerView
     * @return
     */
    public static int calculateItemCountInScreen(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForLayoutPosition(layoutManager.findFirstVisibleItemPosition());
        int height = viewHolder.itemView.getHeight();

        int itemCountInScreen = recyclerView.getHeight() / height;
        return itemCountInScreen;
    }
}
