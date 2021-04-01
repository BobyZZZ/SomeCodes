package com.bb.module_common.utils.palette;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/4/1
 * Time: 16:26
 * 封装图片的6中Palette颜色
 */
public class PaletteColorInfo {
    public int mVibrantColor;
    public int mVibrantDarkColor;
    public int mVibrantLightColor;
    public int mMutedColor;
    public int mMutedDarkColor;
    public int mMutedLightColor;

    public void setVibrantColor(int vibrantColor) {
        mVibrantColor = vibrantColor;
    }

    public void setVibrantDarkColor(int vibrantDarkColor) {
        mVibrantDarkColor = vibrantDarkColor;
    }

    public void setVibrantLightColor(int vibrantLightColor) {
        mVibrantLightColor = vibrantLightColor;
    }

    public void setMutedColor(int mutedColor) {
        mMutedColor = mutedColor;
    }

    public void setMutedDarkColor(int mutedDarkColor) {
        mMutedDarkColor = mutedDarkColor;
    }

    public void setMutedLightColor(int mutedLightColor) {
        mMutedLightColor = mutedLightColor;
    }
}
