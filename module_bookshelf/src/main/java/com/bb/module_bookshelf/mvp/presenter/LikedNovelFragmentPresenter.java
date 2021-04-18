package com.bb.module_bookshelf.mvp.presenter;

import com.bb.module_bookshelf.mvp.contract.CheckUpdateContract;
import com.bb.module_bookshelf.mvp.model.CheckUpdateModel;
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
public class LikedNovelFragmentPresenter extends BasePresenter<LikedNovelFragment> implements LikedNovelFragmentContract.IPresenter,
        CheckUpdateContract.IPresenter {
    String TAG = "LikedNovelFragmentPresenter";
    private NovelDBManager mNovelDBManager;
    private final CheckUpdateModel mCheckUpdateModel;

    public LikedNovelFragmentPresenter() {
        mNovelDBManager = DaoHelper.getInstance().getNovelDBManager();
        mCheckUpdateModel = new CheckUpdateModel(this);
    }

    public void getAllLiked() {
        LogUtils.d(TAG, "process() called");
        List<NovelDetails> allLikedNovel = mNovelDBManager.getAllLikedNovel();
        mView.updateLikedNovelList(allLikedNovel);

        mCheckUpdateModel.checkUpdate(allLikedNovel);//检测是否有更新
    }

    public void updateLiked(NovelDetails novelDetails) {
        mNovelDBManager.updateLikedNovel(novelDetails);
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

    @Override
    public void onCheckUpdateSuccess(List<NovelDetails> oldNovelDetails, List<NovelDetails> newNovelDetails) {
        for (int i = 0; i < newNovelDetails.size(); i++) {
            NovelDetails oldData = oldNovelDetails.get(i);
            String novelName = oldData.getName();
            String novelAuthor = oldData.getAuthor();

            for (NovelDetails newData : newNovelDetails) {
                if (novelName.equals(newData.getName()) && novelAuthor.equals(newData.getAuthor())) {
                    if (!oldData.hasNewChapter) {
                        oldData.hasNewChapter = !oldData.getNewestChapter().equals(newData.getNewestChapter());
                    }
                    oldData.setInfos(newData.getInfos());
                }
            }
        }
        mView.updateLikedNovelList(oldNovelDetails);
        mNovelDBManager.updateLikedNovels(oldNovelDetails);
    }
}
