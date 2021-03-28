package com.bb.module_common.view.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/2/2
 * Time: 23:28
 */
public class FabInOutBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
    String TAG = "FabInOutBehavior";

    public FabInOutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes
     * @param type
     * @return 是否要处理滑动
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
//        Log.d(TAG, "onNestedPreScroll() called with: dx = [" + dx + "], dy = [" + dy + "], consumed = [" + consumed + "], type = [" + type + "]");
        if (dy > 0 && !mCurrentIn) {
            consumed[1] = dy;
            translate(child, true);
        } else if (dy < 0 && mCurrentIn) {
            translate(child, false);
            consumed[1] = dy;
        }
    }

    private final long mTotalDuration = 200;
    private boolean mCurrentIn = true;

    private void translate(View view, final boolean in) {
        Log.d(TAG, "translate() called with: in = [" + in + "]");
        float currentY = view.getTranslationY();
        float start, end;
        long duration;
        if (in) {
            start = currentY;
            end = 0;
            duration = (long) (currentY / view.getHeight() * mTotalDuration);
        } else {
            start = currentY;
            end = view.getHeight();
            duration = (long) ((end - start) / view.getHeight() * mTotalDuration);
        }

        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", start, end)
                .setDuration(duration);
        translationY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                mCurrentIn = in;
                super.onAnimationStart(animation);
            }
        });
        translationY.start();
    }
}
