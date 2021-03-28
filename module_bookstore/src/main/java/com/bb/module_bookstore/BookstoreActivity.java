package com.bb.module_bookstore;

import com.bb.module_bookstore.mvp.view.TabViewPagerFragment;
import com.bb.module_common.base.BaseMVPFragmentActivity;
import com.bb.module_common.base.BasePresenter;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/3/28
 * Time: 15:38
 */
public class BookstoreActivity extends BaseMVPFragmentActivity {
    @Override
    protected void process() {
        show(TabViewPagerFragment.newInstance(TabViewPagerFragment.TYPE_SHUCHENG), "" + TabViewPagerFragment.TYPE_SHUCHENG);
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }
}
