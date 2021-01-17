package com.bb.reading.adapter.viewpager;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Boby on 2019/6/17.
 */

public class TabFragmentPagerAdapter extends FragmentStatePagerAdapter {
    String TAG = "TabFragmentPagerAdapter";
    private Context mContext;
    private List<Fragment> mFragments;
    private String[] mTitles;

    public void setData(List<Fragment> fragments, String[] titles) {
        mFragments = fragments;
        mTitles = titles;
        notifyDataSetChanged();
    }

    public TabFragmentPagerAdapter(Context context, FragmentManager fm, String tag) {
        super(fm/*,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT*/);
        this.mContext = context;
        this.TAG = tag;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragments.get(position);
/*        if (TAG != null) {
            Log.e(TAG, "getItem: " + position + "---fragment:" + fragment);
        }*/
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
