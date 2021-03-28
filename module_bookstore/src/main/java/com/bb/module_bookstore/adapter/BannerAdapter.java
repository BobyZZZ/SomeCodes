package com.bb.module_bookstore.adapter;

import android.widget.ImageView;

import com.bb.module_bookstore.R;
import com.bb.module_common.utils.GlideUtils;
import com.bb.module_common.adapter.base.BaseRvAdapter;
import com.bb.module_common.adapter.base.BaseVH;
import com.bb.module_novelmanager.entity.PageData;


/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/17
 * Time: 14:03
 */
public class BannerAdapter extends BaseRvAdapter<PageData.TopNovel> {
    String TAG = "BannerAdapter";

    public BannerAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    public int getItemCount() {
        return (mDatas == null || mDatas.isEmpty()) ? 0 : Integer.MAX_VALUE;
    }

    @Override
    protected PageData.TopNovel getItemData(int position) {
        return super.getItemData(position % mDatas.size());
    }

    @Override
    protected void convert(BaseVH holder, PageData.TopNovel data) {
        ImageView ivCover = holder.getView(R.id.iv_novel_cover, ImageView.class);
        GlideUtils.load(data.getCoverUrl(), ivCover);

        holder.setText(R.id.tv_novel_introduction, data.getIntroduction());
    }
}
