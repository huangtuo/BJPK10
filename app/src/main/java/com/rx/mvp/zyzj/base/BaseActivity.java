package com.rx.mvp.zyzj.base;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.activity.BuyAuthorActivity;
import com.rx.mvp.zyzj.activity.LoginActivity;
import com.rx.mvp.zyzj.common.Constants;
import com.rx.mvp.zyzj.entity.resp.LoginServerEntity;
import com.rx.mvp.zyzj.listener.LifeCycleListener;
import com.rx.mvp.zyzj.manager.ActivityStackManager;
import com.rx.mvp.zyzj.utils.SPUtils;
import com.rx.mvp.zyzj.utils.SecretKeyUtils;
import com.rx.mvp.zyzj.utils.StringUtils;
import com.rx.mvp.zyzj.widget.LoadingDialog;
import com.rx.mvp.zyzj.widget.NoButShow3SDialog;
import com.rx.mvp.zyzj.widget.OneButtonDialog;
import com.rx.mvp.zyzj.widget.SystemBarTintManager;
import com.rx.mvp.zyzj.widget.TwoButtonDialog;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 基类Activity
 * 备注:所有的Activity都继承自此Activity
 * 1.规范团队开发
 * 2.统一处理Activity所需配置,初始化
 *
 * @author ZhongDaFeng
 */
public abstract class BaseActivity extends RxFragmentActivity implements EasyPermissions.PermissionCallbacks {

    protected Context mContext;
    protected Unbinder unBinder;
    public LifeCycleListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mListener != null) {
            mListener.onCreate(savedInstanceState);
        }
        ActivityStackManager.getManager().push(this);
        setContentView(getContentViewId());
        mContext = this;
        unBinder = ButterKnife.bind(this);
        initBundleData();
        initView();
        initData();
    }

    protected void initSystemBar(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setNavigationBarTintEnabled(true);
        mTintManager.setTintColor(getResources().getColor(color));
//        mTintManager.setStatusBarTintResource(color);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mListener != null) {
            mListener.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mListener != null) {
            mListener.onRestart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getName()); //手动统计页面("SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this); //统计时长
        if (mListener != null) {
            mListener.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getName()); //手动统计页面("SplashScreen"为页面名称，可自定义)，必须保证 onPageEnd 在 onPause 之前调用，因为SDK会在 onPause 中保存onPageEnd统计到的页面数据。
        MobclickAgent.onPause(this);
        if (mListener != null) {
            mListener.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mListener != null) {
            mListener.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mListener != null) {
            mListener.onDestroy();
        }
        //移除view绑定
        if (unBinder != null) {
            unBinder.unbind();
        }
        ActivityStackManager.getManager().remove(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
    }

    /**
     * 获取显示view的xml文件ID
     */
    protected abstract int getContentViewId();


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

    /**
     * 设置生命周期回调函数
     *
     * @param listener
     */
    public void setOnLifeCycleListener(LifeCycleListener listener) {
        mListener = listener;
    }


    protected void showToast(String toastMsg) {
        if (!isFinishing()) {
            NoButShow3SDialog.showToast(this, toastMsg);
        }
    }

    protected void showToastFinish(String toastMsg, NoButShow3SDialog.OnDismissFinishListener onDismissFinishListener) {
        if (!isFinishing()) {
            NoButShow3SDialog.showToast(this, toastMsg, onDismissFinishListener);
        }
    }

    protected void showOneButDialog(String text, String butStr, OneButtonDialog.OnSureButtonClickListener onSureButtonClickListener) {
        if (!isFinishing()) {
            OneButtonDialog.showDialog(mContext, text, butStr, onSureButtonClickListener);
        }
    }

//    protected void showOneButDialog(String text, String butStr, String title, OneButtonDialog.OnSureButtonClickListener onSureButtonClickListener) {
//        if (!isFinishing()) {
//            OneButtonDialog.showDialog(context, text, butStr, title, onSureButtonClickListener);
//        }
//    }

    protected void showTwoButDialog(String text, String butStr, String cancleStr, TwoButtonDialog.OnSureButtonClickListener onSureButtonClickListener, TwoButtonDialog.OnCancleButtonClickListener onCancleButtonClickListener) {
        if (!isFinishing()) {
            TwoButtonDialog.showDialog(mContext, text, butStr, cancleStr, onSureButtonClickListener, onCancleButtonClickListener);
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
        if (!isFinishing() && !ZyzjApplication.getApplication().isLoadshowing) {
            ZyzjApplication.getApplication().isLoadshowing = true;
            LoadingDialog.showDialog(this, isAllowBack, "加载中...");
        }
    }

    protected void disLoding() {
        if (!isFinishing()) {
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
                            startActivity(new Intent(this, BuyAuthorActivity.class));
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

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
