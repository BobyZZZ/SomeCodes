package com.bb.module_noveldetail.mvp.presenter;

import com.bb.module_common.base.BasePresenter;
import com.bb.module_noveldetail.R;
import com.bb.module_noveldetail.mvp.modle.NovelDetailActivityModel;
import com.bb.module_noveldetail.mvp.view.NovelDetailActivity;
import com.bb.module_novelmanager.db.greenDao.DaoHelper;
import com.bb.module_novelmanager.db.greenDao.impl.NovelDBManager;
import com.bb.module_novelmanager.entity.NovelDetails;
import com.bb.module_noveldetail.mvp.contract.NovelDetailActivityContract;
import com.bb.module_common.utils.log.LogUtils;

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
        if (mView != null) {
            mView.onLoadStart();
        }
        mNovelDetailActivityModel.getDetailData(novelIndex);
    }

    @Override
    public void onDetailDataSuccess(NovelDetails novelDetails) {
        LogUtils.d(TAG, "onDetailDataSuccess() called with: mView = [" + mView + "]");
        if (mView != null) {
            mView.updateNovelDetail(novelDetails);
            mView.updateNovelInfo(novelDetails);
            mView.onLoadEnd();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mView != null) {
            mView.onError(e);
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
