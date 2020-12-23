package com.bb.reading.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/8
 * Time: 1:03
 * 目录章节bean
 */
@Entity
public class NovelChapterInfo {
    @Id(autoincrement = true)
    private Long id;
    private int index;
    private String novelID;
    private String name;
    private String chapterId;

    @Generated(hash = 1307350200)
    public NovelChapterInfo() {
    }

    @Keep
    public NovelChapterInfo(int index, String novelID, String name, String chapterId) {
        this.index = index;
        this.novelID = novelID;
        this.name = name;
        this.chapterId = chapterId;
    }

    @Generated(hash = 1976943208)
    public NovelChapterInfo(Long id, int index, String novelID, String name,
            String chapterId) {
        this.id = id;
        this.index = index;
        this.novelID = novelID;
        this.name = name;
        this.chapterId = chapterId;
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

    @Override
    public String toString() {
        return "NovelChapterInfo{" +
                "id=" + id +
                ", index=" + index +
                ", novelID='" + novelID + '\'' +
                ", name='" + name + '\'' +
                ", chapterId='" + chapterId + '\'' +
                '}';
    }
}
