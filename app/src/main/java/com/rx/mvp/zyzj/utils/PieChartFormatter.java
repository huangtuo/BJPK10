package com.rx.mvp.zyzj.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by huangtuo on 2018/8/2.
 */

public class PieChartFormatter implements IValueFormatter, IAxisValueFormatter {

    protected DecimalFormat mFormat;

    public PieChartFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0");
    }

    /**
     * Allow a custom decimalformat
     *
     * @param format
     */
    public PieChartFormatter(DecimalFormat format) {
        this.mFormat = format;
    }

    // IValueFormatter
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        PieEntry entry1 = (PieEntry) entry;
        return entry1.getLabel() + " - " + mFormat.format(value) + "%";
    }

    // IAxisValueFormatter
    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value) + "%";
    }

    public int getDecimalDigits() {
        return 1;
    }

}
