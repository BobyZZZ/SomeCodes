package com.ist.fishtoucher.base;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initPresenter();
        initView();
        test();
    }

    protected abstract int getLayoutId();

    protected void initView() {

    }

    protected abstract void initPresenter();

    protected void test() {
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
