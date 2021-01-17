package com.bb.reading.utils;

import android.content.Context;

import com.bb.reading.base.FishApplication;

/**
 * Created by Boby on 2019/6/17.
 */

public class ResUtils {
    private static Context mContext = FishApplication.mContext;

    public static String[] getStringArray(int id) {
        return mContext.getResources().getStringArray(id);
    }

    public static int getColor(int id) {
        return mContext.getResources().getColor(id);
    }
}
