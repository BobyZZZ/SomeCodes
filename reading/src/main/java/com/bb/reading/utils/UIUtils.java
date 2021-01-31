package com.bb.reading.utils;

import android.content.Context;
import com.bb.reading.base.FishApplication;

public class UIUtils {
    static String TAG = "UIUtils";
    private static Context mContext = FishApplication.mContext;

    public static int dp2Px(int dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
