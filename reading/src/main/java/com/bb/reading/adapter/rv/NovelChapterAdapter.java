package com.bb.reading.adapter.rv;

import com.bb.reading.R;
import com.bb.reading.adapter.base.BaseRvAdapter;
import com.bb.reading.adapter.base.BaseVH;
import com.bb.reading.entity.NovelDetails;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/18
 * Time: 1:01
 */
public class NovelChapterAdapter extends BaseRvAdapter<NovelDetails.Chapter> {
    public NovelChapterAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(BaseVH holder, NovelDetails.Chapter data) {
        holder.setText(R.id.tv_chapter_name,data.name);
    }
}
