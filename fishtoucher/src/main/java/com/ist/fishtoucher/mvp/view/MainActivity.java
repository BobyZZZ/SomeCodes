package com.ist.fishtoucher.mvp.view;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.ist.fishtoucher.R;
import com.ist.fishtoucher.base.BaseMvpActivity;
import com.ist.fishtoucher.mvp.contract.MainContract;
import com.ist.fishtoucher.entity.NovelCategory;
import com.ist.fishtoucher.entity.NovelChapterInfo;
import com.ist.fishtoucher.iApiService.NovelService;
import com.ist.fishtoucher.mvp.presenter.MainPresenter;
import com.ist.fishtoucher.utils.LogUtils;
import com.ist.fishtoucher.utils.LongLogUtils;
import com.ist.fishtoucher.utils.SPUtils;
import com.ist.fishtoucher.utils.SoftInputUtils;
import com.ist.fishtoucher.adapter.CategoryAdapter;
import com.ist.fishtoucher.adapter.NovelContentAdapter;

public class MainActivity extends BaseMvpActivity<MainActivity, MainPresenter> implements MainContract.IMainView, View.OnClickListener {
    String TAG = "MainActivity";
    private final String KEY_NOVELID = "novelID";
    private String mNovelID = NovelService.DIYI_XULIE_NOVEL_INDEX;

    private EditText mEditText;
    private boolean firstInit = true;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRvCategory, mRvNovelContent;
    private CategoryAdapter mCategoryAdapter;
    private NovelContentAdapter mNovelContentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        if (getIntent().hasExtra(KEY_NOVELID)) {
            mNovelID = getIntent().getStringExtra("novelID");
        }
        return new MainPresenter(mNovelID);
    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        findViewById(R.id.tv_next).setOnClickListener(this);
        mEditText = findViewById(R.id.et_which_chapter);

        mDrawerLayout = findViewById(R.id.drawerlayout);
        //左侧菜单
        mRvCategory = findViewById(R.id.rv_category);
        //小说内容recyclerView
        mRvNovelContent = findViewById(R.id.rv_novel_content);

        mDrawerLayout.addDrawerListener(getDrawerLayoutListener());

        initData();
    }

    private DrawerLayout.DrawerListener getDrawerLayoutListener() {
        DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                int currentChapterNumber = getPresenter().getCurrentChapterNumber() - 1;
                LogUtils.d(TAG, "onDrawerOpened: " + drawerView.getId() + ",currentChapterNumber: " + currentChapterNumber);
                if (mRvCategory.getAdapter().getItemCount() > currentChapterNumber) {
//                    mRvCategory.smoothScrollToPosition(currentChapterNumber);
//                    mRvCategory.scrollToPosition(currentChapterNumber);只滚动到显示出来，不置顶
                    ((LinearLayoutManager) mRvCategory.getLayoutManager()).scrollToPositionWithOffset(currentChapterNumber, 0);
                } else {
                    LogUtils.d(TAG, "cancel scrollToPosition: ");
                }
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        };
        return drawerListener;
    }

    private void initData() {
        mEditText.setText(SPUtils.getInt(SPUtils.KEY_LAST_READ, 1) + "");
        getPresenter().getCategory(mNovelID);

        initRV();
    }

    private void initRV() {
        //左侧菜单-目录
        mCategoryAdapter = new CategoryAdapter(R.layout.item_category, getPresenter());
        mCategoryAdapter.setOnChapterClickListener(new CategoryAdapter.OnChapterClickListener() {
            @Override
            public void onclick(NovelCategory.Chapter chapter, int chapterNumber) {
                getPresenter().read(mNovelID, chapter.getUrl(), chapterNumber + 1);
            }
        });
        mRvCategory.setAdapter(mCategoryAdapter);
        mRvCategory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        mRvCategory.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

        //小说内容
        mNovelContentAdapter = new NovelContentAdapter(R.layout.item_novel_content,getPresenter());
        mRvNovelContent.setAdapter(mNovelContentAdapter);
        mRvNovelContent.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mNovelContentAdapter.initAutoLoadMoreEvent();
    }

    @Override
    public void onError(Throwable throwable) {
        BaseLoadMoreModule loadMoreModule = mNovelContentAdapter.getLoadMoreModule();
        if (loadMoreModule.isLoading()) {
            LogUtils.d(TAG, "onError: loadMoreFail");
            loadMoreModule.loadMoreFail();
        }
    }

    @Override
    public void loading() {
//        hideSoftInput();
    }

    @Override
    public void updateCategory(NovelCategory novelCategory) {
        mCategoryAdapter.setNewInstance(novelCategory.getChapters());
        Log.e(TAG, "updateCategory: " + novelCategory);
        if (firstInit) {//首次打开时自动打开上次观看章节
            firstInit = false;
            getPresenter().read(mNovelID, SPUtils.getInt(SPUtils.KEY_LAST_READ, 1));
        }

    }

    @Override
    public void displayContent(NovelChapterInfo novelChapterInfo, int chapterNumber) {
        LongLogUtils.w(TAG, "displayContent: " + novelChapterInfo);
        mEditText.setText(chapterNumber + "");
        mDrawerLayout.closeDrawer(GravityCompat.START);

//        TextView textView = findViewById(R.id.tv_test);
//        textView.setText(novelChapterInfo);
//        scrollToTop();
        mNovelContentAdapter.addData(novelChapterInfo);
        if (mNovelContentAdapter.getLoadMoreModule().isLoading()) {
            LogUtils.d(TAG, "load more complete: " + chapterNumber);
            mNovelContentAdapter.getLoadMoreModule().loadMoreComplete();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                String text = mEditText.getText().toString().trim();
                if (!TextUtils.isEmpty(text)) {
                    int chapter = Integer.parseInt(text);
                    LogUtils.d(TAG, "onClick chapter: " + chapter + "---novel: " + mNovelID);
                    getPresenter().read(mNovelID, chapter);
                }
                break;
            case R.id.tv_next:
                getPresenter().read(mNovelID, getPresenter().getCurrentChapterNumber() + 1);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    private void scrollToTop() {
//        ScrollView scrollView = findViewById(R.id.sv_content);
//        scrollView.scrollTo(0,0);
    }

    private void hideSoftInput() {
        SoftInputUtils.hideSoftInput(this);
    }
}
