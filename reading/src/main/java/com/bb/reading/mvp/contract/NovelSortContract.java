package com.bb.reading.mvp.contract;

import com.bb.reading.entity.NovelsSort;
import com.bb.reading.mvp.callback.BaseCallback;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/12/27
 * Time: 23:41
 */
public interface NovelSortContract {
    interface INovelSortModel {
        void getNovelBySort(int sort, BaseCallback<NovelsSort> callback);
    }
}
