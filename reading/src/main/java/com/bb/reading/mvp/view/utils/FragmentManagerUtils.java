package com.bb.reading.mvp.view.utils;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/24
 * Time: 0:33
 * 每个fragment类只能操作一个实例对象
 */
public class FragmentManagerUtils {
    String TAG = "FragmentManagerUtils";

    private final FragmentManager mFragmentManager;

    public FragmentManagerUtils(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void show(int containerId, Fragment fragment) {
        Log.d(TAG, "show() called with: containerId = [" + containerId + "], fragment = [" + fragment + "]");
        String tag = fragment.getClass().getSimpleName();
        Fragment fragmentByTag = mFragmentManager.findFragmentByTag(tag);
        if (fragmentByTag != null && fragmentByTag.isVisible()) {
            //当前显示的就是这个fragment
            Log.d(TAG, "fragment is showing , don't need to change ");
            return;
        }
        FragmentTransaction beginTransaction = mFragmentManager.beginTransaction();
        if (getCurrentFragment() != null) {
            beginTransaction.hide(getCurrentFragment());
            beginTransaction.addToBackStack(null);
        }
        if (fragmentByTag == null) {
            beginTransaction.add(containerId, fragment, tag)
                    .show(fragment);

            beginTransaction.commit();
        } else {
            beginTransaction.show(fragmentByTag)
                    .commit();
        }
    }

    private Fragment getCurrentFragment() {
        List<Fragment> fragments = mFragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment.isVisible()) {
                return fragment;
            }
        }
        return null;
    }
}
