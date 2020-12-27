package com.bb.reading.entity;

import java.util.List;

import me.ghui.fruit.annotations.Pick;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/12/28
 * Time: 0:29
 */
@Pick("div#wrapper")
public class NovelsSort {
    @Pick("div.l > ul > li")
    List<NovelChapterInfo2> mChapters;

    @Override
    public String toString() {
        return "NovelsSort{" +
                "mChapters=" + mChapters +
                '}';
    }
}
