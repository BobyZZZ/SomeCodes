package com.ist.fishtoucher.entity;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;


public class NovelCategory {
    String TAG = "NovelCategory";
    private final String ATTR_HREF = "href";
    private final String ATTR_A = "a";

    private Elements mATagList;
    private final List<String> mHrefs;
    private final List<String> mTitles;
    private List<Chapter> mChapters;

    public NovelCategory(String body) {
        Document doc = Jsoup.parse(body);
        mATagList = doc.getElementById("list").getElementsByTag(ATTR_A);
        mHrefs = mATagList.eachAttr(ATTR_HREF);
        mTitles = mATagList.eachText();
        mChapters = new ArrayList<>();
        filterUselessInfo();
    }

    private void filterUselessInfo() {
        if (mHrefs != null && mTitles != null && !mTitles.isEmpty() && mHrefs.size() == mTitles.size()) {
            String regex = "\\d+\\W[\\u4e00-\\u9fa5]*";//44、只准吃两口
            Pattern pattern = Pattern.compile(regex);

            Iterator<String> iterator = mTitles.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                String chapterName = iterator.next();
                boolean matches = chapterName.matches(regex);
                boolean find = pattern.matcher(chapterName).find();
                boolean match = pattern.matcher(chapterName).matches();
//                Log.d(TAG, "filterUselessInfo next: " + next + ",matches: " + matches + ",find: " + find + ",match: " + match);
                if (!find) {
                    iterator.remove();
                    mHrefs.remove(i);
                } else {
                    mChapters.add(new Chapter(chapterName, mHrefs.get(i++)));
                }
            }
            Log.e(TAG, "filterUselessInfo after filter,the remain length is: " + mHrefs.size());
        }
    }

    public int size() {
        return mChapters == null ? 0 : mChapters.size();
    }

    public List<Chapter> getChapters() {
        return mChapters;
    }

    @Override
    public String toString() {
        return "NovelCategory{" +
                "TAG='" + TAG + '\'' +
                ", mChapters=" + mChapters +
                '}';
    }

    public static class Chapter {
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
    }
}
