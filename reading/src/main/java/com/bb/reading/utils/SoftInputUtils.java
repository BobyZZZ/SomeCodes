package com.bb.reading.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bb.reading.base.FishApplication;

public class SoftInputUtils {
    /**
     * 显示输入法
     */
    public static void showSoftInput() {
        InputMethodManager systemService = (InputMethodManager) FishApplication.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        systemService.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 根据获取焦点的view隐藏输入法
     * @param view
     */
    public static void hideSoftInput(View view) {
        InputMethodManager systemService = (InputMethodManager) FishApplication.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        systemService.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    public static void hideSoftInput(Window window) {
        View view = window.getCurrentFocus();
        if (view == null) {
            View decorView = window.getDecorView();
            View tagView = decorView.findViewWithTag("keyboardTagView");
            if (tagView == null) {
                view = new EditText(window.getContext());
                view.setTag("keyboardTagView");
                ((ViewGroup)decorView).addView(view,0,0);
            } else {
                view = tagView;
            }
            view.requestFocus();
        }
        hideSoftInput(view);
    }

    public static void hideSoftInput(Activity activity) {
        hideSoftInput(activity.getWindow());
    }
}
