package com.bb.module_booksearch.mvp.presenter;

import com.bb.module_booksearch.R;
import com.bb.module_common.base.BasePresenter;
import com.bb.module_novelmanager.entity.SearchResult;
import com.bb.module_booksearch.mvp.contract.SearchActivityContract;
import com.bb.module_booksearch.mvp.contract.SearchResultFragmentContract;
import com.bb.module_booksearch.mvp.modle.SearchActivityModel;
import com.bb.module_booksearch.mvp.view.SearchResultFragment;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/24
 * Time: 1:33
 */
public class SearchResultFragmentPresenter extends BasePresenter<SearchResultFragment> implements SearchResultFragmentContract.IPresenter,
        SearchActivityContract.IPresenter {
    String TAG = "SearchResultFragmentPresenter";
    private final SearchActivityModel mSearchModel;

    public SearchResultFragmentPresenter() {
        mSearchModel = new SearchActivityModel(this);
    }

    @Override
    public void search(String searchKey) {
        mSearchModel.search(searchKey);
    }

    @Override
    public void onSearchSuccess(SearchResult searchResult) {
        if (mView != null) {
            if (!searchResult.getResults().isEmpty()) {
                mView.updateResults(searchResult);
            } else {
                mView.showToast(R.string.search_result_is_empty);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mView != null) {
            mView.onError(e);
        }
    }

    @Override
    public void process() {

    }
}
