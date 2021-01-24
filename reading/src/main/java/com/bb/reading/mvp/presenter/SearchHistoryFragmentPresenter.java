package com.bb.reading.mvp.presenter;

import android.util.Log;

import com.bb.reading.base.BasePresenter;
import com.bb.reading.entity.SearchHistory;
import com.bb.reading.entity.SearchResult;
import com.bb.reading.mvp.contract.SearchActivityContract;
import com.bb.reading.mvp.contract.SearchHistoryFragmentContract;
import com.bb.reading.mvp.modle.SearchActivityModel;
import com.bb.reading.mvp.modle.SearchHistoryFragmentModel;
import com.bb.reading.mvp.view.activity.SearchActivity;
import com.bb.reading.mvp.view.fragment.SearchHistoryFragment;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/21
 * Time: 22:24
 */
public class SearchHistoryFragmentPresenter extends BasePresenter<SearchHistoryFragment> implements SearchHistoryFragmentContract.IPresenter {
    String TAG = "SearchActivityPresenter";
    private SearchHistoryFragmentModel mModel;

    public SearchHistoryFragmentPresenter() {
        mModel = new SearchHistoryFragmentModel(this);
    }

    public void recordHistory(String key) {
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
}
