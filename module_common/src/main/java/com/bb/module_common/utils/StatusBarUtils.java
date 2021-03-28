package com.bb.module_common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.Window;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/31
 * Time: 21:39
 */
public class StatusBarUtils {
    public static final int DefaultStatusBarColor = -1;
    String TAG = "StatusBarUtils";

    public static void setStatusBarColor(Activity activity, int color) {
        activity.getWindow().setStatusBarColor(color);
    }

    /**
     * 修改状态栏颜色，跟随内容布局颜色
     *
     * @param activity
     * @param statusBarColor 状态栏颜色
     * @param darkTextColor  状态栏黑色字体
     * @param fullScreen     页面内容是否显示在状态栏底下
     */
    public static void setStatusBar(Activity activity, int statusBarColor, boolean darkTextColor, boolean fullScreen) {
        int flag = 0;
        if (fullScreen) {
            //布局延申到状态栏下
            flag |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        }
        if (darkTextColor) {
            //状态栏黑色字体
            flag |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }

        Window window = activity.getWindow();
        window.getDecorView().setSystemUiVisibility(flag);
        if (statusBarColor != DefaultStatusBarColor) {
            window.setStatusBarColor(statusBarColor);
        }
        /*final ViewGroup content = activity.findViewById(android.R.id.content);
        content.post(new Runnable() {
            @Override
            public void run() {
                Drawable background = content.getChildAt(0).getBackground();
                if (background instanceof ColorDrawable) {
                    setStatusBarColor(activity,((ColorDrawable)background).getColor());
                } else if (background instanceof BitmapDrawable) {
                    Observable.just(((BitmapDrawable) background).getBitmap())
                            .map(new Function<Bitmap, Integer>() {
                                @Override
                                public Integer apply(Bitmap bitmap) throws Exception {
                                    LogUtils.d(TAG, "apply in: " + Thread.currentThread().getName());
                                    Palette generate = Palette.from(bitmap).generate();
                                    int vibrantColor = generate.getVibrantColor(Color.TRANSPARENT);
                                    return vibrantColor;
                                }
                            })
                            .compose(RxUtils.<Integer>rxScheduers())
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer vibrantColor) throws Exception {
                                    LogUtils.d(TAG, "accept in: " + Thread.currentThread().getName());
                                    window.setStatusBarColor(vibrantColor);
                                }
                            });
                } else {
                    //透明
                    setStatusBarColor(activity,Color.RED);
                }
            }
        });*/
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int id = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(id);
    }
}
