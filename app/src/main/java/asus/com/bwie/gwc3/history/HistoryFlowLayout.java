package asus.com.bwie.gwc3.history;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class HistoryFlowLayout extends LinearLayout {
    private int mChildMaxHeight;
    private int mHSpace=20;
    private int mVSpace=20;


    public HistoryFlowLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    //找到孩子最大值
    private void findMaxChildMaxHeight(){
        mChildMaxHeight=0;
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++){
            View view = getChildAt(i);
            if (view.getMeasuredHeight()>mChildMaxHeight){
                mChildMaxHeight=view.getMeasuredHeight();
            }


        }

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);
        findMaxChildMaxHeight();
        int left=0,top=0;
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++){
            View view = getChildAt(i);
            if (left!=0){
                if ((left+view.getMeasuredWidth())>sizeWidth){
                    top +=mChildMaxHeight+mVSpace;
                    left=0;
                }
            }
            left+=view.getMeasuredWidth()+mHSpace;
        }
        setMeasuredDimension(sizeWidth,(top+mChildMaxHeight)>sizeHeight?sizeHeight:top+mChildMaxHeight);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int left=0,top=0;
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++){
            View view = getChildAt(i);
            if (left!=0){
                if ((left+view.getMeasuredWidth())>getWidth()){
                    top +=mChildMaxHeight+mVSpace;
                    left=0;
                }
            }
            view.layout(left,top,left+view.getMeasuredWidth(),top+view.getMeasuredHeight());
            left+=view.getMeasuredWidth()+mHSpace;
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

}
