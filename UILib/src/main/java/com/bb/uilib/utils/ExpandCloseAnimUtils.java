package com.bb.uilib.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/4/17
 * Time: 17:14
 * 控制Recyclerview（支持竖直方向）、LinearLayout（支持横竖方向）展开/关闭
 */
public class ExpandCloseAnimUtils {
    String TAG = "ExpandCloseAnimUtils";
    private final ViewGroup mParentView;
    private long mDuration = 200;
    private boolean mExpanded = true;//当前状态（展开或关闭）
    private int mParentOriginPadding;//初始padding(top或right)
    private int mClosedPadding;//关闭时的padding大小（top或right）
    private AnimatorSet mAnimatorSet;

    public ExpandCloseAnimUtils(ViewGroup parent) {
        mParentView = parent;
        init(parent);
    }

    private View getAnchorView() {
        if (mParentView.getChildCount() <= 1) {
            return null;
        }
        if (isVertical()) {
            return mParentView.getChildAt(mParentView.getChildCount() - 1);//竖直方向时取最后一个
        } else {
            return mParentView.getChildAt(0);//水平方向时取第一个
        }
    }

    public void init(ViewGroup parent) {
        parent.post(new Runnable() {
            @Override
            public void run() {
                View anchorView = getAnchorView();
                if (anchorView != null && isVertical()) {
                    mParentOriginPadding = mParentView.getPaddingTop();
                    mClosedPadding = -anchorView.getTop();
                } else {
                    mParentOriginPadding = mParentView.getPaddingRight();
/*                    Log.d(TAG, "parentWidth: " + mParentView.getWidth() + ",anchorWidth: " + anchorView.getWidth()
                            + ",anchorRight: " + anchorView.getRight());*/
                    mClosedPadding = anchorView.getWidth() - mParentView.getRight();
                }
                Log.d(TAG, "mParentOriginPadding: " + mParentOriginPadding + ",closedPadding: " + mClosedPadding);
            }
        });
    }

    /**
     * 展开或关闭
     */
    public void toggle() {
        if (mExpanded) {
            close();
        } else {
            expand();
        }
    }

    /**
     * 关闭
     */
    public void close() {
        boolean isVertical = isVertical();
        startTranslateAndAlpha(mParentView, isVertical, false);
    }

    /**
     * 展开
     */
    public void expand() {
        boolean isVertical = isVertical();
        startTranslateAndAlpha(mParentView, isVertical, true);
    }

    /**
     * 获取方向
     * @return
     */
    public boolean isVertical() {
        boolean isVertical = true;
        if (mParentView instanceof RecyclerView) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) ((RecyclerView) mParentView).getLayoutManager();
            isVertical = layoutManager.getOrientation() == LinearLayoutManager.VERTICAL;
        } else if (mParentView instanceof LinearLayout) {
            isVertical = ((LinearLayout) mParentView).getOrientation() == LinearLayout.VERTICAL;
        }
        return isVertical;
    }

    private void startTranslateAndAlpha(final ViewGroup viewParent, final boolean vertical, final boolean show) {
        if (viewParent.getChildCount() <= 1) {
            return;
        }

        /*
        根据不同情况查找锚点View
         */
        View anchorView = getAnchorView();
        initAnimation(viewParent, vertical, show, anchorView);
    }

    /**
     * 初始化展开和关闭动画
     */
    private void initAnimation(final ViewGroup viewParent, final boolean vertical, final boolean show, View anchorView) {
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }

        final HashMap<View, Integer> viewTranslationMap = calculateTranslation(viewParent, vertical, anchorView);

        //计算动画的开始、结束值
        float startValue;
        if (vertical) {
            View sample = viewParent.getChildAt(viewParent.getChildCount() - 2);
            startValue = sample.getTranslationY() / (anchorView.getTop() - sample.getTop());
        } else {
            View sample = viewParent.getChildAt(1);
            startValue = sample.getTranslationX() / (anchorView.getLeft() - sample.getLeft());
        }
        float endValue = show ? 0 : 1;

        long duration = (long) (show ? startValue * mDuration : (endValue - startValue) * mDuration);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(startValue, endValue);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /*
                平移、透明度动画
                 */
                float value = (float) animation.getAnimatedValue();
                onTranslateAndAlphaAnim(value, vertical, anchorView, viewTranslationMap);
            }
        });

        //改变父view高度的动画
        int startPaddingTop = vertical ? viewParent.getPaddingTop() : viewParent.getPaddingRight();
        int endPaddingTop = show ? mParentOriginPadding : mClosedPadding;
        Log.d(TAG, "show: " + show + ",duration: " + duration +
                ",startPaddingTop: " + startPaddingTop + ",endPaddingTop: " + endPaddingTop);
        ValueAnimator paddingTopAnimator = ValueAnimator.ofInt(startPaddingTop, endPaddingTop);
        paddingTopAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /*
                高度动画
                 */
                int animatedValue = (int) animation.getAnimatedValue();
                onChangeHeightAnim(animatedValue, vertical, viewParent);
            }
        });

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(valueAnimator, paddingTopAnimator);
        mAnimatorSet.setDuration(duration);
        mAnimatorSet.setInterpolator(new LinearInterpolator());
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mExpanded = show;//记录展开或关闭的状态
            }
        });
        mAnimatorSet.start();
    }

    private void onTranslateAndAlphaAnim(float value, boolean isVertical, View anchorView, Map<View, Integer> viewTranslationMap) {
//        anchorView.setRotation(value * 360f);

        for (Map.Entry<View, Integer> entry : viewTranslationMap.entrySet()) {
            View view = entry.getKey();
            if (isVertical) {
                view.setTranslationY(value * entry.getValue());
            } else {
                view.setTranslationX(value * entry.getValue());
            }
            view.setAlpha(1 - value);
        }
    }

    private void onChangeHeightAnim(int animatedValue, boolean vertical, ViewGroup viewParent) {
        if (vertical) {
            viewParent.setPadding(viewParent.getPaddingLeft(),
                    animatedValue,
                    viewParent.getPaddingRight(),
                    viewParent.getPaddingBottom());
        } else {
            viewParent.setPadding(viewParent.getPaddingLeft(),
                    viewParent.getPaddingTop(),
                    animatedValue,
                    viewParent.getPaddingBottom());
        }
    }

    /**
     * 计算每个需要平移的view的偏移量
     */
    private HashMap<View, Integer> calculateTranslation(ViewGroup viewParent, boolean vertical, View anchorView) {
        final HashMap<View, Integer> viewTranslationMap = new HashMap<>();
        for (int i = 0; i < viewParent.getChildCount(); i++) {
            View child = viewParent.getChildAt(i);
            if (child != anchorView) {
                if (vertical) {
                    viewTranslationMap.put(child, anchorView.getTop() - child.getTop());
                } else {
                    viewTranslationMap.put(child, anchorView.getLeft() - child.getLeft());
                }
            }
        }
        return viewTranslationMap;
    }

    /**
     * 设置动画时间
     *
     * @param duration
     */
    public void setDuration(long duration) {
        this.mDuration = duration;
    }
}
