package com.bb.module_booksearch.adapter;

import android.util.Log;
import android.view.View;

import com.bb.module_booksearch.R;
import com.bb.module_common.utils.log.LogUtils;
import com.bb.module_novelmanager.db.greenDao.DaoHelper;
import com.bb.module_novelmanager.db.greenDao.impl.NovelDBManager;
import com.bb.module_novelmanager.entity.SearchResult;
import com.bb.module_common.utils.ResUtils;
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
    protected void convert(@NotNull final BaseViewHolder baseViewHolder, final SearchResult.Item item) {
        baseViewHolder.setText(R.id.tv_novel_name,ResUtils.getString(R.string.novel_name,item.getName()));
//        baseViewHolder.setText(R.id.tv_novel_author,item.getAuthor());
        baseViewHolder.setText(R.id.tv_novel_state,ResUtils.getString(R.string.novel_author,item.getAuthor()));
        baseViewHolder.setText(R.id.tv_novel_newest_chapter, ResUtils.getString(R.string.newest_chapter,item.getNewestChapter()));
        baseViewHolder.setText(R.id.tv_novel_word_count,ResUtils.getString(R.string.novel_update_time,item.getWordCount()));

        boolean alreadyLiked = mNovelDBManager.isAlreadyLiked(item.novelId);
        baseViewHolder.setImageResource(R.id.iv_add_to_liked,alreadyLiked ? R.drawable.ic_unliked : R.drawable.ic_liked);

        baseViewHolder.getView(R.id.iv_add_to_liked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mNovelDBManager.isAlreadyLiked(item.novelId)) {
                    mNovelDBManager.saveLikedNovel(item.novelId, new Runnable() {
                        @Override
                        public void run() {
                            notifyItemChanged(baseViewHolder.getLayoutPosition(),R.id.iv_add_to_liked);
                        }
                    });
                } else {
                    boolean result = mNovelDBManager.deleteLikedNovel(item);
                    Log.d(TAG, "deleteLikedNovel: " + result);
                    notifyItemChanged(baseViewHolder.getLayoutPosition(),R.id.iv_add_to_liked);
                }
            }
        });
    }
}
