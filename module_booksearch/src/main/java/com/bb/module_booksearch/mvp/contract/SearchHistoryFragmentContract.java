package com.bb.module_booksearch.mvp.contract;


import com.bb.module_novelmanager.entity.SearchHistory;

import java.util.List;

public interface SearchHistoryFragmentContract {
    interface IModel {
        void getHistory();

        void recordHistory(String searchKey);

        void cleanHistory();
    }

    interface IView {
        void updateHistory(List<SearchHistory> histories);
    }

    interface IPresenter {
        void refreshHistory();

        void onGetHistorySuccess(List<SearchHistory> histories);
    }
}
