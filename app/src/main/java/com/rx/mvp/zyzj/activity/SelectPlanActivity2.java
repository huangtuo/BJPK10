package com.rx.mvp.zyzj.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.base.widget.NoScrollListview;
import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.adapter.SelectPlanAdapter;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.entity.event.SelectPlanEvent;
import com.rx.mvp.zyzj.entity.resp.PlanConfigEntity;
import com.rx.mvp.zyzj.entity.resp.PlanConfigGroupEntity;
import com.rx.mvp.zyzj.iface.IPlanConfigView;
import com.rx.mvp.zyzj.presenter.PlanConfigPresenter;
import com.rx.mvp.zyzj.widget.flowLayout.FlowLayout;
import com.rx.mvp.zyzj.widget.flowLayout.TagAdapter;
import com.rx.mvp.zyzj.widget.flowLayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by huangtuo on 2018/7/27.
 */

public class SelectPlanActivity2 extends BaseActivity implements IPlanConfigView {

    @BindView(R.id.common_title)
    TextView tvTitle;
    @BindView(R.id.common_left_photo)
    ImageView imgBack;
    @BindView(R.id.common_right_text)
    TextView tvRightTitle;

    @BindView(R.id.selectPlan_img_clear)
    ImageView imgClear;
    @BindView(R.id.selectPlan_tv_null)
    TextView tvNull;
    @BindView(R.id.selectPlan_flowLayout)
    TagFlowLayout tagFlowLayout;
    @BindView(R.id.selectPlan_listview)
    NoScrollListview listview;

    private SelectPlanAdapter selectPlanAdapter;
    private List<PlanConfigGroupEntity> listPlan = new ArrayList<>();
    private List<PlanConfigEntity> checkedList = new ArrayList<>();
    private TagAdapter<PlanConfigEntity> checkedAdapter;

    private PlanConfigPresenter planConfigPresenter = new PlanConfigPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_select_plan;
    }

    @Override
    protected void initBundleData() {
//        checkedList = (List<PlanConfigEntity>) SPUtils.getObject(this, BundleKey.SELECT_PLANS);
//        if(checkedList== null)
//            checkedList = new ArrayList<>();


    }

    @Override
    protected void initView() {
        tvTitle.setText("选择计划");
        imgBack.setOnClickListener(v -> {
            finish();
        });
        imgBack.setVisibility(View.VISIBLE);
        tvRightTitle.setText("确定");

        tvRightTitle.setOnClickListener(v -> {
            if (checkedList.isEmpty()) {
                showToast("选择的数据不完整！");
            } else {
//                SPUtils.putObject(this, BundleKey.SELECT_PLANS, checkedList);
                String level;
                if(ZyzjApplication.getApplication().getUserInfoBean() != null){
                    level = ZyzjApplication.getApplication().getUserInfoBean().getUserLevel();
                }else{
                    level = "0";
                }
                if("1".equals(level) && checkedList.size() > 10){ //标准版最多十条
                    showTwoButDialog("标准版最多能选10条计划，请升级专业版！", "去升级", "取消", dialog -> {
                        startActivity(new Intent(SelectPlanActivity2.this, BuyAuthorActivity.class));
                    }, dialog -> {});
                } else if("2".equals(level) && checkedList.size() > 20){
                    showOneButDialog("专业版最多能选20条计划，请重新选择！", "我知道了", dialog -> {});
                } else{
                    StringBuilder sb = new StringBuilder();
                    for (PlanConfigEntity entity : checkedList) {
                        sb.append(entity.getId() + ",");
                    }
                    if (sb.length() > 0) {
                        sb.setLength(sb.length() - 1);
                    }
                    planConfigPresenter.updatePlan(sb.toString());
                }
            }
        });
        imgClear.setOnClickListener(v -> {
            checkedList.clear();
            checkedAdapter.setmTagDatas(checkedList);
            selectPlanAdapter.setCheckedList(checkedList);
            tvNull.setVisibility(View.VISIBLE);
        });

    }

    @Override
    protected void initData() {
        planConfigPresenter.getUserPlan();

        selectPlanAdapter = new SelectPlanAdapter(this);
        listview.setAdapter(selectPlanAdapter);

        selectPlanAdapter.setSelectCheckedListener((isChecked, value) -> {
            if (isChecked) {
                for (PlanConfigEntity entity : checkedList) {
                    if (entity.getId().equals(value.getId())) {
                        value = entity;
                    }
                }
                if (!checkedList.contains(value))
                    checkedList.add(value);
            } else {
                for (PlanConfigEntity entity : checkedList) {
                    if (entity.getId().equals(value.getId())) {
                        value = entity;
                    }
                }
                if (checkedList.contains(value))
                    checkedList.remove(value);
            }

            checkedAdapter.notifyDataChanged();
            if (checkedList.isEmpty()) {
                tvNull.setVisibility(View.VISIBLE);
            } else {
                tvNull.setVisibility(View.GONE);
            }
        });
    }

    private void setCheckedData() {
        if (checkedList.isEmpty()) {
            tvNull.setVisibility(View.VISIBLE);
        } else {
            tvNull.setVisibility(View.GONE);
        }
        selectPlanAdapter.setCheckedList(checkedList);

        //选中数据栏
        tagFlowLayout.setAdapter(checkedAdapter = new TagAdapter<PlanConfigEntity>(checkedList) {
            @Override
            public View getView(FlowLayout parent, int position, final PlanConfigEntity entity) {
                TextView tv = (TextView) LayoutInflater.from(SelectPlanActivity2.this).inflate(R.layout.item_lottery_item,
                        tagFlowLayout, false);
                if (entity != null)
                    tv.setText(entity.getRuleName());
                return tv;
            }
        });

        tagFlowLayout.setOnTagClickListener((view, position, parent) -> { //选中数据
            PlanConfigEntity value = checkedList.get(position);
//            List<PlanConfigEntity> lists = new ArrayList<>();
//            lists.addAll(checkedList);
            for (PlanConfigEntity entity : checkedList) {
                if (entity.getId().equals(value.getId())) {
                    value = entity;
                }
            }
            checkedList.remove(value);
            checkedAdapter.notifyDataChanged();
            if (checkedList.isEmpty()) {
                tvNull.setVisibility(View.VISIBLE);
            }
            selectPlanAdapter.setCheckedList(checkedList);
            return false;
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
    public void userPlanConfig(List<PlanConfigEntity> planConfigEntityList) {
        if (planConfigEntityList != null) {
            checkedList = planConfigEntityList;
            setCheckedData();
            planConfigPresenter.getAllPlan();
        }

    }

    @Override
    public void allPlanConfig(List<PlanConfigGroupEntity> planConfigGroupEntityList) {
        listPlan = planConfigGroupEntityList;
        selectPlanAdapter.appendToList(listPlan);
    }

    @Override
    public void updatePlanConfig() {
        EventBus.getDefault().post(new SelectPlanEvent());
        finish();
    }
}
