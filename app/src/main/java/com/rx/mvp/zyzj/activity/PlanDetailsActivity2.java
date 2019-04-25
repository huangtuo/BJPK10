package com.rx.mvp.zyzj.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.common.BundleKey;
import com.rx.mvp.zyzj.entity.resp.PlanConfigEntity;
import com.rx.mvp.zyzj.entity.resp.PlanDetailsEntity2;
import com.rx.mvp.zyzj.fragment.PlanDetailsFragment;
import com.rx.mvp.zyzj.iface.IPlanDetailsView;
import com.rx.mvp.zyzj.presenter.PlanDetailsPresenter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by huangtuo on 2018/7/24.
 */

public class PlanDetailsActivity2 extends BaseActivity implements View.OnClickListener, IPlanDetailsView{

    @BindView(R.id.common_left_photo)
    ImageView imgBack;
    @BindView(R.id.common_title)
    TextView tvTitle;
    @BindView(R.id.common_right_photo)
    ImageView imgSearch;
    @BindView(R.id.planDetails_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;

    private List<PlanConfigEntity> mTitles;
    private List<PlanDetailsFragment> mFragments = new ArrayList<>();
    private PlanAdapter viewPagerAdapter;

    private String planId;
    private String planName;
    public int currentIndex;
    private PlanDetailsPresenter planDetailsPresenter = new PlanDetailsPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_plan_details;
    }

    @Override
    protected void initBundleData() {
        planId = getIntent().getStringExtra(BundleKey.PLAN_ID);
        planName = getIntent().getStringExtra(BundleKey.PLAN_NAME);
    }

    @Override
    protected void initView() {
        tvTitle.setText("计划详情");
        imgBack.setVisibility(View.VISIBLE);
        imgSearch.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(this);
        imgSearch.setOnClickListener(this);

    }

    @Override
    protected void initData() {

        planDetailsPresenter.getUserPlan();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_left_photo:
                finish();
                break;
            case R.id.common_right_photo:
                Intent intent = new Intent(this, SearchFormulaAtivity.class);
                if(mFragments != null && mFragments.size() > currentIndex){
                    intent.putExtra(BundleKey.RESULT_SIZE, mFragments.get(currentIndex).dingSize);
                }
                intent.putExtra(BundleKey.PLAN_ID, planId);
                intent.putExtra(BundleKey.PLAN_NAME, planName);
                startActivity(intent);
                break;

            default:
                break;
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
    public void getPlanDetailsSuccess(PlanDetailsEntity2 planDetailsEntity) {

    }

    @Override
    public void userPlanConfig(List<PlanConfigEntity> planConfigEntityList) {
        mTitles = planConfigEntityList;
        for(int i = 0; i < mTitles.size(); i++){
            if(planId.equals(mTitles.get(i).getId())){
                currentIndex = i;
            }
            mFragments.add(PlanDetailsFragment.newInstance(mTitles.get(i).getId()));
        }
        viewPagerAdapter = new PlanAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);

        initMagicIndicator();
        ViewPagerHelper.bind(magicIndicator, mViewPager);
        mViewPager.setCurrentItem(currentIndex);
    }

    private class PlanAdapter extends FragmentPagerAdapter {
        PlanDetailsFragment currentFragment;

        public PlanAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles == null ? "" : mTitles.get(position).getRuleName();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            currentFragment = (PlanDetailsFragment) object;
            super.setPrimaryItem(container, position, object);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }
//    private class PlanAdapter extends FragmentStatePagerAdapter {
//        PlanDetailsFragment currentFragment;
//
//        public PlanAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragments.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mTitles == null ? "" : mTitles.get(position).getRuleName();
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragments.get(position);
//        }
//
//        @Override
//        public void setPrimaryItem(ViewGroup container, int position, Object object) {
//            currentFragment = (PlanDetailsFragment) object;
//            super.setPrimaryItem(container, position, object);
//        }
//    }
    private void initMagicIndicator(){
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(ContextCompat.getColor(context, R.color.text_color_black03));
                colorTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(context, R.color.text_color_red));
                colorTransitionPagerTitleView.setText(mTitles.get(index).getRuleName());
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        planId = mTitles.get(index).getId();
                        planName = mTitles.get(index).getRuleName();
                        mViewPager.setCurrentItem(index);
                        currentIndex = index;
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(ContextCompat.getColor(context, R.color.text_color_red));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);

                return indicator;
            }

        });
        magicIndicator.setNavigator(commonNavigator);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
