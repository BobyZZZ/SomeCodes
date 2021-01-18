package com.bb.reading.mvp.contract;

import com.bb.reading.entity.NovelChapterContent;
import com.bb.reading.entity.NovelChapterInfo;
import com.bb.reading.mvp.callback.BaseCallback;

import java.util.List;

public interface ReadingActivityContract {
    interface IMainModel<Chapter,Category> {
        void getChapter(String novelIndex, String chapterIndex, BaseCallback<Chapter> baseCallback);
        void getCategory(String novelIndex, boolean readFromCache, BaseCallback<Category> baseCallback);
    }

    interface IMainView {
        /**
         * 开始加载
         */
        void loadingStart();
        void loadingStop();
        void updateCategory(List<NovelChapterInfo> novelCategory);
        void loadContentSuccessAndToDisplay(NovelChapterContent content, int chapterIndex, boolean resetData);

        String getNovelID();
        String getChapterID();
    }

    interface IMainPresenter {
        /**
         * 通过确定的href阅读
         * @param novelID 哪本小说
         * @param chapterID  该章节的href
         */
        void read(String novelID, String chapterID);

        /**
         * 获取目录
         * @param novelIndex 哪本小说
         * @param readCache 是否从缓存获取
         */
        void getCategory(String novelIndex, boolean readCache);

        /**
         * 加载下一章
         */
        void loadMore();
    }
}
