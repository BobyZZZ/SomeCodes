package com.bb.reading.adapter.rv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bb.reading.R;
import com.bb.reading.entity.RankPageDataFruitBean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/2/18
 * Time: 15:21
 */
public class RankExpandableAdapter extends BaseExpandableListAdapter {
    String TAG = "RankExpandableAdapter";

    private List<RankPageDataFruitBean.RankPageData.TypeRank> mGroupDatas;
    public int mPeriodType = 0;

    public void changePeriod(int periodType) {
        if (periodType == mPeriodType) {
            return;
        }
        mPeriodType = periodType;
        notifyDataSetChanged();
    }

    public void setGroupDatas(List<RankPageDataFruitBean.RankPageData.TypeRank> groupDatas) {
        mGroupDatas = groupDatas;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mGroupDatas == null ? 0 : mGroupDatas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroupDatas.get(groupPosition).getChildCount(mPeriodType);
    }

    @Override
    public RankPageDataFruitBean.RankPageData.TypeRank getGroup(int groupPosition) {
        return mGroupDatas.get(groupPosition);
    }

    @Override
    public RankPageDataFruitBean.TypeRank.TimeTypeRank.Item getChild(int groupPosition, int childPosition) {
        return mGroupDatas.get(groupPosition).getChild(mPeriodType, childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        long childId = 0;
        for (int i = 0; i < groupPosition; i++) {
            childId += mGroupDatas.get(groupPosition).getChildCount(mPeriodType) + 1;
        }
        childId += childPosition + 1;
        return childId;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.ivArrow.animate().rotation(isExpanded ? 0 : 180);
        groupViewHolder.tvText.setText(getGroup(groupPosition).title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        RankPageDataFruitBean.TypeRank.TimeTypeRank.Item item = getChild(groupPosition, childPosition);

        childViewHolder.tvText.setText(item.novelName);
        if (mOnChildClickListener != null) {
            childViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnChildClickListener.onClick(item);
                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    static class GroupViewHolder {
        View itemView;
        ImageView ivArrow;
        TextView tvText;

        public GroupViewHolder(View itemView) {
            this.itemView = itemView;
            ivArrow = itemView.findViewById(R.id.iv_arrow);
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }

    static class ChildViewHolder {
        View itemView;
        TextView tvText;

        public ChildViewHolder(View itemView) {
            this.itemView = itemView;
            tvText = itemView.findViewById(R.id.tv_text);
        }
    }

    /******************************************点击监听*******************************************/
    OnChildClickListener mOnChildClickListener;

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        mOnChildClickListener = onChildClickListener;
    }

    public interface OnChildClickListener {
        void onClick(RankPageDataFruitBean.TypeRank.TimeTypeRank.Item novelItem);
    }
    /******************************************点击监听*******************************************/
}
