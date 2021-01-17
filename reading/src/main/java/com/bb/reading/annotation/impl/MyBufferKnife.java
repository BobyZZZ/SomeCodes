package com.bb.reading.annotation.impl;

import android.app.Activity;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.bb.reading.annotation.BindView;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/1
 * Time: 23:54
 */
public class MyBufferKnife {
    static String TAG = "MyBufferKnife";

    public static void bind(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(BindView.class)) {
                try {
                    field.setAccessible(true);
                    View view = activity.findViewById(field.getAnnotation(BindView.class).value());
                    if (view instanceof TabLayout) {
                        field.set(activity, (TabLayout)view);
                    } else if (view instanceof ViewPager) {
                        field.set(activity, (ViewPager)view);
                    } else {
                        field.set(activity, view);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
