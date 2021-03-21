package com.bb.reading.mvp.presenter;

import android.util.Log;

import com.bb.reading.base.BasePresenter;
import com.bb.reading.db.DaoHelper;
import com.bb.reading.db.greenDao.beanManager.NovelDBManager;
import com.bb.reading.entity.NovelDetails;
import com.bb.reading.mvp.contract.LikedNovelFragmentContract;
import com.bb.reading.mvp.view.fragment.LikedNovelFragment;
import com.bb.reading.utils.log.LogUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/21
 * Time: 22:24
 */
public class LikedNovelFragmentPresenter extends BasePresenter<LikedNovelFragment> implements LikedNovelFragmentContract.IPresenter {
    String TAG = "LikedNovelFragmentPresenter";
    private NovelDBManager mNovelDBManager;

    public LikedNovelFragmentPresenter() {
        mNovelDBManager = DaoHelper.getInstance().getNovelDBManager();
    }

    public void getAllLiked() {
        LogUtils.d(TAG, "process() called");
        List<NovelDetails> allLikedNovel = mNovelDBManager.getAllLikedNovel();
        mView.updateLikedNovelList(allLikedNovel);
    }

    public void delete(String novelId) {
        mNovelDBManager.deleteLikedNovel(novelId);
        mView.refreshFavorite();
    }
    @Override
    public void onError(Throwable e) {
        if (mView != null) {
            mView.onError(e);
        }
    }
}
