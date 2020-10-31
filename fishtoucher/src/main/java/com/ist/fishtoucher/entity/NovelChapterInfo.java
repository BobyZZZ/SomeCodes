package com.ist.fishtoucher.entity;

import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 小说内容实体类
 */
public class NovelChapterInfo {
    String TAG = "NovelChapterInfo";
    private int chapterNumber = 1;
    private String chapterName;
    private String content;

    public NovelChapterInfo(String body) {
        Document doc = Jsoup.parse(body);
        content = doc.getElementById("content").text();

        //解析获取章节名
        Element booknameBody = Jsoup.parseBodyFragment(doc.getElementsByClass("bookname").html()).body();
        Elements h1Tags = booknameBody.getElementsByTag("h1");
        if (h1Tags.size() > 0) {
            chapterName = h1Tags.get(0).text();
        }

        //get chapterNumber
        char[] chars = chapterName.toCharArray();
        String result = "";
        for (char c : chars) {
            if (Character.isDigit(c)) {
                result += c;
            } else {
                break;
            }
        }
        if (!TextUtils.isEmpty(result)) {
            chapterNumber = Integer.parseInt(result);
        }
//        LogUtils.d(TAG, this.toString());
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    @Override
    public String toString() {
        return "NovelChapterInfo{" +
                "TAG='" + TAG + '\'' +
                ", chapterNumber=" + chapterNumber +
                ", bookName='" + chapterName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
