package com.bb.module_booksearch.mvp.contract;

import com.bb.module_novelmanager.entity.SearchResult;

/**
 * Created by Boby on 2019/6/25.
 */

public interface SearchActivityContract {
    interface IModel {
        void search(String searchKey);
    }

    interface IView {
        String getSearchKey();
    }

    interface IPresenter {
        void search(String searchKey);

        void onSearchSuccess(SearchResult searchResult);

        void onError(Throwable e);

        void process();
    }
}
