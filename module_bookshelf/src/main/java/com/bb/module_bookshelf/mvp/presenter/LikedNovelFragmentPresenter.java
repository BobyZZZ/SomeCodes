package com.bb.module_bookshelf.mvp.presenter;

import com.bb.module_common.base.BasePresenter;
import com.bb.module_novelmanager.db.greenDao.DaoHelper;
import com.bb.module_novelmanager.db.greenDao.impl.NovelDBManager;
import com.bb.module_novelmanager.entity.NovelDetails;
import com.bb.module_bookshelf.mvp.contract.LikedNovelFragmentContract;
import com.bb.module_bookshelf.mvp.view.LikedNovelFragment;
import com.bb.module_common.utils.log.LogUtils;

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
