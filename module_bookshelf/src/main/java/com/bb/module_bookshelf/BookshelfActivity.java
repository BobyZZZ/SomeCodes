package com.bb.module_bookshelf;

import com.bb.module_bookshelf.mvp.view.LikedNovelFragment;
import com.bb.module_common.base.BaseMVPFragmentActivity;
import com.bb.module_common.base.BasePresenter;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/3/28
 * Time: 13:03
 */
public class BookshelfActivity extends BaseMVPFragmentActivity {
    @Override
    protected void process() {
        show(LikedNovelFragment.newInstance(),LikedNovelFragment.TAG);
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }
}
