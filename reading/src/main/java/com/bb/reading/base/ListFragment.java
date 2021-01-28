package com.bb.reading.base;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.reading.R;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/27
 * Time: 23:41
 */
public abstract class ListFragment<A extends RecyclerView.Adapter> extends BaseFragment {

    private RecyclerView mRecyclerView;
    protected A mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = view.findViewById(R.id.rv_list);
        mAdapter = createAdapter();
        mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    protected abstract A createAdapter();
}
