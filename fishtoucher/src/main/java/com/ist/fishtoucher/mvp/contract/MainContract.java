package com.ist.fishtoucher.mvp.contract;

import com.ist.fishtoucher.entity.NovelCategory;
import com.ist.fishtoucher.entity.NovelChapterContent;
import com.ist.fishtoucher.entity.NovelChapterInfo;
import com.ist.fishtoucher.mvp.callback.BaseCallback;

import java.util.List;

public interface MainContract {
    interface IMainModel {
        void getChapter(String novelIndex, String chapterIndex,BaseCallback baseCallback);
        void getCategory(String novelIndex, BaseCallback baseCallback);

    }

    interface IMainView {
        /**
         * 开始加载
         */
        void loading();
        void updateCategory(List<NovelChapterInfo> novelCategory);
        void loadContentSuccessAndToDisplay(NovelChapterContent content, int chapterIndex, boolean resetData);
    }

    interface IMainPresenter {
        /**
         * 通过确定的href阅读
         * @param novelID 哪本小说
         * @param chapterID  该章节的href
         * @param resetData  是否需要重新设置数据
         */
        void read(String novelID, String chapterID, boolean resetData);
/*        *//**
         * 通过章节数阅读
         * @param novelID 哪本小说
         * @param chapterNumber  第几章
         *//*
        void read(String novelID, int chapterNumber);*/

        /**
         * 获取目录
         * @param novelIndex 哪本小说
         */
        void getCategory(String novelIndex);

        /**
         * 加载下一章
         */
        void loadMore();
    }
}
