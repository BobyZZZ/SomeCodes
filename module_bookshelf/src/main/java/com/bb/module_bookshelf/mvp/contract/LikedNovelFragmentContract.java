package com.bb.module_bookshelf.mvp.contract;

import com.bb.module_novelmanager.entity.NovelDetails;

import java.util.List;

public interface LikedNovelFragmentContract {
    interface IView {
        void updateLikedNovelList(List<NovelDetails> allLikedNovel);
    }

    interface IPresenter {
        void onError(Throwable e);
    }
}
