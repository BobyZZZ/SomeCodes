package com.ist.fishtoucher.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.palette.graphics.Palette;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class UIUtils {
    static String TAG = "UIUtils";

    public static void ColorfulStatusBar(Activity activity) {
        ColorfulStatusBar(activity,false);
    }
    /**
     * 修改状态栏颜色，跟随内容布局颜色
     * @param activity
     */
    public static void ColorfulStatusBar(Activity activity,boolean darkTextColor) {
        final Window window = activity.getWindow();
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        window.getDecorView().setPadding(0, getStatusBarHeight(activity), 0, 0);
        if (darkTextColor) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        final ViewGroup content = activity.findViewById(android.R.id.content);
        content.post(new Runnable() {
            @Override
            public void run() {
                Drawable background = content.getChildAt(0).getBackground();
                if (background instanceof ColorDrawable) {
                    window.setStatusBarColor(((ColorDrawable)background).getColor());
                } else if (background instanceof BitmapDrawable) {
                    Observable.just(((BitmapDrawable)background).getBitmap())
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
                }
            }
        });
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int id = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(id);
    }
}
