package com.bb.module_novelmanager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.module_common.adapter.base.BaseVH;
import com.bb.module_novelmanager.R;
import com.bb.module_novelmanager.arouter.RouterManager;
import com.bb.module_novelmanager.entity.HomePageBean;
import com.bb.module_common.utils.GlideUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/15
 * Time: 23:48
 */
public class SortNovelListView extends FrameLayout {
    String TAG = "SortNovelList";

    private View mView;
    private TextView mTvSort;
    private RecyclerView mRvNovels;
    private SortNovelAdapter mSortNovelAdapter;

    public SortNovelListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SortNovelListView(Context context) {
        super(context);
        mView = addLayoutOut();
        initView(mView);
    }

    private View addLayoutOut() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_sort_novel_list, this, false);
        addView(inflate);
        return inflate;
    }

    public void setData(HomePageBean.SortHotNovel novels) {
        mTvSort.setText(novels.type);
        mSortNovelAdapter.setData(novels);
    }

    private void initView(View view) {
        mTvSort = view.findViewById(R.id.tv_sort);
        mRvNovels = view.findViewById(R.id.rv_novel_list);

        //rv
        mSortNovelAdapter = new SortNovelAdapter();
        mRvNovels.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRvNovels.setAdapter(mSortNovelAdapter);
    }

    private class SortNovelAdapter extends RecyclerView.Adapter<BaseVH> {
        public static final int TYPE_HOTTEST_NOVEL = 0;
        public static final int TYPE_NORMAL = 1;

        private List<HomePageBean.SortHotNovel.Item> mOtherNovels;
        private HomePageBean.SortHotNovel.HottestNovel mHottestNovel;

        public void setData(HomePageBean.SortHotNovel novels) {
            mHottestNovel = novels.hottestNovel;
            mOtherNovels = novels.otherNovels;
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0 && mHottestNovel != null) {
                return TYPE_HOTTEST_NOVEL;
            }
            return TYPE_NORMAL;
        }

        @NonNull
        @Override
        public BaseVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == TYPE_HOTTEST_NOVEL) {
                return BaseVH.create(parent, R.layout.item_sort_novel_hottest);
            }
            return BaseVH.create(parent, R.layout.item_sort_novel);
        }

        @Override
        public void onBindViewHolder(@NonNull BaseVH holder, int position) {
            if (getItemViewType(position) == TYPE_HOTTEST_NOVEL) {
                onBindHottest(holder, position);
            } else {
                onBindNormal(holder, mHottestNovel == null ? position : position - 1);
            }
        }

        @Override
        public int getItemCount() {
            int itemCount = 0;
            if (mHottestNovel != null) {
                itemCount++;
            }
            if (mOtherNovels != null) {
                itemCount += mOtherNovels.size();
            }
            return itemCount;
        }

        /**
         * 最热
         *
         * @param holder
         * @param position
         */
        private void onBindHottest(final BaseVH holder, final int position) {
//            LongLogUtils.d(TAG, "onBindHottest() called with: holder = [" + holder + "], mHottestNovel = [" + mHottestNovel + "]");
            GlideUtils.load(mHottestNovel.coverUrl, holder.getView(R.id.iv_novel_cover, ImageView.class));
//            holder.setText(R.id.tv_novel_name, mHottestNovel.name);
            holder.setText(R.id.tv_novel_author, mHottestNovel.author);
            holder.setText(R.id.tv_novel_introduction, mHottestNovel.introduction);
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(holder, position);
                }
            });
        }

        /**
         * 其他排不上号的渣渣小说
         *
         * @param holder
         * @param position
         */
        private void onBindNormal(final BaseVH holder, final int position) {
            HomePageBean.SortHotNovel.Item item = mOtherNovels.get(position);
//            LongLogUtils.d(TAG, "onBindNormal() called with: holder = [" + holder + "], data's position = [" + position + "], item: " + item);
            holder.setText(R.id.tv_novel_name, item.name);
            holder.setText(R.id.tv_novel_author, item.author);
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(holder, position);
                }
            });
        }

        private void onItemClick(BaseVH holder, int position) {
            int itemViewType = mSortNovelAdapter.getItemViewType(position);
            String novelIndex = (itemViewType == SortNovelAdapter.TYPE_HOTTEST_NOVEL) ? mHottestNovel.novelDetailUrl : mOtherNovels.get(position).novelDetailUrl;
//            Intent intent = NovelDetailActivity.createIntent(getContext(), novelIndex);
//            SortNovelListView.this.getContext().startActivity(intent);
            RouterManager.getInstance().toNovelDetail(novelIndex);
        }
    }
}
