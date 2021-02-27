package com.bb.reading.entity;

import me.ghui.fruit.annotations.Pick;

/**
 * 小说内容实体类
 */
@Pick("div#wrapper")
public class NovelChapterContentFruitBean {
    String TAG = "NovelChapterInfo";
    public String novelId;
    public String chapterId;
    @Pick("div.content_read > div.box_con > div.bookname > h1")
    public String chapterName;
    @Pick("div#content")
    public String content;

    @Override
    public String toString() {
        return "NovelChapterContentFruitBean{" +
                "novelId='" + novelId + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", chapterName='" + chapterName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
