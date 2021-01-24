package com.bb.reading.mvp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.reading.R;
import com.bb.reading.adapter.base.BaseRvAdapter;
import com.bb.reading.adapter.rv.NovelChapterAdapter;
import com.bb.reading.base.BaseMvpActivity;
import com.bb.reading.constant.NovelConstant;
import com.bb.reading.entity.NovelDetails;
import com.bb.reading.mvp.contract.NovelDetailActivityContract;
import com.bb.reading.mvp.presenter.NovelDetailActivityPresenter;
import com.bb.reading.utils.GlideUtils;

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

    public String getNovelId() {
        return mNovelId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_novel_detail;
    }

    @Override
    protected void initView() {
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

/*        mTvNovelName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addLikedNovelToDB();
            }
        });*/
    }

    @Override
    protected void process() {
        mPresenter.getDetailData(mNovelId);
    }

    @Override
    public NovelDetailActivityPresenter createPresenter() {
        return new NovelDetailActivityPresenter();
    }

    public void updateNovelDetail(NovelDetails novelDetails) {
        mNovelChapterAdapter.setData(novelDetails);
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
