package com.bb.module_booksearch.mvp.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;

import com.bb.module_booksearch.R;
import com.bb.module_booksearch.mvp.presenter.SearchHistoryFragmentPresenter;


/**
 * Created by Boby on 2019/6/20.
 */

public class SearchHistoryFragmentWithSearchBar extends SearchHistoryFragment {
    public static String TAG = "SearchHistoryFragmentWithSearchBar";
    private EditText mEtSearch;

    public static SearchHistoryFragmentWithSearchBar newInstance() {
        SearchHistoryFragmentWithSearchBar fragment = new SearchHistoryFragmentWithSearchBar();
        return fragment;
    }

    @Override
    public SearchHistoryFragmentPresenter createPresenter() {
        SearchHistoryFragmentPresenter presenter = new SearchHistoryFragmentPresenter();
        setOthersPresenter(presenter);
        return presenter;
    }

    @Override
    protected void initView(View view) {
        //显示搜索框
        ViewStub viewStub = view.findViewById(R.id.ll_searchView_container);
        View inflate = viewStub.inflate();
        mEtSearch = inflate.findViewById(R.id.et_search);

        super.initView(view);
    }

    @Override
    protected void initListener() {
        super.initListener();

        mEtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mEtSearch.setHint(null);
                } else {
                    mEtSearch.setHint(R.string.search_text);
                }
            }
        });
        mEtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        if (!TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                            mPresenter.search(mEtSearch.getText().toString().trim());
                        } else {
                            showToast(R.string.search_key_could_not_empty);
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.refreshHistory();
    }
}
