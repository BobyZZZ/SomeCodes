package com.ist.fishtoucher.entity;

import android.util.Log;

import com.ist.fishtoucher.utils.LogUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class NovelCategory {
    static String TAG = "NovelCategory";

    @Id
    private String mNovelId;
    private String responseBody;

    private static final String ATTR_HREF = "href";
    private static final String ATTR_A = "a";

    @Generated(hash = 444063137)
    public NovelCategory(String mNovelId, String responseBody) {
        this.mNovelId = mNovelId;
        this.responseBody = responseBody;
    }

    @Generated(hash = 531682760)
    public NovelCategory() {
    }

    public static List<NovelChapterInfo> parse(String novelId,String body) {
        Document doc = Jsoup.parse(body);
        Elements mATagList = doc.getElementById("list").getElementsByTag(ATTR_A);
        List<String> hrefs = mATagList.eachAttr(ATTR_HREF);
        List<String> titles = mATagList.eachText();
        return filterUselessInfo(novelId,hrefs,titles);
    }

    private static List<NovelChapterInfo> filterUselessInfo(String novelId, List<String> originHrefs, List<String> originTitles) {
        List<NovelChapterInfo> mChapters = new ArrayList<>();
        if (originHrefs != null && originTitles != null) {
/*            List<String> titles = new ArrayList<String>(originTitles);
            List<String> hrefs = new ArrayList<String>(originHrefs);

            String regex = "\\d+\\W[\\u4e00-\\u9fa5]*";//44、只准吃两口
            Pattern pattern = Pattern.compile(regex);

            Iterator<String> iterator = titles.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                String chapterName = iterator.next();
                boolean matches = chapterName.matches(regex);
                boolean find = pattern.matcher(chapterName).find();
                boolean match = pattern.matcher(chapterName).matches();
//                LogUtils.d(TAG, "filterUselessInfo next: " + next + ",matches: " + matches + ",find: " + find + ",match: " + match);
                if (!find) {
                    iterator.remove();
                    hrefs.remove(i);
                } else {
                    mChapters.add(new NovelChapterInfo(novelId,chapterName, hrefs.get(i++)));
                }
            }
            Log.e(TAG, "filterUselessInfo after filter,the remain length is: " + mChapters.size());
            if (mChapters.isEmpty()) {
                //如果过滤后没有数据，则使用过滤前的数据去填充
                */
                int maxLength = Math.min(originTitles.size(),originHrefs.size());
                for (int j = 0; j < maxLength; j++) {
                    mChapters.add(new NovelChapterInfo(novelId,originTitles.get(j),originHrefs.get(j)));
                }
                LogUtils.d(TAG, "filterUselessInfo without filter,size is: " + mChapters.size());
//            }
        }
        return mChapters;
    }
/*
    @Entity
    public static class Chapter {
        @Id
        private String novelID;
        private String name;
        private String url;

        public Chapter(String chapterName, String url) {
            this.name = chapterName;
            this.url = url;
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
            return "Chapter{" +
                    "name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }*/

    public String getMNovelId() {
        return this.mNovelId;
    }

    public void setMNovelId(String mNovelId) {
        this.mNovelId = mNovelId;
    }

    public String getResponseBody() {
        return this.responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        return "NovelCategory{" +
                "mNovelId='" + mNovelId + '\'' +
                ", responseBody='" + responseBody + '\'' +
                '}';
    }
}
