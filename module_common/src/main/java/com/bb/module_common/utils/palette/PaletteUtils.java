package com.bb.module_common.utils.palette;

import android.graphics.Bitmap;

import androidx.palette.graphics.Palette;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/4/1
 * Time: 12:40
 */
public class PaletteUtils {
    static String TAG = "PaletteUtils";

    /**
     * 获取Bitmap的Palette6种颜色
     * @param bitmap
     * @return
     */
    public static PaletteColorInfo getPicThemeColor(Bitmap bitmap) {
        PaletteColorInfo paletteColorInfo = new PaletteColorInfo();
        Palette palette = Palette.from(bitmap).generate();
//            Palette palette = new Palette.Builder(bitmap)
//                    .setRegion(bitmap.getWidth() - regionWidth, bitmap.getHeight() - regionWidth, bitmap.getWidth(), bitmap.getHeight())
//                    .generate();

        if (palette.getVibrantSwatch() != null) {
            paletteColorInfo.setVibrantColor(palette.getVibrantSwatch().getRgb());
        }
        if (palette.getDarkVibrantSwatch() != null) {
            paletteColorInfo.setVibrantDarkColor(palette.getDarkVibrantSwatch().getRgb());
        }
        if (palette.getLightVibrantSwatch() != null) {
            paletteColorInfo.setVibrantLightColor(palette.getLightVibrantSwatch().getRgb());
        }
        if (palette.getMutedSwatch() != null) {
            paletteColorInfo.setMutedColor(palette.getMutedSwatch().getRgb());
        }
        if (palette.getDarkMutedSwatch() != null) {
            paletteColorInfo.setMutedDarkColor(palette.getDarkMutedSwatch().getRgb());
        }
        if (palette.getLightVibrantSwatch() != null) {
            paletteColorInfo.setMutedLightColor(palette.getLightVibrantSwatch().getRgb());
        }
        return paletteColorInfo;
    }
}
