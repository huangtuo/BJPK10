package com.rx.mvp.zyzj.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.common.Constants;
import com.rx.mvp.zyzj.entity.resp.LotteryEntity;
import com.rx.mvp.zyzj.entity.resp.LotteryListEntity;
import com.rx.mvp.zyzj.entity.resp.UpdateEntity;
import com.rx.mvp.zyzj.iface.ILotteryView;
import com.rx.mvp.zyzj.iface.IUpdateVersionView;
import com.rx.mvp.zyzj.presenter.LotteryPresenter;
import com.rx.mvp.zyzj.presenter.UpdateVersionPresenter;
import com.rx.mvp.zyzj.utils.SPUtils;
import com.rx.mvp.zyzj.utils.StringUtils;
import com.rx.mvp.zyzj.utils.UpdateUtils;
import com.rx.mvp.zyzj.utils.VersionUtil;
import com.rx.mvp.zyzj.widget.OneButtonDialog;
import com.rx.mvp.zyzj.widget.TwoButtonDialog;

import butterknife.BindView;

/**
 * Created by huangtuo on 2018/8/3.
 */

public class LoadingActivity extends BaseActivity implements ILotteryView, IUpdateVersionView {

    @BindView(R.id.imageLoading)
    ImageView imgLoading;

    public final static int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 9999;

    private LotteryPresenter lotteryPresenter = new LotteryPresenter(this, this);
    private UpdateVersionPresenter updateVersionPresenter = new UpdateVersionPresenter(this, this);
    private int errorCount = 5;
    private long timeWait = 0; //启动页至少停留时长

    @Override
    protected int getContentViewId() {
        return R.layout.activity_loading;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        timeWait = System.currentTimeMillis();
        if(ZyzjApplication.getApplication().getLotteryEntity() == null){
            lotteryPresenter.getLottery();
        }else{
            toCheckUpdate();
        }
    }

    private void toCheckUpdate() {
        updateVersionPresenter.getUpdateVersion(VersionUtil.getVersionName(this));
    }

    private void showDownDialog(final UpdateEntity model) {
        //返回的 appVersion不为空、url不为空、 appVersion与本地版本不相同 才去弹出更新
        if (!StringUtils.isEmpty(model.getAppVersion()) && !StringUtils.isEmpty(model.getUrl()) && !model.getAppVersion().equals(VersionUtil.getVersionName(this))) {
            TwoButtonDialog twoButtonDialog = new TwoButtonDialog(this);
            if ("1".equals(model.getForceUpdate())) {
                twoButtonDialog.showDialog(this, model.getUpdateContent() + "", "立即更新", "退出", new TwoButtonDialog.OnSureButtonClickListener() {
                    @Override
                    public void onSuerButClisk(Dialog dialog) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            if (ContextCompat.checkSelfPermission(LoadingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                //申请WRITE_EXTERNAL_STORAGE权限
                                ActivityCompat.requestPermissions(LoadingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                                return;
                            } else {
                                setupDownloadThread(model.getUrl());
                                finish();
                            }
                        } else {
                            setupDownloadThread(model.getUrl());
                            finish();
                        }

                    }
                }, new TwoButtonDialog.OnCancleButtonClickListener() {
                    @Override
                    public void onCancleButClisk(Dialog dialog) {
                        ZyzjApplication.application.exit();
                    }
                }, true);
            } else {
                twoButtonDialog.showDialog(this, model.getUpdateContent() + "", "立即更新", "取  消", new TwoButtonDialog.OnSureButtonClickListener() {
                    @Override
                    public void onSuerButClisk(Dialog dialog) {
                        if (Build.VERSION.SDK_INT >= 23) {
                            if (ContextCompat.checkSelfPermission(LoadingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                //申请WRITE_EXTERNAL_STORAGE权限
                                ActivityCompat.requestPermissions(LoadingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);

                                return;
                            } else {
                                setupDownloadThread(model.getUrl());
                                goNext();
                            }
                        } else {
                            setupDownloadThread(model.getUrl());
                            goNext();
                        }
                    }
                }, new TwoButtonDialog.OnCancleButtonClickListener() {
                    @Override
                    public void onCancleButClisk(Dialog dialog) {
                        goNext();
                    }
                }, true);
            }
        } else {
            goNext();
        }
    }

    // 下载线程
    private void setupDownloadThread(String url) {// 下载线程
        UpdateUtils.startUpdate(this, url);
    }


    private void goNext() {
        if (System.currentTimeMillis() - timeWait > 3000) {
            if (isLogin()) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isLogin()) {
                        startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                    }
                }
            }, 2000);
        }


    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void onError(int code, String msg) {
        goNext();
    }

    @Override
    public void getLotterySuccess(LotteryListEntity listEntity) {
        if (listEntity.getList() != null && listEntity.getList().size() > 0) {
            ZyzjApplication.getApplication().setLotteryEntity(listEntity.getList().get(0));
        }
        toCheckUpdate();
    }

    @Override
    public void getLotteryError(String msg) {
        errorCount--;
        lotteryPresenter.getLottery();
        if (errorCount < 0) {
            showOneButDialog("获取彩种失败,请重启应用", "确定", dialog -> {
                SPUtils.putObject(this, Constants.LOTTERY, null);
                ZyzjApplication.getApplication().exit();
            });
        }
    }

    @Override
    public void getUpdateSuccess(UpdateEntity updateEntity) {
        if (updateEntity != null) {
            showDownDialog(updateEntity);
        } else {
            goNext();
        }
    }
}
