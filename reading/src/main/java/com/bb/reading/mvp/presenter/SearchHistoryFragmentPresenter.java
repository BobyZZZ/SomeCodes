package com.bb.reading.mvp.presenter;

import android.content.Intent;

import com.bb.reading.base.BasePresenter;
import com.bb.reading.entity.SearchHistory;
import com.bb.reading.entity.SearchResult;
import com.bb.reading.mvp.contract.SearchActivityContract;
import com.bb.reading.mvp.contract.SearchHistoryFragmentContract;
import com.bb.reading.mvp.modle.SearchHistoryFragmentModel;
import com.bb.reading.mvp.view.activity.SearchResultActivity;
import com.bb.reading.mvp.view.fragment.SearchHistoryFragment;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/21
 * Time: 22:24
 */
public class SearchHistoryFragmentPresenter extends BasePresenter<SearchHistoryFragment> implements SearchHistoryFragmentContract.IPresenter,
SearchActivityContract.IPresenter{
    String TAG = "SearchActivityPresenter";
    private SearchHistoryFragmentModel mHistoryModel;

    public SearchHistoryFragmentPresenter() {
        mHistoryModel = new SearchHistoryFragmentModel(this);
    }

    public void recordHistory(String key) {
        mHistoryModel.recordHistory(key);
    }

    public void cleanHistory() {
        mHistoryModel.cleanHistory();
        mView.updateHistory(null);
    }

    @Override
    public void refreshHistory() {
        mHistoryModel.getHistory();
    }

    @Override
    public void onGetHistorySuccess(List<SearchHistory> histories) {
        mView.updateHistory(histories);
    }

    /*******************************************只做页面跳转，不做实际搜索功能处理******************************************/
    @Override
    public void search(String searchKey) {
        recordHistory(searchKey);
        refreshHistory();
        Intent intent = SearchResultActivity.createIntent(mView.getContext(), searchKey);
        mView.startActivityForResult(intent,0);
    }

    @Override
    public void onSearchSuccess(SearchResult searchResult) {

    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void process() {

    }
    /*******************************************只做页面跳转，不做实际搜索功能处理******************************************/
}
