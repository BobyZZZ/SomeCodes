package com.ist.flyframeview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;

public class LogImageView extends androidx.appcompat.widget.AppCompatImageView {
    String TAG = "LogImageView";
    public LogImageView(Context context) {
        super(context);
    }

    public LogImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LogImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
    }
}
