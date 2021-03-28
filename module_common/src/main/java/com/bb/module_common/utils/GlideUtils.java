package com.bb.module_common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bb.module_common.utils.log.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.util.concurrent.ExecutionException;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/17
 * Time: 14:13
 */
public class GlideUtils {
    static String TAG = "GlideUtils";

    public static void load(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    public static void load(Context context, String url, final ImageView... imageViews) {
        Glide.with(context).load(url).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                int intrinsicWidth = resource.getIntrinsicWidth();
                int intrinsicHeight = resource.getIntrinsicHeight();
                LogUtils.d(TAG, "onResourceReady [intrinsicWidth]: " + intrinsicWidth + ",[intrinsicHeight]: " + intrinsicHeight);
                for (ImageView imageView : imageViews) {
                    imageView.setImageDrawable(resource);
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }

    /**
     * 获取bitmap
     *
     * @param imageView
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static Bitmap getBitmap(String url, ImageView imageView) {
        try {
            return Glide.with(imageView.getContext())
                    .asBitmap()
                    .load(url)
                    .override(imageView.getWidth(), imageView.getHeight())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void blur(Bitmap bitmap, ImageView imageView, float radius) {
        Bitmap blur = renderScriptBlur(imageView.getContext(), bitmap, radius);
        imageView.setImageBitmap(blur);
    }

    private static Bitmap renderScriptBlur(Context context, Bitmap source, float radius) {
        LogUtils.d(TAG, "blur: start");
        //(1)
        RenderScript rs = RenderScript.create(context);

        // Allocate memory for Renderscript to work with
        //(2)
        Allocation tmpIn = Allocation.createFromBitmap(rs, source);
        Allocation tmpOut = Allocation.createTyped(rs, tmpIn.getType());

        //(3)
        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //(4)
        scriptIntrinsicBlur.setInput(tmpIn);
        //(5)
        // Set the blur radius
        scriptIntrinsicBlur.setRadius(radius);
        //(6)
        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(tmpOut);
        //(7)
        // Copy the output to the blurred bitmap
        tmpOut.copyTo(source);
        //(8)
        rs.destroy();
        LogUtils.d(TAG, "blur: end");
        return source;
    }
}
