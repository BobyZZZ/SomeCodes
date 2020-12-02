package com.bb.reading.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;

import com.bb.reading.view.BScrollerControl;
import com.bb.reading.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        BScrollerControlTester bScrollerControlTester = new BScrollerControlTester((BScrollerControl) findViewById(R.id.bsc));
        new SeekBarTester((SeekBar) findViewById(R.id.sb),bScrollerControlTester);
    }
}