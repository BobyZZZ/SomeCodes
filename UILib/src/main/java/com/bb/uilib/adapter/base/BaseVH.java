package com.bb.uilib.adapter.base;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2021/1/15
 * Time: 23:59
 */
public class BaseVH extends RecyclerView.ViewHolder {

    private final View mItemView;

    public static BaseVH create(ViewGroup parent, int layoutId) {
        return new BaseVH(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    private BaseVH(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
    }

    public View getView(int viewId) {
        return mItemView.findViewById(viewId);
    }

    public <T extends View> T getView(int viewId, Class<T>... clazz) {
        return (T) mItemView.findViewById(viewId);
    }

    public void setText(int viewId, String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        TextView textView = (TextView) getView(viewId);
        if (textView != null) {
            textView.setText(text);
        }
    }

    public void setText(int viewId, int textRes) {
        TextView textView = (TextView) getView(viewId);
        if (textView != null) {
            textView.setText(textRes);
        }
    }
}
