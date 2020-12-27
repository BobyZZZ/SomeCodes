package com.bb.reading.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;

import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/8
 * Time: 1:03
 * 目录章节bean
 */
@Entity
public class NovelChapterInfo2 {
    @Id(autoincrement = true)
    Long id;
    int index;
    @Pick(value = "span.s2 > a", attr = Attrs.HREF)
    String novelID;
    @Pick("span.s2 > a")
    String name;
    @Pick(value = "span.s3 > a", attr = Attrs.HREF)
    String chapterId;
    @Pick("span.s3 > a")
    String chapterName;
    @Pick("span.s4")
    String author;
    @Pick("span.s5")
    String data;

    @Keep
    public NovelChapterInfo2(int index, String novelID, String name, String chapterId) {
        this.index = index;
        this.novelID = novelID;
        this.name = name;
        this.chapterId = chapterId;
    }


    @Generated(hash = 1621107053)
    public NovelChapterInfo2(Long id, int index, String novelID, String name,
            String chapterId, String chapterName, String author, String data) {
        this.id = id;
        this.index = index;
        this.novelID = novelID;
        this.name = name;
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.author = author;
        this.data = data;
    }


    @Generated(hash = 1056903652)
    public NovelChapterInfo2() {
    }


    public String getNovelID() {
        return novelID;
    }

    public void setNovelID(String novelID) {
        this.novelID = novelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NovelChapterInfo2{" +
                "id=" + id +
                ", index=" + index +
                ", novelID='" + novelID + '\'' +
                ", name='" + name + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", chapterName='" + chapterName + '\'' +
                ", author='" + author + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
