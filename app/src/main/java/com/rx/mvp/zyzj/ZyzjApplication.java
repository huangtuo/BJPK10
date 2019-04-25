package com.rx.mvp.zyzj;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.rx.mvp.zyzj.activity.LoginActivity;
import com.rx.mvp.zyzj.common.Constants;
import com.rx.mvp.zyzj.entity.resp.LoginServerEntity;
import com.rx.mvp.zyzj.entity.resp.LotteryEntity;
import com.rx.mvp.zyzj.entity.resp.UserInfoEntity;
import com.rx.mvp.zyzj.manager.ActivityStackManager;
import com.rx.mvp.zyzj.utils.LogUtils;
import com.rx.mvp.zyzj.utils.SPUtils;
import com.rx.mvp.zyzj.utils.SecretKeyUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class ZyzjApplication extends MultiDexApplication {
    public static ZyzjApplication application;

    private UserInfoEntity userInfoBean;
    //登录服务信息
    private LoginServerEntity serviceModel;
    public boolean isOpenBuy; //是否弹出去购买框
    public boolean isLoadshowing; //加载框正在显示

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        LogUtils.openLog(BuildConfig.isDebug);
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.openActivityDurationTrack(false);
        //bugly
        CrashReport.initCrashReport(getApplicationContext(), "9dba1b0b4d", true);
    }


    public void exit() {
        ActivityStackManager.getManager().exitApp(this);
    }


    public static ZyzjApplication getApplication() {
        return application;
    }


    public LoginServerEntity getServiceModel() {
        if (this.serviceModel == null) {
            this.serviceModel = (LoginServerEntity) SPUtils.getObject(this.getApplicationContext(), Constants.LOGIN_SERVER);
        }
        return serviceModel;
    }

    public void setServiceModel(LoginServerEntity serviceModel) {
        SPUtils.putObject(this.getApplicationContext(), Constants.LOGIN_SERVER, serviceModel);
        this.serviceModel = serviceModel;
    }

    public UserInfoEntity getUserInfoBean() {
        if (userInfoBean == null) {
            userInfoBean = (UserInfoEntity) SPUtils.getObject(this.getApplicationContext(), Constants.USER_INFO + SecretKeyUtils.getToekn());
        }
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoEntity userInfoBean) {
        SPUtils.putObject(this.getApplicationContext(), Constants.USER_INFO + SecretKeyUtils.getToekn(), userInfoBean);
        this.userInfoBean = userInfoBean;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        Multidex.install(this);
    }

    /**
     * 获取彩种ID
     * @return
     */
    public String getLotteryId() {
        if(getLotteryEntity() != null){
            return getLotteryEntity().getId();
        }else{
            logout();
            return ""; //为空时退出登录
        }
    }
    /**
     * 获取彩种
     * @return
     */
    public LotteryEntity getLotteryEntity() {
        //测试数据
//        LotteryEntity entity = new LotteryEntity();
//        entity.setId("508347ce72564ec580c23ab721d720a6");
//        entity.setName("北京pk拾");
//        entity.setAbbreviation("bjpk10");
//        return entity;

        return (LotteryEntity) SPUtils.getObject(this, Constants.LOTTERY);
    }

    public void setLotteryEntity(LotteryEntity lotteryEntity){
        SPUtils.putObject(this, Constants.LOTTERY, lotteryEntity);
    }

    /**
     * 退出登录
     */
    public void logout() {
        SPUtils.cleanObject(this, Constants.USER_INFO + SecretKeyUtils.getToekn());
        SPUtils.cleanObject(this, Constants.LOGIN_SERVER);
        setServiceModel(null);
        setUserInfoBean(null);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
