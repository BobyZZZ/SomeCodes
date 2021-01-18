package com.bb.reading.mvp.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.network.exceptionHandler.ExceptionHandler;
import com.bb.reading.base.BaseMvpActivity;
import com.bb.reading.constant.NovelConstant;
import com.bb.reading.mvp.contract.ReadingActivityContract;
import com.bb.reading.mvp.presenter.ReadingPresenter;
import com.bb.reading.utils.log.LogUtils;
import com.bb.reading.utils.log.LongLogUtils;
import com.bb.reading.utils.NovelSpUtils;
import com.bb.reading.utils.RecyclerViewUtils;
import com.bb.reading.view.BScrollerControl;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.bb.reading.R;
import com.bb.reading.constant.GlobalConstant;
import com.bb.reading.entity.NovelChapterInfo;
import com.bb.reading.entity.NovelChapterContent;
import com.bb.reading.network.NovelService;
import com.bb.reading.adapter.rv.CategoryAdapter;
import com.bb.reading.adapter.rv.NovelContentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 小说阅读Activity
 */
public class ReadingActivity extends BaseMvpActivity<ReadingPresenter> implements ReadingActivityContract.IMainView, View.OnClickListener, BScrollerControl.OnScrollChange {
    String TAG = "MainActivity";
    private String mNovelID = NovelService.JIAN_LAI_NOVEL_INDEX;
    private String mChapterID = NovelService.JIAN_LAI_NOVEL_INDEX;

    private DrawerLayout mDrawerLayout;
    private RecyclerView mRvCategory, mRvNovelContent;
    private CategoryAdapter mCategoryAdapter;
    private NovelContentAdapter mNovelContentAdapter;
    private BScrollerControl mScrollerControl;
    private LinearLayoutManager mLayoutManagerCategory;
    private LinearLayoutManager mLayoutManagerNovelContent;

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
        mRvNovelContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            String TAG = "mRvNovelContent$Listener";
            /**
             * 当前阅读所处position
             */
            private int mLastReadingItemPosition = -1;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //更新顶部标题
                int readingItemPosition = -1;
                if (dy <= 0) {
                    //手指向下滑动
                    readingItemPosition = mLayoutManagerNovelContent.findFirstVisibleItemPosition();
                } else {
                    //手指向上滑动
                    readingItemPosition = mLayoutManagerNovelContent.findLastVisibleItemPosition();
                }
//                    LogUtils.d(TAG, "onScrollChange this---new: " + this.lastReadingItemPosition + "," + readingItemPosition);
                if (mLastReadingItemPosition != readingItemPosition) {
                    RecyclerView.ViewHolder readingItemViewHolder = mRvNovelContent.findViewHolderForLayoutPosition(readingItemPosition);
                    if (readingItemViewHolder != null) {
                        int top = readingItemViewHolder.itemView.getTop();
                        if (top <= 0) {
//                            LogUtils.d(TAG, "onCurrentReadingChapterChange currentReading: " + mNovelContentAdapter.getItem(readingItemPosition) + ",top: " + top);
                            onCurrentReadingChapterChange(mNovelContentAdapter.getItem(readingItemPosition));
                            mLastReadingItemPosition = readingItemPosition;
                        }
                    }
                }

                View currentView = mLayoutManagerNovelContent.findViewByPosition(mLastReadingItemPosition);
                if (currentView != null) {
                    //更新底部页码:1/7
                    int totalPage = (int) Math.ceil(1.0d * currentView.getHeight() / mRvNovelContent.getHeight());//总页数
                    int alreadyScrollY = Math.abs(currentView.getTop());
                    int currentPage = 1 + alreadyScrollY / mRvNovelContent.getHeight();//当前页码
//                    LogUtils.d(TAG, currentPage + "/" + totalPage + "---alreadyScrollY: " + alreadyScrollY);
                    onPageNumberChanged(currentPage, totalPage);
                    //更新阅读位置
                    onLastReadingProgressChanged(mNovelContentAdapter.getItem(mLastReadingItemPosition), alreadyScrollY);
                } else {
                    LogUtils.e(TAG, "currentView == null");
                }
            }
        });
    }

    @Override
    protected void process() {
        mPresenter.getCategory(mNovelID, true);
    }

    /**
     * 更新页码
     *
     * @param currentPage
     * @param totalPage
     */
    private void onPageNumberChanged(int currentPage, int totalPage) {
        TextView tvPageNumber = findViewById(R.id.tv_pageNumber);
        tvPageNumber.setText(getString(R.string.pageNumber, currentPage, totalPage));
    }

    private void onCurrentReadingChapterChange(NovelChapterContent novelChapterContent) {
        onCurrentReadingChapterChange(novelChapterContent, false);
    }

    /**
     * 当前阅读章节变化
     */
    private void onCurrentReadingChapterChange(NovelChapterContent novelChapterContent, boolean resetReadingPosition) {
        //currentReadingChapter change!!
        mPresenter.saveCurrentReading(novelChapterContent, resetReadingPosition);

        TextView tvReading = findViewById(R.id.tv_current_reading_chapter);
        tvReading.setText(novelChapterContent.getChapterName());
    }

    @Override
    public void onError(Throwable throwable) {
        LogUtils.e(TAG, "onError: " + throwable);
        if (throwable instanceof ExceptionHandler.ResponseThrowable) {
            ExceptionHandler.ResponseThrowable error = (ExceptionHandler.ResponseThrowable) throwable;
            switch (error.code) {
                case ExceptionHandler.Error.LOCAL_CACHE_ERROR:
                    break;
                default:
                    showToast(R.string.error_server);
                    hideLoading();
                    break;
            }
        }

        BaseLoadMoreModule loadMoreModule = mNovelContentAdapter.getLoadMoreModule();
        if (loadMoreModule.isLoading()) {
            loadMoreModule.loadMoreFail();
        }
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
    public void loadingStop() {
        hideLoading();
    }

    @Override
    public void updateCategory(List<NovelChapterInfo> novelCategory) {
        mCategoryAdapter.setNewInstance(novelCategory);
//        LogUtils.w(TAG, "updateCategory: " + novelCategory);
    }

    @Override
    public void loadContentSuccessAndToDisplay(NovelChapterContent novelChapterContent, int chapterNumber, boolean resetData) {
        closeCategoryMenu();
        if (GlobalConstant.isFishMode()) {
            LongLogUtils.w(TAG, "displayContent: " + novelChapterContent + ",resetData: " + resetData);
        } else {
            if (resetData) {
                //设置新数据,场景：左侧菜单目录中选择某一章
                ArrayList<NovelChapterContent> newData = new ArrayList();
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
                LogUtils.d(TAG, "load more complete: " + novelChapterContent.getChapterName());
                mNovelContentAdapter.getLoadMoreModule().loadMoreComplete();
            }
        }
    }

    /**
     * 阅读位置变化
     *
     * @param item
     * @param alreadyScrollY
     */
    private void onLastReadingProgressChanged(NovelChapterContent item, int alreadyScrollY) {
//        LogUtils.v(TAG, "saveLastReadingState: " + item.getNovelId() + "," + item.getChapterId() + ",offset: " + alreadyScrollY);
        NovelSpUtils.saveLastReadingPosition(item.getNovelId(), alreadyScrollY);
    }

    /**
     * 恢复上次阅读状态（阅读位置）
     *
     * @param novelChapterContent
     */
    public void recoverLastReadingState(NovelChapterContent novelChapterContent) {
        //获取当前阅读章节position
        int position = -1;
        List<NovelChapterContent> datas = mNovelContentAdapter.getData();
        for (int i = 0; i < mNovelContentAdapter.getItemCount(); i++) {
            if (datas.get(i).getChapterId().equals(novelChapterContent.getChapterId())) {
                position = i;
                break;
            }
        }
        //向上滚offset应该为负值，但保存时存的是绝对值
        int offset = -NovelSpUtils.getLastReadingPosition(novelChapterContent.getNovelId());
        LogUtils.d(TAG, "recoverLastReadingState: " + novelChapterContent.getNovelId()
                + "," + novelChapterContent.getChapterId() + ",position: " + position + "---offset: " + offset);
        if (position != -1) {
            mLayoutManagerNovelContent.scrollToPositionWithOffset(position, offset);
        }
    }

    public NovelChapterInfo getChapterInfoWithOffset(String chapterId, int offset) {
        return mCategoryAdapter.getChapterInfoWithOffset(chapterId, offset);
    }

    @Override
    public void onClick(View v) {

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
}
