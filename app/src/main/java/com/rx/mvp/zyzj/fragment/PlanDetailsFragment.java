package com.rx.mvp.zyzj.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.base.widget.NoScrollListview;
import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.activity.BuyAuthorActivity;
import com.rx.mvp.zyzj.activity.PlanDetailsActivity2;
import com.rx.mvp.zyzj.adapter.PlanDetailsAdapter;
import com.rx.mvp.zyzj.base.BaseFragment;
import com.rx.mvp.zyzj.entity.event.RefreshPlan;
import com.rx.mvp.zyzj.entity.resp.LotteryCurrentInfoEntity;
import com.rx.mvp.zyzj.entity.resp.PlanConfigEntity;
import com.rx.mvp.zyzj.entity.resp.PlanDetailsEntity2;
import com.rx.mvp.zyzj.entity.resp.PlanDetailsGuessEntity2;
import com.rx.mvp.zyzj.iface.ILotteryPlanView;
import com.rx.mvp.zyzj.iface.IPlanDetailsView;
import com.rx.mvp.zyzj.presenter.LotteryPlanPresenter;
import com.rx.mvp.zyzj.presenter.PlanDetailsPresenter;
import com.rx.mvp.zyzj.utils.StringUtils;
import com.rx.mvp.zyzj.widget.MyScrollView;
import com.rx.mvp.zyzj.widget.SnapUpCountDownTimerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

/**
 * Created by huangtuo on 2018/7/24.
 */

public class PlanDetailsFragment extends BaseFragment implements IPlanDetailsView, ILotteryPlanView {
    @BindView(R.id.planDetails_scroll)
    MyScrollView scrollView;
    @BindView(R.id.planDetails_tv_old_period)
    TextView tvOldPeriod;
    @BindView(R.id.planDetails_ll_numbers)
    LinearLayout llOldNumbers;
    @BindView(R.id.planDetails_tv_periodArea)
    TextView tvPeriodArea;
    @BindView(R.id.planDetails_tv_guess)
    TextView tvGuess;
    @BindView(R.id.planDetals_countDownTimerView)
    SnapUpCountDownTimerView countDownTimerView;
    @BindView(R.id.planDetails_tv_opening)
    TextView tvOpening;
    @BindView(R.id.planDetails_tv_countdownPeriod)
    TextView tvCountdownPeriod;
    @BindView(R.id.planDetails_tv_index)
    TextView tvIndexType;
    @BindView(R.id.planDetails_tv_precisionRate)
    TextView tvPrecisionRate;
    @BindView(R.id.planDetails_tv_maxRight)
    TextView tvMaxRight;
    @BindView(R.id.planDetails_tv_maxWrong)
    TextView tvMaxWrong;
    @BindView(R.id.planDetails_tv_currentRorW)
    TextView tvCurrentRorw;
    @BindView(R.id.planDetails_listview)
    NoScrollListview listView;

    private boolean isTimerFinish; //是否已倒计时完成

    private PlanDetailsAdapter adapter;
    private static final String bundleKey = "planId";
    private String planId;
    LinearLayout.LayoutParams params;
    public int dingSize = 0; //定码个数长度

    private LotteryPlanPresenter lotteryPlanPresenter = new LotteryPlanPresenter(this, this);
    private PlanDetailsPresenter planDetailsPresenter = new PlanDetailsPresenter(this, this);

    public static PlanDetailsFragment newInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(bundleKey, id);
        PlanDetailsFragment fragment = new PlanDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_plan_details, null);
    }

    @Override
    protected void initBundleData() {
        EventBus.getDefault().register(this);
        Bundle arguments = getArguments();
        planId = arguments.getString(bundleKey);

    }

    @Override
    protected void initView() {

        adapter = new PlanDetailsAdapter(mContext);
        listView.setAdapter(adapter);

        params = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

        countDownTimerView.setTimerFinish(() -> {
            isTimerFinish = true;
            setCountDownTimerView(true, 0);

        });
    }

    @Override
    protected void initData() {
        getNewData();
    }

    @Override
    public void onResume() {
        super.onResume();
        listView.setFocusable(false);
    }

    private void setCountDownTimerView(boolean isTimerFinsh, int times) {
        //倒计时
        if (isTimerFinsh) {//结束， activity去刷新数据
            tvOpening.setVisibility(View.VISIBLE);
            countDownTimerView.setVisibility(View.GONE);
            delayedGetData();
        } else {
            countDownTimerView.setSecond(times);
            tvOpening.setVisibility(View.GONE);
            countDownTimerView.setVisibility(View.VISIBLE);
            countDownTimerView.start();
        }
    }

    @Subscribe
    public void onEvent(RefreshPlan event) {
        if(event != null && event.getPlanDetailsEntity() != null){
            if(planId.equals(event.getPlanId()))
                getPlanDetailsSuccess(event.getPlanDetailsEntity());
        }

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
    public void getCurrentLottery(LotteryCurrentInfoEntity lotteryCurrentInfoEntity) {
        if (lotteryCurrentInfoEntity != null)
            setCurrentLottery(lotteryCurrentInfoEntity);
    }


    @Override
    public void getPlanDetailsSuccess(PlanDetailsEntity2 planDetailsEntity) {
        if (planDetailsEntity != null) {
            //设置猜测数据
            tvPrecisionRate.setText(StringUtils.getTwoDecimals(planDetailsEntity.getCorrectRatio() * 100 + "") + "%");
            tvMaxRight.setText(planDetailsEntity.getContinuousCorrect());
            tvMaxWrong.setText(planDetailsEntity.getContinuousWrong());
            tvCurrentRorw.setText(planDetailsEntity.getCurrentContinuous());

            List<PlanDetailsGuessEntity2> guessEntityList = planDetailsEntity.getResults();
            if(guessEntityList != null && guessEntityList.size() > 0){
                //第一个是顶部数据
                PlanDetailsGuessEntity2 headPlanDetail = guessEntityList.get(0);
                if(headPlanDetail.getGuessResult() != null)
                    dingSize = headPlanDetail.getGuessResult().size();
                //设置顶部猜测数据
                if(headPlanDetail != null){
                    tvPeriodArea.setText(headPlanDetail.getStartIssue() + "-" + headPlanDetail.getEndIssue() + "期");

                    //预期数字
                    List<List<String>> groupGuess = headPlanDetail.getGuessResult();
                    if(groupGuess != null){
                        StringBuilder sb = new StringBuilder();
                        for(List<String> childGuess : groupGuess){
                            for(String unit : childGuess){
                                sb.append(unit + "-");
                            }
                            //去掉最后一个 “-”
                            if(sb.length() > 1)
                                sb.setLength(sb.length() - 1);

                            sb.append(" ");
                        }
                        tvGuess.setText(sb.toString());
                    }

                    tvIndexType.setText(headPlanDetail.getCorrectIndex());

                }

                guessEntityList.remove(0);
                adapter.clear();
                adapter.appendToList(guessEntityList);

            }
        }
    }

    @Override
    public void userPlanConfig(List<PlanConfigEntity> planConfigEntityList) {

    }

    private void setCurrentLottery(LotteryCurrentInfoEntity lotteryCurrentInfoEntity) {
        if(llOldNumbers == null){
            return;
        }
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

        llOldNumbers.removeAllViews();
        int length = numbers.size();
        for (int i = 0; i < length; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_plan_details_nums, null);
            TextView tvNumber = (TextView) view.findViewById(R.id.period_tv_number);
            View line = view.findViewById(R.id.period_v_line);
            tvNumber.setText(numbers.get(i));
            if (i == length - 1) {
                line.setVisibility(View.GONE);
            }

            view.setLayoutParams(params);
            llOldNumbers.addView(view);
        }
        tvOldPeriod.setText("第" + lotteryCurrentInfoEntity.getCurrentIssue() + "期开奖");

        if (!isTimerFinish) {
            countDownTimerView.setVisibility(View.VISIBLE);
            countDownTimerView.setSecond(lotteryCurrentInfoEntity.getNextOpenRestTime());
            countDownTimerView.start();
            tvOpening.setVisibility(View.GONE);
        } else {
            countDownTimerView.setVisibility(View.GONE);
            tvOpening.setVisibility(View.VISIBLE);
            delayedGetData();
        }

        tvCountdownPeriod.setText(lotteryCurrentInfoEntity.getNextIssue() + "期");
    }

    Handler handler = new Handler();

    /**
     * 延时10秒 重新获取数据
     */
    private void delayedGetData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getNewData();
            }
        }, 10000);
    }

    private void getNewData() {
        planDetailsPresenter.getDetails(planId);
        lotteryPlanPresenter.getCurrentLottery();

    }

}
