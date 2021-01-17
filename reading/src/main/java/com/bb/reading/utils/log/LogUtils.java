package com.bb.reading.utils.log;

import android.util.Log;

public class LogUtils {
    private static final int LEVEL_VERBOSE = 4;
    private static final int LEVEL_DEBUG = 3;
    private static final int LEVEL_INFO = 2;
    private static final int LEVEL_WARN = 1;
    private static final int LEVEL_ERROR = 0;

    private static final int LOG_LEVEL = LEVEL_VERBOSE;
    private static final boolean DEBUG = true;

    public static void v(String TAG, String msg) {
        if (LOG_LEVEL >= LEVEL_VERBOSE && isDebug()) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String TAG, String msg) {
        if (LOG_LEVEL >= LEVEL_DEBUG && isDebug()) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String TAG, String msg) {
        if (LOG_LEVEL >= LEVEL_INFO && isDebug()) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String TAG, String msg) {
        if (LOG_LEVEL >= LEVEL_WARN && isDebug()) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (LOG_LEVEL >= LEVEL_ERROR && isDebug()) {
            Log.e(TAG, msg);
        }
    }

    private static boolean isDebug() {
        return DEBUG;
    }
}
