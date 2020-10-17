package com.ist.fishtoucher.entity;

import android.text.TextUtils;
import android.util.Log;

import com.ist.fishtoucher.utils.LogUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 小说内容实体类
 */
public class NovelChapterInfo {
    String TAG = "NovelChapterInfo";
    private int chapterNumber;
    private String bookName;
    private String content;

    public NovelChapterInfo(String body) {
        Document doc = Jsoup.parse(body);
        content = doc.getElementById("content").text();
        bookName = doc.getElementsByClass("bookname").text();

        //get chapterNumber
        char[] chars = bookName.toCharArray();
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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
                ", bookName='" + bookName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
