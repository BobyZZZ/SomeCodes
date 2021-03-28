package com.bb.module_common.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bb.module_common.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/31
 * Time: 22:16
 */
public class CustomBar extends FrameLayout implements View.OnClickListener {
    String TAG = "CustomBar";

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TITLE = 2;

    private ImageView mIvLeft;
    private TextView mTvLeft;
    private TextView mTvTitle;
    private ImageView mIvRight;
    private TextView mTvRight;
    private View mStatusBarPlaceholder;


    public CustomBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        ViewParent parent = getParent();
        if (parent != null && parent instanceof CollapsingToolbarLayout) {
            CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) parent;
            //配合app:layout_scrollFlags="scroll|exitUntilCollapsed"，使自定义的customBar停留在屏幕顶部不至于被划出去
            collapsingToolbarLayout.setMinimumHeight(h);
        }
    }

    private void init(Context context) {
        initView(context);
        initDefaultListener();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_bar, this, true);
        mStatusBarPlaceholder = findViewById(R.id.view_status_bar_placeholder);
        mIvLeft = findViewById(R.id.iv_left);
        mTvLeft = findViewById(R.id.tv_left);
        mTvTitle = findViewById(R.id.tv_title);
        mIvRight = findViewById(R.id.iv_right);
        mTvRight = findViewById(R.id.tv_right);
    }

    private void initDefaultListener() {
        mIvLeft.setOnClickListener(this);
    }

    public void setText(int type, String text) {
        switch (type) {
            case LEFT:
                mTvLeft.setText(text);
                break;
            case RIGHT:
                mTvRight.setText(text);
                break;
            case TITLE:
                mTvTitle.setText(text);
                break;
        }
    }

    public void setIcon(int type, int resId) {
        switch (type) {
            case LEFT:
                mIvLeft.setImageResource(resId);
                break;
            case RIGHT:
                mIvRight.setImageResource(resId);
                break;
        }
    }

    public void setIconColor(int type, int resId) {
        switch (type) {
            case LEFT:
                mIvLeft.setImageTintList(ColorStateList.valueOf(resId));
                break;
            case RIGHT:
                mIvRight.setImageTintList(ColorStateList.valueOf(resId));
                break;
        }
    }

    /**
     * 隐藏状态栏时，可通过此方法填充状态栏高度
     *
     * @param expand
     */
    public void expandToStatusBarPlace(boolean expand) {
        Resources resources = getContext().getResources();
        int id = resources.getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = resources.getDimensionPixelSize(id);
        mStatusBarPlaceholder.getLayoutParams().height = expand ? statusBarHeight : 0;
    }

    public TextView getTextView(int type) {
        switch (type) {
            case LEFT:
                return mTvLeft;
            case RIGHT:
                return mTvRight;
            case TITLE:
                return mTvTitle;
        }
        return null;
    }

    public void setOnIconClickListener(int type, OnClickListener onClickListener) {
        setOnIconClickListener(type, -1, onClickListener);
    }

    public void setOnIconClickListener(int type, int resId, OnClickListener onClickListener) {
        if (resId != -1) {
            setIcon(type,resId);
        }

        switch (type) {
            case LEFT:
                mIvLeft.setOnClickListener(onClickListener);
                break;
            case RIGHT:
                mIvRight.setOnClickListener(onClickListener);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_left) {
            Context context = getContext();
            if (context instanceof Activity) {
                ((Activity) context).onBackPressed();
            } else {
                Log.d(TAG, "onClick: ");
            }
        }
    }
}
