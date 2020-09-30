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
        void read(String novelIndex, int chapterIndex);
        void getCategory(String novelIndex);
    }
}
