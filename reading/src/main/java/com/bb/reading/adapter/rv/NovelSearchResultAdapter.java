package com.bb.reading.adapter.rv;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.bb.reading.R;
import com.bb.reading.db.DaoHelper;
import com.bb.reading.db.greenDao.beanManager.NovelDBManager;
import com.bb.reading.entity.SearchResult;
import com.bb.reading.mvp.contract.NovelDetailActivityContract;
import com.bb.reading.utils.ResUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/24
 * Time: 15:59
 */
public class NovelSearchResultAdapter extends BaseQuickAdapter<SearchResult.Item, BaseViewHolder> {
    String TAG = "NovelSearchResultAdapter";
    private final NovelDBManager mNovelDBManager;
    private NovelDetailActivityContract.IPresenter mPresenter;

    public NovelSearchResultAdapter(int layoutResId) {
        super(layoutResId);
        mNovelDBManager = DaoHelper.getInstance().getNovelDBManager();
    }

    @Override
    public void onBindViewHolder(@NotNull BaseViewHolder holder, int position, @NotNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            if (payloads.contains(R.id.iv_add_to_liked)) {
                SearchResult.Item item = getItem(position);
                boolean alreadyLiked = mNovelDBManager.isAlreadyLiked(item.novelId);
                holder.setImageResource(R.id.iv_add_to_liked,alreadyLiked ? R.drawable.ic_unliked : R.drawable.ic_liked);
            }
        }
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, SearchResult.Item item) {
        baseViewHolder.setText(R.id.tv_novel_name,ResUtils.getString(R.string.novel_name,item.getName()));
        baseViewHolder.setText(R.id.tv_novel_author,item.getAuthor());
        baseViewHolder.setText(R.id.tv_novel_state,ResUtils.getString(R.string.novel_state,item.getState()));
        baseViewHolder.setText(R.id.tv_novel_newest_chapter, ResUtils.getString(R.string.newest_chapter,item.getNewestChapter()));
        baseViewHolder.setText(R.id.tv_novel_word_count,item.getWordCount());

        boolean alreadyLiked = mNovelDBManager.isAlreadyLiked(item.novelId);
        baseViewHolder.setImageResource(R.id.iv_add_to_liked,alreadyLiked ? R.drawable.ic_unliked : R.drawable.ic_liked);

        baseViewHolder.getView(R.id.iv_add_to_liked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mNovelDBManager.isAlreadyLiked(item.novelId)) {
                    boolean result = mNovelDBManager.saveLikedNovel(item);
                    mPresenter.getDetailData(item.novelId);
                    Log.d(TAG, "saveLikedNovel: " + result);
                } else {
                    boolean result = mNovelDBManager.deleteLikedNovel(item);
                    Log.d(TAG, "deleteLikedNovel: " + result);
                }
                notifyItemChanged(baseViewHolder.getLayoutPosition(),R.id.iv_add_to_liked);
            }
        });
    }

    public void setPresenter(NovelDetailActivityContract.IPresenter presenter) {
        mPresenter = presenter;
    }
}
