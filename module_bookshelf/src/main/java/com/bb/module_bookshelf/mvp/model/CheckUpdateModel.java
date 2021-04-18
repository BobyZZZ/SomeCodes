package com.bb.module_bookshelf.mvp.model;

import android.util.Log;

import com.bb.module_bookshelf.mvp.contract.CheckUpdateContract;
import com.bb.module_bookshelf.mvp.presenter.LikedNovelFragmentPresenter;
import com.bb.module_common.utils.RxUtils;
import com.bb.module_common.utils.log.LogUtils;
import com.bb.module_novelmanager.entity.NovelDetails;
import com.bb.module_novelmanager.network.NovelService;
import com.bb.module_novelmanager.network.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/4/18
 * Time: 1:44
 */
public class CheckUpdateModel implements CheckUpdateContract.IModel {
    private final CheckUpdateContract.IPresenter mCheckUpdatePresenter;
    String TAG = "CheckUpdateModel";

    private final NovelService mNovelService;

    public CheckUpdateModel(LikedNovelFragmentPresenter likedNovelFragmentPresenter) {
        mNovelService = RetrofitManager.getInstance().createRs(NovelService.class);
        mCheckUpdatePresenter = likedNovelFragmentPresenter;
    }

    @Override
    public void checkUpdate(final List<NovelDetails> oldNovelDetails) {
        Observable.fromIterable(oldNovelDetails)
                .flatMap(new Function<NovelDetails, ObservableSource<NovelDetails>>() {
                    @Override
                    public ObservableSource<NovelDetails> apply(NovelDetails novelDetails) throws Exception {
                        return mNovelService.getNovelDetails(novelDetails.novelId)
                                .compose(RxUtils.<NovelDetails>rxScheduers());
                    }
                })
                .subscribe(new Observer<NovelDetails>() {
                    private ArrayList<NovelDetails> details = new ArrayList<>();

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NovelDetails novelDetails) {
                        details.add(novelDetails);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "checkUpdate onComplete");
                        mCheckUpdatePresenter.onCheckUpdateSuccess(oldNovelDetails,details);
                    }
                });
    }
}
