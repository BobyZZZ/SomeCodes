package com.bb.reading.entity;

import java.util.List;

import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/15
 * Time: 22:28
 * 主页数据
 */
@Pick("div#wrapper")
public class HomePageBean {
    /**
     * 头条
     */
    @Pick("div#main > div#hotcontent > div.l > div.item")
    public List<PageData.TopNovel> bannerNovels;
//    /**
//     * 上期强推
//     */
//    @Pick("")
//    public List<NewUpdate.Item> lastRecommends;
    /**
     * 玄幻小说、修真小说、网游小说、穿越小说、都市小说、科幻小说
     */
    @Pick("div.content")
    public List<SortHotNovel> sortHotNovels;
    /**
     * 最近更新小说列表
     */
    @Pick("div#main > div#newscontent > div.l")
    public NewUpdate updateRecent;
    /**
     * 最新添加小说
     */
    @Pick("div#main > div#newscontent > div.r")
    public NewUpdate updatePublish;

    @Override
    public String toString() {
        return "HomePageBean{" +
                "bannerNovels=" + bannerNovels +
                ", sortHotNovels=" + sortHotNovels +
                ", updateRecent=" + updateRecent +
                ", updatePublish=" + updatePublish +
                '}';
    }

    public static class SortHotNovel {
        @Pick("h2")
        public String type;
        @Pick("div.top")
        public HottestNovel hottestNovel;
        @Pick("ul > li")
        public List<Item> otherNovels;

        @Override
        public String toString() {
            return "SortHotNovel{" +
                    "type='" + type + '\'' +
                    ", hottestNovel=" + hottestNovel +
                    ", otherNovels=" + otherNovels +
                    '}';
        }

        public static class HottestNovel {
            @Pick(value = "div.image > a",attr = Attrs.HREF)
            public String novelDetailUrl;
            @Pick(value = "div.image > a > img", attr = Attrs.SRC)
            public String coverUrl;
            @Pick("dl > dt")
            public String author;
            @Pick("dl > dt > a")
            public String name;
            @Pick("dl > dd")
            public String introduction;

            @Override
            public String toString() {
                return "HottestNovel{" +
                        "novelDetailUrl='" + novelDetailUrl + '\'' +
                        ", coverUrl='" + coverUrl + '\'' +
                        ", author='" + author + '\'' +
                        ", name='" + name + '\'' +
                        ", introduction='" + introduction + '\'' +
                        '}';
            }
        }

        public static class Item {
            @Pick(value = "a", attr = Attrs.HREF)
            public String novelDetailUrl;
            @Pick("a")
            public String name;
            @Pick(value = "li",attr = Attrs.OWN_TEXT)
            public String author;

            @Override
            public String toString() {
                return "Item{" +
                        "novelDetailUrl='" + novelDetailUrl + '\'' +
                        ", name='" + name + '\'' +
                        ", author='" + author + '\'' +
                        '}';
            }
        }
    }

    public static class NewUpdate {
        /**
         *类型：最近更新小说列表、最新添加小说
         */
        @Pick("h2")
        public String type;
        @Pick("ul > li")
        public List<Item> items;

        public static class Item{
            /**
             * 分类
             */
            @Pick("span.s1")
            public String type;
            /**
             * 小说名称
             */
            @Pick("span.s2 > a")
            public String name;
            /**
             * 小说详情页
             */
            @Pick(value = "span.s2 > a", attr = Attrs.HREF)
            public String novelDetailUrl;
            /**
             * 最新章节名称
             */
            @Pick("span.s3 > a")
            public String newestChapterName;
            /**
             * 最新章节链接
             */
            @Pick(value = "span.s3 > a",attr = Attrs.HREF)
            public String newestChapterUrl;
            /**
             * 作者
             */
            @Pick("span.s4")
            public String author;
            /**
             * 最近更新小说列表中表示更新日期；
             * 最新添加小说列表中表示作者
             */
            @Pick("span.s5")
            public String updateTimeOrAuthor;
        }

        @Override
        public String toString() {
            return "NewUpdate{" +
                    "type='" + type + '\'' +
                    ", items=" + items +
                    '}';
        }
    }
}
