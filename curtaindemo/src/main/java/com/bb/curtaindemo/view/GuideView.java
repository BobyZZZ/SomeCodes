package com.bb.curtaindemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.bb.curtaindemo.bean.HollowInfo;

import java.util.ArrayList;

public class GuideView extends View {
    String TAG = getClass().getSimpleName();
    private Paint mPaint;
    private int mBackgroundColor = 0x88000000;
    private ArrayList<HollowInfo> mHollowInfos;

    public GuideView(Context context) {
        super(context);
        init();
    }

    public GuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setHollowInfos(ArrayList<HollowInfo> hollowInfos) {
        mHollowInfos = hollowInfos;
//        invalidate();
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(getScreenWidth(), getScreenHeight());
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            count = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);
        } else {
            count = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        }

        //绘制透明背景
        drawBackground(canvas);
        //绘制挖空区域
        drawHollowFields(canvas);

        canvas.restoreToCount(count);
    }

    /**
     * 绘制所有挖空区域
     *
     * @param canvas
     */
    private void drawHollowFields(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        if (mHollowInfos != null && !mHollowInfos.isEmpty()) {
            for (HollowInfo info : mHollowInfos) {
                drawSingleHollow(canvas, info);
            }
        }
    }

    /**
     * 绘制单个挖空区域
     *
     * @param canvas
     * @param info
     */
    private void drawSingleHollow(Canvas canvas, HollowInfo info) {
        info.getDrawingRect();
        Log.e(TAG, "drawSingleHollow info: " + info);

        //计算绘制的区域rect
        int[] locations = new int[2];
        info.targetView.getLocationOnScreen(locations);
        info.rect.left = locations[0];
        info.rect.top = locations[1] - getStatusBarHeight(getContext());
        info.rect.right += info.rect.left;
        info.rect.bottom += info.rect.top;

        realDrawSingleHollow(canvas, info);
    }

    private void realDrawSingleHollow(Canvas canvas, HollowInfo info) {
        if (info.getHollowShape() != null) {
            info.getHollowShape().drawHollowShape(canvas,mPaint,info.rect);//根绝自定义形状绘制挖空区域
            return;
        }

        if (!drawHollowIfMatch(canvas, info)) {//根据view背景绘制挖空区域
            canvas.drawRect(info.rect, mPaint);//根据view区域绘制挖空区域
        }
    }

    /**
     * 以背景绘制
     *
     * @param canvas
     * @param info
     * @return
     */
    private boolean drawHollowIfMatch(Canvas canvas, HollowInfo info) {
        Drawable background = info.targetView.getBackground();
        if (background instanceof GradientDrawable) {//shape背景
            drawGradientDrawable(canvas, info, (GradientDrawable) background);
            return true;
        }
        if (background instanceof StateListDrawable) {//selector背景
            if (background.getCurrent() instanceof GradientDrawable) {
//                Log.e(TAG, "drawHollowIfMatch: StateListDrawable" + "---" + ((StateListDrawable) background).getStateCount());
                drawGradientDrawable(canvas, info, (GradientDrawable) background.getCurrent());
            }
            return true;
        }
        return false;
    }

    /**
     * 根据背景绘制挖空区域
     * @param canvas
     * @param info
     * @param background 目前支持shape、selector
     */
    private void drawGradientDrawable(Canvas canvas, HollowInfo info, GradientDrawable background) {
        //背景为shape,可能需要绘制圆角
/*        int shape = 0;
        Object mGradientState = null;
        try {
            Field field = Class.forName("android.graphics.drawable.GradientDrawable").getDeclaredField("mGradientState");
            field.setAccessible(true);
            mGradientState = field.get(background);
            Field fieldOfShape = mGradientState.getClass().getDeclaredField("mShape");
            fieldOfShape.setAccessible(true);
            shape = (int) fieldOfShape.get(mGradientState);
            Log.e(TAG, "drawGradientDrawable shape: " + shape);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        float radius = 0;
        //获取radius
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            radius = background.getCornerRadius();
        }/* else {
            Field mRadius = null;
            try {
                mRadius = mGradientState.getClass().getField("mRadius");
                mRadius.setAccessible(true);
                radius = (float) mRadius.get(mGradientState);
                Log.e(TAG, "drawGradientDrawable radius2: " + radius);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }*/

        if (background.getShape() == GradientDrawable.OVAL) {
            canvas.drawOval(info.rect.left, info.rect.top, info.rect.right, info.rect.bottom, mPaint);
        } else {
            radius = Math.min(radius,
                    Math.min(info.targetView.getWidth(), info.targetView.getHeight()) / 2f);
            Log.e(TAG, "drawGradientDrawable radius: " + radius);
            canvas.drawRoundRect(info.rect.left, info.rect.top, info.rect.right, info.rect.bottom, radius, radius, mPaint);
        }
    }

    /**
     * 绘制透明背景
     *
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        mPaint.setXfermode(null);
        mPaint.setColor(mBackgroundColor);
//        Log.e(TAG, "drawBackground getWidth: " + getWidth() + ",getHeight: " + getHeight());
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        Log.e(TAG, "getScreenWidth: " + width);
        return width;
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    private int getScreenHeight() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int height = windowManager.getDefaultDisplay().getHeight();
        Log.e(TAG, "getScreenWidth: " + height);
        return height;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
}
