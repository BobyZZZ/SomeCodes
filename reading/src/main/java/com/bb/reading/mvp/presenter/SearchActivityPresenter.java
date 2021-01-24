package com.bb.reading.mvp.presenter;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.bb.reading.R;
import com.bb.reading.base.BasePresenter;
import com.bb.reading.entity.SearchResult;
import com.bb.reading.mvp.contract.SearchActivityContract;
import com.bb.reading.mvp.modle.SearchActivityModel;
import com.bb.reading.mvp.view.activity.SearchActivity;
import com.bb.reading.mvp.view.fragment.SearchHistoryFragment;
import com.bb.reading.mvp.view.fragment.SearchResultFragment;

import java.util.HashMap;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/21
 * Time: 22:24
 */
public class SearchActivityPresenter extends BasePresenter<SearchActivity> implements SearchActivityContract.IPresenter {
    String TAG = "SearchActivityPresenter";
    private SearchActivityModel mModel;
    public static final int FRAGMENT_SEARCH_HISTORY_TYPE = 0;
    public static final int FRAGMENT_SEARCH_RESULT_TYPE = 1;
    private HashMap<Integer, Fragment> mFragments = new HashMap();

    public SearchActivityPresenter() {
        mModel = new SearchActivityModel(this);
    }

    private <F extends Fragment> F getFragment(int fragmentType) {
        return (F) mFragments.get(fragmentType);
    }

    public Fragment createFragment(int fragmentType) {
        Fragment fragment = getFragment(fragmentType);
        if (fragment != null) {
            return fragment;
        }

        switch (fragmentType) {
            case FRAGMENT_SEARCH_HISTORY_TYPE:
                SearchHistoryFragment searchHistoryFragment = SearchHistoryFragment.newInstance();
                searchHistoryFragment.setOthersPresenter(this);
                fragment = searchHistoryFragment;
                break;
            case FRAGMENT_SEARCH_RESULT_TYPE:
                SearchResultFragment searchResultFragment = SearchResultFragment.newInstance();
                fragment = searchResultFragment;
                break;
        }
        mFragments.put(fragmentType, fragment);
        return fragment;
    }

    @Override
    public void process() {
        //加载搜索历史fragment
        mView.show(createFragment(FRAGMENT_SEARCH_HISTORY_TYPE));
    }

    @Override
    public void search(String searchKey) {
        SearchHistoryFragment searchHistoryFragment = getFragment(FRAGMENT_SEARCH_HISTORY_TYPE);
        if (searchHistoryFragment != null && searchHistoryFragment.getPresenter() != null) {
            searchHistoryFragment.getPresenter().recordHistory(searchKey);
            searchHistoryFragment.getPresenter().refreshHistory();
        }
        mView.show(createFragment(FRAGMENT_SEARCH_RESULT_TYPE));
        mModel.search(searchKey);
    }

    @Override
    public void onSearchSuccess(SearchResult searchResult) {
        if (!searchResult.getResults().isEmpty()) {
            SearchResultFragment searchResultFragment = getFragment(FRAGMENT_SEARCH_RESULT_TYPE);
            if (searchResultFragment != null && searchResultFragment.getPresenter() != null) {
                searchResultFragment.getPresenter().onSearchSuccess(searchResult);
            }
        } else {
            mView.showToast(R.string.search_result_is_empty);
        }
    }

    @Override
    public void onError(Throwable e) {

    }
}
