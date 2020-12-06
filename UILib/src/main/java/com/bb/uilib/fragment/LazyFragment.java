package com.bb.uilib.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;

/**
 * 老方法实现延迟加载
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/12/5
 * Time: 22:12
 */
public abstract class LazyFragment extends Fragment {

    /**
     * setUserVisibleHint()比onCreateView()调用时间早，这时候view还没加载，
     * 需要在onCreateView执行后才加载数据进行显示，否则数据加载完成后刷新ui时会报空指针异常
     */
    private boolean isViewCreated = false;
    private boolean mLastVisibleToUserState = false;//上一次的可见状态

    /**
     * 表示fragment是否可见，由不可见到可见时才加载数据！！
     * @param isVisibleToUser 是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isViewCreated) {
            /*setUserVisibleHint(false)会被多次调用，所以需要对mLastVisibleToUserState进行判断,
            * 由不可见到可见时才加载数据
            */
            if (!mLastVisibleToUserState && isVisibleToUser) {
                dispatchVisibleToUser(true);
            } else if (mLastVisibleToUserState && !isVisibleToUser) {
                dispatchVisibleToUser(false);
            }
        }
    }

    private void dispatchVisibleToUser(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            onFragmentLoad();
        } else {
            onFragmentLoadStop();
        }
        mLastVisibleToUserState = isVisibleToUser;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutRes(), container, false);
        ButterKnife.bind(rootView);
        initView(rootView);
        isViewCreated = true;
        /**
         * 方法回调顺序：因为fragment是事务型操作，生命周期方法在transaction commit之后才执行，而setUserVisibleHint是马上执行
         * setUserVisibleHint(FragmentPagerAdapter的instantiateItem()和setPrimaryItem()调用)
         * -> (onAttach...)
         * -> onCreateView，首次setUserVisibleHint(true)时，isViewCreated为false，所以加载数据被拦截掉，
         * 这里需要补一次加载数据方法调用
         */
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
    }

    /**
     * 加载布局
     */
    protected abstract int getLayoutRes();

    /**
     * 初始化ui
     *
     * @param rootView 根布局
     */
    protected abstract void initView(View rootView);

    /**
     * 开始加载数据
     */
    protected abstract void onFragmentLoad();

    /**
     * 停止加载数据
     */
    protected abstract void onFragmentLoadStop();
}
