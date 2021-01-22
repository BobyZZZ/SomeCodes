package com.bb.reading.mvp.presenter;

import android.util.Log;

import com.bb.reading.base.BasePresenter;
import com.bb.reading.entity.SearchHistory;
import com.bb.reading.entity.SearchResult;
import com.bb.reading.mvp.contract.SearchActivityContract;
import com.bb.reading.mvp.modle.SearchActivityModel;
import com.bb.reading.mvp.view.activity.SearchActivity;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/21
 * Time: 22:24
 */
public class SearchActivityPresenter extends BasePresenter<SearchActivity> implements SearchActivityContract.IPresenter {
    String TAG = "SearchActivityPresenter";
    private SearchActivityModel mModel;

    public SearchActivityPresenter() {
        mModel = new SearchActivityModel(this);
    }

    private void recordHistory(String key) {
        mModel.recordHistory(key);
    }

    public void cleanHistory() {
        mModel.cleanHistory();
        mView.updateHistory(null);
    }

    @Override
    public void refreshHistory() {
        mModel.getHistory();
    }

    @Override
    public void onGetHistorySuccess(List<SearchHistory> histories) {
        mView.updateHistory(histories);
    }

    @Override
    public void search(String searchKey) {
        recordHistory(searchKey);
        mModel.search(searchKey);
        refreshHistory();
    }

    @Override
    public void onSearchSuccess(SearchResult searchResult) {
        Log.d(TAG, "onSearchSuccess() called with: searchResult = [" + searchResult + "]");
    }

    @Override
    public void onError(Throwable e) {

    }
}
