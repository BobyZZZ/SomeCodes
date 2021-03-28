package com.bb.module_novelmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bb.module_common.base.BaseApplication;


public class SPUtils {
    private static final String PREF_NAME = "sp";
    public static final String KEY_LAST_READ_NOVEL = "key_last_read_novel";
    public static final String KEY_LAST_READ_NOVEL_CHAPTER = "key_last_read_novel_chapter";
    public static final String KEY_LAST_READ_NOVEL_CHAPTER_STATE = "key_last_read_novel_chapter_state";

    public static void putString(String key, String value) {
        SharedPreferences preferences = BaseApplication.mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defaultValue) {
        return BaseApplication.mContext.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE).getString(key,defaultValue);
    }

    public static void putInt(String key, int value) {
        SharedPreferences preferences = BaseApplication.mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        preferences.edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defaultValue) {
        return BaseApplication.mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getInt(key, defaultValue);
    }
}
