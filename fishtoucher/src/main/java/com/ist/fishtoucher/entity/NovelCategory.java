package com.ist.fishtoucher.entity;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

    public NovelCategory(String body) {
        Document doc = Jsoup.parse(body);
        mATagList = doc.getElementById("list").getElementsByTag(ATTR_A);
        mHrefs = mATagList.eachAttr(ATTR_HREF);
        mTitles = mATagList.eachText();
        filterUselessInfo();
    }

    private void filterUselessInfo() {
        if (mHrefs != null && mTitles != null && !mTitles.isEmpty() && mHrefs.size() == mTitles.size()) {
            String regex = "\\d+\\W[\\u4e00-\\u9fa5]*";//44、只准吃两口
            Pattern pattern = Pattern.compile(regex);

            Iterator<String> iterator = mTitles.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                String next = iterator.next();
                boolean matches = next.matches(regex);
                boolean find = pattern.matcher(next).find();
                boolean match = pattern.matcher(next).matches();
//                Log.d(TAG, "filterUselessInfo next: " + next + ",matches: " + matches + ",find: " + find + ",match: " + match);
                if (!find) {
                    iterator.remove();
                    mHrefs.remove(i);
                } else {
                    i++;
                }
            }
            Log.e(TAG, "filterUselessInfo after filter,the remain length is: " + mHrefs.size());
        }
    }

    public Elements getATagList() {
        return mATagList;
    }

    public int size() {
        return mATagList == null ? 0 : mATagList.size();
    }

    public List<String> getHrefs() {
        return mHrefs;
    }

    public List<String> getText() {
        return mTitles;
    }

    @Override
    public String toString() {
        return "NovelCategory{" +
                "mATagList=" + mATagList +
                ", mHrefs=" + mHrefs +
                ", mTitles=" + mTitles +
                '}';
    }
}
