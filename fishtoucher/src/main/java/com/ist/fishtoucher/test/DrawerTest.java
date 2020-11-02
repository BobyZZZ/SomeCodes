package com.ist.fishtoucher.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ist.fishtoucher.R;

public class DrawerTest extends BaseTest {

    private static DrawerLayout mDrawerLayout;
    private BBWindowManager mBbWindowManager;
    private View mViewAdded;

    public void test(View view) {
        if (view != null) {

            mDrawerLayout = view.findViewById(R.id.drawerlayout);

            view.findViewById(R.id.btn_1).setOnClickListener(this);
            view.findViewById(R.id.btn_2).setOnClickListener(this);
            view.findViewById(R.id.btn_3).setOnClickListener(this);
            view.findViewById(R.id.btn_4).setOnClickListener(this);
            view.findViewById(R.id.btn_5).setOnClickListener(this);
            view.findViewById(R.id.btn_6).setOnClickListener(this);
        }
    }

    public void test(Activity activity) {
        mBbWindowManager = new BBWindowManager(activity);
        mViewAdded = mBbWindowManager.add(R.layout.test_drawer);

        mDrawerLayout = activity.findViewById(R.id.drawerlayout);

        mViewAdded.findViewById(R.id.btn_1).setOnClickListener(this);
        mViewAdded.findViewById(R.id.btn_2).setOnClickListener(this);
        mViewAdded.findViewById(R.id.btn_3).setOnClickListener(this);
        mViewAdded.findViewById(R.id.btn_4).setOnClickListener(this);
        mViewAdded.findViewById(R.id.btn_5).setOnClickListener(this);
        mViewAdded.findViewById(R.id.btn_6).setOnClickListener(this);
        mViewAdded.findViewById(R.id.btn_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.btn_2:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.btn_3:
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, GravityCompat.START);
                break;
            case R.id.btn_4:
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.START);
                break;
            case R.id.btn_5:
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED, GravityCompat.START);
                break;
            case R.id.btn_6:
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START);
                break;
            case R.id.btn_exit:
                mBbWindowManager.mWm.removeView(mViewAdded);
                break;
        }
    }

    class BBWindowManager {

        private final Context mContext;
        private WindowManager mWm;

        BBWindowManager(Context context) {
            mContext = context;
            mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            havePermission();
        }

        public View add(int resID) {
            if (havePermission()) {
                View inflate = LayoutInflater.from(mContext).inflate(resID, null, false);
                mWm.addView(inflate, createLayoutParams());
                return inflate;
            }
            return null;
        }

        private WindowManager.LayoutParams createLayoutParams() {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT
                    , WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    , WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.RGB_565);
            return lp;
        }

        private boolean havePermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(mContext)) {
                    return true;
                } else {
                    //若没有权限，提示获取.
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + mContext.getPackageName()));
                    if (mContext instanceof Activity) {
                        Toast.makeText(mContext, "需要取得权限以使用悬浮窗", Toast.LENGTH_SHORT).show();
                    }
                    mContext.startActivity(intent);
                    return false;
                }
            } else {
                //SDK在23以下，不用管.
            }
            return true;
        }
    }
}
