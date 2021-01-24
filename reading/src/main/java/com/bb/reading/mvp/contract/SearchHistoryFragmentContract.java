package com.bb.reading.mvp.contract;

import com.bb.reading.entity.SearchHistory;

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
