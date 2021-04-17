package com.bb.module_noveldetail.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.uilib.adapter.base.BaseVH;
import com.bb.module_noveldetail.R;
import com.bb.module_novelmanager.entity.NovelDetails;
import com.bb.module_common.utils.GlideUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/18
 * Time: 1:01
 */
public class NovelChapterAdapter extends RecyclerView.Adapter<BaseVH> {
    String TAG = "NovelChapterAdapter";
    private final int TYPE_HEADER = 0;
    private final int TYPE_NORMAL = 1;
    private NovelDetails mNovelDetails;
    private boolean hasHeader = false;

    public void setData(NovelDetails novelDetails) {
        mNovelDetails = novelDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BaseVH.create(parent, viewType == TYPE_HEADER ? R.layout.item_header_novel_detail_info : R.layout.item_category);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseVH holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == TYPE_HEADER) {
            onBindHeader(holder, position);
        } else {
            if (hasHeader) {
                position -= 1;
            }
            NovelDetails.Chapter chapter = mNovelDetails.chapterList.get(position);
            onBindNormal(holder, chapter);
        }
    }

    private void onBindHeader(BaseVH holder, int position) {
        holder.setText(R.id.tv_novel_author, mNovelDetails.getAuthor());
        holder.setText(R.id.tv_novel_introduction, mNovelDetails.introduction);
        holder.setText(R.id.tv_novel_last_update, mNovelDetails.getLastUpdateTime());
        holder.setText(R.id.tv_novel_type, mNovelDetails.getType());
        holder.setText(R.id.tv_novel_name, mNovelDetails.name);

        ImageView ivCover = holder.getView(R.id.iv_novel_cover, ImageView.class);
        GlideUtils.load(mNovelDetails.getCoverUrl(), ivCover);
    }

    private void onBindNormal(BaseVH holder, final NovelDetails.Chapter chapter) {
        holder.setText(R.id.tv_chapter_name, chapter.name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(chapter);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mNovelDetails == null) {
            return 0;
        }
        List<NovelDetails.Chapter> chapterList = mNovelDetails.chapterList;
        int count;
        if (hasHeader) {
            count = 1 + (chapterList == null ? 0 : chapterList.size());
        } else {
            count = chapterList == null ? 0 : chapterList.size();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasHeader && position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(NovelDetails.Chapter data);
    }
}
