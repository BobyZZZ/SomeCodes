package com.bb.curtaindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.bb.curtaindemo.bean.HollowInfo;
import com.bb.curtaindemo.custom.IShape;
import com.bb.curtaindemo.view.Guider;

public class MainActivity extends AppCompatActivity {
    String TAG = getClass().getSimpleName();
    private View textView;
    private View textView_round;
    private View imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();
        initEvent();
    }

    private void initView() {
        textView = findViewById(R.id.textView);
        textView_round = findViewById(R.id.textView_round);
        imageView = findViewById(R.id.imageView);
    }

    private void initEvent() {
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGuideView();
            }
        });
    }

    private void showGuideView() {
        new Guider(this)
                .with(textView)//根据背景或view的drawingRect挖空
                .withShape(textView_round, new IShape() {//根据自定义形状挖空
                    @Override
                    public void drawHollowShape(Canvas canvas, Paint paint, Rect rect) {
                        canvas.drawRoundRect(rect.left, rect.top, rect.right, rect.bottom, 20, 20, paint);
                    }
                })
                .with(textView_round)
                .with(imageView)
                .setTopViewResId(R.layout.activity_main)
                .show();
    }
}
