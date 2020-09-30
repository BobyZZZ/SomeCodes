package com.ist.fishtoucher.entity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class NovelContent {
    private String bookName;
    private String content;

    public NovelContent(String body) {
        Document doc = Jsoup.parse(body);
        content = doc.getElementById("content").text();
        bookName = doc.getElementsByClass("bookname").text();
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
}
