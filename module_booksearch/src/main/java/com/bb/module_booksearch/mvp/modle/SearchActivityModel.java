package com.bb.module_booksearch.mvp.modle;

import com.bb.module_novelmanager.network.NovelService;
import com.bb.module_novelmanager.network.RetrofitManager;
import com.bb.network.observer.BaseObserver;
import com.bb.module_novelmanager.entity.SearchResult;
import com.bb.module_booksearch.mvp.contract.SearchActivityContract;
import com.bb.module_common.utils.RxUtils;

/**
 * Created by Boby on 2019/6/25.
 */

public class SearchActivityModel implements SearchActivityContract.IModel {
    SearchActivityContract.IPresenter mPresenter;
    private final NovelService mNovelService;


    public SearchActivityModel(SearchActivityContract.IPresenter searchFragmentPresenter) {
        this.mPresenter = searchFragmentPresenter;
        mNovelService = RetrofitManager.getInstance().createRs(NovelService.class);
    }

    @Override
    public void search(String searchKey) {
        mNovelService.searchNovel(searchKey)
                .compose(RxUtils.<SearchResult>rxScheduers())
                .onErrorResumeNext(RxUtils.<SearchResult>handleError())
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
