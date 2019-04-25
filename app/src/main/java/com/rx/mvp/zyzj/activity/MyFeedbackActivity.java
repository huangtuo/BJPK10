package com.rx.mvp.zyzj.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.base.widget.pulltorefresh.PullToRefreshBase;
import com.android.base.widget.pulltorefresh.PullToRefreshListView;
import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.adapter.MyFeedbackAdapter;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.common.BundleKey;
import com.rx.mvp.zyzj.common.Constants;
import com.rx.mvp.zyzj.entity.event.RefreshFeedbackEvent;
import com.rx.mvp.zyzj.entity.resp.MyFeedbackEntity;
import com.rx.mvp.zyzj.iface.IMyFeedbackView;
import com.rx.mvp.zyzj.presenter.MyFeedbackPresenter;
import com.rx.mvp.zyzj.utils.PxPipConvert;
import com.rx.mvp.zyzj.utils.WalleteViewUtil;
import com.rx.mvp.zyzj.widget.RecycleViewDivider;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by huangtuo on 2018/7/30.
 */

public class MyFeedbackActivity extends BaseActivity implements IMyFeedbackView{
    @BindView(R.id.feedback_img_back)
    ImageView imgBack;
    @BindView(R.id.feedback_ll_rightTitle)
    LinearLayout rightTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.feedback_tv_null)
    TextView tvNull;

    private MyFeedbackAdapter adapter;
    private List<MyFeedbackEntity> feedbackList;
    private MyFeedbackPresenter myFeedbackPresenter = new MyFeedbackPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_feedback;
    }

    @Override
    protected void initBundleData() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        imgBack.setOnClickListener(v -> {
            finish();
        });
        rightTitle.setOnClickListener(v -> {
            startActivity(new Intent(this, FeedbackEditActivity.class));
        });

        feedbackList = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new MyFeedbackAdapter(this);
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, PxPipConvert.convertDip2Px(this,1), WalleteViewUtil.getColor(this,R.color.divider_line)));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickLinstener(entity -> {
            if(entity != null){
                Intent intent = new Intent(MyFeedbackActivity.this, FeedbackDetailsActivity.class);
                intent.putExtra(BundleKey.OPINION_ID, entity.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        myFeedbackPresenter.getMyFeedback();
    }

    private void setData(){
        if(feedbackList.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            adapter.appendToList(feedbackList);
            tvNull.setVisibility(View.GONE);
        }else{
            recyclerView.setVisibility(View.GONE);
            tvNull.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(RefreshFeedbackEvent event) {
        myFeedbackPresenter.getMyFeedback();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void onError(int code, String msg) {

    }

    @Override
    public void getMyFeedbackSuccess(List<MyFeedbackEntity> myFeedbackList) {
        feedbackList.clear();
        adapter.clear();
        if(myFeedbackList != null){
            feedbackList = myFeedbackList;
            setData();
        }
    }

    @Override
    public void submitFeedbackSuccess(String msg) {

    }

    @Override
    public void getFeedbackDetails(MyFeedbackEntity myFeedbackEntity) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getName()); //手动统计页面("SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this); //统计时长
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getName()); //手动统计页面("SplashScreen"为页面名称，可自定义)，必须保证 onPageEnd 在 onPause 之前调用，因为SDK会在 onPause 中保存onPageEnd统计到的页面数据。
        MobclickAgent.onPause(this);
    }
}
