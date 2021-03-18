package com.bb.reading.mvp.presenter;

import android.util.Log;

import com.bb.reading.R;
import com.bb.reading.base.BasePresenter;
import com.bb.reading.db.DaoHelper;
import com.bb.reading.entity.NovelDetails;
import com.bb.reading.entity.SearchResult;
import com.bb.reading.mvp.contract.NovelDetailActivityContract;
import com.bb.reading.mvp.contract.SearchActivityContract;
import com.bb.reading.mvp.contract.SearchResultFragmentContract;
import com.bb.reading.mvp.modle.NovelDetailActivityModel;
import com.bb.reading.mvp.modle.SearchActivityModel;
import com.bb.reading.mvp.view.fragment.SearchResultFragment;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/24
 * Time: 1:33
 */
public class SearchResultFragmentPresenter extends BasePresenter<SearchResultFragment> implements SearchResultFragmentContract.IPresenter,
        NovelDetailActivityContract.IPresenter, SearchActivityContract.IPresenter {
    String TAG = "SearchResultFragmentPresenter";
    private final NovelDetailActivityModel<SearchResultFragmentPresenter> mNovelDetailModel;
    private final SearchActivityModel mSearchModel;

    public SearchResultFragmentPresenter() {
        mNovelDetailModel = new NovelDetailActivityModel<>(this);
        mSearchModel = new SearchActivityModel(this);
    }

    @Override
    public void search(String searchKey) {
        mSearchModel.search(searchKey);
    }

    @Override
    public void onSearchSuccess(SearchResult searchResult) {
        if (!searchResult.getResults().isEmpty()) {
            mView.updateResults(searchResult);
        } else {
            mView.showToast(R.string.search_result_is_empty);
        }
    }

    @Override
    public void onError(Throwable e) {
        mView.onError(e);
    }

    @Override
    public void process() {

    }

    @Override
    public void getDetailData(String novelIndex) {
        mNovelDetailModel.getDetailData(novelIndex,true);
    }

    @Override
    public void onDetailDataSuccess(NovelDetails novelDetails) {
        Log.d(TAG, "onDetailDataSuccess() called with to save liked: [" + novelDetails.novelId +
                "] in thread: [" + Thread.currentThread().getName() + "]");
        DaoHelper.getInstance().getNovelDBManager().saveLikedNovel(novelDetails);
    }
}
