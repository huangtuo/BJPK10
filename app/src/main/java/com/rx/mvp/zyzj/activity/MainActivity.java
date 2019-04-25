package com.rx.mvp.zyzj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.base.BaseFragment;
import com.rx.mvp.zyzj.common.BundleKey;
import com.rx.mvp.zyzj.entity.event.SwitchAnalysisEvent;
import com.rx.mvp.zyzj.fragment.AnalysisFragment;
import com.rx.mvp.zyzj.fragment.HistoryFragment;
import com.rx.mvp.zyzj.fragment.MineFragment;
import com.rx.mvp.zyzj.fragment.PlanFragment;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * Created by huangtuo on 2018/7/19.
 */

public class MainActivity extends BaseActivity
        implements View.OnClickListener {


    @BindView(R.id.common_left_text)
    TextView tvTitleLeft;
    @BindView(R.id.common_left_photo)
    ImageView imgTitleLeft;
    @BindView(R.id.common_title)
    TextView tvTitle;
    @BindView(R.id.common_title_image)
    ImageView imgTitle;
    @BindView(R.id.common_right_text)
    TextView tvTitleRight;
    @BindView(R.id.common_right_photo)
    ImageView imgTitleRight;

    @BindView(R.id.main_tabhost)
    FragmentTabHost mTabHost;

    @BindView(R.id.main_content)
    FrameLayout frameLayout;

    private String[] fragmentTag = new String[]{"计划", "分析", "历史", "我的"};
    private Class fragmentArray[] = {PlanFragment.class, AnalysisFragment.class,
            HistoryFragment.class, MineFragment.class};
    private int mImageViewArray[] = {R.drawable.home_plan_select, R.drawable.home_analysis_select,
            R.drawable.home_history_select,
            R.drawable.home_mine_select};
    // 定义一个布局
    private LayoutInflater layoutInflater;


    private int index = 0;
    private String titleString = "北京赛车PK10";
    private int anaylsisFlag = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initBundleData() {
        EventBus.getDefault().register(this);

    }

    @Override
    protected void initView() {
        // 实例化布局对象
        layoutInflater = LayoutInflater.from(this);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.main_content);
        // 得到fragment的个数
        int size = fragmentArray.length;
        for (int i = 0; i < size; i++) {
            View view = getIndicatorView(i);
            // mTabHost.setTag(view.getId(), view);---替换底部图片用到
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(fragmentTag[i]).setIndicator(view);
            // 将Tab按钮添加进Tab选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
        }
        mTabHost.getTabWidget().setDividerDrawable(null);

        mTabHost.setCurrentTab(index);
        setTitleInfo();

    }

    @Override
    protected void initData() {

        imgTitleRight.setOnClickListener(this);
        imgTitleLeft.setOnClickListener(this);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                clickTab(tabId);
            }
        });

    }

    private void clickTab(String tabId) {
        for (int i = 0; i < fragmentTag.length; i++) {
            if (fragmentTag[i].equals(tabId)) {
                index = i;
            }
        }
        setTitleInfo();
    }

    private void setTitleInfo() {
        switch (index) {
            case 1:
                titleString = getResources().getString(R.string.tab_analyze);
                imgTitleRight.setBackgroundResource(R.mipmap.settings);
                imgTitleRight.setVisibility(View.VISIBLE);
                break;
            case 2:
                titleString = getResources().getString(R.string.tab_history);
                imgTitleRight.setVisibility(View.GONE);
                break;
            case 3:
                titleString = getResources().getString(R.string.tab_mine);
                imgTitleRight.setBackgroundResource(R.mipmap.settings);
                imgTitleRight.setVisibility(View.GONE);
                break;
            default:

                if(ZyzjApplication.getApplication().getLotteryEntity() != null){
                    titleString = ZyzjApplication.getApplication().getLotteryEntity().getName();
                }else{
                    titleString = getResources().getString(R.string.app_name);
                }

                String level;
                if(ZyzjApplication.getApplication().getUserInfoBean() != null){
                    level = ZyzjApplication.getApplication().getUserInfoBean().getUserLevel();
                }else{
                    level = "0";
                }
                if("1".equals(level)){
                    titleString = titleString + "-标准版";
                } else if("2".equals(level)){
                    titleString = titleString + "-专业版";
                } else {
                    titleString = titleString + "-预览版";
                }

                imgTitleRight.setVisibility(View.GONE);
                break;
        }
        tvTitle.setText(titleString);
    }

    private View getIndicatorView(int index) {
        View view = layoutInflater.inflate(R.layout.nav_tab, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview_home);
        TextView textView = (TextView) view.findViewById(R.id.textview_home);
        imageView.setImageResource(mImageViewArray[index]);
        textView.setText(fragmentTag[index]);
        view.setTag(index);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.common_right_photo:
                if (index == 1) {
                    Intent intent = new Intent(this, SetAnalysisActivity.class);
                    intent.putExtra(BundleKey.FLAG, anaylsisFlag);
                    startActivity(intent);
                    return;
                }
                if (index == 3) {
//                    CommonHelper.activityToActivity(MainActivity.this, SettingActivity.class);
                    return;
                }
                break;
            case R.id.common_left_photo:
                if (index == 0) {
                    Intent intent = new Intent(this, LotteryGroupsActivity.class);
                    startActivity(intent);
                    return;
                }

                break;

            default:
                break;
        }

    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序!", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            ZyzjApplication.getApplication().exit();

//            System.gc();
//            finish();
//             System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Subscribe
    public void onEvent(SwitchAnalysisEvent event) {
        if(event != null){
            anaylsisFlag = event.getAnalysisFlag();
        }
    }

}
