package com.ist.flyframeview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AnimationSet;

public class AnimationUtils {

    public static void scale(View target,float from,float to,long duration) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, "scaleX", from, to);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, "scaleY", from, to);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        set.playTogether(scaleX,scaleY);
        set.start();
    }
}
