package com.bb.module_noveldetail.listener;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.module_common.utils.log.LogUtils;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/20
 * Time: 22:33
 */
public class NovelContentOnScrollListener extends RecyclerView.OnScrollListener {
    private final LinearLayoutManager mLayoutManagerNovelContent;
    private final RecyclerView mRvNovelContent;
    String TAG = "mRvNovelContent$Listener";

    public NovelContentOnScrollListener(LinearLayoutManager layoutManager, RecyclerView recyclerView,
                                        ReadingChangeListener readingChangeListener) {
        mLayoutManagerNovelContent = layoutManager;
        mRvNovelContent = recyclerView;
        mReadingChangeListener = readingChangeListener;
    }

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
//        LogUtils.d(TAG, "onScrollChange this---new: " + this.mLastReadingItemPosition + "," + readingItemPosition);
        if (mLastReadingItemPosition != readingItemPosition) {
            RecyclerView.ViewHolder readingItemViewHolder = mRvNovelContent.findViewHolderForLayoutPosition(readingItemPosition);
            if (readingItemViewHolder != null) {
                int top = readingItemViewHolder.itemView.getTop();
                if (top <= 0) {
//                    LogUtils.d(TAG, "onCurrentReadingChapterChange currentReading: " + ((NovelContentAdapter)mRvNovelContent.getAdapter()).getItem(readingItemPosition).getChapterName() + ",top: " + top);
                    onCurrentReadingChapterChange(readingItemPosition);
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
//            LogUtils.d(TAG, currentPage + "/" + totalPage + "---alreadyScrollY: " + alreadyScrollY);
            onPageNumberChanged(currentPage, totalPage);
            //更新阅读位置
            onLastReadingProgressChanged(mLastReadingItemPosition, alreadyScrollY);
        } else {
            LogUtils.e(TAG, "currentView == null");
        }
    }

    private void onPageNumberChanged(int currentPage, int totalPage) {
        if (mReadingChangeListener != null) {
            mReadingChangeListener.onPageNumberChanged(currentPage,totalPage);
        }
    }

    private void onCurrentReadingChapterChange(int currentReadingItemPosition) {
        if (mReadingChangeListener != null) {
            mReadingChangeListener.onCurrentReadingChapterChange(currentReadingItemPosition);
        }
    }

    private void onLastReadingProgressChanged(int currentReadingItemPosition, int alreadyScrollY) {
        if (mReadingChangeListener != null) {
            mReadingChangeListener.onLastReadingProgressChanged(currentReadingItemPosition,alreadyScrollY);
        }
    }


    private ReadingChangeListener mReadingChangeListener;
    public interface ReadingChangeListener {
        void onPageNumberChanged(int currentPage, int totalPage);
        void onCurrentReadingChapterChange(int currentReadingItemPosition);
        void onLastReadingProgressChanged(int currentReadingItemPosition, int alreadyScrollY);
    }
}
