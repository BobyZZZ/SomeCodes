package com.bb.module_booksearch;

import com.bb.module_booksearch.mvp.view.SearchHistoryFragmentWithSearchBar;
import com.bb.module_common.base.BaseMVPFragmentActivity;
import com.bb.module_common.base.BasePresenter;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/3/28
 * Time: 13:18
 */
public class BooksearchActivity extends BaseMVPFragmentActivity {
    @Override
    protected void process() {
        show(SearchHistoryFragmentWithSearchBar.newInstance(),SearchHistoryFragmentWithSearchBar.TAG);
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }
}
