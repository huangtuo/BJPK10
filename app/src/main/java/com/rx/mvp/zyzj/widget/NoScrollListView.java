package com.rx.mvp.zyzj.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by huangtuo on 2018/7/20.
 */

public class NoScrollListView extends ListView
{
    private int mPosition;

    public NoScrollListView(Context paramContext)
    {
        super(paramContext);
    }

    public NoScrollListView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
    }

    public NoScrollListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
    }

    public static void setListViewHeight(ListView paramListView)
    {
        ListAdapter localListAdapter = paramListView.getAdapter();
        if (localListAdapter == null) {
            return;
        }
        int j = 0;
        int i = 0;
        int k = localListAdapter.getCount();
        Object localObject = paramListView.getLayoutParams();
        while (i < k)
        {
            localObject = localListAdapter.getView(i, null, paramListView);
            ((View)localObject).measure(0, 0);
            j += ((View)localObject).getMeasuredHeight();
            i += 1;
        }


        ((ViewGroup.LayoutParams)localObject).height = (paramListView.getDividerHeight() * (localListAdapter.getCount() - 1) + j);
        paramListView.setLayoutParams((ViewGroup.LayoutParams)localObject);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
    {
        boolean bool = true;
        int i = paramMotionEvent.getActionMasked() & 0xFF;
        if (i == 0)
        {
            this.mPosition = pointToPosition((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
            bool = super.dispatchTouchEvent(paramMotionEvent);
        }
        while (i == 2) {
            return bool;
        }
        if ((i == 1) || (i == 3))
        {
            if (pointToPosition((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY()) == this.mPosition) {
                super.dispatchTouchEvent(paramMotionEvent);
            }
        }
        else {
            return super.dispatchTouchEvent(paramMotionEvent);
        }
        setPressed(false);
        invalidate();
        return true;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
    }
//    protected void onMeasure(int paramInt1, int paramInt2)
//    {
//        super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(536870911, Integer.MIN_VALUE));
//    }
}