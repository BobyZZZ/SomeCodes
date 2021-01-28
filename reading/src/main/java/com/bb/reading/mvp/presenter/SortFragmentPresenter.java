package com.bb.reading.mvp.presenter;

import android.util.Log;

import com.bb.reading.R;
import com.bb.reading.base.BasePresenter;
import com.bb.reading.entity.NovelInfo;
import com.bb.reading.entity.PageData;
import com.bb.reading.mvp.contract.SortFragmentContract;
import com.bb.reading.mvp.modle.SortNovelModel;
import com.bb.reading.mvp.view.fragment.SortFragment;
import com.bb.reading.network.NovelService;
import com.bb.reading.utils.ResUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/28
 * Time: 1:07
 */
public class SortFragmentPresenter extends BasePresenter<SortFragment> implements SortFragmentContract.IPresenter {
    String TAG = "SortFragmentPresenter";
    private int mCurrentType = NovelService.NovelType.TYPE_XUANHUAN;
    private final int mFirstPage = 0;
    private int mCurrentPage = mFirstPage;
    private final SortNovelModel mModel;

    public SortFragmentPresenter() {
        mModel = new SortNovelModel(this);
    }

    @Override
    public void process() {
        //获取分类，添加头部（分类选择）
        String[] types = ResUtils.getStringArray(R.array.sort_tabs);
        mView.initTypes(types);

        getNovelsBySort(mCurrentType, mFirstPage);
    }

    @Override
    public void getNovelsBySort(int type, int page) {
        mModel.getNovelsBySort(type, page);
    }

    @Override
    public void loadNovelSuccess(PageData pageData, int type, int page, boolean cleanOldData) {
        Log.d(TAG, "loadNovelSuccess() called with: pageData.size = [" + pageData.getNovels().size() + "], type = [" + type + "], page = [" + page + "], cleanOldData = [" + cleanOldData + "]");
        mCurrentType = type;
        mCurrentPage = page;
        List<NovelInfo> novels = pageData.getNovels();
        boolean hasMore = novels != null && novels.size() >= 30;
        mView.addNovels(novels, hasMore, cleanOldData);
    }

    @Override
    public void getNewNovels(int position) {
        mModel.getNovelsBySort(position + 1, mFirstPage,true);
    }

    @Override
    public void loadMore() {
        getNovelsBySort(mCurrentType, (mCurrentPage + 1));
        Log.d(TAG, "loadMore mCurrentType: " + mCurrentType + ",page: " + (mCurrentPage + 1));
    }
}
