package com.bb.module_bookstore.adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import androidx.viewpager.widget.ViewPager;

import com.bb.module_bookstore.R;
import com.bb.module_common.adapter.base.BasePagerAdapter;
import com.bb.module_common.utils.GlideUtils;
import com.bb.module_common.utils.UIUtils;
import com.bb.module_common.utils.log.LogUtils;
import com.bb.module_common.utils.palette.PaletteColorInfo;
import com.bb.module_common.utils.palette.PaletteUtils;
import com.bb.module_novelmanager.entity.PageData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/17
 * Time: 14:03
 * 使用ViewPager实现
 */
public class VPBannerAdapter extends BasePagerAdapter<PageData.TopNovel> {
    String TAG = "VPBannerAdapter";
    private BannerHelper mBannerHelper;

    public VPBannerAdapter(int layoutId, ViewPager viewPager) {
        this(layoutId, null, viewPager);
    }

    public VPBannerAdapter(int layoutId, List<PageData.TopNovel> datas, ViewPager viewPager) {
        super(layoutId, datas);
        mBannerHelper = new BannerHelper(viewPager, 4000, false);
    }

    @Override
    protected void onBindView(View view, final PageData.TopNovel itemData, final int position) {
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
        mBannerHelper.initColorInfo(datas);
        mBannerHelper.startBanner();
    }

    public void setBgView(View bgView) {
        mBannerHelper.setBgView(bgView);
    }

    public class BannerHelper {

        private final ViewPager viewPager;
        private View mBgView;
        private final long mPeriod;
        private ChangePageRunnable changePageRunnable;
        private boolean mWithBgColorChange;//ViewPager切换的时候背景颜色变化（图片的主题色）

        public BannerHelper(@NonNull ViewPager viewPager, long period, boolean withBgColorChange) {
            this.viewPager = viewPager;
            mPeriod = period;
            mWithBgColorChange = withBgColorChange;
            initListener(viewPager, withBgColorChange);
        }

        private void initListener(@NonNull ViewPager viewPager, boolean withBgColorChange) {
            if (withBgColorChange) {
                viewPager.setPageMargin(45);
                viewPager.setOffscreenPageLimit(2);//预加载，防止切换之后图片才加载完成
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) viewPager.getLayoutParams();
                layoutParams.leftMargin = UIUtils.dp2Px(35);
                layoutParams.rightMargin = UIUtils.dp2Px(35);
                addChangeListener();
            }
        }

        /**
         * 背景变色的外层view
         * @param view
         */
        public void setBgView(View view) {
            mBgView = view;
        }

        /**
         * 添加切换监听，根据百分比修改背景颜色
         */
        private void addChangeListener() {
            if (mWithBgColorChange) {
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    String TAG = "OnPageChangeListener";

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                LogUtils.d(TAG, "onPageScrolled() = [" + position + "], positionOffset = [" + positionOffset + "], positionOffsetPixels = [" + positionOffsetPixels + "]");
                        int startPosition = position % mDatas.size();
                        int endPosition = (position + 1) % mDatas.size();

                        PaletteColorInfo startInfo = mDatas.get(startPosition).mPaletteColorInfo;
                        PaletteColorInfo endInfo = mDatas.get(endPosition).mPaletteColorInfo;
                        if (startInfo != null && endInfo != null && mBgView != null) {
                            int startColor = startInfo.mVibrantColor;
                            int endColor = endInfo.mVibrantColor;
                            int blendARGB = ColorUtils.blendARGB(startColor, endColor, positionOffset);
                            mBgView.setBackgroundColor(blendARGB);
                        } else {
                            LogUtils.e(TAG, "PaletteColorInfo have null startInfo: " + startInfo + ",endInfo: " + endInfo);
                        }
                    }

                    @Override
                    public void onPageSelected(final int position) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
            }
        }

        /**
         * 获取图片的主题色
         *
         * @param datas
         */
        public void initColorInfo(List<PageData.TopNovel> datas) {
            if (mWithBgColorChange) {
                Observable.fromIterable(datas)
                        .subscribeOn(Schedulers.io())
                        .map(new Function<PageData.TopNovel, PaletteColorInfo>() {
                            @Override
                            public PaletteColorInfo apply(PageData.TopNovel topNovel) throws Exception {
                                Bitmap bitmap = GlideUtils.getBitmap(topNovel.getCoverUrl(), viewPager.getContext());
                                PaletteColorInfo picThemeColor = PaletteUtils.getPicThemeColor(bitmap);
                                topNovel.mPaletteColorInfo = picThemeColor;
                                return picThemeColor;
                            }
                        }).subscribe(new Consumer<PaletteColorInfo>() {
                    @Override
                    public void accept(PaletteColorInfo paletteColorInfo) throws Exception {
//                LogUtils.d(TAG, "paletteColorInfo: " + paletteColorInfo);
                    }
                });
            }
        }

        /**
         * 开始轮播
         */
        public void startBanner() {
//            viewPager.setCurrentItem(mDatas.size());//会造成anr
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
