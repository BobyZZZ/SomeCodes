package com.bb.reading.mvp.contract;

import com.bb.reading.entity.PageData;
import com.bb.reading.mvp.callback.BaseCallback;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/12/27
 * Time: 23:41
 */
public interface NovelSortContract {
    interface INovelSortModel {
        void getNovelBySort(int sort, BaseCallback<PageData> callback);
    }
}
