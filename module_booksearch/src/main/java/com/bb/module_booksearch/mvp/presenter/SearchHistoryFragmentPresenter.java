package com.bb.module_booksearch.mvp.presenter;

import android.content.Intent;

import com.bb.module_booksearch.mvp.modle.SearchHistoryFragmentModel;
import com.bb.module_booksearch.mvp.view.SearchHistoryFragment;
import com.bb.module_booksearch.mvp.view.SearchResultActivity;
import com.bb.module_common.base.BasePresenter;
import com.bb.module_novelmanager.entity.SearchHistory;
import com.bb.module_novelmanager.entity.SearchResult;
import com.bb.module_booksearch.mvp.contract.SearchActivityContract;
import com.bb.module_booksearch.mvp.contract.SearchHistoryFragmentContract;

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
        if (mView != null) {
            mView.updateHistory(null);
        }
    }

    @Override
    public void refreshHistory() {
        mHistoryModel.getHistory();
    }

    @Override
    public void onGetHistorySuccess(List<SearchHistory> histories) {
        if (mView != null) {
            mView.updateHistory(histories);
        }
    }

    /*******************************************只做页面跳转，不做实际搜索功能处理******************************************/
    @Override
    public void search(String searchKey) {
        recordHistory(searchKey);
        refreshHistory();
        if (mView != null) {
            Intent intent = SearchResultActivity.createIntent(mView.getContext(), searchKey);
            mView.startActivityForResult(intent,0);
        }
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
