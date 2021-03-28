package com.bb.module_common.utils;

import android.content.Context;

import com.bb.module_common.base.BaseApplication;

/**
 * Created by Boby on 2019/6/17.
 */

public class ResUtils {
    private static Context mContext = BaseApplication.mContext;

    public static String getString(int id,String... s) {
        return mContext.getResources().getString(id, (Object[]) s);
    }

    public static int[] getIntArray(int id) {
        return mContext.getResources().getIntArray(id);
    }

    public static String[] getStringArray(int id) {
        return mContext.getResources().getStringArray(id);
    }

    public static int getColor(int id) {
        return mContext.getResources().getColor(id);
    }
}
