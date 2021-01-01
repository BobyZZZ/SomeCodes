package com.bb.reading.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.bb.reading.test.annotation.BindView;
import com.bb.reading.test.annotation.impl.MyBufferKnife;
import com.bb.reading.view.BScrollerControl;
import com.bb.reading.R;

public class TestActivity extends AppCompatActivity {
    String TAG = "TestActivity";

    @BindView(R.id.sb)
    private SeekBar mSeekBar;
    @BindView(R.id.bsc)
    private BScrollerControl mBScrollerControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        MyBufferKnife.bind(this);
        Log.d(TAG, "onCreate mSeekBar: " + mSeekBar);
        Log.d(TAG, "onCreate mBScrollerControl: " + mBScrollerControl);

        BScrollerControlTester bScrollerControlTester = new BScrollerControlTester(mBScrollerControl);
        new SeekBarTester(mSeekBar,bScrollerControlTester);
    }
}