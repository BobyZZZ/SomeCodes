package com.bb.reading.mvp.presenter;

import android.util.Log;

import com.bb.reading.R;
import com.bb.reading.base.BasePresenter;
import com.bb.reading.db.DaoHelper;
import com.bb.reading.db.greenDao.beanManager.NovelDBManager;
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
    String TAG = "NovelDetailActivityPresenter";

    private final NovelDetailActivityModel mNovelDetailActivityModel;
    private NovelDetails mNovelDetails;
    private final NovelDBManager mNovelDBManager;

    public NovelDetailActivityPresenter() {
        mNovelDetailActivityModel = new NovelDetailActivityModel(this);
        mNovelDBManager = DaoHelper.getInstance().getNovelDBManager();
    }

    public void getDetailData(String novelIndex) {
        mNovelDetailActivityModel.getDetailData(novelIndex);
    }

    @Override
    public void onDetailDataSuccess(NovelDetails novelDetails) {
        Log.d(TAG, "onDetailDataSuccess() called with: mView = [" + mView + "]");
        if (mView != null) {
            mNovelDetails = novelDetails;
            mNovelDetails.setNovelId(mView.getNovelId());

            mView.updateNovelDetail(novelDetails);
        }
    }

    public void addLikedNovelToDB() {
        long l = -1;
        if (mNovelDetails != null) {
            l = mNovelDBManager.saveLikedNovel(mNovelDetails);
        }
        mView.showToast((l > 0) ? R.string.add_like_success : R.string.add_like_fail);
    }
}
