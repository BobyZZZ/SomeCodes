package com.ist.fishtoucher.contract;

import com.ist.fishtoucher.entity.NovelCategory;

import io.reactivex.Observable;

public interface MainContract {
    interface IMainModel {
        Observable getChapter(String novelIndex, String chapterIndex);
        Observable getCategory(String novelIndex);

    }

    interface IMainView {
        void updateCategory(NovelCategory novelCategory);
        void displayContent(String content,int chapterIndex);
    }

    interface IMainPresenter {
        /**
         * 通过确定的href阅读
         * @param novelIndex 哪本小说
         * @param chapterIndex  该章节的href
         * @param chapterNumber 第几章，不确定时传-1；
         */
        void read(String novelIndex, String chapterIndex,int chapterNumber);
        /**
         * 通过章节数阅读
         * @param novelIndex 哪本小说
         * @param chapterNumber  第几章
         */
        void read(String novelIndex, int chapterNumber);

        /**
         * 获取目录
         * @param novelIndex 哪本小说
         */
        void getCategory(String novelIndex);
    }
}
