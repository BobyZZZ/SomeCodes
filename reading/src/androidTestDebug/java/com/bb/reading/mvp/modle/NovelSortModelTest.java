package com.bb.reading.mvp.modle;

import com.bb.reading.entity.PageData;
import com.bb.reading.network.NovelService;
import com.bb.reading.mvp.callback.BaseCallback;

import org.junit.Test;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/12/27
 * Time: 23:44
 */
public class NovelSortModelTest {
    String TAG = "NovelSortModelTest";

    @Test
    public void getNovelBySort() {
        new NovelSortModel().getNovelBySort(NovelService.NovelType.TYPE_XUANHUAN, new BaseCallback<PageData>() {
            @Override
            public void onSuccess(PageData data, boolean... fromCache) {
            }
        });
    }
}