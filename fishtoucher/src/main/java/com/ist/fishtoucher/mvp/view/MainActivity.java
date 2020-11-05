package com.ist.fishtoucher.mvp.view;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.ist.fishtoucher.R;
import com.ist.fishtoucher.base.BaseMvpActivity;
import com.ist.fishtoucher.constant.GlobalConstant;
import com.ist.fishtoucher.mvp.contract.MainContract;
import com.ist.fishtoucher.entity.NovelCategory;
import com.ist.fishtoucher.entity.NovelChapterInfo;
import com.ist.fishtoucher.iApiService.NovelService;
import com.ist.fishtoucher.mvp.presenter.MainPresenter;
import com.ist.fishtoucher.test.DrawerTest;
import com.ist.fishtoucher.utils.LogUtils;
import com.ist.fishtoucher.utils.LongLogUtils;
import com.ist.fishtoucher.utils.RecyclerViewUtils;
import com.ist.fishtoucher.utils.SPUtils;
import com.ist.fishtoucher.utils.SoftInputUtils;
import com.ist.fishtoucher.adapter.CategoryAdapter;
import com.ist.fishtoucher.adapter.NovelContentAdapter;
import com.ist.fishtoucher.view.BScrollerControl;

import java.util.ArrayList;

public class MainActivity extends BaseMvpActivity<MainActivity, MainPresenter> implements MainContract.IMainView, View.OnClickListener, BScrollerControl.OnScrollChange {
    String TAG = "MainActivity";
    private final String KEY_NOVELID = "novelID";
    private String mNovelID = NovelService.JIAN_LAI_NOVEL_INDEX;
//    private String mNovelID = NovelService.DIYI_XULIE_NOVEL_INDEX;

    private EditText mEditText;
    private boolean firstInit = true;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRvCategory, mRvNovelContent;
    private CategoryAdapter mCategoryAdapter;
    private NovelContentAdapter mNovelContentAdapter;
    private BScrollerControl mScrollerControl;
    private LinearLayoutManager mLayoutManagerCategory;

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
        //左侧菜单
        mDrawerLayout = findViewById(R.id.drawerlayout);
        mRvCategory = findViewById(R.id.rv_category);
        mScrollerControl = findViewById(R.id.bsc_scroller);
        //通过监听菜单滚动条来同步滚动菜单
        mScrollerControl.setOnScrollChange(this);

        //内容recyclerView
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
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                int currentChapterNumber = getPresenter().getCurrentReading() - 1;
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
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
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
                getPresenter().read(mNovelID, chapter.getUrl(), true);
            }
        });
        mRvCategory.setAdapter(mCategoryAdapter);
        mLayoutManagerCategory = new LinearLayoutManager(getApplicationContext());
        mRvCategory.setLayoutManager(mLayoutManagerCategory);
//        mRvCategory.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        /**
         * 通过监听菜单的滚动同步滚动条的位置
         */
        mRvCategory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int countInScreen = RecyclerViewUtils.calculateItemCountInScreen(mRvCategory);
                LogUtils.i(TAG,"onScrolled: " + dy + "---countInScreen: " + countInScreen);
                int firstVisibleItemPosition = mLayoutManagerCategory.findFirstVisibleItemPosition();
                float fraction = firstVisibleItemPosition * 1f / (mCategoryAdapter.getItemCount() - countInScreen);
                mScrollerControl.setFraction(fraction);
            }
        });

        //小说内容
        mNovelContentAdapter = new NovelContentAdapter(R.layout.item_novel_content, getPresenter());
        mRvNovelContent.setAdapter(mNovelContentAdapter);
        final LinearLayoutManager novelContentLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRvNovelContent.setLayoutManager(novelContentLayoutManager);
        mNovelContentAdapter.initAutoLoadMoreEvent();
        /**
         * 内容页面滚动，用于更新"当前正在阅读"
         */
        mRvNovelContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastReadingItemPosition = -1;
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int readingItemPosition = -1;
                if (dy <= 0) {
                    //手指向下滑动
                    readingItemPosition = novelContentLayoutManager.findFirstVisibleItemPosition();
                } else {
                    //手指向上滑动
                    readingItemPosition = novelContentLayoutManager.findLastVisibleItemPosition();
                }
//                    LogUtils.d(TAG, "onScrollChange this---new: " + this.lastReadingItemPosition + "," + readingItemPosition);
                if (this.lastReadingItemPosition != readingItemPosition) {
                    RecyclerView.ViewHolder readingItemViewHolder = mRvNovelContent.findViewHolderForLayoutPosition(readingItemPosition);
                    if (readingItemViewHolder != null) {
                        int top = readingItemViewHolder.itemView.getTop();
                        if (top <= 0) {
                            LogUtils.d(TAG, "onScrollChange readingItemPosition: " + mNovelContentAdapter.getItem(readingItemPosition).getChapterName() + ",top: " + top);
                            onCurrentReadingChapterChange(mNovelContentAdapter.getItem(readingItemPosition).getChapterNumber(),
                                    mNovelContentAdapter.getItem(readingItemPosition).getChapterName());
                            this.lastReadingItemPosition = readingItemPosition;
                        }
                    }
                }
            }
        });
    }

    /**
     * 当前阅读章节变化
     *
     * @param currentReadingChapter 第几章
     * @param chapterName 章节名字
     */
    private void onCurrentReadingChapterChange(int currentReadingChapter,String chapterName) {
        //currentReadingChapter change!!

        getPresenter().setCurrentReading(currentReadingChapter);

        TextView tvReading = findViewById(R.id.tv_current_reading_chapter);
        tvReading.setText(chapterName);
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
    public void loadContentSuccessAndToDisplay(NovelChapterInfo novelChapterInfo, int chapterNumber, boolean resetData) {
        closeCategoryMenu();
        if (GlobalConstant.isFishMode()) {
        LongLogUtils.w(TAG, "displayContent: " + novelChapterInfo + ",resetData: " + resetData);
        } else {
            mEditText.setText(chapterNumber + "");

//        TextView textView = findViewById(R.id.tv_test);
//        textView.setText(novelChapterInfo);
            if (resetData) {
                //设置新数据,场景：左侧菜单目录中选择某一章
                ArrayList<NovelChapterInfo> newData = new ArrayList();
                newData.add(novelChapterInfo);
                mNovelContentAdapter.setNewInstance(newData);
                //重新加载数据后，手动调用一次方法，确保滑动到顶部
                ((LinearLayoutManager) mRvNovelContent.getLayoutManager()).scrollToPositionWithOffset(0, 0);
                onCurrentReadingChapterChange(novelChapterInfo.getChapterNumber(), novelChapterInfo.getChapterName());
            } else {
                //添加到底部，适用场景：自动加载下一页
                mNovelContentAdapter.addData(novelChapterInfo);
            }

            if (mNovelContentAdapter.getLoadMoreModule().isLoading()) {
                LogUtils.d(TAG, "load more complete: " + chapterNumber);
                mNovelContentAdapter.getLoadMoreModule().loadMoreComplete();
            }
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
                getPresenter().read(mNovelID, getPresenter().getCurrentReading() + 1, true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onBackPressed() {
        if (ifCategoryMenuShowing()) {
            closeCategoryMenu();
        } else {
            super.onBackPressed();
        }
    }

    private void hideSoftInput() {
        SoftInputUtils.hideSoftInput(this);
    }

    private boolean ifCategoryMenuShowing() {
        return mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    private void closeCategoryMenu() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void test() {
//        new DrawerTest().test(this);
    }

    @Override
    public void onScrollBarScroll(float scrollY, float fraction) {
        //目录滚动条
        int itemCountInScreen = RecyclerViewUtils.calculateItemCountInScreen(mRvCategory);
        int position = (int) (fraction * (mCategoryAdapter.getItemCount() - itemCountInScreen));
        mLayoutManagerCategory.scrollToPositionWithOffset(position, 0);
    }
}
