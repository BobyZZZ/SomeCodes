package com.bb.reading.mvp.presenter;

import com.bb.reading.base.BasePresenter;
import com.bb.reading.entity.NovelDetails;
import com.bb.reading.mvp.contract.NovelDetailActivityContract;
import com.bb.reading.mvp.modle.NovelDetailActivityModel;
import com.bb.reading.mvp.view.activity.NovelDetailActivity;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/18
 * Time: 0:01
 */
public class NovelDetailActivityPresenter extends BasePresenter<NovelDetailActivity> implements NovelDetailActivityContract.IPresenter {

    private final NovelDetailActivityModel mNovelDetailActivityModel;

    public NovelDetailActivityPresenter() {
        mNovelDetailActivityModel = new NovelDetailActivityModel(this);
    }

    public void getDetailData(String novelIndex) {
        mNovelDetailActivityModel.getDetailData(novelIndex);
    }

    @Override
    public void onDetailDataSuccess(NovelDetails novelDetails) {
        mView.updateNovelInfo(novelDetails);
        mView.updateChapterList(novelDetails);
    }
}
