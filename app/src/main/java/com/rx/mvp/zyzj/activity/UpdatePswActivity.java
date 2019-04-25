package com.rx.mvp.zyzj.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.iface.IUpdatePswView;
import com.rx.mvp.zyzj.presenter.UpdatePswPresenter;
import com.rx.mvp.zyzj.utils.StringUtils;
import com.rx.mvp.zyzj.widget.NoButShow3SDialog;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/29.
 */

public class UpdatePswActivity extends BaseActivity implements IUpdatePswView{
    @BindView(R.id.common_left_photo)
    ImageView imgBack;
    @BindView(R.id.common_title)
    TextView tvTitle;
    @BindView(R.id.update_edit_oldPsw)
    EditText editOldPsw;
    @BindView(R.id.update_edit_newPsw)
    EditText editNewPsw;
    @BindView(R.id.update_edit_newPswAgain)
    EditText editNewPswAgain;
    @BindView(R.id.update_btn)
    Button btnSubmit;

    private UpdatePswPresenter updatePswPresenter = new UpdatePswPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_update_psw;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        imgBack.setVisibility(View.VISIBLE);
        tvTitle.setText("修改密码");

        imgBack.setOnClickListener(v -> {
            finish();
        });

        btnSubmit.setOnClickListener(v -> {
            checkPsw(editOldPsw.getText().toString(), editNewPsw.getText().toString(), editNewPswAgain.getText().toString());
        });
    }

    private void checkPsw(String oldPsw, String newPsw, String newPswAgain) {
        if(StringUtils.isEmpty(oldPsw)){
            showToast("请输入旧密码");
            return;
        }
        if(StringUtils.isEmpty(newPsw)){
            showToast("请输入新密码");
            return;
        }
        if(newPsw.length() < 6 || newPsw.length() > 20){
            showToast("请输入6-20位字符密码");
            return;
        }
        if(!newPsw.equals(newPswAgain)){
            showToast("两次输入的密码不一致");
            return;
        }

        updatePswPresenter.updatePsw(oldPsw, newPsw);
    }


    @Override
    protected void initData() {

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
    public void updatePsw(String msg) {
        showToastFinish("修改密码成功", () -> finish());
    }
}
