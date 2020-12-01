package com.ist.fishtoucher.mvp.view;

import android.content.DialogInterface;
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
import com.ist.fishtoucher.R;
import com.ist.fishtoucher.base.BaseMvpActivity;
import com.ist.fishtoucher.constant.GlobalConstant;
import com.ist.fishtoucher.entity.NovelChapterInfo;
import com.ist.fishtoucher.mvp.contract.MainContract;
import com.ist.fishtoucher.entity.NovelChapterContent;
import com.ist.fishtoucher.iApiService.NovelService;
import com.ist.fishtoucher.mvp.presenter.MainPresenter;
import com.ist.fishtoucher.utils.LogUtils;
import com.ist.fishtoucher.utils.LongLogUtils;
import com.ist.fishtoucher.utils.NovelUtils;
import com.ist.fishtoucher.utils.RecyclerViewUtils;
import com.ist.fishtoucher.utils.SoftInputUtils;
import com.ist.fishtoucher.adapter.CategoryAdapter;
import com.ist.fishtoucher.adapter.NovelContentAdapter;
import com.ist.fishtoucher.view.BScrollerControl;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseMvpActivity<MainActivity, MainPresenter> implements MainContract.IMainView, View.OnClickListener, BScrollerControl.OnScrollChange {
    String TAG = "MainActivity";
    private final String KEY_NOVELID = "novelID";
    //    private String mNovelID = NovelService.JIAN_LAI_NOVEL_INDEX;
//    private String mNovelID = NovelService.DIYI_XULIE_NOVEL_INDEX;
    private String mNovelID = NovelService.FKNGMN_NOVEL_INDEX;

    private EditText mEditText;
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRvCategory, mRvNovelContent;
    private CategoryAdapter mCategoryAdapter;
    private NovelContentAdapter mNovelContentAdapter;
    private BScrollerControl mScrollerControl;
    private LinearLayoutManager mLayoutManagerCategory;
    private LinearLayoutManager mLayoutManagerNovelContent;

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

    private void initData() {
        NovelUtils.saveLastReadNovel(mNovelID);
        initRV();

        getPresenter().getCategory(mNovelID);
    }

    private void initRV() {
        //左侧菜单-目录
        mCategoryAdapter = new CategoryAdapter(R.layout.item_category, getPresenter());
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
                LogUtils.i(TAG, "onScrolled: " + dy + "---countInScreen: " + countInScreen);
                int firstVisibleItemPosition = mLayoutManagerCategory.findFirstVisibleItemPosition();
                float fraction = firstVisibleItemPosition * 1f / (mCategoryAdapter.getItemCount() - countInScreen);
                mScrollerControl.setFraction(fraction);
            }
        });

        //小说内容
        mNovelContentAdapter = new NovelContentAdapter(R.layout.item_novel_content, getPresenter());
        mRvNovelContent.setAdapter(mNovelContentAdapter);
        mLayoutManagerNovelContent = new LinearLayoutManager(getApplicationContext());
        mRvNovelContent.setLayoutManager(mLayoutManagerNovelContent);
        mNovelContentAdapter.initAutoLoadMoreEvent();
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
                    onPageNumberChanged(currentPage,totalPage);
                    //更新阅读位置
                    onLastReadingProgressChanged(mNovelContentAdapter.getItem(mLastReadingItemPosition),alreadyScrollY);
                } else {
                    LogUtils.e(TAG, "currentView == null");
                }
            }
        });
    }

    /**
     * 更新页码
     * @param currentPage
     * @param totalPage
     */
    private void onPageNumberChanged(int currentPage, int totalPage) {
        TextView tvPageNumber = findViewById(R.id.tv_pageNumber);
        tvPageNumber.setText(getString(R.string.pageNumber,currentPage,totalPage));
    }

    private void onCurrentReadingChapterChange(NovelChapterContent novelChapterContent) {
        onCurrentReadingChapterChange(novelChapterContent,false);
    }
    /**
     * 当前阅读章节变化
     */
    private void onCurrentReadingChapterChange(NovelChapterContent novelChapterContent,boolean resetReadingPosition) {
        //currentReadingChapter change!!
        getPresenter().saveCurrentReading(novelChapterContent,resetReadingPosition);

        TextView tvReading = findViewById(R.id.tv_current_reading_chapter);
        tvReading.setText(novelChapterContent.getChapterName());
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
        showLoading(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                LogUtils.w(TAG,"onCancel");
            }
        });
    }

    @Override
    public void updateCategory(List<NovelChapterInfo> novelCategory) {
        mCategoryAdapter.setNewInstance(novelCategory);
//        LogUtils.e(TAG, "updateCategory: " + novelCategory);

    }

    @Override
    public void loadContentSuccessAndToDisplay(NovelChapterContent novelChapterContent, int chapterNumber, boolean resetData) {
        closeCategoryMenu();
        if (GlobalConstant.isFishMode()) {
            LongLogUtils.w(TAG, "displayContent: " + novelChapterContent + ",resetData: " + resetData);
        } else {
            mEditText.setText(mCategoryAdapter.getChapterPosition(novelChapterContent.getChapterId()) + 1 + "");

            if (resetData) {
                //设置新数据,场景：左侧菜单目录中选择某一章
                ArrayList<NovelChapterContent> newData = new ArrayList();
                newData.add(novelChapterContent);
                mNovelContentAdapter.setNewInstance(newData);
                //重新加载数据后，手动调用一次方法，确保滑动到顶部
                mRvNovelContent.getLayoutManager().scrollToPosition(0);
                onCurrentReadingChapterChange(novelChapterContent,true);
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
     * @param item
     * @param alreadyScrollY
     */
    private void onLastReadingProgressChanged(NovelChapterContent item, int alreadyScrollY) {
        LogUtils.d(TAG, "saveLastReadingState: " + item.getNovelId() + "," + item.getChapterId() + ",offset: " + alreadyScrollY);
        NovelUtils.saveLastReadingPosition(item.getNovelId(), alreadyScrollY);
    }

    /**
     * 恢复上次阅读状态（阅读位置）
     * @param novelChapterContent
     */
    public void recoverLastReadingState(NovelChapterContent novelChapterContent) {
        //获取当前阅读章节position
        getChapterInfoWithOffset(novelChapterContent.getChapterId(),0);
        int position = -1;
        List<NovelChapterContent> datas = mNovelContentAdapter.getData();
        for (int i = 0; i < mNovelContentAdapter.getItemCount(); i++) {
            if (datas.get(i).getChapterId().equals(novelChapterContent.getChapterId())) {
                position = i;
                break;
            }
        }
        //向上滚offset应该为负值，但保存时存的是绝对值
        int offset = -NovelUtils.getLastReadingPosition(novelChapterContent.getNovelId());
        LogUtils.d(TAG, "recoverLastReadingState: "+ novelChapterContent.getNovelId() + "," + novelChapterContent.getChapterId() + ",position: " + position + "---offset: " + offset);
        if (position != -1) {
            mLayoutManagerNovelContent.scrollToPositionWithOffset(position,offset);
        }
    }

    public NovelChapterInfo getChapterInfoWithOffset(String chapterId, int offset) {
        return mCategoryAdapter.getChapterInfoWithOffset(chapterId, offset);
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
                NovelChapterInfo targetChapter = mCategoryAdapter.getChapterInfoWithOffset(NovelUtils.getLastReadChapter(), 1);
                if (targetChapter != null) {
                    getPresenter().read(mNovelID, targetChapter.getChapterId(), true);
                } else {
                    showToast(R.string.no_more);
                }
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
