package com.bb.module_common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/3/31
 * Time: 0:41
 */
public class WrapHeightViewPager extends ViewPager {
    String TAG = "WrapHeightViewPager";

    public WrapHeightViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxHeight = 0;
        int childCount = getChildCount();
//        Log.d(TAG, "onMeasure childCount: " + childCount);
        ViewGroup.LayoutParams childLayoutParams;
        int childHeightMeasureSpec;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            childLayoutParams = child.getLayoutParams();
//            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, getPaddingStart() + getPaddingEnd(), childLayoutParams.width);
            childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), childLayoutParams.height);
            child.measure(widthMeasureSpec,childHeightMeasureSpec);
            int measuredHeight = child.getMeasuredHeight();
//            Log.d(TAG, i + "measuredHeight: "  +  measuredHeight);
            maxHeight = Math.max(maxHeight,measuredHeight);
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(maxHeight,MeasureSpec.EXACTLY));
    }
}
