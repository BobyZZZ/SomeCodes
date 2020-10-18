package com.bb.curtaindemo.custom;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public interface IShape {
    void drawHollowShape(Canvas canvas, Paint paint, Rect rect);
}
