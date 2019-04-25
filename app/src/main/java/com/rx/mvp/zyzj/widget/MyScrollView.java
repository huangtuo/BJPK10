package com.rx.mvp.zyzj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by huangtuo on 2018/7/19.
 */

public class MyScrollView extends ScrollView {
    private ScrollListener mListener;

    public MyScrollView(Context paramContext) {
        super(paramContext);
    }

    public MyScrollView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public MyScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (this.mListener != null) {
            this.mListener.scrollOritention(l, t, oldl, oldt);
        }
    }

    public void setScrollListener(ScrollListener paramScrollListener) {
        this.mListener = paramScrollListener;
    }

    public interface ScrollListener {
        void scrollOritention(int l, int t, int oldl, int oldt);
    }
}
