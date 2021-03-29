package com.bb.module_bookstore.adapter;

import android.view.View;

import com.bb.module_bookstore.R;
import com.bb.module_novelmanager.arouter.RouterManager;
import com.bb.module_novelmanager.entity.NovelInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/28
 * Time: 1:10
 */
public class SortNovelAdapter extends BaseQuickAdapter<NovelInfo,BaseViewHolder> implements LoadMoreModule {
    public SortNovelAdapter(int layoutResId) {
        super(layoutResId);
    }

    public SortNovelAdapter(int layoutResId, @Nullable List<NovelInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, final NovelInfo novelInfo) {
        baseViewHolder.setText(R.id.tv_novel_name,novelInfo.getName());
        baseViewHolder.setText(R.id.tv_newest_chapter,novelInfo.getChapterName());
        baseViewHolder.setText(R.id.tv_novel_author,novelInfo.getAuthor());

        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novelID = novelInfo.getNovelID();
/*Intent intent = NovelDetailActivity.createIntent(getContext(), novelID);
                getContext().startActivity(intent);*/
                //跳转并携带参数
                RouterManager.getInstance().toNovelDetail(novelID);
            }
        });
    }
}
