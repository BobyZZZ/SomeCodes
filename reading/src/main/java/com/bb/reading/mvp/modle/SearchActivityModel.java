package com.bb.reading.mvp.modle;

import com.bb.network.observer.BaseObserver;
import com.bb.reading.db.DaoHelper;
import com.bb.reading.db.greenDao.beanManager.SearchHistoryDB;
import com.bb.reading.entity.SearchHistory;
import com.bb.reading.entity.SearchResult;
import com.bb.reading.mvp.contract.SearchActivityContract;
import com.bb.reading.mvp.presenter.SearchActivityPresenter;
import com.bb.reading.network.NovelService;
import com.bb.reading.network.RetrofitManager;
import com.bb.reading.utils.RxUtils;

import java.util.List;

/**
 * Created by Boby on 2019/6/25.
 */

public class SearchActivityModel implements SearchActivityContract.IModel {
    SearchActivityPresenter mPresenter;
    private final SearchHistoryDB mDao;
    private final NovelService mNovelService;


    public SearchActivityModel(SearchActivityPresenter searchFragmentPresenter) {
        this.mPresenter = searchFragmentPresenter;
        mNovelService = RetrofitManager.getInstance().createRs(NovelService.class);
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

    @Override
    public void search(String searchKey) {
        mNovelService.searchNovel(searchKey)
                .compose(RxUtils.rxScheduers())
                .subscribe(new BaseObserver<SearchResult>() {
                    @Override
                    protected void onSuccess(SearchResult searchResult) {
                        mPresenter.onSearchSuccess(searchResult);
                    }

                    @Override
                    protected void onFail(Throwable e) {
                        mPresenter.onError(e);
                    }
                });
    }
}
