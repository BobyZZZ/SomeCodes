package com.bb.reading.adapter.rv.snaphelper;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/17
 * Time: 15:07
 */
public class BannerSnapHelper extends PagerSnapHelper {

    private final long mPeriod = 2 * 1000L;
    private BannerLayoutManager mBannerLayoutManager;
    private RecyclerView mRecyclerView;

    public BannerSnapHelper() {
/*        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mBanner.setLayoutManager(layoutManager);*/
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
        mBannerLayoutManager = new BannerLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = recyclerView;
        recyclerView.setLayoutManager(mBannerLayoutManager);
        startBanner();
    }

    /**
     * 开始轮播
     */
    public void startBanner() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.smoothScrollToPosition(mBannerLayoutManager.findFirstVisibleItemPosition() + 1 );
            }
        }, mPeriod, mPeriod, TimeUnit.MILLISECONDS);
    }

    class BannerLayoutManager extends LinearLayoutManager {
        public BannerLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }
    }
}
