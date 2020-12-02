package com.bb.fishtoucher.constant;

public class GlobalConstant {
    private static boolean FISH_MODE = true;

    public static boolean isFishMode() {
        return FISH_MODE;
    }

    public static void setFishMode(boolean isFishMode) {
        FISH_MODE = isFishMode;
    }
}
