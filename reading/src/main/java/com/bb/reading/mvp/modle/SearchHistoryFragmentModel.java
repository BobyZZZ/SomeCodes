package com.bb.reading.mvp.modle;

import com.bb.network.observer.BaseObserver;
import com.bb.reading.db.DaoHelper;
import com.bb.reading.db.greenDao.beanManager.SearchHistoryDB;
import com.bb.reading.entity.SearchHistory;
import com.bb.reading.entity.SearchResult;
import com.bb.reading.mvp.contract.SearchActivityContract;
import com.bb.reading.mvp.contract.SearchHistoryFragmentContract;
import com.bb.reading.mvp.presenter.SearchActivityPresenter;
import com.bb.reading.mvp.presenter.SearchHistoryFragmentPresenter;
import com.bb.reading.network.NovelService;
import com.bb.reading.network.RetrofitManager;
import com.bb.reading.utils.RxUtils;

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
