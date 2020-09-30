package com.ist.fishtoucher.view;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.ist.fishtoucher.R;
import com.ist.fishtoucher.base.BaseMvpActivity;
import com.ist.fishtoucher.contract.MainContract;
import com.ist.fishtoucher.entity.NovelCategory;
import com.ist.fishtoucher.iApiService.NovelService;
import com.ist.fishtoucher.presenter.MainPresenter;
import com.ist.fishtoucher.utils.LogUtils;
import com.ist.fishtoucher.utils.SPUtils;

import java.util.List;

public class MainActivity extends BaseMvpActivity<MainActivity, MainPresenter> implements MainContract.IMainView, View.OnClickListener {
    String TAG = "MainActivity";
    private String mNovel = NovelService.DIYI_XULIE_NOVEL_INDEX;

    private EditText mEditText;
    private boolean firstInit = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        findViewById(R.id.tv_next).setOnClickListener(this);
        mEditText = findViewById(R.id.et_which_chapter);


        initData();
    }

    private void initData() {
        mEditText.setText(SPUtils.getInt(SPUtils.KEY_LAST_READ,1)+"");
        getPresenter().getCategory(mNovel);
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void updateCategory(NovelCategory novelCategory) {
        Log.e(TAG, "updateCategory: " + novelCategory);
        if (firstInit) {//首次打开时自动打开上次观看章节
            firstInit = false;
            getPresenter().read(mNovel, SPUtils.getInt(SPUtils.KEY_LAST_READ, 1));
        }
    }

    @Override
    public void displayContent(String content, int chapterIndex) {
        LogUtils.w(TAG, "displayContent: " + content);
//        TextView textView = findViewById(R.id.tv_test);
//        textView.setText(content);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                String text = mEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(text)) {
                    int chapter = Integer.parseInt(text);
                    Log.d(TAG, "onClick chapter: " + chapter + "---novel: " + mNovel);
                    getPresenter().read(mNovel, chapter);
                }
                break;
            case R.id.tv_next:
                getPresenter().read(mNovel, getPresenter().getCurrentChapterIndex() + 1);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
