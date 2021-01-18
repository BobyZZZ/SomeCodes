package com.bb.reading.utils;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/29
 * Time: 14:09
 */
public class NovelSpUtils {
    /**
     * 保存该小说的最后阅读章节
     *
     * @param novelId   小说id
     * @param chapterId 章节id
     */
    public static void saveLastReadChapter(String novelId, String chapterId) {
        SPUtils.putString(SPUtils.KEY_LAST_READ_NOVEL_CHAPTER + "_" + novelId, chapterId);
    }

    /**
     * 获取小说最后阅读章节
     *
     * @param novelId      小说id
     * @param defaultValue 默认值
     * @return 小说最后阅读章节
     */
    public static String getLastReadChapter(String novelId, String... defaultValue) {
        String def = defaultValue.length > 0 ? defaultValue[0] : "";
        return SPUtils.getString(SPUtils.KEY_LAST_READ_NOVEL_CHAPTER + "_" + novelId, def);
    }


    /**
     * 保存最后阅读哪本小说
     *
     * @param novelId
     */
    public static void saveLastReadNovel(String novelId) {
        SPUtils.putString(SPUtils.KEY_LAST_READ_NOVEL, novelId);
    }

    /**
     * 获取最后阅读哪本小说
     */
    public static String getLastReadNovel(String defaultValue) {
        return SPUtils.getString(SPUtils.KEY_LAST_READ_NOVEL, defaultValue);
    }

    /**
     * 记录上次阅读的位置
     */
    public static void saveLastReadingPosition(String novelId, int alreadyScrollY) {
        SPUtils.putInt(SPUtils.KEY_LAST_READ_NOVEL_CHAPTER_STATE + "_" + novelId, alreadyScrollY);
    }

    /**
     * 获取上次阅读的位置
     */
    public static int getLastReadingPosition(String novelId) {
        return SPUtils.getInt(SPUtils.KEY_LAST_READ_NOVEL_CHAPTER_STATE + "_" + novelId, 0);
    }
}
