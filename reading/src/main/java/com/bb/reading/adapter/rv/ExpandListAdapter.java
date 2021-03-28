package com.bb.reading.adapter.rv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.reading.R;
import com.bb.module_common.utils.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/2/9
 * Time: 14:44
 */
public class ExpandListAdapter<G extends ExpandListAdapter.GroupData> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String TAG = "ExpandListAdapter";
    private final int TYPE_GROUP = 1;
    private final int TYPE_CHILD = 0;
    private List<G> mGroupDatas;
    private int mPeriod = 0;
    private ArrayList<Integer> mGroupPositions;

    public void setNewInstance(List<G> datas) {
        mGroupDatas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_GROUP:
                return new GroupVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false));
            case TYPE_CHILD:
            default:
                return new ChildVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GroupVH) {
            onBindGroup((GroupVH) holder, mGroupDatas.get(mGroupPositions.indexOf(position)), position, mGroupPositions.indexOf(position));
        } else if (holder instanceof ChildVH) {
            onBindChild((ChildVH) holder, getChild(getGroupPosition(position), mPeriod
                    ,getChildPosition(position)));
        }
    }

    private void onBindGroup(GroupVH holder, G g, int position, final int groupIndex) {
//        LogUtils.d(TAG, "onBindGroup() called with: g = [" + g + "], position = [" + position + "], groupIndex = [" + groupIndex + "]");
        holder.tvText.setText(g.getType());
        holder.ivArrow.setImageResource(R.drawable.ic_arrow_down);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnGroupClickListener != null) {
                    mOnGroupClickListener.onClick(groupIndex);
                }
            }
        });
    }

    private void onBindChild(ChildVH holder, final ChildData childData) {
//        LogUtils.d(TAG, "onBindChild: " + childData.toString());
        holder.tvText.setText(childData.getName());
        if (mOnChildClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnChildClickListener.onClick(childData);
                }
            });
        }
    }

    /**
     * 计算某position在哪个分组
     *
     * @param position
     * @return
     */
    private int getGroupPosition(int position) {
        if (mGroupPositions.contains(position)) {
            return mGroupPositions.indexOf(position);
        }

        int count = 0;
        for (int i = 0; i < mGroupDatas.size(); i++) {
            G groupData = mGroupDatas.get(i);
            if (position > count + groupData.getChildCount(mPeriod) + 1) {
                count += groupData.getChildCount(mPeriod) + 1;
            } else {
//                LogUtils.d(TAG, "getGroupPosition: " + position + " -> " + (i));
                return i;
            }
        }
        LogUtils.e(TAG, "getGroupPosition-not-found: " + position + " -> " + (error_getGroupPosition));
        return error_getGroupPosition;
    }

    private final int error_getChildPosition = -10;
    private final int error_getGroupPosition = -20;

    /**
     * rv position转化成子数据列表position
     *
     * @param position
     * @return
     */
    private int getChildPosition(int position) {
        if (getItemViewType(position) == TYPE_GROUP) {
//            Log.d(TAG, "TYPE_GROUP: " + position + " -> " + "-1");
            return -1;
        }
        int count = 0;
        for (G groupData : mGroupDatas) {
            if (position > count + groupData.getChildCount(mPeriod) + 1) {
                count += groupData.getChildCount(mPeriod) + 1;
            } else {
//                LogUtils.d(TAG, "TYPE_CHILD: " + position + " -> " + (position - count - 1));
                return position - count - 1;
            }
        }
        LogUtils.e(TAG, "TYPE_ERROR: " + position + " -> " + (error_getChildPosition));
        return error_getChildPosition;
    }

    @Override
    public int getItemViewType(int position) {
//        LogUtils.d(TAG, "getItemViewType() called with: position = [" + position + "]");
        if (mGroupPositions.contains(position)) {
            return TYPE_GROUP;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
//        LogUtils.d(TAG, "getItemCount() called");
        if (mGroupDatas == null) {
            return 0;
        }

        mGroupPositions = new ArrayList<>();
        int count = 0;
        for (G groupData : mGroupDatas) {
            mGroupPositions.add(count++);
            count += groupData.getChildCount(mPeriod);
        }
        return count;
    }

    static class GroupVH extends RecyclerView.ViewHolder {

        private final ImageView ivArrow;
        private final TextView tvText;

        public GroupVH(@NonNull View itemView) {
            super(itemView);
            ivArrow = itemView.findViewById(R.id.iv_arrow);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }

    static class ChildVH extends RecyclerView.ViewHolder {

        private final TextView tvText;

        public ChildVH(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }

    public int getGroupCount() {
        return mGroupDatas == null ? 0 : mGroupDatas.size();
    }

    public int getChildrenCount(int groupPosition, int period) {
        if (mGroupDatas == null) {
            return 0;
        }
        return mGroupDatas.get(groupPosition).getChildCount(period);
    }

    public G getGroup(int groupPosition) {
        return mGroupDatas.get(groupPosition);
    }

    public ChildData getChild(int groupPosition, int period, int childPosition) {
        if (mGroupDatas == null) {
            return null;
        }
        return mGroupDatas.get(groupPosition).getChild(period, childPosition);
    }

    public interface GroupData<C extends ChildData> {
        String getType();

        int getChildCount(int period);

        C getChild(int period, int childPosition);
    }

    public interface ChildData {
        String getName();
    }


    /******************************************点击监听*******************************************/
    OnChildClickListener mOnChildClickListener;

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        mOnChildClickListener = onChildClickListener;
    }

    public interface OnChildClickListener {
        void onClick(ChildData childData);
    }


    OnGroupClickListener mOnGroupClickListener;

    public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener) {
        mOnGroupClickListener = onGroupClickListener;
    }

    public interface OnGroupClickListener {
        void onClick(int groupIndex);
    }
    /******************************************点击监听*******************************************/
}
