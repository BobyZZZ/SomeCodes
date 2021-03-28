package com.bb.module_novelmanager.utils;

import java.util.ArrayList;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/30
 * Time: 0:10
 */
public class JsoupUtils {
    private static String TAG = "JsoupUtils";

    private static final String HTML_BLANK = "&nbsp;";
    private static final String ANDROID_BLANK = " ";
    private static final String HTML_BR = "<br>";
    private static final String ANDROID_BR = "\n";
    private static ArrayList<String> mWordBlackList;

    public static String parseContentToAndroidText(String originText) {
        return parseContentToAndroidText(originText,true);
    }
        /**
         * 替换html格式空格、换行
         */
    public static String parseContentToAndroidText(String originText, boolean filter) {
        if (filter) {
            originText = filterBlackWord(originText);
        }
//        LogUtils.e(TAG, "bobyDebug:\n" + originText + "\n");
        originText = originText.replaceAll(HTML_BR, "");
        originText = originText.replaceAll(HTML_BLANK, ANDROID_BLANK);
        originText = originText.replaceAll("\n{2,}", ANDROID_BR);
//        LogUtils.w(TAG, "bobyDebug after:\n" + originText + "\n");
        return originText;
    }

    private static String filterBlackWord(String originText) {
        initWordBlackList();
        for (String blackWord : mWordBlackList) {
            originText = originText.replaceAll(blackWord,"");
        }
        return originText;
    }

    private static void initWordBlackList() {
        if (mWordBlackList == null) {
            mWordBlackList = new ArrayList<>();
            mWordBlackList.add("<!--go-->");
            mWordBlackList.add("<!--over-->");
            mWordBlackList.add("笔\\D*趣\\D*阁\\D*wｗw.ｂｉquｇｅ.ｉｎｆｏ");
        }
    }
}
