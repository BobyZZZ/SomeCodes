package com.bb.reading.mvp.contract;

import com.bb.reading.entity.SearchResult;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/24
 * Time: 1:31
 */
public interface SearchResultFragmentContract {
    interface IModel {

    }

    interface IView {
        void updateResults(SearchResult searchResult);
    }

    interface IPresenter {
        void onSearchSuccess(SearchResult searchResult);
    }
}
