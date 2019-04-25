package com.rx.mvp.zyzj.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.common.Constants;
import com.rx.mvp.zyzj.entity.resp.LoginRespEntity;
import com.rx.mvp.zyzj.entity.resp.LoginServerEntity;
import com.rx.mvp.zyzj.entity.resp.UserInfoEntity;
import com.rx.mvp.zyzj.iface.IRegisterView;
import com.rx.mvp.zyzj.presenter.RegisterPresenter;
import com.rx.mvp.zyzj.utils.DES3;
import com.rx.mvp.zyzj.utils.SPUtils;
import com.rx.mvp.zyzj.utils.StringUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/29.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener, IRegisterView {
    @BindView(R.id.common_left_photo)
    ImageView imgBack;
    @BindView(R.id.common_title)
    TextView tvTitle;
    @BindView(R.id.register_edit_user)
    EditText editUser;
    @BindView(R.id.register_edit_psw)
    EditText editPsw;
    @BindView(R.id.register_edit_pswAgain)
    EditText editPswAgain;
    @BindView(R.id.register_btn)
    Button btnRegister;
    @BindView(R.id.register_tv_login)
    TextView tvLogin;

    private String userName, password;
    private RegisterPresenter registerPresenter = new RegisterPresenter(this, this);

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        imgBack.setVisibility(View.VISIBLE);
        tvTitle.setText("注册");

        imgBack.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_left_photo:
                finish();
                break;
            case R.id.register_btn:
                userName = editUser.getText().toString().trim();
                password = editPsw.getText().toString();
                checkInfo(userName,password, editPswAgain.getText().toString());
                break;
            case R.id.register_tv_login:
                finish();
            default:
                break;
        }
    }

    private void checkInfo(String userName, String password, String passwordAgain) {
        if (StringUtils.isEmpty(userName)) {
            showToast("请输入用户名");
            return;
        }
        if (StringUtils.isEmpty(password)) {
            showToast("请输入密码");
            return;
        }
        if (userName.length() < 6 || userName.length() > 20) {
            showToast("请输入6-20位字符用户名！");
            return;
        }
        if (password.length() < 6 || password.length() > 20) {
            showToast("请输入6-20位字符密码！");
            return;
        }
        if (!password.equals(passwordAgain)) {
            showToast("两次输入的密码不一致");
            return;
        }

        toRegister(userName, password);
    }

    private void toRegister(String userName, String password) {
        registerPresenter.register(userName, password);
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
        showToast(msg);
    }

    @Override
    public void showResult(LoginRespEntity bean) {
        if (bean != null) {
            LoginServerEntity loginServerEntity = new LoginServerEntity();
            loginServerEntity.setToken(bean.getToken());
            ZyzjApplication.application.setServiceModel(loginServerEntity);

            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setUserId(bean.getUserId());
            userInfoEntity.setUserName(bean.getUserName());
            userInfoEntity.setUserLevel(bean.getUserLevel());
            ZyzjApplication.application.setUserInfoBean(userInfoEntity);

            //登录成功保存用户名
            SPUtils.putString(this, Constants.CONFIG, Constants.LOGIN_NAME, userName);
            try {
                SPUtils.putString(this, Constants.CONFIG, Constants.PSW, DES3.encode(password));
            } catch (Exception e) {
                e.printStackTrace();
            }

            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
