package com.bb.reading.entity;

import java.util.ArrayList;
import java.util.List;

import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/22
 * Time: 0:12
 */
@Pick("div#wrapper")
public class SearchResult {
    @Pick("table.grid > tbody > tr")
    private List<Item> originResults;
    private List<Item> validResults;

    public List<Item> getResults() {
        if (validResults == null) {
            validResults = new ArrayList<>();
            //从第二项开始才是有效数据，第一项是文章名称、最新章节。。。
            for (int i = 1; i < originResults.size(); i++) {
                validResults.add(originResults.get(i));
            }
        }
        return validResults;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "validResults=" + getResults() +
                '}';
    }

    public static class Item {
        //文章名称	最新章节	    作者	更新
        @Pick(value = "td.even > a", attr = Attrs.HREF)
        public String novelId;
        @Pick(value = "td.odd > a", attr = Attrs.HREF)
        public String newestChapterId;
        @Pick("td.even")
        private List<String> list1;
        @Pick("td.odd")
        private List<String> list2;

        public String getName() {
            return list1 != null ? list1.get(0) : "";
        }

        public String getAuthor() {
            return list1 != null ? list1.get(1) : "";
        }

        public String getUpdateTime() {
            return list1 != null ? list1.get(2) : "";
        }

        public String getNewestChapter() {
            return list2 != null ? list2.get(0) : "";
        }

        public String getWordCount() {
            return list2 != null ? list2.get(1) : "";
        }

        @Deprecated
        private String getState() {
            return list2 != null ? list2.get(2) : "";
        }

        @Override
        public String toString() {
            return "Item{" +
                    "name='" + getName() + '\'' +
                    ", novelId='" + novelId + '\'' +
                    ", author='" + getAuthor() + '\'' +
                    ", updateTime='" + getUpdateTime() + '\'' +
                    ", newestChapter='" + getNewestChapter() + '\'' +
                    ", newestChapterId='" + newestChapterId + '\'' +
                    ", wordCount='" + getWordCount() + '\'' +
                    ", state=" + getState() +
                    '}';
        }
    }

}
