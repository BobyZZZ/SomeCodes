package com.bb.curtaindemo.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.bb.curtaindemo.R;
import com.bb.curtaindemo.bean.HollowInfo;
import com.bb.curtaindemo.custom.IShape;

import java.util.ArrayList;

public class Guider {
    String TAG = getClass().getSimpleName();
    private LongSparseArray<HollowInfo> mHollows;
    private int topViewResId = -1;
    private FragmentActivity mActivity;

    public Guider(FragmentActivity activity) {
        mActivity = activity;
        mHollows = new LongSparseArray<>();
    }

    public void show() {
        GuideView guideView = new GuideView(mActivity);
        addHollwsToGuideView(guideView);

        GuideDialog dialog = new GuideDialog();
        dialog.setGuideView(guideView);
        dialog.setTopViewResId(topViewResId);
        dialog.show();
    }

    private void addHollwsToGuideView(GuideView guideView) {
        ArrayList<HollowInfo> hollowInfos = new ArrayList<>();
        for (int i = 0; i < mHollows.size(); i++) {
            hollowInfos.add(mHollows.valueAt(i));
        }
        guideView.setHollowInfos(hollowInfos);
    }

    public Guider with(View targetView) {
        getHollowInfo(targetView).setHollowShape(null);//如果调用过withShape(view1,...)，再调用with(view1),需要清除之前的自定义IShape
        return this;
    }

    public Guider withShape(View targetView, IShape shape) {
        getHollowInfo(targetView).setHollowShape(shape);
        return this;
    }

    public Guider setTopViewResId(int resId) {
        topViewResId = resId;
        return this;
    }

    private HollowInfo getHollowInfo(View which) {
        int hashCode = which.hashCode();
        HollowInfo hollowInfo = mHollows.get(hashCode);
        if (hollowInfo == null) {
            hollowInfo = new HollowInfo(which);
            mHollows.append(hashCode, hollowInfo);
        }
        return hollowInfo;
    }

    public static class GuideDialog extends DialogFragment {
        private Dialog dialog;
        private GuideView mGuideView;
        private int mTopViewResId = -1;

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            return dialog;
        }

        private void show() {
            FragmentActivity activity = (FragmentActivity) mGuideView.getContext();
            FrameLayout frameLayout = new FrameLayout(activity);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            frameLayout.addView(mGuideView, lp);
            //用于显示其他内容，例如：文字，按钮之类
            addTopViewToRootContainer(frameLayout);

            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.GuideDialog);
            dialog = builder.setView(frameLayout).create();

            show(activity.getSupportFragmentManager(), getClass().getSimpleName());
        }

        private void addTopViewToRootContainer(ViewGroup root) {
            if (mTopViewResId != -1) {
                View inflate = LayoutInflater.from(mGuideView.getContext()).inflate(mTopViewResId, root, true);
            }
        }

        public void setGuideView(GuideView mGuideView) {
            this.mGuideView = mGuideView;
        }

        public void setTopViewResId(int mTopViewResId) {
            this.mTopViewResId = mTopViewResId;
        }

        /**
         * DialogFragment 源码的show中默认使用commit提交Fragment的事务,在一些Activity界面重建的情况下可能出现状态丢失的异常,我们try/catch住并重新实现保证逻辑的正常执行:
         * @param manager
         * @param tag
         */
        @Override
        public void show(FragmentManager manager, String tag) {
            try {
                super.show(manager, tag);
            } catch (Exception e) {
                manager.beginTransaction()
                        .add(this, tag)
                        .commitAllowingStateLoss();
            }
        }
    }


}
