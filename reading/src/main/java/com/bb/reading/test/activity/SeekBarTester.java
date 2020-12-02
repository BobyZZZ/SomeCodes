package com.bb.reading.test.activity;

import android.widget.SeekBar;

public class SeekBarTester {

    private final SeekBar mSeekBar;

    public SeekBarTester(SeekBar seekBar, SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        mSeekBar = seekBar;
        mSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }
}
