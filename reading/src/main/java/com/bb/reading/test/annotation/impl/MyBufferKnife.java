package com.bb.reading.test.annotation.impl;

import android.app.Activity;
import android.util.Log;

import com.bb.reading.test.annotation.BindView;

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
                    field.set(activity, activity.findViewById(field.getAnnotation(BindView.class).value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
