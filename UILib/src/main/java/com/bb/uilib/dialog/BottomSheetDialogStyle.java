package com.bb.uilib.dialog;

import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.ist.uilib.R;

public class BottomSheetDialogStyle implements CustomDialog.CustomStyle {
    @Override
    public void setFinalStyle(Window window) {
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setWindowAnimations(R.style.dialog_animation_style);
        window.setGravity(Gravity.BOTTOM);
    }
}
