package com.bb.reading.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/17
 * Time: 14:13
 */
public class GlideUtils {
    public static void load(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }
}
