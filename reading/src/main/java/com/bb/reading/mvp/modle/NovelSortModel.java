package com.bb.reading.mvp.modle;

import android.util.Log;

import com.bb.network.observer.BaseObserver;
import com.bb.reading.entity.NovelsSort;
import com.bb.reading.iApiService.NovelService;
import com.bb.reading.mvp.callback.BaseCallback;
import com.bb.reading.mvp.contract.NovelSortContract;
import com.bb.reading.utils.LogUtils;
import com.bb.reading.utils.RetrofitManager;
import com.bb.reading.utils.RxUtils;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/12/27
 * Time: 23:40
 */
public class NovelSortModel implements NovelSortContract.INovelSortModel {
    String TAG = "NovelSortModel";

    private final NovelService mNovelService;

    public NovelSortModel() {
        mNovelService = RetrofitManager.getInstance().createRs(NovelService.class);
    }

    @Override
    public void getNovelBySort(int sort, final BaseCallback<NovelsSort> callback) {
        Observable<NovelsSort> novelBySort = mNovelService.getNovelBySort(NovelService.NovelType.TYPE_XUANHUAN,1);
        novelBySort
                .compose(RxUtils.rxScheduers())
                .subscribe(new BaseObserver<NovelsSort>() {
                    @Override
                    protected void onSuccess(NovelsSort novelsSort) {
                        LogUtils.d(TAG, "getNovelBySort success: " + novelsSort);
                        callback.onSuccess(novelsSort);
                    }

                    @Override
                    protected void onFail(Throwable e) {
                        LogUtils.d(TAG, "getNovelBySort fail() called with: e = [" + e + "]");
                    }
                });
    }
}
