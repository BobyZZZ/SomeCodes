package com.bb.reading.mvp.contract;

import com.bb.reading.entity.NovelChapterContent;
import com.bb.reading.entity.NovelChapterInfo;
import com.bb.reading.mvp.callback.BaseCallback;

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
