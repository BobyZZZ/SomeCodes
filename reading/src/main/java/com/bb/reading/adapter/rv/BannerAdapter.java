package com.bb.reading.adapter.rv;

import android.widget.ImageView;

import com.bb.reading.R;
import com.bb.reading.adapter.base.BaseRvAdapter;
import com.bb.reading.adapter.base.BaseVH;
import com.bb.reading.entity.PageData;
import com.bb.reading.utils.GlideUtils;

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
