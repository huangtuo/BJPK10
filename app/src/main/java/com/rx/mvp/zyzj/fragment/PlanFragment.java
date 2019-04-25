package com.rx.mvp.zyzj.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.activity.BuyAuthorActivity;
import com.rx.mvp.zyzj.activity.PlanDetailsActivity2;
import com.rx.mvp.zyzj.activity.SelectPlanActivity2;
import com.rx.mvp.zyzj.adapter.HomePlanAdapter;
import com.rx.mvp.zyzj.base.BaseFragment;
import com.rx.mvp.zyzj.common.BundleKey;
import com.rx.mvp.zyzj.entity.event.SelectPlanEvent;
import com.rx.mvp.zyzj.entity.resp.HomePlanEntity;
import com.rx.mvp.zyzj.entity.resp.LotteryCurrentInfoEntity;
import com.rx.mvp.zyzj.iface.ILotteryPlanView;
import com.rx.mvp.zyzj.iface.IPlanListView;
import com.rx.mvp.zyzj.presenter.LotteryPlanPresenter;
import com.rx.mvp.zyzj.presenter.PlanListPresenter;
import com.rx.mvp.zyzj.utils.StringUtils;
import com.rx.mvp.zyzj.widget.MyScrollView;
import com.rx.mvp.zyzj.widget.NoScrollListView;
import com.rx.mvp.zyzj.widget.SnapUpCountDownTimerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

/**
 * Created by huangtuo on 2018/7/19.
 */

public class PlanFragment extends BaseFragment implements View.OnClickListener, ILotteryPlanView, IPlanListView {

    @BindView(R.id.plan_scroll)
    MyScrollView scrollView;
    @BindView(R.id.plan_tv_old_period)
    TextView tvOldPeriod;
    @BindView(R.id.plan_ll_numbers)
    LinearLayout llNumbers;
    @BindView(R.id.plan_tv_countdown)
    TextView tvPeriodCountdown;
    @BindView(R.id.plan_countDownTimerView)
    SnapUpCountDownTimerView countDownTimerView;
    @BindView(R.id.plan_ll_buy_accredit)
    LinearLayout llBuyAccredit;
    @BindView(R.id.plan_ll_change_formula)
    LinearLayout llChangeFormula;
    @BindView(R.id.plan_ll_change_plan)
    LinearLayout llChangePlan;
    @BindView(R.id.recyclerView)
    NoScrollListView recyclerView;

    private HomePlanAdapter adapter;

    private boolean isTimerFinish; //是否已倒计时完成
    private String nextPeriod; //下一期
    private int width;

    private LotteryPlanPresenter lotteryPlanPresenter = new LotteryPlanPresenter(this, this);
    private PlanListPresenter planListPresenter = new PlanListPresenter(this, this);

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_plan, null);
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

        adapter = new HomePlanAdapter(activity);
        recyclerView.setAdapter(adapter);

        llBuyAccredit.setOnClickListener(this);
        llChangeFormula.setOnClickListener(this);
        llChangePlan.setOnClickListener(this);


        countDownTimerView.setTimerFinish( () ->{
            isTimerFinish = true;
            tvPeriodCountdown.setText("第" + nextPeriod + "期正在开奖中...");
            countDownTimerView.setVisibility(View.GONE);
            delayedGetData();
        });

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, 0);
            }
        });

        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, PlanDetailsActivity2.class);
                intent.putExtra(BundleKey.PLAN_ID,  adapter.getList().get(position).getLotteryPlanId());
                intent.putExtra(BundleKey.PLAN_NAME,  adapter.getList().get(position).getLotteryPlanName());
                startActivity(intent);
            }
        });



    }

    Handler handler = new Handler();

    /**
     * 延时10秒 重新获取数据
     */
    private void delayedGetData(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getNewData();
            }
        }, 10000);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getNewData();
    }

    private void getNewData(){
        lotteryPlanPresenter.getCurrentLottery();
        planListPresenter.getHomePlans();
    }

    private void setCurrentLottery(LotteryCurrentInfoEntity lotteryCurrentInfoEntity) {

        if(!StringUtils.isEmpty(lotteryCurrentInfoEntity.getPopMsg())){
            if(!ZyzjApplication.getApplication().isOpenBuy && ZyzjApplication.getApplication().getUserInfoBean() != null
                    && "0".equals(ZyzjApplication.getApplication().getUserInfoBean().getUserLevel())){
                ZyzjApplication.getApplication().isOpenBuy = true;
                showTwoButDialog(lotteryCurrentInfoEntity.getPopMsg(), "去购买", "知道了", dialog -> {
                            ZyzjApplication.getApplication().isOpenBuy = false;
                            startActivity(new Intent(activity, BuyAuthorActivity.class));
                        },
                        dialog -> {
                            ZyzjApplication.getApplication().isOpenBuy = false;
                        });
            }
        }

        List<String> numbers = lotteryCurrentInfoEntity.getResults();
        if(!"2".equals(lotteryCurrentInfoEntity.getStatus())){ //不是已开奖状态
            isTimerFinish = false;
        }else{//已开奖
            isTimerFinish = true;
        }

        llNumbers.removeAllViews();
        int size = numbers.size();

        int codeWH = (width-20) / 10 - 4;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                codeWH, codeWH);
        params.setMargins(2,2,2,2);
        LinearLayout.LayoutParams params02 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params02.setMargins(10,50,10,0);
        llNumbers.setLayoutParams(params02);

        for (int i = 0; i < size; i++) {
            TextView tvNumber = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_period_numbers, null);
            tvNumber.setText(numbers.get(i));
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            tvNumber.setLayoutParams(params);
            llNumbers.addView(tvNumber);

        }
        nextPeriod = lotteryCurrentInfoEntity.getNextIssue();
        tvOldPeriod.setText("第" + lotteryCurrentInfoEntity.getCurrentIssue() + "期开奖");
        if(!isTimerFinish){
            tvPeriodCountdown.setText("第" + nextPeriod + "期开奖倒计时");
            countDownTimerView.setVisibility(View.VISIBLE);
            countDownTimerView.setSecond(lotteryCurrentInfoEntity.getNextOpenRestTime());
            countDownTimerView.start();
        }else{
            tvPeriodCountdown.setText("第" + nextPeriod + "期正在开奖中...");
            countDownTimerView.setVisibility(View.GONE);
            delayedGetData();
        }

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (countDownTimerView != null) {
            countDownTimerView.stop();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plan_ll_buy_accredit:
                startActivity(new Intent(mContext, BuyAuthorActivity.class));
                break;
            case R.id.plan_ll_change_formula:
                planListPresenter.refreshPlan();
                break;
            case R.id.plan_ll_change_plan:
                startActivity(new Intent(mContext, SelectPlanActivity2.class));
                break;
            default:
                break;
        }
    }
    @Subscribe
    public void onEvent(SelectPlanEvent event) {
        if(event != null){
            planListPresenter.getHomePlans();
        }
    }

    @Override
    public void showLoading() {
        disLoding();
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
    public void getCurrentLottery(LotteryCurrentInfoEntity lotteryCurrentInfoEntity) {
        if(lotteryCurrentInfoEntity != null)
            setCurrentLottery(lotteryCurrentInfoEntity);
    }

    @Override
    public void getPlanListSuccess(List<HomePlanEntity> homePlanEntityList) {
        adapter.clear();
        adapter.appendToList(homePlanEntityList);
    }

    @Override
    public void refreshPlanSuccess(List<HomePlanEntity> homePlanEntityList) {
        adapter.clear();
        adapter.appendToList(homePlanEntityList);
    }
}
