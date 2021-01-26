package com.bb.reading.mvp.presenter;

import android.util.Log;

import com.bb.reading.base.BasePresenter;
import com.bb.reading.db.DaoHelper;
import com.bb.reading.entity.NovelDetails;
import com.bb.reading.entity.SearchResult;
import com.bb.reading.mvp.contract.NovelDetailActivityContract;
import com.bb.reading.mvp.contract.SearchResultFragmentContract;
import com.bb.reading.mvp.modle.NovelDetailActivityModel;
import com.bb.reading.mvp.view.fragment.SearchResultFragment;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/24
 * Time: 1:33
 */
public class SearchResultFragmentPresenter extends BasePresenter<SearchResultFragment> implements SearchResultFragmentContract.IPresenter,
        NovelDetailActivityContract.IPresenter {
    String TAG = "SearchResultFragmentPresenter";
    private final NovelDetailActivityModel<SearchResultFragmentPresenter> mNovelDetailModel;

    public SearchResultFragmentPresenter() {
        mNovelDetailModel = new NovelDetailActivityModel<>(this);
    }

    @Override
    public void onSearchSuccess(SearchResult searchResult) {
        mView.updateResults(searchResult);
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
