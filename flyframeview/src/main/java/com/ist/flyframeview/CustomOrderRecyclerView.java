package com.ist.flyframeview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class CustomOrderRecyclerView extends RecyclerView {
    public CustomOrderRecyclerView(@NonNull Context context) {
        super(context);
        setChildrenDrawingOrderEnabled(true);
    }

    public CustomOrderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setChildrenDrawingOrderEnabled(true);
    }

    public CustomOrderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setChildrenDrawingOrderEnabled(true);
    }

    /**
     * 方法：只置换当前获取焦点的view的绘制顺序：修改为最后一个绘制
     */
    String TAG = "zyc_test";

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int order = -1;
//        Log.d(TAG, "getChildDrawingOrder childCount: " + childCount + "---i: " + i);
        View view = getLayoutManager().getFocusedChild();
        if (null == view) {
            order = super.getChildDrawingOrder(childCount, i);
//            Log.d(TAG, "return super1: " + order);
            return order;
        }
        int position = indexOfChild(view);
        if (position < 0) {
            order = super.getChildDrawingOrder(childCount, i);
//            Log.d(TAG, "return super2: " + order);
            return order;
        }
        if (i == childCount - 1) {
//            Log.d(TAG, "return position: " + position);
            return position;
        }
        if (i == position) {
//            Log.d(TAG, "return childCount - 1: " + (childCount - 1));
            return childCount - 1;
        }
        order = super.getChildDrawingOrder(childCount, i);
//        Log.d(TAG, "return super3: " + order);
        return order;
    }
}
