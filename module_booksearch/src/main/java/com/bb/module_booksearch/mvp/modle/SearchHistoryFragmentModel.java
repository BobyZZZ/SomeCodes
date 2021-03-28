package com.bb.module_booksearch.mvp.modle;

import com.bb.module_booksearch.mvp.contract.SearchHistoryFragmentContract;
import com.bb.module_booksearch.mvp.presenter.SearchHistoryFragmentPresenter;
import com.bb.module_novelmanager.db.greenDao.DaoHelper;
import com.bb.module_novelmanager.db.greenDao.impl.SearchHistoryDB;
import com.bb.module_novelmanager.entity.SearchHistory;

import java.util.List;

/**
 * Created by Boby on 2019/6/25.
 */

public class SearchHistoryFragmentModel implements SearchHistoryFragmentContract.IModel {
    SearchHistoryFragmentPresenter mPresenter;
    private final SearchHistoryDB mDao;


    public SearchHistoryFragmentModel(SearchHistoryFragmentPresenter searchHistoryFragmentPresenter) {
        this.mPresenter = searchHistoryFragmentPresenter;
        mDao = DaoHelper.getInstance().getSearchHistoryDB();
    }

    @Override
    public void getHistory() {
        List<SearchHistory> histories = mDao.loadAll();
        mPresenter.onGetHistorySuccess(histories);
    }

    @Override
    public void recordHistory(String name) {
        long insert = mDao.insertOrReplace(new SearchHistory(name, null));
    }

    @Override
    public void cleanHistory() {
        mDao.deleteAll();
    }
}
