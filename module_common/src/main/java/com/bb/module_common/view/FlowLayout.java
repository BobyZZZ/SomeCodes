package com.bb.module_common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bb.module_common.R;
import com.bb.module_common.utils.UIUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boby on 2018/7/17.
 */

public class FlowLayout<T extends FlowLayout.TextProvider> extends ViewGroup {
    String TAG = "FlowLayout";

    private Context mContext;
    private ArrayList<Line> lineList = new ArrayList<>();
    private int mCurrentWidth = 0;//一行中所有控件占的宽度
    private int horizontalSpace = UIUtils.dp2Px(5);
    private int verticalSpace = UIUtils.dp2Px(6);
    private int maxLine = 50;
    private OnTagClickedListener mOnTagClickedListener;
    private List<T> datas;
    private Line mLine = null;
    private boolean mRevert = false;

    public FlowLayout(Context context) {
        super(context);
        this.mContext = context;
        setWillNotDraw(false);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public void setRevert(boolean revert) {
        mRevert = revert;
        setData(datas);
    }

    public void addData(T item) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        datas.add(item);
        TextView itemView = createItemView(item.getText(), datas.size() - 1);
        addView(itemView);
    }

    public void setData(List<T> histories) {
        this.datas = histories;
        if (histories != null && histories.size() > 0) {
            removeAllViews();
            if (!mRevert) {
                for (int i = 0; i < histories.size(); i++) {
                    TextView textView = createItemView(histories.get(i).getText(), i);
                    addView(textView);
                }
            } else {
                for (int i = histories.size() - 1; i >= 0; i--) {
                    TextView textView = createItemView(histories.get(i).getText(), i);
                    addView(textView);
                }
            }
        }
    }

    @NotNull
    private TextView createItemView(final String text, final int index) {
        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundResource(R.drawable.shape_black_stroke_round);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTagClickedListener != null) {
                    mOnTagClickedListener.onClick(text, index);
                }
            }
        });
        return textView;
    }

    public void removeAll() {
        this.datas = null;
        removeAllViews();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "共有：" + lineList.size() + "行");
        int left = l + getPaddingLeft();
        int top = t + getPaddingTop();
        for (int j = 0; j < lineList.size(); j++) {
            Line line = lineList.get(j);
            line.layout(left, top);
            top += line.maxHeight + verticalSpace;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        lineList.clear();
        mLine = new Line();
        mCurrentWidth = 0;

        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();//控件的总宽度
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        printMode(widthMode, heightMode, width, height);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : widthMode);//父控件确定时，包裹内容；其余情况跟父控件一致，最大为width
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : heightMode);

            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            mCurrentWidth += childWidth;
            if (mCurrentWidth <= width) {//当前行总宽度小于控件总宽度，则可以将添加在当前行
                //将当前子控件添加到行对象中
                if (mLine == null) {
                    mLine = new Line();
                }
                mLine.addView(childView);
                //加上水平间距
                mCurrentWidth += horizontalSpace;
                if (mCurrentWidth >= width) {
                    //换行
                    if (!newLine()) {
                        break;
                    }
                }
            } else {
                if (mLine.getViewCount() == 0) {
                    //一行只有一个超长的子控件
                    mLine.addView(childView);//强行添加
                    if (!newLine()) {
                        break;
                    }
                } else {
                    //所剩空间不足以添加一个子控件，换行，添加进去
                    if (!newLine()) {
                        break;
                    }
                    mLine.addView(childView);
                    mCurrentWidth += childWidth + horizontalSpace;
                }
            }
        }
        //将最后一行添加到集合中
        if (mLine != null && mLine.getViewCount() > 0 && !lineList.contains(mLine)) {
            lineList.add(mLine);
        }


        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        //此处不能直接用MeasureSpec.getSize(widthMeasureSpec)来获取高度，应该通过测量计算获取高度;
        int totalHeight = 0;
        for (int i = 0; i < lineList.size(); i++) {
            Line line = lineList.get(i);
            totalHeight += line.maxHeight;
        }
        totalHeight += (lineList.size() - 1) * verticalSpace + getPaddingTop() + getPaddingBottom();
        Log.e(TAG, "totalWidth: " + totalWidth + ",totalHeight: " + totalHeight);
        setMeasuredDimension(totalWidth, totalHeight);
    }

    private void printMode(int widthMode, int heightMode, int width, int height) {
//        switch (widthMode) {
//            case MeasureSpec.AT_MOST:
//                Log.e("zyc", "widthMode: at_most ---- " + width);
//                break;
//            case MeasureSpec.EXACTLY:
//                Log.e("zyc", "widthMode: exactly ---- " + width);
//                break;
//            case MeasureSpec.UNSPECIFIED:
//                Log.e("zyc", "widthMode: unspecified ---- " + width);
//                break;
//        }
//
//        switch (heightMode) {
//            case MeasureSpec.AT_MOST:
//                Log.e("zyc", "heightMode: at_most ---- " + height);
//                break;
//            case MeasureSpec.EXACTLY:
//                Log.e("zyc", "heightMode: exactly ---- " + height);
//                break;
//            case MeasureSpec.UNSPECIFIED:
//                Log.e("zyc", "heightMode: unspecified ---- " + height);
//                break;
//        }
    }

    private boolean newLine() {
        lineList.add(mLine);
        if (lineList.size() < maxLine) {
            mLine = new Line();
            mCurrentWidth = 0;//清零
            return true;
        }
        return false;
    }

    public void setMaxLinesCount(int count) {
        if (count < 1) {
            count = 1;
        }
        maxLine = count;
        invalidate();
    }

    class Line {
        private ArrayList<View> viewList = new ArrayList<>();
        private int totalWidth;
        private int maxHeight;

        public void addView(View childView) {
            viewList.add(childView);
            int measuredWidth = childView.getMeasuredWidth();
            int measuredHeight = childView.getMeasuredHeight();
            totalWidth += measuredWidth;

            if (measuredHeight > maxHeight) {
                maxHeight = measuredHeight;
            }
        }

        public int getViewCount() {
            return viewList.size();
        }

        public void layout(int left, int top) {
            int count = viewList.size();
            int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            int surplus = width - totalWidth - (count - 1) * horizontalSpace;//剩余的宽度
            int addWidth = surplus / count;//平均分配的宽度

            for (int i = 0; i < viewList.size(); i++) {
                View view = viewList.get(i);

                int measuredWidth = view.getMeasuredWidth() + addWidth;
                int measuredHeight = view.getMeasuredHeight();

                int widthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY);
                int heightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY);

                view.measure(widthMeasureSpec, heightMeasureSpec);//重新测量

                int offSet = 0;
                if (measuredHeight < maxHeight) {
                    //向下偏移量
                    offSet = (maxHeight - measuredHeight) / 2;
                }
                view.layout(left, top + offSet, left + measuredWidth, top + offSet + measuredHeight);
                left += measuredWidth + horizontalSpace;
            }
        }
    }

    public interface OnTagClickedListener {
        void onClick(String text, int index);
    }

    public void setOnTagClickedListener(OnTagClickedListener onTagClickedListener) {
        this.mOnTagClickedListener = onTagClickedListener;
    }

    public interface TextProvider {
        String getText();
    }
}
