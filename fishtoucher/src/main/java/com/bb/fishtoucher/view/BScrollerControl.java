package com.bb.fishtoucher.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.ist.fishtoucher.R;

/**
 * 滚动条
 */
public class BScrollerControl extends FrameLayout {
    String TAG = getClass().getSimpleName();
    private Context mContext;
    private View mThumb;
    private int mThumbId;
    private int mThumbWidth, mThumbHeight;

    public BScrollerControl(Context context) {
        this(context, null);
    }

    public BScrollerControl(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BScrollerControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        parseAttrs(context, attrs);
        addThumb();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void parseAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BScrollerControl);
        mThumbId = typedArray.getResourceId(R.styleable.BScrollerControl_thumbRes, R.drawable.scroller_drawable);
        mThumbWidth = (int) typedArray.getDimension(R.styleable.BScrollerControl_thumbWidth, -1);
        mThumbHeight = (int) typedArray.getDimension(R.styleable.BScrollerControl_thumbHeight, 50);
        typedArray.recycle();

//        LogUtils.d(TAG, "parseAttrs mThumbWidth: " + mThumbWidth + ",mThumbHeight: " + mThumbHeight);
    }

    private void addThumb() {
        mThumb = new View(mContext);
        mThumb.setBackgroundResource(mThumbId);
        int thumbWidth = mThumbWidth;
        if (thumbWidth == -1) {
            thumbWidth = LayoutParams.MATCH_PARENT;
        }
        LayoutParams lp = new FrameLayout.LayoutParams(thumbWidth, mThumbHeight);
        addView(mThumb, lp);
    }

    private float mLastFraction = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        int translation = -1;
        float minValidTouchPoint = getPaddingTop() + mThumbHeight / 2f;
        float maxValidTouchPoint = getHeight() - getPaddingBottom() - mThumbHeight / 2f;
        if (y > minValidTouchPoint && y < maxValidTouchPoint) {
            translation = (int) (y - minValidTouchPoint);
        } else if (y < minValidTouchPoint) {
            translation = 0;
        } else if (y > maxValidTouchPoint) {
            translation = getHeight() - getPaddingTop() - getPaddingBottom() - mThumbHeight;
        }

//        String temp = String.format("%.3f", translation * 1f / (getHeight() - mThumbHeight));//保留小数点后3位
//        float fraction = Float.parseFloat(temp);

        float fraction = calculateFraction(translation);
//        LogUtils.d(TAG, "onTouchEvent translation: " + translation + "---temp: " + temp + "---targetTranslation: " + targetTranslation);
        if (mLastFraction != fraction) {
            mThumb.setTranslationY(translation);
            onScroll(translation, fraction);
            mLastFraction = fraction;
        } else {
//            LogUtils.e(TAG, "onScroll no need to scroll ,mLastFraction == fraction : " + mLastFraction);
        }
        return true;
    }

    /**
     * 计算百分比
     * @param translation 偏移量
     * @return
     */
    private float calculateFraction(int translation) {
        //偏移量除以最大可偏移范围
        return translation * 1f / (getHeight() - getPaddingTop() - getPaddingBottom() - mThumbHeight);
    }

    /**
     * 计算偏移量
     * @param fraction 百分比
     * @return
     */
    private float calculateTranslation(float fraction) {
        return fraction * (getHeight() - getPaddingTop() - getPaddingBottom() - mThumbHeight);
    }

    private void onScroll(float scrollY, float fraction) {
//        LogUtils.v(TAG, "onScroll: " + fraction);
        if (mOnScrollChange != null) {
            mOnScrollChange.onScrollBarScroll(scrollY, fraction);
        }
    }

    /**
     * 更新thumb的位置
     * @param fraction 位置的百分比
     */
    public void setFraction(float fraction) {
        float translation = calculateTranslation(fraction);
        if (mLastFraction != fraction) {
            mThumb.setTranslationY(translation);
            mLastFraction = fraction;
        } else {
//            LogUtils.e(TAG, "setFraction(), mLastFraction == fraction : " + mLastFraction);
        }
    }

    /**************************************************************************************************/
    private OnScrollChange mOnScrollChange;

    public void setOnScrollChange(OnScrollChange onScrollChange) {
        mOnScrollChange = onScrollChange;
    }

    /**
     * 滚动回调
     */
    public interface OnScrollChange {
        /**
         * @param translation   thumb当前的偏移量（一般用不到）
         * @param fraction  thumb当前位置的百分比
         */
        void onScrollBarScroll(float translation, float fraction);
    }
    /**************************************************************************************************/
}
