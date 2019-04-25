package com.rx.mvp.zyzj.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.utils.PxPipConvert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"ResourceAsColor"})
public class SeekBarPressure
        extends View {
    private static final int CLICK_INVAILD = 0;
    private static final int CLICK_IN_HIGH_AREA = 4;
    private static final int CLICK_IN_LOW_AREA = 3;
    private static final int CLICK_ON_HIGH = 2;
    private static final int CLICK_ON_LOW = 1;
    private static final int CLICK_OUT_AREA = 5;
    private static final int[] STATE_NORMAL = new int[0];
    private static final int[] STATE_PRESSED = {android.R.attr.state_pressed, android.R.attr.state_window_focused};
    private Context context;
    private List<String> data = new ArrayList();
    private int drawY = 0;
    private OnSeekBarChangeListener mBarChangeListener;
    private int mDistance = 0;
    private int mDuration = 0;
    private int mFlag = 0;
    private float mMax = 5.0F;
    private float mMin = 1.0F;
    private int mOffsetHigh = 0;
    private int mOffsetLow = 0;
    private int mScollBarHeight;
    private int mScollBarWidth;
    private Drawable mScrollBarBgNormal;
    private Drawable mScrollBarProgress;
    private int mThumbHeight;
    private Drawable mThumbHigh;
    private Drawable mThumbLow;
    private int mThumbWidth;
    private int mprogressHigh;
    private int mprogressLow;

    public SeekBarPressure(Context paramContext) {
        this(paramContext, null);
    }

    public SeekBarPressure(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public SeekBarPressure(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        this.context = paramContext;
        Resources resources = getResources();
        mScrollBarBgNormal = resources.getDrawable(R.drawable.progress_bg);
        mScrollBarProgress = resources.getDrawable(R.drawable.area);
        mThumbLow = resources.getDrawable(R.mipmap.heidian);
        mThumbHigh = resources.getDrawable(R.mipmap.landian);

        mThumbLow.setState(STATE_NORMAL);
        mThumbHigh.setState(STATE_NORMAL);
        mThumbWidth = mThumbLow.getIntrinsicWidth();
        mThumbHeight = mThumbLow.getIntrinsicHeight();
        mScollBarHeight = mScrollBarBgNormal.getIntrinsicHeight();
        drawY = (mThumbHeight / 2 - mScollBarHeight / 2);
    }

    public static double formatDouble(double paramDouble) {
        return new BigDecimal(paramDouble).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private int formatInt(double paramDouble) {
        return new BigDecimal(paramDouble).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {
        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY) {
        }

        return specSize;
    }

    //    public int getAreaFlag(MotionEvent e) {
//        int top = 0;
//        int bottom = this.mThumbHeight;
//        if (e.getY() >= top && e.getY() <= bottom && e.getX() >= (mOffsetLow - mThumbWidth / 2) && e.getX() <= mOffsetLow + mThumbWidth / 2) {
//            return CLICK_ON_LOW;
//        } else if (e.getY() >= top && e.getY() <= bottom && e.getX() >= (mOffsetHigh - mThumbWidth / 2) && e.getX() <= (mOffsetHigh + mThumbWidth / 2)) {
//            return CLICK_ON_HIGH;
//        } else if (e.getY() >= top
//                && e.getY() <= bottom
//                && ((e.getX() >= 0 && e.getX() < (mOffsetLow - mThumbWidth / 2)) || ((e.getX() > (mOffsetLow + mThumbWidth / 2))
//                && e.getX() <= ((double) mOffsetHigh + mOffsetLow) / 2))) {
//            return CLICK_IN_LOW_AREA;
//        } else if (e.getY() >= top
//                && e.getY() <= bottom
//                && (((e.getX() > ((double) mOffsetHigh + mOffsetLow) / 2) && e.getX() < (mOffsetHigh - mThumbWidth / 2)) || (e
//                .getX() > (mOffsetHigh + mThumbWidth/2) && e.getX() <= mScollBarWidth))) {
//            return CLICK_IN_HIGH_AREA;
//        } else if (!(e.getX() >= 0 && e.getX() <= mScollBarWidth && e.getY() >= top && e.getY() <= bottom)) {
//            return CLICK_OUT_AREA;
//        } else {
//            return CLICK_INVAILD;
//        }
//    }
    public int getAreaFlag(MotionEvent paramMotionEvent) {
        int i = this.mThumbHeight;
        if ((paramMotionEvent.getY() >= 0) && (paramMotionEvent.getY() <= i) && (paramMotionEvent.getX() >= this.mOffsetLow) && (paramMotionEvent.getX() <= this.mOffsetLow + this.mThumbWidth)) {
            if ((this.mOffsetLow != this.mOffsetHigh) || (this.mOffsetLow != 0)) {
            }
            return CLICK_ON_LOW;
        } else if ((paramMotionEvent.getY() >= 0) && (paramMotionEvent.getY() <= i) && (paramMotionEvent.getX() >= this.mOffsetHigh) && (paramMotionEvent.getX() <= this.mOffsetHigh + this.mThumbWidth)) {
            return CLICK_ON_HIGH;

        } else if ((paramMotionEvent.getY() >= 0) && (paramMotionEvent.getY() <= i) && (((paramMotionEvent.getX() >= 0.0F) && (paramMotionEvent.getX() < this.mOffsetLow)) || ((paramMotionEvent.getX() > this.mOffsetLow + this.mThumbWidth) && (paramMotionEvent.getX() <= (this.mOffsetHigh + this.mOffsetLow + this.mThumbWidth) / 2.0D)))) {
            return CLICK_IN_LOW_AREA;
        } else if ((paramMotionEvent.getY() >= 0) && (paramMotionEvent.getY() <= i) && (((paramMotionEvent.getX() > (this.mOffsetHigh + this.mOffsetLow + this.mThumbWidth) / 2.0D) && (paramMotionEvent.getX() < this.mOffsetHigh)) || ((paramMotionEvent.getX() > this.mOffsetHigh + this.mThumbWidth) && (paramMotionEvent.getX() <= this.mScollBarWidth)))) {
            return CLICK_IN_HIGH_AREA;
        } else if ((paramMotionEvent.getX() < 0.0F) || (paramMotionEvent.getX() > this.mScollBarWidth) || (paramMotionEvent.getY() < 0) || (paramMotionEvent.getY() > i)) {
            return CLICK_OUT_AREA;
        } else
            return CLICK_INVAILD;


    }

    public List<String> getData() {
        return this.data;
    }

    public double getMax() {
        return this.mMax;
    }

    public double getMin() {
        return this.mMin;
    }

    public double getProgressHigh() {
        return formatDouble(this.mOffsetHigh * (this.mMax - this.mMin) / this.mDistance + this.mMin);
    }

    public double getProgressLow() {
        return formatDouble(this.mOffsetLow * (this.mMax - this.mMin) / this.mDistance + this.mMin);
    }

    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        Paint localPaint1 = new Paint();
        localPaint1.setColor(ContextCompat.getColor(context, R.color.color_kedu));
        localPaint1.setStrokeWidth(2.0F);
        Paint localPaint2 = new Paint();
        localPaint2.setTextAlign(Paint.Align.CENTER);
        localPaint2.setColor(ContextCompat.getColor(context, R.color.color_keduzhi));
        localPaint2.setAntiAlias(true);
        localPaint2.setTextSize(PxPipConvert.convertDip2Px(context, 10));
        int j = (this.mScollBarWidth - this.mThumbWidth) / (this.data.size() - 1);
        float f1 = 0.0F;
        int i = 0;
        while (i < this.data.size()) {
            f1 = this.mThumbWidth / 2 + i * j;
            paramCanvas.drawLine(this.mThumbWidth / 2 + i * j, this.mThumbHeight + 1, this.mThumbWidth / 2 + i * j, this.mThumbHeight + PxPipConvert.convertDip2Px(context, 6), localPaint1);
            i += 1;
        }
        float f2 = this.mThumbWidth / 2;
        this.mScrollBarBgNormal.setBounds(0, 0, (int) (f1 + f2), this.mScollBarHeight - 3);
        this.mScrollBarBgNormal.draw(paramCanvas);
        this.mScrollBarProgress.setBounds(this.mOffsetLow, 0, this.mOffsetHigh + this.mThumbWidth, this.mScollBarHeight - 3);
        this.mScrollBarProgress.draw(paramCanvas);
        this.mThumbLow.setBounds(this.mOffsetLow, 0, this.mOffsetLow + this.mThumbWidth, this.mThumbHeight);
        this.mThumbLow.draw(paramCanvas);
        this.mThumbHigh.setBounds(this.mOffsetHigh, 0, this.mOffsetHigh + this.mThumbWidth, this.mThumbHeight);
        this.mThumbHigh.draw(paramCanvas);
        i = this.mOffsetLow / j;
        int k = this.mOffsetHigh / j;
        paramCanvas.drawText((String) this.data.get(i), this.mThumbWidth / 2 + i * j, this.mThumbHeight + PxPipConvert.convertDip2Px(context, 18), localPaint2);
        paramCanvas.drawText((String) this.data.get(k), this.mThumbWidth / 2 + k * j, this.mThumbHeight + PxPipConvert.convertDip2Px(context, 18), localPaint2);
        if (this.mBarChangeListener != null) {
            double d1 = formatDouble(this.mOffsetLow * (this.mMax - this.mMin) / this.mDistance + this.mMin);
            double d2 = formatDouble(this.mOffsetHigh * (this.mMax - this.mMin) / this.mDistance + this.mMin);
            this.mBarChangeListener.onProgressChanged(this, d1, d2, this.mprogressLow, this.mprogressHigh, this.mMax, this.mMin);
        }
    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        paramInt1 = measureWidth(paramInt1);
        paramInt2 = this.mThumbHeight;
        int i = PxPipConvert.convertDip2Px(context, 20);
        this.mScollBarWidth = paramInt1;
        this.mDistance = (this.mScollBarWidth - this.mThumbWidth);
        this.mDuration = ((int) Math.rint(0.5D * this.mDistance / (this.mMax - this.mMin)));
        this.mOffsetLow = (this.mprogressLow * ((this.mScollBarWidth - this.mThumbWidth) / (this.data.size() - 1)));
        this.mOffsetHigh = (this.mprogressHigh * ((this.mScollBarWidth - this.mThumbWidth) / (this.data.size() - 1)));
        setMeasuredDimension(paramInt1, paramInt2 + i);
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent) {

        if (paramMotionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if(!moveAble){
                if(noMoveListener != null){
                    noMoveListener.noMove();
                }
                return true;
            }
            this.mFlag = getAreaFlag(paramMotionEvent);
            if (this.mFlag == CLICK_ON_LOW) {
                this.mThumbLow.setState(STATE_PRESSED);
            }else if(this.mFlag == CLICK_ON_HIGH) {
                mThumbHigh.setState(STATE_PRESSED);
            }else if (mFlag == CLICK_IN_LOW_AREA) {
                this.mThumbLow.setState(STATE_PRESSED);
                if ((paramMotionEvent.getX() < 0.0F) || (paramMotionEvent.getX() <= this.mThumbWidth)) {
                    this.mOffsetLow = 0;
                } else if (paramMotionEvent.getX() > this.mScollBarWidth - this.mThumbWidth) {
                    this.mOffsetLow = (this.mThumbWidth / 2 + this.mDistance);
                } else {
                    this.mOffsetLow = formatInt(paramMotionEvent.getX() - this.mThumbWidth / 2.0D);
                    //                    if (mOffsetHigh<= mOffsetLow) {
//                        mOffsetHigh = (mOffsetLow + mDuration <= mDistance) ? (mOffsetLow + mDuration)
//                                : mDistance;
//                        mOffsetLow = mOffsetHigh - mDuration;
//                    }
                }
            }else if (mFlag == CLICK_IN_HIGH_AREA) {
                this.mThumbHigh.setState(STATE_PRESSED);
                if (paramMotionEvent.getX() >= this.mScollBarWidth - this.mThumbWidth / 2) {
                    this.mOffsetHigh = this.mDistance;
                } else {
                    this.mOffsetHigh = formatInt(paramMotionEvent.getX() - this.mThumbWidth / 2.0D);
                }
            }
        } else if (paramMotionEvent.getAction() ==MotionEvent.ACTION_MOVE) {
            getParent().requestDisallowInterceptTouchEvent(true);
            if (this.mFlag == CLICK_ON_LOW) {
                if ((paramMotionEvent.getX() < 0.0F) || (paramMotionEvent.getX() <= this.mThumbWidth)) {
                    this.mOffsetLow = 0;
                } else if (paramMotionEvent.getX() > this.mScollBarWidth - this.mThumbWidth / 2) {
                    this.mOffsetLow = this.mDistance;
                    this.mOffsetHigh = this.mOffsetLow;
                } else {
                    this.mOffsetLow = formatInt(paramMotionEvent.getX() - this.mThumbWidth / 2.0D);

                    if (mOffsetHigh - mOffsetLow <= 0) {
                        mOffsetHigh = (mOffsetLow <= mDistance+mThumbWidth/2) ? (mOffsetLow) : (mDistance+mThumbWidth/2);
                    }
                }
            } else if (this.mFlag == CLICK_ON_HIGH) {
                if (paramMotionEvent.getX() < 0.0F) {
                    this.mOffsetHigh = 0;
                    this.mOffsetLow = 0;
                } else if (paramMotionEvent.getX() > this.mScollBarWidth - this.mThumbWidth / 2) {
                    this.mOffsetHigh = this.mDistance;
                } else {
                    this.mOffsetHigh = formatInt(paramMotionEvent.getX() - this.mThumbWidth / 2.0D);
                    if (mOffsetHigh - mOffsetLow <= 0) {
                        mOffsetLow = (mOffsetHigh >= mThumbWidth/2) ? (mOffsetHigh) : mThumbWidth/2;
                    }
                    if (mOffsetHigh - mOffsetLow <= 0) {
                        mOffsetLow = (mOffsetHigh >= mThumbWidth/2) ? (mOffsetHigh) - mThumbWidth/2 : mThumbWidth/2;
                    }
//                    if (this.mOffsetHigh - this.mOffsetLow <= 0) {
//                        i = j;
//                        if (this.mOffsetHigh - this.mThumbWidth / 2 >= 0) {
//                            i = this.mOffsetHigh - this.mThumbWidth / 2;
//                        }
//                        this.mOffsetLow = i;
//                    }
                }
                if (paramMotionEvent.getX() <  mThumbWidth/2) {
                    mOffsetHigh = mThumbWidth/2;
                    mOffsetLow = mThumbWidth/2;
                } else if (paramMotionEvent.getX() > mScollBarWidth - mThumbWidth/2) {
                    mOffsetHigh = mThumbWidth/2 + mDistance;
                } else {
                    mOffsetHigh = formatInt(paramMotionEvent.getX());
                    if (mOffsetHigh - mOffsetLow <= 0) {
                        mOffsetLow = (mOffsetHigh >= mThumbWidth/2) ? (mOffsetHigh) : mThumbWidth/2;
                    }
                }
            }
        } else if (paramMotionEvent.getAction() == MotionEvent.ACTION_UP) {
            this.mThumbLow.setState(STATE_NORMAL);
            this.mThumbHigh.setState(STATE_NORMAL);

            //这两个for循环 是用来自动对齐刻度的，注释后，就可以自由滑动到任意位置
            for (int i2 = 0; i2 < data.size(); i2++) {
                if (Math.abs(mOffsetLow - i2 * ((mScollBarWidth - mThumbWidth) / (data.size() - 1))) <= (mScollBarWidth - mThumbWidth) / (data.size() - 1) / 2) {
                    mprogressLow = i2;
                    mOffsetLow = i2 * ((mScollBarWidth - mThumbWidth) / (data.size() - 1));
                    invalidate();
                    break;
                }
            }

            for (int i3 = 0; i3 < data.size(); i3++) {
                if (Math.abs(mOffsetHigh - i3 * ((mScollBarWidth - mThumbWidth) / (data.size() -1 ))) < (mScollBarWidth - mThumbWidth) / (data.size() - 1) / 2) {
                    mprogressHigh = i3;
                    mOffsetHigh = i3 * ((mScollBarWidth - mThumbWidth) / (data.size() - 1));
                    invalidate();
                    break;
                }
            }
        }
        return true;
    }

    public void setData(List<String> paramList) {
        this.data = paramList;
    }

    public void setMax(double paramDouble) {
        this.mMax = ((float) paramDouble);
    }

    public void setMin(double paramDouble) {
        this.mMin = ((float) paramDouble);
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener paramOnSeekBarChangeListener) {
        this.mBarChangeListener = paramOnSeekBarChangeListener;
    }

    public void setProgressHigh(double paramDouble) {
        this.mOffsetHigh = formatInt((paramDouble - this.mMin) / (this.mMax - this.mMin) * this.mDistance);
        invalidate();
    }

    public void setProgressHighInt(int paramInt) {
        this.mprogressHigh = paramInt;
    }

    public void setProgressLow(double paramDouble) {
        this.mOffsetLow = formatInt((paramDouble - this.mMin) / (this.mMax - this.mMin) * this.mDistance);
        invalidate();
    }

    public void setProgressLowInt(int paramInt) {
        this.mprogressLow = paramInt;
    }

    public static abstract interface OnSeekBarChangeListener {
        public abstract void onProgressChanged(SeekBarPressure paramSeekBarPressure, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2, double paramDouble3, double paramDouble4);
    }

    public interface NoMoveListener{
        void noMove();
    }

    public void setMoveAble(boolean moveable){
        moveAble = moveable;
    }
    private boolean moveAble = true;
    private NoMoveListener noMoveListener;

    public void setNoMoveListener(NoMoveListener noMoveListener) {
        this.noMoveListener = noMoveListener;
    }
}
