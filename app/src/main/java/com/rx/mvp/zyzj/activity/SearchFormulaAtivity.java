package com.rx.mvp.zyzj.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.adapter.BottomSelectAdapter;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.common.BundleKey;
import com.rx.mvp.zyzj.common.Constants;
import com.rx.mvp.zyzj.entity.event.RefreshPlan;
import com.rx.mvp.zyzj.entity.resp.PlanDetailsEntity2;
import com.rx.mvp.zyzj.entity.resp.UserInfoEntity;
import com.rx.mvp.zyzj.iface.IFormulaView;
import com.rx.mvp.zyzj.presenter.FormulaPresenter;
import com.rx.mvp.zyzj.utils.PxPipConvert;
import com.rx.mvp.zyzj.utils.SPUtils;
import com.rx.mvp.zyzj.utils.StringUtils;
import com.rx.mvp.zyzj.widget.SeekBarPressure;
import com.rx.mvp.zyzj.widget.TwoButtonDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 搜索公式
 * Created by huangtuo on 2018/7/26.
 * userLevel= 0,1 不可以拖动滚动条
 */

public class SearchFormulaAtivity extends BaseActivity implements IFormulaView{

    @BindView(R.id.common_title)
    TextView tvTitle;
    @BindView(R.id.common_left_photo)
    ImageView imgBack;
    @BindView(R.id.common_right_text)
    TextView tvRightTitle;

    @BindView(R.id.searchFormula_tv_setCode)
    TextView tvSetCode;
    @BindView(R.id.searchFormula_tv_period)
    TextView tvPeriod;
    @BindView(R.id.searchFormula_seek_rate)
    SeekBarPressure seekRate;
    @BindView(R.id.searchFormula_seek_maxRight)
    SeekBarPressure seekMaxRight;
    @BindView(R.id.searchFormula_seek_maxWrong)
    SeekBarPressure seekMaxWrong;
    @BindView(R.id.searchFormula_seek_current)
    SeekBarPressure seekCurrent;


    private List<String> data1 = Arrays.asList(new String[] { "0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100" });
    private List<String> data2 = Arrays.asList(new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });
    private List<String> data3 = Arrays.asList(new String[] { "-10", "-9", "-8", "-7", "-6", "-5", "-4", "-3", "-2", "-1", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });
    private String setCodeStr, periodStr, lowRate, highRate, lowMaxRight, highMaxRight, lowMaxWrong, highMaxWrong, lowCurrent, highCurrent;
    private String planId;
    private String planName;

    private BottomSelectAdapter adapter;
    private List<String> datas = Arrays.asList(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });

    private FormulaPresenter formulaPresenter = new FormulaPresenter(this, this);
    private int codeSize;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search_formula;
    }

    @Override
    protected void initBundleData() {
        planId = getIntent().getStringExtra(BundleKey.PLAN_ID);
        planName = getIntent().getStringExtra(BundleKey.PLAN_NAME);
        codeSize = getIntent().getIntExtra(BundleKey.RESULT_SIZE, 0);

        setCodeStr = SPUtils.getString(this, Constants.SEARCH_FORMULA, planId + Constants.SET_CODE);
        periodStr = SPUtils.getString(this, Constants.SEARCH_FORMULA, planId + Constants.PERIOD_F);


        if(StringUtils.isEmpty(setCodeStr)){ //设置默认选中的定码
            if(planName.contains("普选") || planName.contains("精选") || planName.contains("012")){
                setCodeStr = "1";
            }else{
                setCodeStr = (codeSize <= 10) ? Math.min(4, Math.max(1, codeSize)) + "" : "6";
                if(!datas.contains(setCodeStr)){
                    setCodeStr = "1";
                }
            }

        }
        if(StringUtils.isEmpty(periodStr)){ //设置默认期数
            if(planName.contains("普选") || planName.contains("精选2")){
                periodStr = "4";
            }else if(planName.contains("精选3")){
                periodStr = "8";
            }else if(planName.contains("精选4")){
                periodStr = "10";
            }else if(planName.contains("012")){
                periodStr = "3";
            }else{
                periodStr = "4";
            }

        }

        UserInfoEntity userInfoEntity = ZyzjApplication.getApplication().getUserInfoBean();
        if("0".equals(userInfoEntity.getUserLevel()) || "1".equals(userInfoEntity.getUserLevel())){

        }else{
            lowRate = SPUtils.getString(this, Constants.SEARCH_FORMULA, planId + Constants.LOW_RATE);
            highRate = SPUtils.getString(this, Constants.SEARCH_FORMULA, planId + Constants.HIGH_RATE);
            lowMaxRight = SPUtils.getString(this, Constants.SEARCH_FORMULA, planId + Constants.LOW_RIGHT);
            highMaxRight = SPUtils.getString(this, Constants.SEARCH_FORMULA, planId + Constants.HIGH_RIGHT);
            lowMaxWrong = SPUtils.getString(this, Constants.SEARCH_FORMULA, planId + Constants.LOW_WRONG);
            highMaxWrong = SPUtils.getString(this, Constants.SEARCH_FORMULA, planId + Constants.HIGH_WRONG);
            lowCurrent = SPUtils.getString(this, Constants.SEARCH_FORMULA, planId + Constants.LOW_CURRENT);
            highCurrent = SPUtils.getString(this, Constants.SEARCH_FORMULA, planId + Constants.HIGH_CURRENT);
        }
        if(StringUtils.isEmpty(lowRate)){
            lowRate = "0";
        }
        if(StringUtils.isEmpty(highRate)){
            highRate = "100";
        }
        if(StringUtils.isEmpty(lowMaxRight)){
            lowMaxRight = "0";
        }
        if(StringUtils.isEmpty(highMaxRight)){
            highMaxRight = "10";
        }
        if(StringUtils.isEmpty(lowMaxWrong)){
            lowMaxWrong = "0";
        }
        if(StringUtils.isEmpty(highMaxWrong)){
            highMaxWrong = "10";
        }
        if(StringUtils.isEmpty(lowCurrent)){
            lowCurrent = "-10";
        }
        if(StringUtils.isEmpty(highCurrent)){
            highCurrent = "10";
        }

    }

    @Override
    protected void initView() {
        tvTitle.setText("搜索公式");
        tvRightTitle.setText("提交");
        imgBack.setVisibility(View.VISIBLE);
        tvSetCode.setText(setCodeStr);
        tvPeriod.setText(periodStr);

        tvRightTitle.setOnClickListener(v -> {
            formulaPresenter.submitFormula(planId, setCodeStr, "10", periodStr, lowRate, highRate, lowMaxRight, highMaxRight, lowMaxWrong, highMaxWrong, lowCurrent, highCurrent);
        });
        imgBack.setOnClickListener(v -> {
            finish();
        });
        tvSetCode.setOnClickListener( v -> {
            showPop(1);
        });
        tvPeriod.setOnClickListener( v -> {
            showPop(0);
        });

        seekRate.setOnSeekBarChangeListener((paramSeekBarPressure, paramDouble1, paramDouble2, paramInt1, paramInt2, paramDouble3, paramDouble4) -> {
            lowRate = data1.get(paramInt1);
            highRate = data1.get(paramInt2);
        });
        seekMaxRight.setOnSeekBarChangeListener((paramSeekBarPressure, paramDouble1, paramDouble2, paramInt1, paramInt2, paramDouble3, paramDouble4) -> {
            lowMaxRight = data2.get(paramInt1);
            highMaxRight = data2.get(paramInt2);
        });
        seekMaxWrong.setOnSeekBarChangeListener((paramSeekBarPressure, paramDouble1, paramDouble2, paramInt1, paramInt2, paramDouble3, paramDouble4) -> {
            lowMaxWrong = data2.get(paramInt1);
            highMaxWrong = data2.get(paramInt2);
        });
        seekCurrent.setOnSeekBarChangeListener((paramSeekBarPressure, paramDouble1, paramDouble2, paramInt1, paramInt2, paramDouble3, paramDouble4) -> {
            lowCurrent = data3.get(paramInt1);
            highCurrent = data3.get(paramInt2);
        });

        seekRate.setNoMoveListener(()-> showTipDialog());
        seekMaxRight.setNoMoveListener(()-> showTipDialog());
        seekMaxWrong.setNoMoveListener(()-> showTipDialog());
        seekCurrent.setNoMoveListener(()-> showTipDialog());
    }

    private void showTipDialog(){
        showTwoButDialog("只有专业版才能使用此功能", "去升级", "取消", dialog -> {
            startActivity(new Intent(SearchFormulaAtivity.this, BuyAuthorActivity.class));
        }, dialog -> {});
    }


    @Override
    protected void initData() {
        seekRate.setData(data1);
        seekMaxRight.setData(data2);
        seekMaxWrong.setData(data2);
        seekCurrent.setData(data3);

        setSeekProgress(seekRate, data1, lowRate, highRate);
        setSeekProgress(seekMaxRight, data2, lowMaxRight, highMaxRight);
        setSeekProgress(seekMaxWrong, data2, lowMaxWrong, highMaxWrong);
        setSeekProgress(seekCurrent, data3, lowCurrent, highCurrent);

        adapter = new BottomSelectAdapter(this);

        UserInfoEntity userInfoEntity = ZyzjApplication.getApplication().getUserInfoBean();
        if("0".equals(userInfoEntity.getUserLevel()) || "1".equals(userInfoEntity.getUserLevel())){
            seekRate.setMoveAble(false);
            seekMaxRight.setMoveAble(false);
            seekMaxWrong.setMoveAble(false);
            seekCurrent.setMoveAble(false);
        }
    }

    /**
     * 设置seekBar刻度值
     * @param seek
     * @param data
     * @param low 低值
     * @param high 高值
     */
    private void setSeekProgress(SeekBarPressure seek, List<String> data, String low, String high){
        int lowIndex, highIndex;
        if(data.contains(low)){
            lowIndex = data.indexOf(low);
        }else{
            lowIndex = 0;
        }
        if(data.contains(high)){
            highIndex = data.indexOf(high);
        }else{
            highIndex = data.size() - 1;
        }
        seek.setProgressLowInt(lowIndex);
        seek.setProgressHighInt(highIndex);
    }

    private void showPop(int flag) {
        // TODO Auto-generated method stub
        View view = LayoutInflater.from(this).inflate(
                R.layout.pop_bottom_open_select, null);
        final PopupWindow pop = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, PxPipConvert.convertDip2Px(this, 200), true);
        pop.setAnimationStyle(R.style.popAnimation);
        pop.setFocusable(true);
        pop.setOutsideTouchable(false);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pop.showAtLocation(view, Gravity.BOTTOM, 0, ViewGroup.LayoutParams.MATCH_PARENT);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        adapter.clear();
        adapter.appendToList(datas);


        adapter.setCheckedItemListener(value -> {
            if(flag == 1){
                setCodeStr = value;
                tvSetCode.setText(setCodeStr);
            }else{
                periodStr = value;
                tvPeriod.setText(periodStr);
            }
            pop.dismiss();
        });

        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
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
    public void submitSuccess(PlanDetailsEntity2 planDetailsEntity) {
        SPUtils.putString(this, Constants.SEARCH_FORMULA, planId + Constants.SET_CODE, setCodeStr);
        SPUtils.putString(this, Constants.SEARCH_FORMULA, planId + Constants.PERIOD_F, periodStr);
        SPUtils.putString(this, Constants.SEARCH_FORMULA, planId + Constants.LOW_RATE, lowRate);
        SPUtils.putString(this, Constants.SEARCH_FORMULA, planId + Constants.HIGH_RATE, highRate);
        SPUtils.putString(this, Constants.SEARCH_FORMULA, planId + Constants.LOW_RIGHT, lowMaxRight);
        SPUtils.putString(this, Constants.SEARCH_FORMULA, planId + Constants.HIGH_RIGHT, highMaxRight);
        SPUtils.putString(this, Constants.SEARCH_FORMULA, planId + Constants.LOW_WRONG, lowMaxWrong);
        SPUtils.putString(this, Constants.SEARCH_FORMULA, planId + Constants.HIGH_WRONG, highMaxWrong);
        SPUtils.putString(this, Constants.SEARCH_FORMULA, planId + Constants.LOW_CURRENT, lowCurrent);
        SPUtils.putString(this, Constants.SEARCH_FORMULA, planId + Constants.HIGH_CURRENT, highCurrent);

        EventBus.getDefault().post(new RefreshPlan(planId, planDetailsEntity));
        finish();
    }
}
