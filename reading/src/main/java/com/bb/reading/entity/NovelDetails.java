package com.bb.reading.entity;

import java.util.List;

import me.ghui.fruit.Attrs;
import me.ghui.fruit.annotations.Pick;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/17
 * Time: 21:46
 */
@Pick("div#wrapper")
public class NovelDetails {
    @Pick(value = "div#fmimg > img", attr = Attrs.SRC)
    public String coverUrl;
    @Pick("div#info > h1")
    public String name;
    @Pick("div#info > p")
    private List<String> infos;
    @Pick("div#intro > p")
    public String introduction;
    @Pick("div#list > dl > dd")
    public List<Chapter> chapterList;

    public String getAuthor() {
        return infos.get(0);
    }

    public String getType() {
        return infos.get(1);
    }

    public String getLastUpdateTime() {
        return infos.get(2);
    }

    public String getNewestChapter() {
        return infos.get(3);
    }

    public static class Chapter {
        @Pick("a")
        public String name;
        @Pick(value = "a", attr = Attrs.HREF)
        public String chapterUrl;
    }
}
