package com.bb.reading.adapter.rv;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.bb.reading.R;
import com.bb.reading.adapter.base.BaseRvAdapter;
import com.bb.reading.adapter.base.BaseVH;
import com.bb.reading.entity.NovelDetails;
import com.bb.reading.utils.NovelSpUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/18
 * Time: 1:01
 */
public class NovelChapterAdapter extends BaseRvAdapter<NovelDetails.Chapter> {
    private final String PAYLOAD_LAST_READ = "payload_last_read";

    public NovelChapterAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseVH holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            Bundle bundle = (Bundle) payloads.get(0);
            String lastRead = bundle.getString(PAYLOAD_LAST_READ);
            if (!TextUtils.isEmpty(lastRead)) {
                holder.setText(R.id.tv_cached, R.string.last_read);
            }
        }
    }

    @Override
    protected void convert(BaseVH holder, NovelDetails.Chapter data) {
        holder.setText(R.id.tv_chapter_name, data.name);
    }

    private int mLastReadPosition = -1;

    public void setLastRead(String novelId) {
        String lastReadChapter = NovelSpUtils.getLastReadChapter(novelId);
        if (TextUtils.isEmpty(lastReadChapter)) {
            return;
        }
        Bundle bundle = new Bundle();
        for (int i = 0; i < mDatas.size(); i++) {
            if (getItemData(i).chapterUrl.equals(lastReadChapter)) {
                if (mLastReadPosition > -1 && mLastReadPosition < mDatas.size()) {
                    notifyItemChanged(mLastReadPosition, bundle);//清除上次
                }

                mLastReadPosition = i;
                bundle.putString(PAYLOAD_LAST_READ, lastReadChapter);//更新最后阅读

                notifyItemChanged(i, bundle);
                break;
            }
        }
    }
}
