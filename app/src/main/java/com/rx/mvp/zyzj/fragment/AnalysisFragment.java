package com.rx.mvp.zyzj.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.activity.SetAnalysisActivity;
import com.rx.mvp.zyzj.base.BaseFragment;
import com.rx.mvp.zyzj.common.BundleKey;
import com.rx.mvp.zyzj.common.Constants;
import com.rx.mvp.zyzj.entity.event.RefreshAnalysisEvent;
import com.rx.mvp.zyzj.entity.event.RestoreRadioEvent;
import com.rx.mvp.zyzj.entity.event.SwitchAnalysisEvent;
import com.rx.mvp.zyzj.entity.resp.AnalysisConfigEntity;
import com.rx.mvp.zyzj.entity.resp.AnalysisConfigGroupEntity;
import com.rx.mvp.zyzj.entity.resp.AnalysisConfigParentEntity;
import com.rx.mvp.zyzj.entity.resp.HotEntity;
import com.rx.mvp.zyzj.entity.resp.MissEntity;
import com.rx.mvp.zyzj.iface.IAnalysisConfigView;
import com.rx.mvp.zyzj.iface.IAnalysisView;
import com.rx.mvp.zyzj.presenter.AnalysisConfigPresenter;
import com.rx.mvp.zyzj.presenter.AnalysisPresenter;
import com.rx.mvp.zyzj.utils.BarChartManager;
import com.rx.mvp.zyzj.utils.PieChartManager;
import com.rx.mvp.zyzj.utils.SPUtils;
import com.rx.mvp.zyzj.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by huangtuo on 2018/7/19.
 */

public class AnalysisFragment extends BaseFragment implements IAnalysisView, IAnalysisConfigView {

    @BindView(R.id.analysis_radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.analysis_btn_miss)
    RadioButton btnMiss;
    @BindView(R.id.analysis_btn_hot)
    RadioButton btnHot;
    @BindView(R.id.analysis_btn_targetMiss)
    RadioButton btnTargetMiss;
    @BindView(R.id.analysis_btn_targetHot)
    RadioButton btnTargetHot;
    @BindView(R.id.analysis_ll_miss)
    LinearLayout llMiss;
    @BindView(R.id.analysis_tv_missTitle)
    TextView tvMissTitle;
    @BindView(R.id.analysis_barChart_miss)
    BarChart missBarChart;

    @BindView(R.id.analysis_ll_hot)
    LinearLayout llHot;
    @BindView(R.id.analysis_tv_hotTitle)
    TextView tvHotTitle;
    @BindView(R.id.analysis_pieChart_hot)
    PieChart hotPieChart;

    @BindView(R.id.analysis_ll_targetMiss)
    LinearLayout llTargetMiss;
    @BindView(R.id.analysis_tv_targetMissTitle)
    TextView tvTargetMissTitle;
    @BindView(R.id.analysis_barChart_targetMiss)
    BarChart targetMissBarChart;

    @BindView(R.id.analysis_ll_targetHot)
    LinearLayout llTargetHot;
    @BindView(R.id.analysis_tv_targetHotTitle)
    TextView tvTargetHotTitle;
    @BindView(R.id.analysis_pieChart_targetHot)
    PieChart targetHotPieChart;

    @BindView(R.id.analysis_rel_dialogInfo)
    RelativeLayout relDialogInfo;
    @BindView(R.id.analysis_tv_num)
    TextView tvXNum;
    @BindView(R.id.analysis_tv_count)
    TextView tvYCount;

    private int radioIndex; //用于没有选中指标跳转到设置页面， RadioGroup 要还原上一个选中的RadioButton
    private int dialogW, dialogH, dialogSrceenW, dialogSrceenH;// dialog的宽高； dialog左上角离屏幕左边跟顶部的距离
    private int touchX, touchY; //手指点击的坐标
    private int width, height;//屏幕宽高
    private int offset = 10; //偏移量
    private Map<Integer, Coordinate> coordinateMap = new HashMap<>(); //存储柱形图显示dialog的坐标跟值
    ViewGroup.MarginLayoutParams layoutParams; //dialog的参数
    private BarChartManager missManager;
    private BarChartManager targetMissManager;
//    private PieChartManager hotManager;
//    private PieChartManager targetHotManager;
    private List<String> xMissValues = new LinkedList<>(); //遗漏分析X轴坐标
    private List<Integer> yMissValues= new LinkedList<>(); //遗漏分析Y轴坐标
    private List<String> xTargetMissValues = new LinkedList<>(); //指标遗漏分析X轴坐标
    private List<Integer> yTargetMissValues= new LinkedList<>(); //指标遗漏分析Y轴坐标
    private AnalysisPresenter analysisPresenter = new AnalysisPresenter(this, this);
    private AnalysisConfigPresenter analysisConfigPresenter = new AnalysisConfigPresenter(this, this);

    private boolean isHotInit, isTargetMisInit, isTargetHotInit;
    private AnalysisConfigEntity missLocationChecked; //遗漏  已选中位置
    private String hotCheckedData; //冷热 已选中数据
    private AnalysisConfigEntity hotLocationChecked; //冷热 已选中位置
    private AnalysisConfigEntity missTarget; //指标遗漏 已选中的指标
    private String hotTargetDate; //指标冷热： 已选中的数据
    private AnalysisConfigEntity hotTarget; //指标冷热已选中的指标


    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_analysis, null);
    }

    @Override
    protected void initBundleData() {
        EventBus.getDefault().register(this);

    }

    @Override
    protected void initView() {

        WindowManager wm = (WindowManager) activity
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.analysis_btn_miss:
                    radioIndex = 0;
                    showDialog(radioIndex);
                    EventBus.getDefault().post(new SwitchAnalysisEvent(0));
                    clickMiss(false);
                    break;
                case R.id.analysis_btn_hot:
                    radioIndex = 1;
                    EventBus.getDefault().post(new SwitchAnalysisEvent(1));
                    clickHot(!isHotInit);

                    break;
                case R.id.analysis_btn_targetMiss:

                    clickTargetMiss(!isTargetMisInit);
                    radioIndex = 2;
                    showDialog(radioIndex);
                    break;
                case R.id.analysis_btn_targetHot:
                    clickTargetHot(!isTargetHotInit);
                    radioIndex = 3;
                    break;

                default:
                    break;
            }
        });
        //遗漏分析 图表
        initMissBarChart();
        clickMiss(true);

        relDialogInfo.post(() -> {
            dialogW = relDialogInfo.getWidth();
            dialogH = relDialogInfo.getHeight();
            layoutParams = (ViewGroup.MarginLayoutParams) relDialogInfo.getLayoutParams();
            int[] position = new int[2];
            relDialogInfo.getLocationInWindow(position);
            dialogSrceenW = position[0];
            dialogSrceenH = position[1];
        });
    }

    private void clickMiss(boolean isPost){
        llMiss.setVisibility(View.VISIBLE);
        llHot.setVisibility(View.GONE);
        llTargetMiss.setVisibility(View.GONE);
        llTargetHot.setVisibility(View.GONE);
        if(isPost){
            missLocationChecked = (AnalysisConfigEntity) SPUtils.getObject(activity, Constants.MISS_LOCATION);
            if(missLocationChecked == null || StringUtils.isEmpty(missLocationChecked.getId())){
                analysisConfigPresenter.getMissConfig();
            }else{
                tvMissTitle.setText(missLocationChecked.getRuleName() + "-遗漏分析");
                analysisPresenter.analysisMiss(missLocationChecked.getId());
            }

        }
    }
    private void clickHot(boolean isPost){
        llMiss.setVisibility(View.GONE);
        llHot.setVisibility(View.VISIBLE);
        llTargetMiss.setVisibility(View.GONE);
        llTargetHot.setVisibility(View.GONE);
        relDialogInfo.setVisibility(View.GONE);
        if(isPost){ //第一次点击获取网络请求
            //第一次进入 已选条件可能为空
            hotCheckedData = SPUtils.getString(activity, Constants.ANALYSIS_CHECKED, Constants.HOT_DATA);
            hotLocationChecked = (AnalysisConfigEntity) SPUtils.getObject(activity, Constants.HOT_LOCATION);
            if(StringUtils.isEmpty(hotCheckedData) || hotLocationChecked == null || StringUtils.isEmpty(hotLocationChecked.getId())){
                analysisConfigPresenter.getHotConfig();
            }else{
                //冷热分析 饼形图
                analysisPresenter.analysisHot(hotLocationChecked.getId(), hotCheckedData);
                tvHotTitle.setText(hotLocationChecked.getRuleName() + "近" + hotCheckedData + "期-冷热分析");
            }
        }
    }
    private void clickTargetMiss(boolean isPost){
        missTarget = (AnalysisConfigEntity) SPUtils.getObject(mContext, Constants.MISS_TARGET);
        if (missTarget == null || StringUtils.isEmpty(missTarget.getId())) {
            Intent intent = new Intent(mContext, SetAnalysisActivity.class);
            intent.putExtra(BundleKey.FLAG, 2);
            intent.putExtra(BundleKey.RADIO_INDEX, radioIndex);
            startActivity(intent);
            return;
        }else if(isPost){
            //指标遗漏分析 图表
            initTargetMissBarChart();
            analysisPresenter.targetMiss(missTarget.getId());
            tvTargetMissTitle.setText(missTarget.getRuleName() + "-遗漏分析");
        }
        llMiss.setVisibility(View.GONE);
        llHot.setVisibility(View.GONE);
        llTargetMiss.setVisibility(View.VISIBLE);
        llTargetHot.setVisibility(View.GONE);
        EventBus.getDefault().post(new SwitchAnalysisEvent(2));
    }
    private void clickTargetHot(boolean isPost){
        hotTarget = (AnalysisConfigEntity) SPUtils.getObject(mContext, Constants.HOT_TARGET);
        hotTargetDate = SPUtils.getString(activity, Constants.ANALYSIS_CHECKED, Constants.HOT_TARGET_DATA);
        if (StringUtils.isEmpty(hotTargetDate) || hotTarget == null || StringUtils.isEmpty(hotTarget.getId())) {
            Intent intent = new Intent(mContext, SetAnalysisActivity.class);
            intent.putExtra(BundleKey.FLAG, 3);
            intent.putExtra(BundleKey.RADIO_INDEX, radioIndex);
            startActivity(intent);
            return;
        }else if(isPost) {
            analysisPresenter.targetHot(hotTarget.getId(), hotTargetDate);
            tvTargetHotTitle.setText(hotTarget.getRuleName() + "近" + hotTargetDate + "期-冷热分析");
        }

        relDialogInfo.setVisibility(View.GONE);
        llMiss.setVisibility(View.GONE);
        llHot.setVisibility(View.GONE);
        llTargetMiss.setVisibility(View.GONE);
        llTargetHot.setVisibility(View.VISIBLE);
        EventBus.getDefault().post(new SwitchAnalysisEvent(3));
    }

    /**
     * 初始化遗漏分析柱形图
     */
    private void initMissBarChart() {

        missManager = new BarChartManager(missBarChart);
        missBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Coordinate coordinate = new Coordinate();
                coordinate.setxValue(xMissValues.get((int) e.getX()));
//                coordinate.setxValue((int) e.getX() + "");
                coordinate.setyValue((int) e.getY() + "");
                show(0, coordinate);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        missBarChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {
            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
//                showToast(me.getRawX() + "--" + me.getRawY());
            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
//                showToast(me.getRawX() + "--" + me.getRawY());
                touchX = (int) me.getRawX();
                touchY = (int) me.getRawY();
            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });

    }

    /**
     * 遗漏分析填充数据
     */
    private void setMissData(List<MissEntity> listMiss){
        xMissValues.clear();
        yMissValues.clear();

        for(MissEntity missEntity : listMiss){
            //设置x轴的数据
            xMissValues.add(missEntity.getBall());
            //设置y轴的数据()
            yMissValues.add(missEntity.getMissNum());
        }

        sort(true, xMissValues, yMissValues);
        //创建多条折线的图表
        missManager.showBarChart(xMissValues, yMissValues, "", activity.getResources().getColor(R.color.bg_red), activity.getResources().getColor(R.color.bg_red_checked));
    }


    /**
     * 初始化指标遗漏分析柱形图
     */
    private void initTargetMissBarChart() {
        targetMissManager = new BarChartManager(targetMissBarChart);

        //创建多条折线的图表
        targetMissBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Coordinate coordinate = new Coordinate();
                coordinate.setxValue((int) e.getX() + "");
                coordinate.setyValue((int) e.getY() + "");
                show(2, coordinate);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        targetMissBarChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {
            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
                touchX = (int) me.getRawX();
                touchY = (int) me.getRawY();
            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });

    }

    /**
     * 指标遗漏分析填充数据
     */
    private void setTargetMissData(List<MissEntity> listMiss){
        xTargetMissValues.clear();
        yTargetMissValues.clear();

        for(MissEntity missEntity : listMiss){
            //设置x轴的数据
            xTargetMissValues.add(missEntity.getBall());
            //设置y轴的数据()
            yTargetMissValues.add(missEntity.getMissNum());
        }

        sort(false, xTargetMissValues, yTargetMissValues);
        //创建多条折线的图表
        targetMissManager.showBarChart(xTargetMissValues, yTargetMissValues, "", activity.getResources().getColor(R.color.bg_red), activity.getResources().getColor(R.color.bg_red_checked));
    }

    /**
     *冷热分析 饼形图
     */
    private void initHotPieChart(List<HotEntity> hotEntityList) {

        HashMap dataMap = new HashMap();
        for(HotEntity hotEntity : hotEntityList){
            dataMap.put(hotEntity.getBall(), hotEntity.getAnalyze());
        }
        new PieChartManager().setPieChart(hotPieChart, dataMap, "", false);
    }

    /**
     *冷热分析 饼形图
     */
    private void initTargetHotPieChart(List<HotEntity> hotEntityList) {
        HashMap dataMap = new HashMap();
        for(HotEntity hotEntity : hotEntityList){
            dataMap.put(hotEntity.getBall(), hotEntity.getAnalyze());
        }
        new PieChartManager().setPieChart(targetHotPieChart, dataMap, "", false);
    }


    private void show(int index, Coordinate coordinate) {

        int rawX, rawY; //dialog实际显示 左上角的坐标
        if (width < touchX - dialogSrceenW + dialogW + offset) {//右边超出
            rawX = touchX - dialogSrceenW - dialogW - offset; //则显示在手指点击的左边
        } else {
            rawX = touchX - dialogSrceenW + offset;//显示在手指点击的右边
        }
        rawY = touchY - dialogH - dialogSrceenH + offset;
        coordinate.setX(rawX);
        coordinate.setY(rawY);
        coordinateMap.put(index, coordinate);
        showDialog(index);
    }

    private void showDialog(int index) {
        if (coordinateMap.containsKey(index)) {
            Coordinate coordinate = coordinateMap.get(index);
            if (coordinate != null) {
                tvXNum.setText(coordinate.getxValue());
                tvYCount.setText("出现次数：" + coordinate.getyValue());
                layoutParams.leftMargin = coordinate.getX();
                layoutParams.topMargin = coordinate.getY();
                relDialogInfo.setLayoutParams(layoutParams);
                relDialogInfo.setVisibility(View.VISIBLE);
                return;
            }
        }
        relDialogInfo.setVisibility(View.GONE);
    }

    /**
     * 对Y轴值进行降序排序，并 调换对应的X轴位置
     * @param isMiss
     * @param keyList
     * @param valueList
     */
    private void sort(boolean isMiss, List<String> keyList, List<Integer> valueList){
        Map<String, Integer> map = new HashMap<String, Integer>();
        int size = Math.max(keyList.size(), valueList.size());
        for(int i = 0; i < size; i++){
            map.put(keyList.get(i), valueList.get(i));
        }
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            //降序排序
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
        if(isMiss){ //遗漏分析
            xMissValues.clear();
            yMissValues.clear();
            for(Map.Entry<String,Integer> mapping:list){
                xMissValues.add(mapping.getKey());
                yMissValues.add(mapping.getValue());
            }
        }else{ //目标遗漏分析
            xTargetMissValues.clear();
            yTargetMissValues.clear();
            for(Map.Entry<String,Integer> mapping:list){
                xTargetMissValues.add(mapping.getKey());
                yTargetMissValues.add(mapping.getValue());
            }
        }
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(RefreshAnalysisEvent event) {
        if (event != null) {
            switch (event.getFlag()) {
                case 1:
                    clickHot(true);
                    break;
                case 2:
                    clickTargetMiss(true);
                    break;
                case 3:
                    clickTargetHot(true);
                    break;
                default:
                    clickMiss(true);

                    break;
            }
        }

    }

    @Subscribe
    public void onEvent(RestoreRadioEvent event) {
        if (event != null) {
            switch (event.getIndex()) {
                case 1:
                    btnHot.setChecked(true);
                    break;
                case 2:
                    btnTargetMiss.setChecked(true);
                    break;
                case 3:
                    btnTargetHot.setChecked(true);
                    break;
                default:
                    btnMiss.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void showLoading() {
        showLoding();
    }

    @Override
    public void closeLoading() {
        disLoding();
    }

    @Override
    public void onError(int code, String msg) {
        sendFail(code, msg);
    }

    @Override
    public void getAnalysisMiss(List<MissEntity> missEntity) {
        if(missEntity != null && missEntity.size() > 0){
            setMissData(missEntity);
            missBarChart.setVisibility(View.VISIBLE);
        }else{
            missBarChart.setVisibility(View.GONE);
        }

    }

    @Override
    public void getAnalysisHot(List<HotEntity> hotEntityList) {
        if(hotEntityList != null && hotEntityList.size() > 0){
            isHotInit = true;
            initHotPieChart(hotEntityList);
            hotPieChart.setVisibility(View.VISIBLE);
        }else{
            hotPieChart.setVisibility(View.GONE);
        }
    }

    @Override
    public void getTargetMiss(List<MissEntity> missEntity) {
        if(missEntity != null && missEntity.size() > 0){
            isTargetMisInit = true;
            setTargetMissData(missEntity);
            targetMissBarChart.setVisibility(View.VISIBLE);
        }else{
            targetMissBarChart.setVisibility(View.GONE);
        }
    }

    @Override
    public void getTargetHot(List<HotEntity> hotEntityList) {
        if(hotEntityList != null && hotEntityList.size() > 0){
            isTargetHotInit = true;
            initTargetHotPieChart(hotEntityList);
            targetHotPieChart.setVisibility(View.VISIBLE);
        }else{
            targetHotPieChart.setVisibility(View.GONE);
        }
    }

    @Override
    public void missConfigSuccess(List<AnalysisConfigParentEntity> configParentEntityList) {
        if(configParentEntityList != null && !configParentEntityList.isEmpty()){
            List<AnalysisConfigGroupEntity> groupEntities = configParentEntityList.get(0).getSelectValues();
            if(groupEntities != null && !groupEntities.isEmpty() && !groupEntities.get(0).getValues().isEmpty()) {
                missLocationChecked = groupEntities.get(0).getValues().get(0);
                if(missLocationChecked != null && !StringUtils.isEmpty(missLocationChecked.getId())){
                    SPUtils.putObject(mContext, Constants.MISS_LOCATION, missLocationChecked);
                    analysisPresenter.analysisMiss(missLocationChecked.getId());
                }
            }
        }
    }

    @Override
    public void hotConfigSuccess(List<AnalysisConfigParentEntity> configParentEntityList) {
        if(configParentEntityList != null && !configParentEntityList.isEmpty()){
            List<AnalysisConfigGroupEntity> groupDatas = configParentEntityList.get(0).getSelectValues();
            if(groupDatas != null && !groupDatas.isEmpty() && !groupDatas.get(0).getValues().isEmpty()) {
                if(groupDatas.get(0).getValues().get(0) != null){
                    hotCheckedData = groupDatas.get(0).getValues().get(0).getRuleName();
                }
            }
            if(configParentEntityList.size() > 1){
                List<AnalysisConfigGroupEntity> groupLocation = configParentEntityList.get(1).getSelectValues();
                if(groupLocation != null && !groupLocation.isEmpty() && !groupLocation.get(0).getValues().isEmpty()) {
                    hotLocationChecked  = groupLocation.get(0).getValues().get(0);
                }
            }

            if(!StringUtils.isEmpty(hotCheckedData) && hotLocationChecked != null && !StringUtils.isEmpty(hotLocationChecked.getId())){
                SPUtils.putString(mContext, Constants.ANALYSIS_CHECKED, Constants.HOT_DATA, hotCheckedData);
                SPUtils.putObject(mContext, Constants.HOT_LOCATION, hotLocationChecked);
                analysisPresenter.analysisHot(hotLocationChecked.getId(), hotCheckedData);
            }

        }
    }

    @Override
    public void targetMissConfigSuccess(List<AnalysisConfigParentEntity> configParentEntityList) {

    }

    @Override
    public void targetHotConfigSuccess(List<AnalysisConfigParentEntity> configParentEntityList) {

    }


    private class Coordinate {
        private int X;
        private int Y;
        private String xValue;
        private String yValue;

        public String getxValue() {
            return xValue;
        }

        public void setxValue(String xValue) {
            this.xValue = xValue;
        }

        public String getyValue() {
            return yValue;
        }

        public void setyValue(String yValue) {
            this.yValue = yValue;
        }

        public int getX() {
            return X;
        }

        public void setX(int x) {
            X = x;
        }

        public int getY() {
            return Y;
        }

        public void setY(int y) {
            Y = y;
        }
    }

}
