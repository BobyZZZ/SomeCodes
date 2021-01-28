package com.bb.reading.mvp.contract;

import com.bb.reading.entity.NovelInfo;
import com.bb.reading.entity.PageData;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/28
 * Time: 1:06
 */
public interface SortFragmentContract {
    interface IModel {
        void getNovelsBySort(int type, int page);
    }

    interface IView {
        void initTypes(String[] types);
        void addNovels(List<NovelInfo> novels, boolean hasMore, boolean cleanOldData);
    }

    interface IPresenter {
        void getNovelsBySort(int type, int page);
        void process();

        void loadNovelSuccess(PageData pageData, int type, int page, boolean cleanOldData);

        void loadMore();

        void getNewNovels(int position);
    }
}
