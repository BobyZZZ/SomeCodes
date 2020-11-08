package com.ist.fishtoucher.entity;


/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/11/8
 * Time: 1:03
 */
public class NovelChapterInfo {
    private String novelID;
    private String name;
    private String url;

    public NovelChapterInfo(String novelID, String name, String url) {
        this.novelID = novelID;
        this.name = name;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NovelChapterInfo{" +
                "novelID='" + novelID + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
