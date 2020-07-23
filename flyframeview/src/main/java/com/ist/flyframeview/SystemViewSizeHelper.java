package com.ist.flyframeview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.nfc.Tag;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class SystemViewSizeHelper {
    static String TAG = "SystemViewSizeHelper";

    /**
     * 活动屏幕信息
     */
    private static WindowManager wm;

    /**
     * 获取真实屏幕高度
     *
     * @return
     */
    public static int getRealHeight(Context context) {
        if (null == wm) {
            wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取标题栏高度有问题
     * @param context
     */
    public static void test(Activity context) {
        //屏幕
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Log.e(TAG, "屏幕高:" + dm.heightPixels);

        //应用区域
        Rect outRect1 = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        Log.e(TAG, "应用区顶部" + outRect1.top);
        Log.e(TAG, "应用区高" + outRect1.height());

        //View绘制区域
        Rect outRect2 = new Rect();
        context.findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect2);
        Log.e(TAG, "View绘制区域顶部-错误方法：" + outRect2.top);   //不能像上边一样由outRect2.top获取，这种方式获得的top是0，可能是bug吧
        int viewTop = context.findViewById(Window.ID_ANDROID_CONTENT).getTop();   //要用这种方法
        Log.e(TAG, "View绘制区域顶部-正确方法：" + viewTop);
        Log.e(TAG, "View绘制区域高度：" + outRect2.height());

        int titleHeight1 = viewTop - outRect1.top;
        Log.e(TAG, "标题栏高度-方法1：" + titleHeight1);
    }
}
