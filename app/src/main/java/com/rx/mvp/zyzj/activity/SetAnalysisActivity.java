package com.rx.mvp.zyzj.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.adapter.AnalysisTargetAdapter;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.common.BundleKey;
import com.rx.mvp.zyzj.common.Constants;
import com.rx.mvp.zyzj.entity.event.RefreshAnalysisEvent;
import com.rx.mvp.zyzj.entity.event.RestoreRadioEvent;
import com.rx.mvp.zyzj.entity.resp.AnalysisConfigEntity;
import com.rx.mvp.zyzj.entity.resp.AnalysisConfigGroupEntity;
import com.rx.mvp.zyzj.entity.resp.AnalysisConfigParentEntity;
import com.rx.mvp.zyzj.iface.IAnalysisConfigView;
import com.rx.mvp.zyzj.presenter.AnalysisConfigPresenter;
import com.rx.mvp.zyzj.utils.SPUtils;
import com.rx.mvp.zyzj.utils.StringUtils;
import com.rx.mvp.zyzj.widget.flowLayout.FlowLayout;
import com.rx.mvp.zyzj.widget.flowLayout.TagAdapter;
import com.rx.mvp.zyzj.widget.flowLayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

/**
 * Created by huangtuo on 2018/7/26.
 */

public class SetAnalysisActivity extends BaseActivity implements IAnalysisConfigView{
    @BindView(R.id.common_title)
    TextView tvTitle;
    @BindView(R.id.common_left_photo)
    ImageView imgBack;
    @BindView(R.id.common_right_text)
    TextView tvRightTitle;

    @BindView(R.id.analysis_ll_data)
    LinearLayout llData;
    @BindView(R.id.analysis_flow_data)
    TagFlowLayout flowLayoutData;
    @BindView(R.id.analysis_ll_location)
    LinearLayout llLocation;
    @BindView(R.id.analysis_flow_location)
    TagFlowLayout flowLayoutLocation;
    @BindView(R.id.analysis_ll_target)
    LinearLayout llTarget;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private int flag; //0:遗漏分析 1:冷热分析 2:指标遗漏分析 3:指标冷热分析

    private List<AnalysisConfigEntity> dataList ;
    private List<AnalysisConfigEntity> locationList;
    private TagAdapter<AnalysisConfigEntity> dataAdapter;
    private TagAdapter<AnalysisConfigEntity> locationAdapter;
    private AnalysisTargetAdapter targetAdapter;
    private List<AnalysisConfigGroupEntity> targetEntityList; //指标List


    private AnalysisConfigEntity missLocationChecked; //遗漏  已选中位置
    private String hotCheckedData; //冷热 已选中数据
    private AnalysisConfigEntity hotLocationChecked; //冷热 已选中位置
    private AnalysisConfigEntity missTarget; //指标遗漏 已选中的指标
    private String hotTargetDate; //指标冷热： 已选中的数据
    private AnalysisConfigEntity hotTarget; //指标冷热已选中的指标

    private AnalysisConfigPresenter analysisConfigPresenter = new AnalysisConfigPresenter(this, this);
    private int oldRadioIndex;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_set_analysis;
    }

    @Override
    protected void initBundleData() {
        flag = getIntent().getIntExtra(BundleKey.FLAG, 0);
        oldRadioIndex = getIntent().getIntExtra(BundleKey.RADIO_INDEX, 0);

        missLocationChecked = (AnalysisConfigEntity) SPUtils.getObject(this, Constants.MISS_LOCATION);
        hotCheckedData = SPUtils.getString(this, Constants.ANALYSIS_CHECKED, Constants.HOT_DATA);
        hotLocationChecked = (AnalysisConfigEntity) SPUtils.getObject(this, Constants.HOT_LOCATION);
        missTarget = (AnalysisConfigEntity) SPUtils.getObject(this, Constants.MISS_TARGET);
        hotTargetDate = SPUtils.getString(this, Constants.ANALYSIS_CHECKED, Constants.HOT_TARGET_DATA);
        hotTarget = (AnalysisConfigEntity) SPUtils.getObject(this,  Constants.HOT_TARGET);

    }

    @Override
    protected void initView() {

        switch (flag) {
            case 1:
                tvTitle.setText("冷热设置");
                llData.setVisibility(View.VISIBLE);
                llLocation.setVisibility(View.VISIBLE);
                analysisConfigPresenter.getHotConfig();
                break;
            case 2:
                tvTitle.setText("指标遗漏设置");
                llTarget.setVisibility(View.VISIBLE);
                analysisConfigPresenter.getTargetMissConfig();
                break;
            case 3:
                tvTitle.setText("指标冷热设置");
                llData.setVisibility(View.VISIBLE);
                llTarget.setVisibility(View.VISIBLE);
                analysisConfigPresenter.getTargetHotConfig();
                break;
            default:
                tvTitle.setText("遗漏分析");
                llLocation.setVisibility(View.VISIBLE);
                analysisConfigPresenter.getMissConfig();
                break;
        }

        tvRightTitle.setText("确定");
        imgBack.setVisibility(View.VISIBLE);

        tvRightTitle.setOnClickListener(v -> {

            switch (flag) {
                case 1:
                    if(StringUtils.isEmpty(hotCheckedData) || hotLocationChecked == null || StringUtils.isEmpty(hotLocationChecked.getId())){
                        showToast("选择的数据不完整！");
                        return;
                    }
                    SPUtils.putString(this, Constants.ANALYSIS_CHECKED, Constants.HOT_DATA, hotCheckedData);
                    SPUtils.putObject(this, Constants.HOT_LOCATION, hotLocationChecked);
                    EventBus.getDefault().post(new RefreshAnalysisEvent(flag, hotCheckedData, null, hotLocationChecked));

                    break;
                case 2:
                    if(targetAdapter.getCurrentChecked() == null || StringUtils.isEmpty(targetAdapter.getCurrentChecked().getId())){
                        showToast("选择的数据不完整！");
                        return;
                    }

                    missTarget = targetAdapter.getCurrentChecked();
                    SPUtils.putObject(this, Constants.MISS_TARGET, targetAdapter.getCurrentChecked());
                    EventBus.getDefault().post(new RefreshAnalysisEvent(flag, "", missTarget, null));

                    break;
                case 3:
                    if(StringUtils.isEmpty(hotTargetDate) || targetAdapter.getCurrentChecked() == null || StringUtils.isEmpty(targetAdapter.getCurrentChecked().getId())){
                        showToast("选择的数据不完整！");
                        return;
                    }

                    hotTarget = targetAdapter.getCurrentChecked();
                    SPUtils.putString(this, Constants.ANALYSIS_CHECKED, Constants.HOT_TARGET_DATA, hotTargetDate);
                    SPUtils.putObject(this, Constants.HOT_TARGET, targetAdapter.getCurrentChecked());
                    EventBus.getDefault().post(new RefreshAnalysisEvent(flag, hotTargetDate, hotTarget, null));

                    break;
                default:
                    if(missLocationChecked == null || StringUtils.isEmpty(missLocationChecked.getId())){
                        showToast("选择的数据不完整！");
                        return;
                    }
                    SPUtils.putObject(this, Constants.MISS_LOCATION, missLocationChecked);
                    EventBus.getDefault().post(new RefreshAnalysisEvent(flag, "", null, missLocationChecked));

                    break;
            }
            finish();
        });
        imgBack.setOnClickListener(v -> {
            goBack();
        });

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        targetAdapter = new AnalysisTargetAdapter(this);
        recyclerView.setAdapter(targetAdapter);
        if(flag == 2){
            targetAdapter.setCurrentChecked(missTarget);
        }else if(flag == 3){
            targetAdapter.setCurrentChecked(hotTarget);
        }

    }

    @Override
    protected void initData() {

    }

    private void setSelectData(){
        if(flag == 1){ //冷热分析
            if(!StringUtils.isEmpty(hotCheckedData)){
                for(int i = 0; i < dataList.size(); i++){
                    if(hotCheckedData.equals(dataList.get(i).getRuleName())){
                        dataAdapter.setSelectedList(i);
                    }
                }
            }

        }else if(flag == 3){ //冷热指标
            if(!StringUtils.isEmpty(hotTargetDate)){
                for(int i = 0; i < dataList.size(); i++){
                    if(hotTargetDate.equals(dataList.get(i).getRuleName())){
                        dataAdapter.setSelectedList(i);
                    }
                }
            }
        }
    }

    private void initLocationData(){
        //选中位置栏
        flowLayoutLocation.setAdapter(locationAdapter = new TagAdapter<AnalysisConfigEntity>(locationList) {
            @Override
            public View getView(FlowLayout parent, int position, final AnalysisConfigEntity entity) {
                TextView tv = (TextView) LayoutInflater.from(SetAnalysisActivity.this).inflate(R.layout.item_lottery_item,
                        flowLayoutLocation, false);
                tv.setText(entity.getRuleName());

                return tv;
            }

        });

        flowLayoutLocation.setOnSelectListener(selectPosSet -> {
            if(flag == 0){
                if(selectPosSet.size() > 0){ //有选中的数据才清空并添加
//                    missLocationCheckedList.clear();
//                    for (int index : selectPosSet) {
//                        //向选中列表中添加数据， 要先清空
//                        missLocationCheckedList.add(locationList.get(index).getId());
//                    }
                    missLocationChecked = locationList.get(selectPosSet.iterator().next());
                }
            }else if(flag == 1){
                if(selectPosSet.size() > 0){
//                    hotLocationCheckedList.clear();
//                    for (int index : selectPosSet) {
//                        hotLocationCheckedList.add(locationList.get(index).getId());
//                    }
                    hotLocationChecked = locationList.get(selectPosSet.iterator().next());
                }
            }
            setSelectLocation();
        });

        setSelectLocation();
    }

    private void initSelectData(){
        //选中数据栏
        flowLayoutData.setAdapter(dataAdapter = new TagAdapter<AnalysisConfigEntity>(dataList) {
            @Override
            public View getView(FlowLayout parent, int position, final AnalysisConfigEntity entity) {
                TextView tv = (TextView) LayoutInflater.from(SetAnalysisActivity.this).inflate(R.layout.item_lottery_item,
                        flowLayoutData, false);
                if(entity != null)
                    tv.setText(entity.getRuleName());

                return tv;
            }

        });
        //进入页面要选中的数据
        setSelectData();

        flowLayoutData.setOnTagClickListener((view, position, parent) -> { //选中数据

            if(flag == 1){
                hotCheckedData = dataList.get(position).getRuleName();
            }else if(flag == 3){
                hotTargetDate = dataList.get(position).getRuleName();
            }
            setSelectData();
            return false;
        });
    }
    /**
     * 选中 默认已选择的位置
     */
    private void setSelectLocation(){
        Set<Integer> set = new HashSet<>();
        if(flag == 0){
            if(missLocationChecked != null){
                for(int i = 0; i < locationList.size(); i++){
                    if(locationList.get(i).getId().equals(missLocationChecked.getId()))
                        set.add(i);
                }

            }
        }else if(flag == 1){
            if(hotLocationChecked != null){
                for(int i = 0; i < locationList.size(); i++){
                    if(locationList.get(i).getId().equals(hotLocationChecked.getId()))
                        set.add(i);
                }
            }
        }
        locationAdapter.setSelectedList(set);
    }

    private void initTargetData(){

        targetAdapter.setTargetCheckedListener(value -> {
            targetAdapter.setCurrentChecked(value);
            targetAdapter.notifyDataSetChanged();
        });
        targetAdapter.appendToList(targetEntityList);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack(){
        if(flag == 2 && (missTarget == null || StringUtils.isEmpty(missTarget.getId()))){
            EventBus.getDefault().post(new RestoreRadioEvent(oldRadioIndex));
        }else if(flag == 3 && ( (hotTarget == null || StringUtils.isEmpty(hotTarget.getId())) || StringUtils.isEmpty(hotTargetDate))){
            EventBus.getDefault().post(new RestoreRadioEvent(oldRadioIndex));
        }
        finish();
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
    public void missConfigSuccess(List<AnalysisConfigParentEntity> configParentEntityList) {

        if(configParentEntityList != null && !configParentEntityList.isEmpty()){
            List<AnalysisConfigGroupEntity> groupEntities = configParentEntityList.get(0).getSelectValues();
            if(groupEntities != null && !groupEntities.isEmpty()) {
                locationList = groupEntities.get(0).getValues();
                //选中 默认已选择的位置
                initLocationData();
            }
        }

    }

    @Override
    public void hotConfigSuccess(List<AnalysisConfigParentEntity> configParentEntityList) {
        if(configParentEntityList != null && !configParentEntityList.isEmpty()){
            List<AnalysisConfigGroupEntity> groupDatas = configParentEntityList.get(0).getSelectValues();
            if(groupDatas != null && !groupDatas.isEmpty()) {
                dataList = groupDatas.get(0).getValues();
                //选中 默认已选择的数据
                initSelectData();
            }
            if(configParentEntityList.size() > 1){
                List<AnalysisConfigGroupEntity> groupLocation = configParentEntityList.get(1).getSelectValues();
                if(groupLocation != null && !groupLocation.isEmpty()) {
                    locationList = groupLocation.get(0).getValues();
                    //选中 默认已选择的位置
                    initLocationData();
                }
            }
        }
    }

    @Override
    public void targetMissConfigSuccess(List<AnalysisConfigParentEntity> configParentEntityList) {
        if(configParentEntityList != null && !configParentEntityList.isEmpty()){
            targetEntityList = configParentEntityList.get(0).getSelectValues();
            initTargetData();
        }
    }

    @Override
    public void targetHotConfigSuccess(List<AnalysisConfigParentEntity> configParentEntityList) {
        if(configParentEntityList != null && !configParentEntityList.isEmpty()){
            List<AnalysisConfigGroupEntity> groupDatas = configParentEntityList.get(0).getSelectValues();
            if(groupDatas != null && !groupDatas.isEmpty()) {
                dataList = groupDatas.get(0).getValues();
                //选中 默认已选择的数据
                initSelectData();
            }
            if(configParentEntityList.size() > 1){
                targetEntityList = configParentEntityList.get(1).getSelectValues();
                initTargetData();
            }
        }
    }
}
