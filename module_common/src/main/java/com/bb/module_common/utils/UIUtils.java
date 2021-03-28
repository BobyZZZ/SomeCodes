package com.bb.module_common.utils;

import android.content.Context;

import com.bb.module_common.base.BaseApplication;

public class UIUtils {
    static String TAG = "UIUtils";
    private static Context mContext = BaseApplication.mContext;

    public static int dp2Px(int dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
