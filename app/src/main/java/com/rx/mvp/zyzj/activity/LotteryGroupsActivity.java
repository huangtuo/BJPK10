package com.rx.mvp.zyzj.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.adapter.LotteryGroupsAdapter;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.common.Constants;
import com.rx.mvp.zyzj.entity.resp.LotteryGoupsEntity;
import com.rx.mvp.zyzj.entity.resp.LotteryGoupsItemEntity;
import com.rx.mvp.zyzj.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by huangtuo on 2018/7/19.
 */

public class LotteryGroupsActivity extends BaseActivity {

    @BindView(R.id.groups_top)
    RelativeLayout back;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private LotteryGroupsAdapter lotteryGroupsAdapter;

    private List<LotteryGoupsEntity> datas = new ArrayList<LotteryGoupsEntity>();
    private LotteryGoupsItemEntity currentEntity = null;

    private boolean isFisrt = true; //是否是第一次启动


    @Override
    protected int getContentViewId() {
        return R.layout.activity_lottery_groups;
    }

    @Override
    protected void initBundleData() {
        isFisrt = SPUtils.getBoolean(this, Constants.CONFIG, Constants.FISRTENTER);
        currentEntity = (LotteryGoupsItemEntity) SPUtils.getObject(this, Constants.CHECK_PLAN);

        //测试数据
        currentEntity = new LotteryGoupsItemEntity();
        currentEntity.setSID(3);
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        lotteryGroupsAdapter = new LotteryGroupsAdapter(this, currentEntity);
        recyclerView.setAdapter(lotteryGroupsAdapter);

        if(isFisrt){
            back.setVisibility(View.GONE);
        }else{
            back.setVisibility(View.VISIBLE);
        }

        lotteryGroupsAdapter.setCheckedItemListener(entity -> {
            showToast(entity.getCPName());
        });
    }

    @Override
    protected void initData() {
        getDatas();
    }

    private void getDatas() {
        //测试数据
        for(int i = 0; i < 5; i++){
            LotteryGoupsEntity group = new LotteryGoupsEntity();
            List<LotteryGoupsItemEntity> itemList = new ArrayList<LotteryGoupsItemEntity>();
            group.setCPType("" + i);
            for(int j = 0; j < 5; j++){
                LotteryGoupsItemEntity item = new LotteryGoupsItemEntity();
                item.setCPName("时时彩" + i + j);
                item.setNumFormat("123");
                item.setSID(j);
                itemList.add(item);
            }
            group.setCPNames(itemList);
            datas.add(group);
        }

        lotteryGroupsAdapter.appendToList(datas);

    }


    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        if(isFisrt){
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast("再按一次退出程序!");
                exitTime = System.currentTimeMillis();
            } else {
                ZyzjApplication.getApplication().exit();

//            System.gc();
//            finish();
//             System.exit(0);
            }
        }else{
            super.onBackPressed();
        }

    }
}

