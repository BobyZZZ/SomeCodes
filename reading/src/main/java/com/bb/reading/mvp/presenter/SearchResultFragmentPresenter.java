package com.bb.reading.mvp.presenter;

import android.util.Log;

import com.bb.reading.base.BasePresenter;
import com.bb.reading.entity.SearchResult;
import com.bb.reading.mvp.contract.SearchResultFragmentContract;
import com.bb.reading.mvp.view.fragment.SearchResultFragment;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/24
 * Time: 1:33
 */
public class SearchResultFragmentPresenter extends BasePresenter<SearchResultFragment> implements SearchResultFragmentContract.IPresenter {
    String TAG = "SearchResultFragmentPresenter";

    @Override
    public void onSearchSuccess(SearchResult searchResult) {
        mView.updateResults(searchResult);
    }

    @Override
    public void attachView(SearchResultFragment view) {
        super.attachView(view);
        Log.d(TAG, "attachView() called with: view = [" + view + "]");
    }

    @Override
    public void detachView() {
        super.detachView();
        Log.d(TAG, "detachView() called");
    }
}
