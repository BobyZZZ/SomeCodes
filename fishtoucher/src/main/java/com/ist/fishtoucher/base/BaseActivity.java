package com.ist.fishtoucher.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ist.fishtoucher.utils.UIUtils;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        setContentView(getLayoutId());
        initPresenter();
        initView();
        test();
    }

    protected void initStatusBar(){
        UIUtils.ColorfulStatusBar(this);
    }

    protected abstract int getLayoutId();

    protected void initView() {

    }

    protected abstract void initPresenter();

    protected void test() {
    }

    /**
     * 启动Fragment
     *
     * @param id       id
     * @param fragment 碎片
     */
    protected void startFragment(int id, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(id, fragment);
        fragmentTransaction.commit();
    }

    public void showToast(int resId) {
        showToast(getString(resId));
    }

    public void showToast(String info) {
        Toast.makeText(FishApplication.mContext, info, Toast.LENGTH_SHORT).show();
    }

    public void showProgress() {

    }

    /******************************************************** Permissions ************************************************************************/
    protected boolean havePermission(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    protected void requestPermission(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    protected void onPermissionsDenied(int requestCode, String[] permissions) {

    }

    protected void onPermissionsGranted(int requestCode, String[] permissions) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onPermissionsGranted(requestCode, permissions);
        } else {
            onPermissionsDenied(requestCode, permissions);
        }
    }
    /******************************************************** Permissions ************************************************************************/
}
