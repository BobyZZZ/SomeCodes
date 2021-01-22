package com.bb.reading.mvp.contract;

import com.bb.reading.entity.SearchHistory;
import com.bb.reading.entity.SearchResult;

import java.util.List;

/**
 * Created by Boby on 2019/6/25.
 */

public interface SearchActivityContract {
    interface IModel {
        void getHistory();

        void recordHistory(String searchKey);

        void cleanHistory();

        void search(String searchKey);
    }

    interface IView {
        String getSearchKey();

        void updateHistory(List<SearchHistory> histories);
    }

    interface IPresenter {
        void search(String searchKey);

        void refreshHistory();

        void onGetHistorySuccess(List<SearchHistory> histories);

        void onSearchSuccess(SearchResult searchResult);

        void onError(Throwable e);
    }
}
