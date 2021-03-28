package com.bb.module_common.base;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.fragment.app.Fragment;

import com.bb.module_common.R;
import com.bb.module_common.utils.FragmentManagerUtils;


/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/24
 * Time: 0:13
 */
public abstract class BaseMVPFragmentActivity<P extends BasePresenter> extends BaseMvpActivity<P> {
    String TAG = "BaseFragmentActivity";
    private ViewStub mViewStub;
    private ViewGroup mFragmentContainer;
    protected FragmentManagerUtils mFragmentManagerUtils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_mvp_fragment;
    }

    @Override
    protected void initView() {
        mViewStub = findViewById(R.id.view_stub);
        mFragmentContainer = findViewById(R.id.fragment_container);

        int otherViewLayoutId = getOtherViewLayoutId();
        if (otherViewLayoutId != -1) {
            mViewStub.setLayoutResource(otherViewLayoutId);
            View otherView = mViewStub.inflate();
            initOtherView(otherView);
        }
    }

    protected int getOtherViewLayoutId(){
        return -1;
    }

    /**
     * 在fragment头顶
     * @param otherView
     */
    protected void initOtherView(View otherView){
    }

    @Override
    protected void initOthers() {
        super.initOthers();
        mFragmentManagerUtils = new FragmentManagerUtils(getSupportFragmentManager());
    }

    public void show(Fragment fragment, String... tag) {
        mFragmentManagerUtils.show(R.id.fragment_container, fragment);
    }
}
