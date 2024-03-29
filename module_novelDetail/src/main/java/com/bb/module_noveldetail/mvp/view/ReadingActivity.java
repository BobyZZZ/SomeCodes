package com.bb.module_noveldetail.mvp.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bb.module_common.base.BaseMvpActivity;
import com.bb.module_common.utils.RecyclerViewUtils;
import com.bb.module_common.view.BScrollerControl;
import com.bb.module_noveldetail.R;
import com.bb.module_noveldetail.adapter.CategoryAdapter;
import com.bb.module_noveldetail.adapter.NovelContentAdapter;
import com.bb.module_novelmanager.arouter.UrlConstant;
import com.bb.module_novelmanager.constant.NovelConstant;
import com.bb.module_novelmanager.entity.NovelChapterContentFruitBean;
import com.bb.module_noveldetail.mvp.contract.ReadingActivityContract;
import com.bb.module_noveldetail.mvp.presenter.ReadingPresenter;
import com.bb.module_noveldetail.listener.NovelContentOnScrollListener;
import com.bb.module_common.utils.StatusBarUtils;
import com.bb.module_common.utils.log.LogUtils;
import com.bb.module_common.utils.log.LongLogUtils;
import com.bb.module_novelmanager.utils.NovelSpUtils;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.bb.module_novelmanager.constant.GlobalConstant;
import com.bb.module_novelmanager.entity.NovelChapterInfo;
import com.bb.module_novelmanager.network.NovelService;

import java.util.ArrayList;
import java.util.List;

/**
 * 小说阅读Activity
 */
@Route(path = UrlConstant.ACTIVITY_URL_NOVEL_READING)
public class ReadingActivity extends BaseMvpActivity<ReadingPresenter> implements ReadingActivityContract.IMainView,
        BScrollerControl.OnScrollChange, NovelContentOnScrollListener.ReadingChangeListener {
    String TAG = "ReadingActivity";
    private String mNovelID = NovelService.JIAN_LAI_NOVEL_INDEX;
    private String mChapterID = NovelService.JIAN_LAI_NOVEL_INDEX;

    private DrawerLayout mDrawerLayout;
    private RecyclerView mRvCategory, mRvNovelContent;
    private CategoryAdapter mCategoryAdapter;
    private NovelContentAdapter mNovelContentAdapter;
    private BScrollerControl mScrollerControl;
    private LinearLayoutManager mLayoutManagerCategory;
    private LinearLayoutManager mLayoutManagerNovelContent;
    private NovelContentOnScrollListener mNovelContentOnScrollListener;

    public static Intent createIntent(Context context, String novelId, String chapterID) {
        Intent intent = new Intent(context, ReadingActivity.class);
        intent.putExtra(NovelConstant.KEY_NOVEL_ID, novelId);
        intent.putExtra(NovelConstant.KEY_CHAPTER_ID, chapterID);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getIntent().hasExtra(NovelConstant.KEY_NOVEL_ID)) {
            mNovelID = getIntent().getStringExtra(NovelConstant.KEY_NOVEL_ID);
            mChapterID = getIntent().getStringExtra(NovelConstant.KEY_CHAPTER_ID);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtils.setStatusBar(this, Color.TRANSPARENT/*getColor(R.color.bg_novel_content)*/,true,true);
        final int top = StatusBarUtils.getStatusBarHeight(this);
        findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.ll_content_container).setPadding(0,top,0,0);
                findViewById(R.id.fl_menu_container).setPadding(0,top,0,0);
            }
        });
    }

    @Override
    public String getNovelID() {
        return mNovelID;
    }

    @Override
    public String getChapterID() {
        return mChapterID;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reading;
    }

    @Override
    public ReadingPresenter createPresenter() {
        return new ReadingPresenter();
    }

    @Override
    protected void initView() {
        initMode();
        //左侧菜单
        mDrawerLayout = findViewById(R.id.drawerlayout);
        mRvCategory = findViewById(R.id.rv_category);
        mScrollerControl = findViewById(R.id.bsc_scroller);
        //通过监听菜单滚动条来同步滚动菜单
        mScrollerControl.setOnScrollChange(this);

        //内容recyclerView
        mRvNovelContent = findViewById(R.id.rv_novel_content);

        mDrawerLayout.addDrawerListener(getDrawerLayoutListener());

        initRV();
    }

    private void initMode() {
        if (GlobalConstant.isFishMode()) {
            final View rootView = findViewById(R.id.ll_content_container);
            View rvContent = findViewById(R.id.rv_novel_content);

            rootView.setAlpha(0);

            rvContent.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            rootView.setAlpha(1f);
                            return true;
                        case MotionEvent.ACTION_UP:
                            rootView.setAlpha(0);
                            break;
                    }
                    return false;
                }
            });
        }
    }

    private DrawerLayout.DrawerListener getDrawerLayoutListener() {
        DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                LogUtils.d(TAG, "onDrawerOpened: ");
                mCategoryAdapter.scrollToCurrentReading();
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

    private void initRV() {
        //左侧菜单-目录
        mCategoryAdapter = new CategoryAdapter(R.layout.item_category);
        mCategoryAdapter.setOnChapterClickListener(new CategoryAdapter.OnChapterClickListener() {
            @Override
            public void onclick(NovelChapterInfo chapter, int chapterNumber) {
                getPresenter().read(mNovelID, chapter.getChapterId(), true);
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
                //同步滚动条
                int countInScreen = RecyclerViewUtils.calculateItemCountInScreen(mRvCategory);
//                LogUtils.v(TAG, "onScrolled: " + dy + "---countInScreen: " + countInScreen);
                int firstVisibleItemPosition = mLayoutManagerCategory.findFirstVisibleItemPosition();
                float fraction = firstVisibleItemPosition * 1f / (mCategoryAdapter.getItemCount() - countInScreen);
                mScrollerControl.setFraction(fraction);
            }
        });

        //小说内容
        mNovelContentAdapter = new NovelContentAdapter(R.layout.item_novel_content);
        mRvNovelContent.setAdapter(mNovelContentAdapter);
        mLayoutManagerNovelContent = new LinearLayoutManager(getApplicationContext());
        mRvNovelContent.setLayoutManager(mLayoutManagerNovelContent);
        mNovelContentAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadMore();
            }
        });
        /**
         * 内容页面滚动，用于更新"当前正在阅读"
         */
        mNovelContentOnScrollListener = new NovelContentOnScrollListener(mLayoutManagerNovelContent, mRvNovelContent,this);
        mRvNovelContent.addOnScrollListener(mNovelContentOnScrollListener);
    }

    @Override
    protected void process() {
        mPresenter.getCategory(mNovelID, true);
    }

    @Override
    public void loadingStart() {
        showLoading(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                LogUtils.w(TAG, "onCancel");
            }
        }, false);
    }

    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);

        BaseLoadMoreModule loadMoreModule = mNovelContentAdapter.getLoadMoreModule();
        if (loadMoreModule.isLoading()) {
            loadMoreModule.loadMoreFail();
        }
    }

    @Override
    public void loadingStop() {
        hideLoading();
    }

    @Override
    public void updateCategory(List<NovelChapterInfo> novelCategory) {
        mCategoryAdapter.setNewInstance(novelCategory);
//        LogUtils.w(TAG, "updateCategory: " + novelCategory);
    }

    @Override
    public void loadContentSuccessAndToDisplay(NovelChapterContentFruitBean novelChapterContent, boolean hasMore, boolean resetData) {
        closeCategoryMenu();
        LongLogUtils.w(TAG, "displayContent: " + novelChapterContent + ",resetData: " + resetData);
        if (resetData) {
            //设置新数据,场景：左侧菜单目录中选择某一章
            ArrayList<NovelChapterContentFruitBean> newData = new ArrayList();
            newData.add(novelChapterContent);
            mNovelContentAdapter.setNewInstance(newData);
            //重新加载数据后，手动调用一次方法，确保滑动到顶部
            mRvNovelContent.getLayoutManager().scrollToPosition(0);
            onCurrentReadingChapterChange(novelChapterContent, true);
        } else {
            //添加到底部，适用场景：自动加载下一页
            mNovelContentAdapter.addData(novelChapterContent);
        }

        if (mNovelContentAdapter.getLoadMoreModule().isLoading()) {
            LogUtils.d(TAG, "load more complete: " + novelChapterContent.chapterName);
            mNovelContentAdapter.getLoadMoreModule().loadMoreComplete();
        }

        mNovelContentAdapter.getLoadMoreModule().setEnableLoadMore(hasMore);
    }

    /**
     * 恢复上次阅读状态（阅读位置）
     *
     * @param novelChapterContent
     */
    public void recoverLastReadingState(NovelChapterContentFruitBean novelChapterContent) {
        //获取当前阅读章节position
        int position = -1;
        List<NovelChapterContentFruitBean> datas = mNovelContentAdapter.getData();
        for (int i = 0; i < mNovelContentAdapter.getItemCount(); i++) {
            if (datas.get(i).chapterId.equals(novelChapterContent.chapterId)) {
                position = i;
                break;
            }
        }
        //向上滚offset应该为负值，但保存时存的是绝对值
        int offset = -NovelSpUtils.getLastReadingPosition(novelChapterContent.novelId);
        LogUtils.d(TAG, "recoverLastReadingState: " + novelChapterContent.novelId
                + "," + novelChapterContent.chapterId + ",position: " + position + "---offset: " + offset);
        if (position != -1) {
            mLayoutManagerNovelContent.scrollToPositionWithOffset(position, offset);
        }
    }

    public NovelChapterInfo getChapterInfoWithOffset(String chapterId, int offset) {
        return mCategoryAdapter.getChapterInfoWithOffset(chapterId, offset);
    }

    /**
     * 当前阅读章节变化
     */
    private void onCurrentReadingChapterChange(NovelChapterContentFruitBean novelChapterContent, boolean resetReadingPosition) {
        //currentReadingChapter change!!
        mPresenter.saveCurrentReading(novelChapterContent, resetReadingPosition);

        TextView tvReading = findViewById(R.id.tv_current_reading_chapter);
        tvReading.setText(novelChapterContent.chapterName);
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

    private boolean ifCategoryMenuShowing() {
        return mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    private void closeCategoryMenu() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void test() {
//        new DrawerTest().test(this);
//        getPresenter().mytest2();

/*        DaoFactory daoFactory = DaoFactory.getInstance();
        daoFactory.init(this);
        daoFactory.testGreenDaoDBManager();*/
    }

    @Override
    public void onScrollBarScroll(float scrollY, float fraction) {
        //目录滚动条
        int itemCountInScreen = RecyclerViewUtils.calculateItemCountInScreen(mRvCategory);
        int position = (int) (fraction * (mCategoryAdapter.getItemCount() - itemCountInScreen));
        mLayoutManagerCategory.scrollToPositionWithOffset(position, 0);
    }


    /**
     * 更新页码
     *
     * @param currentPage
     * @param totalPage
     */
    @Override
    public void onPageNumberChanged(int currentPage, int totalPage) {
        TextView tvPageNumber = findViewById(R.id.tv_pageNumber);
        tvPageNumber.setText(getString(R.string.pageNumber, currentPage, totalPage));
    }

    /**
     * 更新当前阅读章节
     * @param currentReadingItemPosition
     */
    @Override
    public void onCurrentReadingChapterChange(int currentReadingItemPosition) {
        onCurrentReadingChapterChange(mNovelContentAdapter.getItem(currentReadingItemPosition), false);
    }

    /**
     * 阅读位置变化
     * @param currentReadingItemPosition    当前阅读章节
     * @param alreadyScrollY
     */
    @Override
    public void onLastReadingProgressChanged(int currentReadingItemPosition, int alreadyScrollY) {
        NovelChapterContentFruitBean item = mNovelContentAdapter.getItem(currentReadingItemPosition);
        NovelSpUtils.saveLastReadingPosition(item.novelId, alreadyScrollY);
    }

    @Override
    public void onScrolledToBottom() {
        if (!mPresenter.hasMoreChapter()) {
            showToast(R.string.no_more);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            int currentReadingPosition = mNovelContentOnScrollListener.getLastReadingItemPosition();
            if (currentReadingPosition >= mNovelContentAdapter.getItemCount() - 1) {
                return false;
            }
            mLayoutManagerNovelContent.scrollToPosition(currentReadingPosition + 1);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
