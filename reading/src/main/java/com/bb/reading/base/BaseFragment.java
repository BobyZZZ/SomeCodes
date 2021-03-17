package com.bb.reading.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bb.uilib.dialog.CustomDialog;
import com.bb.uilib.loadingView.CircleLoadingView;

/**
 * Created by Boby on 2019/6/17.
 */

public abstract class BaseFragment extends Fragment{
    private Dialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
        process();
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract void initListener();

    protected abstract void process();

    public void showToast(int resId) {
        showToast(getString(resId));
    }

    public void showToast(String info) {
        Toast.makeText(FishApplication.mContext, info, Toast.LENGTH_SHORT).show();
    }

    public void showLoading(DialogInterface.OnCancelListener onCancelListener) {
        showLoading(onCancelListener, true);
    }

    public void showLoading(DialogInterface.OnCancelListener onCancelListener, Boolean... canCancel) {
        if (mLoadingDialog == null || !mLoadingDialog.isShowing()) {
            CircleLoadingView circleLoadingView = new CircleLoadingView(getContext().getApplicationContext());
            mLoadingDialog = CustomDialog.create(getContext())
                    .setContentView(circleLoadingView)
                    .setWidth((int) (getResources().getDisplayMetrics().density * 80))
                    .setHeight((int) (getResources().getDisplayMetrics().density * 80))
                    .setDimAmount(0)
                    .setBackgroundColor(Color.TRANSPARENT)
                    .setCanceledOnTouchOutside(canCancel.length > 0 ? canCancel[0] : true)
                    .build();
            mLoadingDialog.show();
        }
    }

    public void hideLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
    }
}
