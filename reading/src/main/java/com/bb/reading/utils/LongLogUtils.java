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

    public static void v(String TAG, String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < strLength / LOG_MAXLENGTH + 1; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                LogUtils.v(TAG + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                LogUtils.v(TAG + i, msg.substring(start, strLength));
                break;
            }
        }
    }

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
                LogUtils.i(TAG + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                LogUtils.i(TAG + i, msg.substring(start, strLength));
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
                LogUtils.w(TAG + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                LogUtils.w(TAG + i, msg.substring(start, strLength));
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
                LogUtils.e(TAG + i, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                LogUtils.e(TAG + i, msg.substring(start, strLength));
                break;
            }
        }
    }
}