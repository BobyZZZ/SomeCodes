package com.bb.curtaindemo.bean;

import android.graphics.Rect;
import android.view.View;

import com.bb.curtaindemo.custom.IShape;

public class HollowInfo {
    public View targetView;
    public Rect rect;
    private IShape hollowShape;

    public HollowInfo(View targetView) {
        this.targetView = targetView;
    }

    public Rect getDrawingRect() {
        rect = new Rect();
        targetView.getDrawingRect(rect);
        return rect;
    }

    public IShape getHollowShape() {
        return hollowShape;
    }

    public void setHollowShape(IShape hollowShape) {
        this.hollowShape = hollowShape;
    }

    @Override
    public String toString() {
        return "HollowInfo{" +
                ", rect=" + rect +
                '}';
    }
}
