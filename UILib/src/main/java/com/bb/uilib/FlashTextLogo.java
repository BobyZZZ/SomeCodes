package com.bb.uilib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FlashTextLogo extends FrameLayout {
    String TAG = "FlashTextLogo";

    private int mWidth;
    private int mHeight;
    private String mLogoText = "FlashText  Logo";
    private List<String> mLogoWords;
    private int mTextColor = Color.BLACK;
    private float mTextSize = 50f;
    private float mTextPadding = 2f;
    private int[] mGradientColors = {Color.BLACK, Color.RED, Color.BLACK};
    private float[] mGradientPositions = {0f, 0.5f, 1f};
    private Paint mPaint;
    private SparseArray<PointF> mTargetCoordinate = new SparseArray<>();
    private SparseArray<PointF> mRandomCoordinate = new SparseArray<>();
    private Matrix mGradientMatrix = new Matrix();
    private float mTranslateAnimValue;
    private ValueAnimator mValueAnimator;
    private boolean mAnimTranslate;
    private float mTotalWidth;
    private LinearGradient mLinearGradient;

    public FlashTextLogo(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        initWordArray();
        initPaint();
    }

    private void initWordArray() {
        mLogoWords = new ArrayList<>();
        for (char c : mLogoText.toCharArray()) {
            mLogoWords.add("" + c);
        }
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        initTextCoordinate();
        initAnim();
        mValueAnimator.start();
    }

    /**
     * 初始化字体坐标
     */
    private void initTextCoordinate() {
        float centerY = mHeight / 2 + mPaint.getTextSize() / 2;

        mTotalWidth = 0;
        for (int i = 0; i < mLogoWords.size(); i++) {
            float wordWidth = mPaint.measureText(mLogoWords.get(i));
            if (i != mLogoWords.size() - 1) {
                mTotalWidth += wordWidth + mTextPadding;
            } else {
                mTotalWidth += wordWidth;
            }
        }

        //验证总宽度
        if (mTotalWidth > mWidth) {
            throw new IllegalStateException("太长了,字体总长度！！！");
        }

        //最终坐标
        if (mTargetCoordinate.size() > 0) {
            mTargetCoordinate.clear();
        }
        float startX = mWidth / 2 - mTotalWidth / 2;//x轴开始位置
        for (int i = 0; i < mLogoWords.size(); i++) {
            mTargetCoordinate.put(i, new PointF(startX, centerY));
            float wordWidth = mPaint.measureText(mLogoWords.get(i));
            startX += wordWidth + mTextPadding;
        }


        //随机坐标
        if (mRandomCoordinate.size() > 0) {
            mRandomCoordinate.clear();
        }
        Random random = new Random();
        for (int i = 0; i < mLogoWords.size(); i++) {
            mRandomCoordinate.put(i, new PointF(random.nextInt(mWidth),
                    mPaint.getTextSize() + random.nextInt((int) (mHeight - mPaint.getTextSize()))));
        }
    }

    /**
     * 初始化平移、发光动画
     */
    private void initAnim() {
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setDuration(1500);
        mValueAnimator.setRepeatCount(1);//第一次控制平移，第二次控制发光
//        translateAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        translateAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTranslateAnimValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mAnimTranslate = true;
                Log.d(TAG, "onAnimationStart() called with: animation = [" + animation + "]");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.d(TAG, "onAnimationEnd() called with: animation = [" + animation + "]");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                mAnimTranslate = false;
                mLinearGradient = new LinearGradient(0, 0, mTotalWidth, 0, mGradientColors, mGradientPositions, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                Log.d(TAG, "onAnimationRepeat() called with: animation = [" + animation + "]");
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mAnimTranslate) {
            for (int i = 0; i < mLogoWords.size(); i++) {
                String word = mLogoWords.get(i);
                PointF endPoint = mTargetCoordinate.get(i);
                PointF startPoint = mRandomCoordinate.get(i);
                float startX = startPoint.x + (endPoint.x - startPoint.x) * mTranslateAnimValue;
                float startY = startPoint.y + (endPoint.y - startPoint.y) * mTranslateAnimValue;
                canvas.drawText(word, startX, startY, mPaint);
            }
        } else {
            mGradientMatrix.setTranslate(mTranslateAnimValue * mWidth, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            for (int i = 0; i < mLogoWords.size(); i++) {
                String word = mLogoWords.get(i);
                PointF endPoint = mTargetCoordinate.get(i);
                canvas.drawText(word, endPoint.x, endPoint.y, mPaint);
            }
        }
    }
}
