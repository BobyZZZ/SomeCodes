package com.bb.module_booksearch.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bb.module_common.base.BaseMVPFragmentActivity;
import com.bb.module_common.base.BasePresenter;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/3/18
 * Time: 12:21
 */
public class SearchResultActivity extends BaseMVPFragmentActivity {
    String TAG = "SearchResultActivity";
    public static String KEY_SEARCHKEY = "key_searchkey";
    private String mSearchKey;

    public static Intent createIntent(Context context, String searchKey) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(KEY_SEARCHKEY, searchKey);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mSearchKey = getIntent().getStringExtra(KEY_SEARCHKEY);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void process() {
        show(SearchResultFragment.newInstance(mSearchKey));
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }
}
