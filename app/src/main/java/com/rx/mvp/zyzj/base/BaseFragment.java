package com.rx.mvp.zyzj.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.activity.BuyAuthorActivity;
import com.rx.mvp.zyzj.activity.LoginActivity;
import com.rx.mvp.zyzj.common.Constants;
import com.rx.mvp.zyzj.entity.resp.LoginServerEntity;
import com.rx.mvp.zyzj.entity.resp.LotteryEntity;
import com.rx.mvp.zyzj.listener.LifeCycleListener;
import com.rx.mvp.zyzj.utils.SPUtils;
import com.rx.mvp.zyzj.utils.SecretKeyUtils;
import com.rx.mvp.zyzj.utils.StringUtils;
import com.rx.mvp.zyzj.widget.LoadingDialog;
import com.rx.mvp.zyzj.widget.NoButShow3SDialog;
import com.rx.mvp.zyzj.widget.OneButtonDialog;
import com.rx.mvp.zyzj.widget.TwoButtonDialog;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 基类Fragment
 * 备注:所有的Fragment都继承自此Fragment
 * 1.规范团队开发
 * 2.统一处理Fragment所需配置,初始化
 *
 * @author ZhongDaFeng
 */
public abstract class BaseFragment extends RxFragment implements EasyPermissions.PermissionCallbacks {

    protected Context mContext;
    protected Unbinder unBinder;
    protected View mView;
    public LifeCycleListener mListener;
    public Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        if (mListener != null) {
            mListener.onAttach(activity);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mListener != null) {
            mListener.onCreate(savedInstanceState);
        }
        mContext = getActivity();
        if (mContext == null) return;
        mView = getContentView();
        unBinder = ButterKnife.bind(this, mView);
        initBundleData();
        initView();
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mListener != null) {
            mListener.onCreateView(inflater, container, savedInstanceState);
        }
        if (mView.getParent() != null) {
            ((ViewGroup) mView.getParent()).removeView(mView);
        }

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mListener != null) {
            mListener.onActivityCreated(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mListener != null) {
            mListener.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getName()); //统计页面("MainScreen"为页面名称，可自定义)
        if (mListener != null) {
            mListener.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getName());
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mListener != null) {
            mListener.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mListener != null) {
            mListener.onDestroyView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy();
        }
        //移除view绑定
        if (unBinder != null) {
            unBinder.unbind();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mListener != null) {
            mListener.onDetach();
        }
    }

    /**
     * 是否已经创建
     *
     * @return
     */
    public boolean isCreated() {
        return mView != null;
    }

    /**
     * 获取显示view
     */
    protected abstract View getContentView();

    /**
     * 获取上一个界面传送过来的数据
     */
    protected abstract void initBundleData();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化Data
     */
    protected abstract void initData();

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
    }

    /**
     * 设置生命周期回调函数
     *
     * @param listener
     */
    public void setOnLifeCycleListener(LifeCycleListener listener) {
        mListener = listener;
    }

    protected void showOneButDialog(String text, String butStr, OneButtonDialog.OnSureButtonClickListener onSureButtonClickListener) {
        if (isResumed()) {
            OneButtonDialog.showDialog(mContext, text, butStr, onSureButtonClickListener);
        }
    }

    protected void showTwoButDialog(String text, String butStr, String cancleStr, TwoButtonDialog.OnSureButtonClickListener onSureButtonClickListener, TwoButtonDialog.OnCancleButtonClickListener onCancleButtonClickListener) {
        if (isResumed()) {
            TwoButtonDialog.showDialog(mContext, text, butStr, cancleStr, onSureButtonClickListener, onCancleButtonClickListener);
        }
    }

    protected void showToast(String toastMsg) {
        if (isResumed()) {
            NoButShow3SDialog.showToast(activity, toastMsg);
        }
    }

    /**
     * loading 运行返回键消失
     */
    protected void showLoding() {
        showLoding(true);
    }

    /**
     * @param isAllowBack 是否点击返回键，让消失
     */
    protected void showLoding(boolean isAllowBack) {
        if (isResumed() && !ZyzjApplication.getApplication().isLoadshowing) {
            ZyzjApplication.getApplication().isLoadshowing = true;
            LoadingDialog.showDialog(mContext, isAllowBack, "加载中...");
        }
    }

    protected void disLoding() {
        if (isResumed()) {
            ZyzjApplication.getApplication().isLoadshowing = false;
            LoadingDialog.disDialog();
        }
    }

    protected boolean isLogin() {
        LoginServerEntity loginServerBean = ZyzjApplication.application.getServiceModel();
        if (loginServerBean == null || StringUtils.isEmpty(loginServerBean.getToken())) {
            return false;
        }
        return true;
    }

    /**
     * 接口返回异常处理方法
     *
     * @param code
     * @param msg
     */
    public void sendFail(int code, String msg) {
        if (code == Constants.TOKEN_OUT) {
            showOneButDialog(msg, "重新登录", new OneButtonDialog.OnSureButtonClickListener() {
                @Override
                public void onSuerButClisk(Dialog dialog) {
                    logout();
                }
            });
        }else if(code == Constants.NO_AUTHOR){
            if(!ZyzjApplication.getApplication().isOpenBuy){
                ZyzjApplication.getApplication().isOpenBuy = true;
                showTwoButDialog(msg, "去购买", "知道了", dialog -> {
                            ZyzjApplication.getApplication().isOpenBuy = false;
                            startActivity(new Intent(mContext, BuyAuthorActivity.class));
                        },
                        dialog -> {
                            ZyzjApplication.getApplication().isOpenBuy = false;
                        });
            }
        } else{
            showToast(msg);
        }
    }

    /**
     * 退出登录
     */
    public void logout() {
        SPUtils.cleanObject(mContext, Constants.USER_INFO + SecretKeyUtils.getToekn());
        SPUtils.cleanObject(mContext, Constants.LOGIN_SERVER);
        ZyzjApplication.application.setServiceModel(null);
        ZyzjApplication.application.setUserInfoBean(null);

        Intent intent = new Intent(activity, LoginActivity.class);
        startActivity(intent);
    }

}
