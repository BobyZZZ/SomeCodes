package com.bb.module_bookstore.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.bb.module_bookstore.R;
import com.bb.module_common.adapter.base.BasePagerAdapter;
import com.bb.module_common.utils.GlideUtils;
import com.bb.module_novelmanager.entity.PageData;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/17
 * Time: 14:03
 */
public class VPBannerAdapter extends BasePagerAdapter<PageData.TopNovel> {
    String TAG = "BannerAdapter";
    private BannerHelper mBannerHelper;

    public VPBannerAdapter(int layoutId, ViewPager viewPager) {
        this(layoutId, null, viewPager);
    }

    public VPBannerAdapter(int layoutId, List<PageData.TopNovel> datas, ViewPager viewPager) {
        super(layoutId, datas);
        mBannerHelper = new BannerHelper(viewPager, 4000);
    }

    @Override
    protected void onBindView(View view, PageData.TopNovel itemData) {
        ImageView ivCover = view.findViewById(R.id.iv_novel_cover);
        GlideUtils.load(itemData.getCoverUrl(), ivCover);

        TextView textView = view.findViewById(R.id.tv_novel_introduction);
        textView.setText(itemData.getIntroduction());
    }

    @Override
    public int getCount() {
        return mDatas != null && !mDatas.isEmpty() ? Integer.MAX_VALUE : 0;
    }

    @Override
    protected PageData.TopNovel getItemData(int position) {
        return super.getItemData(position % mDatas.size());
    }

    @Override
    public void setDatas(List<PageData.TopNovel> datas) {
        super.setDatas(datas);
        mBannerHelper.startBanner();
    }

    public class BannerHelper {

        private final ViewPager viewPager;
        private final long mPeriod;
        private ChangePageRunnable changePageRunnable;

        public BannerHelper(@NonNull ViewPager viewPager, long period) {
            this.viewPager = viewPager;
            mPeriod = period;
        }

        /**
         * 开始轮播
         */
        public void startBanner() {
//            viewPager.setCurrentItem(mDatas.size() * 1000);
            stopBanner();
            changePageRunnable = new ChangePageRunnable();
            viewPager.postDelayed(changePageRunnable, mPeriod);
        }

        public void stopBanner() {
            if (changePageRunnable != null) {
                viewPager.removeCallbacks(changePageRunnable);
                changePageRunnable = null;
            }
        }

        class ChangePageRunnable implements Runnable {
            @Override
            public void run() {
                newPage();
                viewPager.postDelayed(this, mPeriod);
            }

            private void newPage() {
                int next = viewPager.getCurrentItem() + 1;
                if (next == Integer.MAX_VALUE - 1) {
                    viewPager.setCurrentItem(0);
                } else {
                    viewPager.setCurrentItem(next, true);
                }
            }
        }

    }
}
