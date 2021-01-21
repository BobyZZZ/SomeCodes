package com.bb.reading.mvp.contract;

import com.bb.reading.entity.NovelDetails;

import java.util.List;

public interface LikedNovelFragmentContract {
    interface IModel {
    }

    interface IView {
        void updateLikedNovelList(List<NovelDetails> allLikedNovel);
    }

    interface IPresenter {
        void onError(Throwable e);
    }
}
