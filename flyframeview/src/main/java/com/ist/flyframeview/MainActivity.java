package com.ist.flyframeview;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.ist.flyframeview.view.FlyFrameView;

public class MainActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    String TAG = "MainActivity";

    private ViewGroup mRoot;
    private FlyFrameView mFlyFrameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();
    }

    private void initListener() {
        mRoot.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
//                CharSequence oldText = ((TextView) oldFocus).getText();
//                CharSequence newText = ((TextView) newFocus).getText();
//                Log.d(TAG, "onGlobalFocusChanged: " + oldText + "--->" + newText);
                mFlyFrameView.moveToTarget(newFocus);
            }
        });
    }

    private void initView() {
        hideActionBar();
        mRoot = findViewById(R.id.root);

        View textView = findViewById(R.id.textView);
        View textView2 = findViewById(R.id.textView2);
        View textView3 = findViewById(R.id.textView3);
        View textView4 = findViewById(R.id.textView4);
        View textView5 = findViewById(R.id.textView5);
        View textView6 = findViewById(R.id.textView6);
        View textView7 = findViewById(R.id.textView7);

        mFlyFrameView = findViewById(R.id.fly_view);

//        textView.setOnFocusChangeListener(this);
//        textView2.setOnFocusChangeListener(this);
//        textView3.setOnFocusChangeListener(this);
//        textView4.setOnFocusChangeListener(this);
//        textView5.setOnFocusChangeListener(this);
//        textView6.setOnFocusChangeListener(this);
//        textView7.setOnFocusChangeListener(this);
    }

    private void hideActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            getCoor(v);
        }
    }

    private void getCoor(View v) {
        CharSequence text = ((TextView) v).getText();
        Rect rect = new Rect();
        mRoot.offsetDescendantRectToMyCoords(v, rect);
        Log.d(TAG, "getCoor: " + text + "---" + rect);
    }
}
