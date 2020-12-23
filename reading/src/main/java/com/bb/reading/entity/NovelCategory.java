package com.bb.reading.entity;

import com.bb.reading.utils.LogUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class NovelCategory {
    static String TAG = "NovelCategory";

    private static final String ATTR_HREF = "href";
    private static final String ATTR_A = "a";

    public static List<NovelChapterInfo> parse(String novelId,String body) {
        Document doc = Jsoup.parse(body);
        Element list = doc.getElementById("list");
        LogUtils.d(TAG,"body: " + body + "\n" + list);
        if (list != null) {
            Elements mATagList = list.getElementsByTag(ATTR_A);
            List<String> hrefs = mATagList.eachAttr(ATTR_HREF);
            List<String> titles = mATagList.eachText();
            return filterUselessInfo(novelId, hrefs, titles);
        } else {
            LogUtils.e(TAG,novelId + "'s chapter list is null");
            return null;
        }
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
                    mChapters.add(new NovelChapterInfo(j,novelId,originTitles.get(j),originHrefs.get(j)));
                }
                LogUtils.d(TAG, "filterUselessInfo without filter,size is: " + mChapters.size());
//            }
        }
        return mChapters;
    }
}
