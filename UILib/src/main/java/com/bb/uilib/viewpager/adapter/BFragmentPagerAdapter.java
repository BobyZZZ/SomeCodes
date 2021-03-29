package com.bb.uilib.viewpager.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/12/5
 * Time: 23:15
 */
public class BFragmentPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {

    private List<T> mDatas;
    private List<String> mTitles;

    public BFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public BFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void setData(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void setTitles(List<String> titles) {
        mTitles = titles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles == null || position > mTitles.size()) {
            return "null";
        } else {
            return mTitles.get(position);
        }
    }
}
