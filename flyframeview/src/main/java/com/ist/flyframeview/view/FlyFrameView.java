package com.ist.flyframeview.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.LinearInterpolator;

import com.ist.flyframeview.R;

public class FlyFrameView extends View {
    String TAG = getClass().getSimpleName();
    private ViewParent mRoot;
    private long mDuration = 250;

    public FlyFrameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setBackground(getResources().getDrawable(R.drawable.bg_fly_view));
    }

    public void moveToTarget(View targetView) {
        Rect currentRect = getCoorRelativeToRootView(this);
        Rect targetRect = getCoorRelativeToRootView(targetView);
        int leftDiff = targetRect.left - currentRect.left;
        int topDiff = targetRect.top - currentRect.top;
        //改变大小
        if (getWidth() != targetView.getWidth() || getHeight() != targetView.getHeight()) {
            changeSize(targetView.getWidth(), targetView.getHeight());
        }
        //改变位置
        if (leftDiff != 0 || topDiff != 0) {
            changeLocation(leftDiff, topDiff);
        }
    }

    /**
     * 位移动画
     *
     * @param leftDiff X轴偏移量
     * @param topDiff  Y轴偏移量
     */
    private void changeLocation(int leftDiff, int topDiff) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(this, "translationX", leftDiff);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(this, "translationY", topDiff);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(mDuration);
        animatorSet.playTogether(translationX, translationY);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }

    private void changeSize(int width, final int height) {
        final ViewGroup.LayoutParams params = getLayoutParams();
        final int currentHeight = params.height;
        ValueAnimator widthAnimator = ValueAnimator.ofInt(getWidth(), width);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                params.width = value;

                //根据百分比改变高度
                float fraction = animation.getAnimatedFraction();
                params.height = (int) (currentHeight + (height - currentHeight) * fraction);

                setLayoutParams(params);
            }
        });
        widthAnimator.setDuration(mDuration);
        widthAnimator.setInterpolator(new LinearInterpolator());
        widthAnimator.start();
    }


    /**
     * 用于获取目标控件相对（同一个）夫容器的坐标
     *
     * @param target 目标
     * @return
     */
    private Rect getCoorRelativeToRootView(View target) {
        Rect rect = new Rect();
        mRoot = getParent();
        ((ViewGroup) mRoot).offsetDescendantRectToMyCoords(target, rect);
        Log.d(TAG, "getCoor: " + rect);
        return rect;
    }
}
