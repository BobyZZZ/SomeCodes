package com.bb.uilib.loadingView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ist.uilib.R;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/12/1
 * Time: 21:22
 */
public class CircleLoadingView extends RelativeLayout {
    String TAG = "CircleLoadingView";

    private float mStartFraction = 0.4f;//动画初始fraction
    private float mEndFraction = 1f;//动画结束fraction
    private int mChildWidth = 50;//子view大小
    private int mChildResId = R.drawable.shape_circle_black;//子view资源文件，默认为黑色实心圆
    private int mChildCount = 3;//子view数量

    public CircleLoadingView(@NonNull Context context) {
        this(context, null);
    }

    public CircleLoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        removeAllViews();
        setGravity(Gravity.CENTER);
        for (int i = 0; i < mChildCount; i++) {
            View view = new View(getContext());
            view.setBackgroundResource(mChildResId);
            ViewGroup.LayoutParams lp = generateDefaultLayoutParams();
            lp.width = mChildWidth;
            lp.height = mChildWidth;
            addView(view, lp);
        }
        setLocation(mStartFraction);

        start();
    }

    private void start() {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator rotation = ObjectAnimator.ofFloat(this, "rotation", 0, 360);
        rotation.setRepeatCount(ValueAnimator.INFINITE);

        ValueAnimator animator = ValueAnimator.ofFloat(mStartFraction, mEndFraction);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = (float) animation.getAnimatedValue();
                setLocation(fraction);
            }
        });

        set.setDuration(1000);
        set.setInterpolator(new LinearInterpolator());
        set.playTogether(rotation, animator);
        set.start();
    }

    /**
     * 设置子view位置
     *
     * @param fraction
     */
    private void setLocation(float fraction) {
        int childCount = getChildCount();
        int radius = getMaxTranslation();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            double degree = i * 360d / childCount;
            double dx = fraction * radius * Math.sin(degree * Math.PI / 180);
            double dy = -fraction * radius * Math.cos(degree * Math.PI / 180);
//            LogUtils.d(TAG, "start i: " + dx + "," + dy);
            child.setTranslationX((float) dx);
            child.setTranslationY((float) dy);
        }
    }

    private int getMaxTranslation() {
/*        int childWidth = 50;
        if (getChildCount() > 0) {
            childWidth = getChildAt(0).getWidth();
        }*/
        return Math.min(getWidth(), getHeight()) / 2 - mChildWidth / 2;
    }
}
