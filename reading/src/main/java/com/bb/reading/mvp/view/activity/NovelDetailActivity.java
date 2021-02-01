package com.bb.reading.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.reading.R;
import com.bb.reading.adapter.rv.NovelChapterAdapter;
import com.bb.reading.base.BaseMvpActivity;
import com.bb.reading.constant.NovelConstant;
import com.bb.reading.entity.NovelDetails;
import com.bb.reading.mvp.contract.NovelDetailActivityContract;
import com.bb.reading.mvp.presenter.NovelDetailActivityPresenter;
import com.bb.reading.utils.GlideUtils;
import com.bb.reading.utils.StatusBarUtils;
import com.bb.reading.view.CustomBar;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;

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
public class NovelDetailActivity extends BaseMvpActivity<NovelDetailActivityPresenter> implements NovelDetailActivityContract.IView {
    String TAG = "NovelDetailActivity";

    private String mNovelId;
    private RecyclerView mRvNovel;
    private NovelChapterAdapter mNovelChapterAdapter;
    private CustomBar mCustomBar;
    private AppBarLayout mAppBarLayout;
    private ImageView mIvCoverBg;

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

        //目录
        mNovelChapterAdapter = new NovelChapterAdapter();
        mNovelChapterAdapter.setOnItemClickListener(new NovelChapterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NovelDetails.Chapter data) {
                Log.d(TAG, "onItemClick() called with: data = [" + data + "]");
                Intent intent = ReadingActivity.createIntent(NovelDetailActivity.this, getNovelId(), data.chapterUrl);
                startActivity(intent);
            }
        });
        mRvNovel.setLayoutManager(new LinearLayoutManager(this));
        mRvNovel.setAdapter(mNovelChapterAdapter);

        initListener();
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

    @Override
    protected void process() {
        mCustomBar.expandToStatusBarPlace(true);
        mCustomBar.setText(CustomBar.TITLE, getString(R.string.novel_detail));
        mPresenter.getDetailData(mNovelId);
    }

    @Override
    public NovelDetailActivityPresenter createPresenter() {
        return new NovelDetailActivityPresenter();
    }

    public void updateNovelDetail(NovelDetails novelDetails) {
        mNovelChapterAdapter.setData(novelDetails);
    }

    public void updateNovelInfo(NovelDetails novelDetails) {
        BaseViewHolder holder = new BaseViewHolder(findViewById(R.id.layout_novel_info));
        holder.setText(R.id.tv_novel_author, novelDetails.getAuthor());
        holder.setText(R.id.tv_novel_name, novelDetails.name);
        holder.setText(R.id.tv_novel_type, novelDetails.getType());
        holder.setText(R.id.tv_novel_last_update, novelDetails.getLastUpdateTime());

        ImageView ivCover = holder.getView(R.id.iv_novel_cover);
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

    @Override
    public void onError(Throwable throwable) {

    }
}
