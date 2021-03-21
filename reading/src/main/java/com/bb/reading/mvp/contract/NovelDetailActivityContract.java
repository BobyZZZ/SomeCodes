package com.bb.reading.mvp.contract;

import com.bb.reading.entity.NovelDetails;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/18
 * Time: 0:02
 */
public interface NovelDetailActivityContract {
    interface IModel {
        void getDetailData(String novelIndex);
    }

    interface IView {
        void onLoadStart();
        void onLoadEnd();
    }

    interface IPresenter {
        void getDetailData(String novelIndex);

        void onDetailDataSuccess(NovelDetails novelDetails);

        void onError(Throwable e);
    }
}
