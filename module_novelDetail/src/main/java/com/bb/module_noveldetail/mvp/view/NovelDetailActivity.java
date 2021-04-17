package com.bb.module_noveldetail.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bb.module_common.base.BaseMvpActivity;
import com.bb.module_common.utils.log.LogUtils;
import com.bb.module_common.view.CustomBar;
import com.bb.module_noveldetail.R;
import com.bb.module_noveldetail.adapter.NovelChapterAdapter;
import com.bb.module_noveldetail.mvp.presenter.NovelDetailActivityPresenter;
import com.bb.module_novelmanager.arouter.RouterManager;
import com.bb.module_novelmanager.arouter.UrlConstant;
import com.bb.module_novelmanager.constant.NovelConstant;
import com.bb.module_novelmanager.db.greenDao.DaoHelper;
import com.bb.module_novelmanager.db.greenDao.impl.NovelDBManager;
import com.bb.module_novelmanager.entity.NovelDetails;
import com.bb.module_noveldetail.mvp.contract.NovelDetailActivityContract;
import com.bb.module_common.utils.GlideUtils;
import com.bb.module_common.utils.StatusBarUtils;
import com.bb.uilib.adapter.base.BaseRvAdapter;
import com.bb.uilib.adapter.base.BaseVH;
import com.bb.uilib.operateMenu.OperateBean;
import com.bb.uilib.operateMenu.OperateMenu;
import com.bb.uilib.operateMenu.OperateMenuAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/18
 * Time: 0:01
 */
@Route(path = UrlConstant.ACTIVITY_URL_NOVEL_DETAIL)
public class NovelDetailActivity extends BaseMvpActivity<NovelDetailActivityPresenter> implements NovelDetailActivityContract.IView {
    String TAG = "NovelDetailActivity";

    private String mNovelId;
    private RecyclerView mRvNovel;
    private NovelChapterAdapter mNovelChapterAdapter;
    private CustomBar mCustomBar;
    private AppBarLayout mAppBarLayout;
    private ImageView mIvCoverBg;
    private OperateMenu mOperateMenu;

    public static Intent createIntent(Context context, String novelId) {
        Intent intent = new Intent(context, NovelDetailActivity.class);
        intent.putExtra(NovelConstant.KEY_NOVEL_ID, novelId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getIntent().hasExtra(NovelConstant.KEY_NOVEL_ID)) {
            mNovelId = getIntent().getStringExtra(NovelConstant.KEY_NOVEL_ID);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtils.setStatusBar(this, Color.TRANSPARENT, false, true);
    }

    public String getNovelId() {
        return mNovelId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_novel_detail;
    }

    @Override
    protected void initView() {
        mCustomBar = findViewById(R.id.customBar);
        mAppBarLayout = findViewById(R.id.appBarLayout);
        mIvCoverBg = findViewById(R.id.iv_novel_cover_bg);
        mRvNovel = findViewById(R.id.rv_novel_list);
        mOperateMenu = findViewById(R.id.operateMenu);

        //目录
        mNovelChapterAdapter = new NovelChapterAdapter();
        mNovelChapterAdapter.setOnItemClickListener(new NovelChapterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NovelDetails.Chapter data) {
//                Intent intent = ReadingActivity.createIntent(NovelDetailActivity.this, getNovelId(), data.chapterUrl);
//                startActivity(intent);
                RouterManager.getInstance().toNovelReading(getNovelId(),data.chapterUrl);
            }
        });
        mRvNovel.setLayoutManager(new LinearLayoutManager(this));
        mRvNovel.setAdapter(mNovelChapterAdapter);

        initOperateMenu();

        initListener();
    }

    /**
     * 初始化操作菜单
     */
    private void initOperateMenu() {
        ArrayList<OperateBean> operateBeans = new ArrayList<>();
        operateBeans.add(new OperateBean(R.drawable.ic_back,getString(R.string.back)));
        operateBeans.add(new OperateBean(R.drawable.ic_top,getString(R.string.toTop)));

        OperateMenuAdapter<OperateBean> operateMenuAdapter = new OperateMenuAdapter<OperateBean>(R.layout.item_operate_default, operateBeans) {
            @Override
            protected void convert(BaseVH holder, OperateBean data) {
                ImageView ivIcon = holder.getView(R.id.iv, ImageView.class);
                ivIcon.setImageResource(data.imgRes);
                ivIcon.setBackgroundResource(R.drawable.shape_circle_black);
            }
        };
        operateMenuAdapter.setOnItemClickListener(new BaseRvAdapter.OnItemClickListener<OperateBean>() {
            @Override
            public void onItemClick(OperateBean data, int position) {
                if (data.imgRes == R.drawable.ic_back) {
                    onBackPressed();
                } else if (data.imgRes == R.drawable.ic_top) {
                    scrollToTop();
                }
            }
        });
        mOperateMenu.setAdapter(operateMenuAdapter);
    }

    private void initListener() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float fraction = Math.min(1, Math.abs(1.0f * i / (mAppBarLayout.getHeight() - mCustomBar.getHeight())));
//                LogUtils.d(TAG, "mAppBarLayout's height: " + mAppBarLayout.getHeight() + ",[offset]: " + i + ",[fraction]: " + fraction);
                ColorDrawable colorDrawable = (ColorDrawable) mCustomBar.getBackground();
                colorDrawable.setAlpha((int) (fraction * 255));

                mCustomBar.getTextView(CustomBar.TITLE).setAlpha(fraction);
            }
        });
    }

    /**
     * 滑动到页面顶部
     */
    private void scrollToTop() {
        //先强制停止rv当前的滚动
        mRvNovel.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
        mRvNovel.getLayoutManager().scrollToPosition(0);
        mAppBarLayout.setExpanded(true);
    }

    @Override
    protected void process() {
        mCustomBar.expandToStatusBarPlace(true);
        mCustomBar.setText(CustomBar.TITLE, getString(R.string.novel_detail));
        mPresenter.getDetailData(mNovelId);
    }

    @Override
    public void onLoadStart() {
        showLoading(null,false);
    }

    @Override
    public void onLoadEnd() {
        hideLoading();
    }

    @Override
    public NovelDetailActivityPresenter createPresenter() {
        return new NovelDetailActivityPresenter();
    }

    public void updateNovelDetail(NovelDetails novelDetails) {
        mNovelChapterAdapter.setData(novelDetails);
    }

    public void updateNovelInfo(final NovelDetails novelDetails) {
        final NovelDBManager novelDBManager = DaoHelper.getInstance().getNovelDBManager();
        boolean alreadyLiked = novelDBManager.isAlreadyLiked(novelDetails.novelId);

        final BaseViewHolder holder = new BaseViewHolder(findViewById(R.id.layout_novel_info));
        holder.setText(R.id.tv_novel_author, novelDetails.getAuthor());
        holder.setText(R.id.tv_novel_name, novelDetails.name);
        holder.setText(R.id.tv_novel_type, novelDetails.getType());
        holder.setText(R.id.tv_novel_last_update, novelDetails.getLastUpdateTime());
        holder.setImageResource(R.id.iv_add_to_liked, R.drawable.selector_like_iv);
        holder.getView(R.id.iv_add_to_liked).setSelected(alreadyLiked);

        holder.getView(R.id.iv_add_to_liked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!novelDBManager.isAlreadyLiked(novelDetails.novelId)) {
                    long result = novelDBManager.saveLikedNovel(novelDetails);
                } else {
                    boolean result = novelDBManager.deleteLikedNovel(novelDetails.novelId);
                }
                holder.getView(R.id.iv_add_to_liked).setSelected(novelDBManager.isAlreadyLiked(novelDetails.novelId));
            }
        });

        final ImageView ivCover = holder.getView(R.id.iv_novel_cover);
        Single.just(novelDetails.coverUrl)
                .subscribeOn(Schedulers.io())
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Exception {
                        //根据url获取bitmap
                        return GlideUtils.getBitmap(s, ivCover);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Bitmap, Bitmap>() {
                    @Override
                    public Bitmap apply(Bitmap bitmap) throws Exception {
                        //小封面清晰图
                        Bitmap bitmapOrigin = Bitmap.createBitmap(bitmap);
                        ivCover.setImageBitmap(bitmapOrigin);
                        return bitmap;
                    }
                })
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        //背景模糊图
                        GlideUtils.blur(bitmap, mIvCoverBg, 15);
                    }
                });
    }
}
