package com.rx.mvp.zyzj.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.activity.AboutActivity;
import com.rx.mvp.zyzj.activity.BuyAuthorActivity;
import com.rx.mvp.zyzj.activity.LoadingActivity;
import com.rx.mvp.zyzj.activity.MyAuthorActivity;
import com.rx.mvp.zyzj.activity.MyFeedbackActivity;
import com.rx.mvp.zyzj.activity.UpdatePswActivity;
import com.rx.mvp.zyzj.base.BaseFragment;
import com.rx.mvp.zyzj.common.BundleKey;
import com.rx.mvp.zyzj.entity.resp.UpdateEntity;
import com.rx.mvp.zyzj.entity.resp.UserInfoEntity;
import com.rx.mvp.zyzj.iface.IUpdateVersionView;
import com.rx.mvp.zyzj.iface.IUserInfoView;
import com.rx.mvp.zyzj.presenter.UpdateVersionPresenter;
import com.rx.mvp.zyzj.presenter.UserInfoPresenter;
import com.rx.mvp.zyzj.utils.StringUtils;
import com.rx.mvp.zyzj.utils.ToastUtils;
import com.rx.mvp.zyzj.utils.UpdateUtils;
import com.rx.mvp.zyzj.utils.VersionUtil;
import com.rx.mvp.zyzj.widget.CircleImageView;
import com.rx.mvp.zyzj.widget.TwoButtonDialog;

import butterknife.BindView;

/**
 * Created by huangtuo on 2018/7/19.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener, IUserInfoView, IUpdateVersionView {
    @BindView(R.id.mine_img_head)
    CircleImageView imgHead;
    @BindView(R.id.mine_tv_userName)
    TextView tvUserName;
    @BindView(R.id.mine_ll_buy)
    LinearLayout llBuy;
    @BindView(R.id.mine_ll_author)
    LinearLayout llAuthor;
    @BindView(R.id.mine_ll_psw)
    LinearLayout llPsw;
    @BindView(R.id.mine_ll_feedback)
    LinearLayout llFeedback;
    @BindView(R.id.mine_ll_about)
    LinearLayout llAbout;
    @BindView(R.id.mine_ll_update)
    LinearLayout llUpdate;
    @BindView(R.id.mine_btn_logout)
    Button btnLogout;

    private UserInfoPresenter userInfoPresenter = new UserInfoPresenter(this, this);
    private UpdateVersionPresenter updateVersionPresenter = new UpdateVersionPresenter(this, this);
    public final static int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 9999;

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_mine, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        llBuy.setOnClickListener(this);
        llAuthor.setOnClickListener(this);
        llPsw.setOnClickListener(this);
        llFeedback.setOnClickListener(this);
        llAbout.setOnClickListener(this);
        llUpdate.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_ll_buy:
                startActivity(new Intent(mContext, BuyAuthorActivity.class));
                break;
            case R.id.mine_ll_author:

                startActivity(new Intent(mContext, MyAuthorActivity.class));
                break;
            case R.id.mine_ll_psw:
                startActivity(new Intent(mContext, UpdatePswActivity.class));
                break;
            case R.id.mine_ll_feedback:
                startActivity(new Intent(mContext, MyFeedbackActivity.class));
                break;
            case R.id.mine_ll_about:
                startActivity(new Intent(mContext, AboutActivity.class));
                break;
            case R.id.mine_ll_update:
                updateVersionPresenter.getUpdateVersion(VersionUtil.getVersionName(activity));
                break;
            case R.id.mine_btn_logout:
                userInfoPresenter.logout();
                //本地先退出
                logout();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        userInfoPresenter.getUserInfo();
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
    public void logoutSuccess(String msg) {

    }

    @Override
    public void getUserInfo(UserInfoEntity entity) {
        if(entity != null){
            tvUserName.setText(entity.getUserName());
        }
    }

    @Override
    public void getUpdateSuccess(UpdateEntity updateEntity) {
        if(updateEntity != null){
            showDownDialog(updateEntity);
        }
    }
    private void showDownDialog(final UpdateEntity model) {
        //返回的 appVersion不为空、url不为空、 appVersion与本地版本不相同 才去弹出更新
        if (!StringUtils.isEmpty(model.getAppVersion()) && !StringUtils.isEmpty(model.getUrl()) && !model.getAppVersion().equals(VersionUtil.getVersionName(activity))) {
            TwoButtonDialog twoButtonDialog = new TwoButtonDialog(activity);
            if ("1".equals(model.getForceUpdate())) {
                twoButtonDialog.showDialog(activity, model.getUpdateContent() + "", "立即更新", "退出", new TwoButtonDialog.OnSureButtonClickListener() {
                    @Override
                    public void onSuerButClisk(Dialog dialog) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                //申请WRITE_EXTERNAL_STORAGE权限
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                                return;
                            } else {
                                setupDownloadThread(model.getUrl());

                            }
                        } else {
                            setupDownloadThread(model.getUrl());
                        }

                    }
                }, new TwoButtonDialog.OnCancleButtonClickListener() {
                    @Override
                    public void onCancleButClisk(Dialog dialog) {
                        ZyzjApplication.application.exit();
                    }
                }, true);
            } else {
                twoButtonDialog.showDialog(activity, model.getUpdateContent() + "", "立即更新", "取  消", new TwoButtonDialog.OnSureButtonClickListener() {
                    @Override
                    public void onSuerButClisk(Dialog dialog) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                //申请WRITE_EXTERNAL_STORAGE权限
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);

                                return;
                            } else {
                                setupDownloadThread(model.getUrl());
                            }
                        } else {
                            setupDownloadThread(model.getUrl());
                        }
                    }
                }, new TwoButtonDialog.OnCancleButtonClickListener() {
                    @Override
                    public void onCancleButClisk(Dialog dialog) {
                    }
                }, true);
            }
        }else {
            showToast("已经是最新版了");
        }
    }

    // 下载线程
    private void setupDownloadThread(String url) {// 下载线程
        UpdateUtils.startUpdate(activity, url);
    }
}
