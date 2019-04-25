package com.rx.mvp.zyzj.utils;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by huangtuo on 2018/8/2.
 */

public class PieChartManager {
    //设置各区块的颜色
    public final int[] PIE_COLORS = {
            Color.rgb(212, 59, 53), Color.rgb(46, 49, 83), Color.rgb(95, 160, 166),
            Color.rgb(211, 130, 100), Color.rgb(144, 198, 172), Color.rgb(114, 159, 130),
            Color.rgb(202, 134, 33), Color.rgb(186, 162, 153), Color.rgb(108, 112, 115)
            , Color.rgb(83, 102, 111)
    };

    //设置饼形图属性
    public void setPieChart(PieChart pieChart, Map<String, Float> pieValues, String title, boolean showLegend) {
        pieChart.setUsePercentValues(true);//设置使用百分比（后续有详细介绍）
        pieChart.getDescription().setEnabled(false);//设置描述
        pieChart.setExtraOffsets(25, 10, 25, 25); //设置边距
        pieChart.setDragDecelerationFrictionCoef(0.95f);//设置摩擦系数（值越小摩擦系数越大）
        pieChart.setRotationEnabled(true);//是否可以旋转
        pieChart.setHighlightPerTapEnabled(true);//点击是否放大
        pieChart.setCenterText(title);//设置环中的文字
        pieChart.setCenterTextSize(22f);//设置环中文字的大小
        pieChart.setDrawCenterText(false);//设置绘制环中文字
        pieChart.setRotationAngle(120f);//设置旋转角度
        pieChart.setTransparentCircleRadius(0f);//设置半透明圆环的半径,看着就有一种立体的感觉
        //这个方法为true就是环形图，为false就是饼图
        pieChart.setDrawHoleEnabled(true);
        //设置环形中间空白颜色是白色
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setHoleRadius(30f);
        //设置半透明圆环的颜色
        pieChart.setTransparentCircleColor(Color.WHITE);
        //设置半透明圆环的透明度
//        pieChart.setTransparentCircleAlpha(110);
        //是否在图形上显示名称
        pieChart.setDrawEntryLabels(false);

        //图例设置
        Legend legend = pieChart.getLegend();
        if (showLegend) {
            legend.setEnabled(true);//是否显示图例
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);//图例相对于图表横向的位置
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//图例相对于图表纵向的位置
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);//图例显示的方向
            legend.setDrawInside(false);
            legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        } else {
            legend.setEnabled(false);
        }

        //设置饼图数据
        setPieChartData(pieChart, pieValues);

        pieChart.animateX(1500, Easing.EasingOption.EaseInOutQuad);//数据显示动画

    }

    //设置饼图数据
    private void setPieChartData(PieChart pieChart, Map<String, Float> pieValues) {
        List<PieEntry> yVals = new ArrayList<>();
        Set set = pieValues.entrySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            yVals.add(new PieEntry(Float.valueOf(entry.getValue().toString()), entry.getKey().toString()));
        }

        PieDataSet dataSet = new PieDataSet(yVals, "");
        dataSet.setSliceSpace(0f);//设置饼块之间的间隔
        dataSet.setSelectionShift(15f);//设置饼块选中时偏离饼图中心的距离
        dataSet.setColors(PIE_COLORS);//设置饼块的颜色

        //设置数据显示方式有见图
        dataSet.setValueLinePart1OffsetPercentage(80f);//数据连接线距图形片内部边界的距离，为百分数
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setValueLineColor(Color.RED);//设置连接线的颜色
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        dataSet.setValueFormatter(new PieChartFormatter());
//        dataSet.setValueFormatter(new DefaultValueFormatter(1));//自己修改的方法

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.DKGRAY);
//        pieData.setValueTextColors(Arrays.asList(PIE_COLORS_02));

        pieChart.setData(pieData);
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

}
