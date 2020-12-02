package com.bb.uilib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class CustomDialog extends Dialog {
    String TAG = "CustomDialog";

    private CustomStyle mCustomStyle;
    private int mLayoutID;
    private View mContentView;
    private int mWidth, mHeight;
    private int mGravity;
    private int mWindowAnimation;
    private boolean mFullScreen;
    private float mDimAmount;
    private int mBackgroundColor;

    public static Builder create(Context context) {
        return new Builder(context);
    }

    private CustomDialog(Builder builder) {
        super(builder.context);
        try {
            initField(builder);
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initField(Builder builder) throws Exception {
        this.mCustomStyle = builder.customStyle;
        this.mLayoutID = builder.layoutID;
        this.mContentView = builder.contentView;
        this.mWidth = builder.width;
        this.mHeight = builder.height;
        this.mGravity = builder.gravity;
        this.mWindowAnimation = builder.windowAnimation;
        this.mFullScreen = builder.fullScreen;
        this.mDimAmount = builder.dimAmount;
        this.mBackgroundColor = builder.backgroundColor;

        if (mLayoutID != 0 && mContentView != null) {
            throw new Exception("can't not set layoutId and contentView at same time");
        }
    }

    private void init() {
        Window window = getWindow();
        //设置自定义布局
        if (mLayoutID != 0) {
            setContentView(mLayoutID);
        }
        if (mContentView != null) {
            setContentView(mContentView);
        }

        window.setGravity(mGravity);
        if (mWindowAnimation != 0) {
            window.setWindowAnimations(mWindowAnimation);
        }


        //全屏
//        if (mFullScreen) {
        View decorView = getWindow().getDecorView();
        decorView.setBackgroundColor(mBackgroundColor);
        decorView.setPadding(0, 0, 0, 0);
//        }
        if (mDimAmount != -1) {
            window.setDimAmount(mDimAmount);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //默认宽、高: wrap_content
        getWindow().setLayout(mWidth, mHeight);

        //会覆盖掉前边设置过的样式
        if (mCustomStyle != null && getWindow() != null) {
            mCustomStyle.setFinalStyle(getWindow());
        }
    }

    /**
     * 自定义样式接口,通过window设置宽高、动画等
     */
    public interface CustomStyle {
        void setFinalStyle(Window window);
    }

    /***************************************************Builder start*********************************************************/
    public static class Builder {
        Context context;
        CustomStyle customStyle;
        int layoutID;
        View contentView;
        int width = WindowManager.LayoutParams.WRAP_CONTENT, height = WindowManager.LayoutParams.WRAP_CONTENT;
        int gravity = Gravity.CENTER;
        int windowAnimation;
        boolean fullScreen;
        float dimAmount = -1;
        int backgroundColor = Color.WHITE;

        private Builder(@Nullable Context context) {
            this.context = context;
        }

        public CustomDialog build() {
            return new CustomDialog(this);
        }

        public Builder setCustomStyle(CustomStyle customStyle) {
            this.customStyle = customStyle;
            return this;
        }

        public Builder setLayoutID(int layoutID) {
            this.layoutID = layoutID;
            return this;
        }

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setWindowAnimation(int windowAnimation) {
            this.windowAnimation = windowAnimation;
            return this;
        }

/*        public Builder setFullScreen(boolean fullScreen) {
            this.fullScreen = fullScreen;
            return this;
        }*/

        public Builder setDimAmount(float dimAmount) {
            this.dimAmount = dimAmount;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }
    }
    /****************************************************Builder end**********************************************************/
}
