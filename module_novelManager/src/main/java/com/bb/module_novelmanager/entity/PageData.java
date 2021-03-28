package com.bb.module_novelmanager.entity;

import java.util.List;

import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/12/28
 * Time: 0:29
 */
@Pick("div#wrapper")
public class PageData {
    @Pick("div.l > ul > li")
    private List<NovelInfo> mNovels;
    @Pick("div.item")
    private List<TopNovel> mTopNovels;

    public List<NovelInfo> getNovels() {
        return mNovels;
    }

    public void setNovels(List<NovelInfo> mChapters) {
        this.mNovels = mChapters;
    }

    public List<TopNovel> getTopNovels() {
        return mTopNovels;
    }

    public void setTopNovels(List<TopNovel> mTopNovels) {
        this.mTopNovels = mTopNovels;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "mChapters=" + mNovels +
                ", mTopNovels=" + mTopNovels +
                '}';
    }

    /**
     * banner
     */
    public static class TopNovel {
        @Pick(value = "div.image > a",attr = Attrs.HREF)
        private String mNovelDetailUrl;
        @Pick(value = "div.image > a > img", attr = Attrs.SRC)
        private String mCoverUrl;
        @Pick("dl > dt > span")
        private String mAuthor;
        @Pick("dl > dt > a")
        private String mName;
        @Pick("dl > dd")
        private String mIntroduction;

        public String getNovelDetailUrl() {
            return mNovelDetailUrl;
        }

        public void setNovelDetailUrl(String mNovelDetailUrl) {
            this.mNovelDetailUrl = mNovelDetailUrl;
        }

        public String getCoverUrl() {
            return mCoverUrl;
        }

        public void setCoverUrl(String mCoverUrl) {
            this.mCoverUrl = mCoverUrl;
        }

        public String getAuthor() {
            return mAuthor;
        }

        public void setAuthor(String mAuthor) {
            this.mAuthor = mAuthor;
        }

        public String getName() {
            return mName;
        }

        public void setName(String mName) {
            this.mName = mName;
        }

        public String getIntroduction() {
            return mIntroduction;
        }

        public void setIntroduction(String mIntroduction) {
            this.mIntroduction = mIntroduction;
        }

        @Override
        public String toString() {
            return "TopNovel{" +
                    "mNovelDetailUrl='" + mNovelDetailUrl + '\'' +
                    ", mCoverUrl='" + mCoverUrl + '\'' +
                    ", mAuthor='" + mAuthor + '\'' +
                    ", mName='" + mName + '\'' +
                    ", mIntroduction='" + mIntroduction + '\'' +
                    '}';
        }
    }
}
