package com.bb.uilib.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;


import com.bb.module_common.view.CircleLoadingView;
import com.bb.uilib.R;
import com.bb.module_common.dialog.CustomDialog;

public class UITester {

    public static void test(Context context) {
        showLoadingDialog(context);
//        showEditDialog(context);
    }

    public static void showEditDialog(Context context) {
        CustomDialog dialog = CustomDialog.create(context)
                .setLayoutID(R.layout.layout_edit_dialog)
                .setWindowAnimation(R.style.dialog_animation_style)
                .setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                .setGravity(Gravity.BOTTOM)
//                .setFullScreen(true)
                .build();
        View root = dialog.findViewById(R.id.ll_dialog_root);
        dialog.show();
    }

    public static void showLoadingDialog(Context context) {
        CircleLoadingView circleLoadingView = new CircleLoadingView(context);
//        circleLoadingView.setBackgroundColor(Color.GREEN);
        CustomDialog dialog = CustomDialog.create(context).setContentView(circleLoadingView)
                .setWidth(300)
                .setHeight(300)
                .setDimAmount(0f)
                .setBackgroundColor(Color.TRANSPARENT)
                .build();
        dialog.show();
    }

    public static void setContentView(Activity activity, int layoutId) {
        activity.setContentView(layoutId);
    }
}
