package com.bb.module_bookshelf.mvp.contract;

import com.bb.module_novelmanager.entity.NovelDetails;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/4/18
 * Time: 1:46
 */
public interface CheckUpdateContract {
    interface IModel {
        void checkUpdate(List<NovelDetails> allLikedNovel);
    }

    interface IPresenter {
        void onCheckUpdateSuccess(List<NovelDetails> oldNovelDetails, List<NovelDetails> newNovelDetails);
    }
}
