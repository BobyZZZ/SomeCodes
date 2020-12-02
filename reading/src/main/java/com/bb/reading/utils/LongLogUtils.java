package com.bb.reading.utils;

import android.util.Log;

/**
 * 打印超长日志的工具类
 *
 * @author donkor
 */
public class LongLogUtils {
    //规定每段显示的长度
    private static int LOG_MAXLENGTH = 70;

    public static void d(String TAG, String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < strLength / LOG_MAXLENGTH + 1; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                LogUtils.d(TAG + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                LogUtils.d(TAG + i, msg.substring(start, strLength));
                break;
            }
        }
    }

    public static void i(String TAG, String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < strLength / LOG_MAXLENGTH + 1; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.i(TAG + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.i(TAG + i, msg.substring(start, strLength));
                break;
            }
        }
    }

    public static void w(String TAG, String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < strLength / LOG_MAXLENGTH + 1; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.w(TAG + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.w(TAG + i, msg.substring(start, strLength));
                break;
            }
        }
    }

    public static void e(String TAG, String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < strLength / LOG_MAXLENGTH + 1; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.e(TAG + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                Log.e(TAG + i, msg.substring(start, strLength));
                break;
            }
        }
    }
}