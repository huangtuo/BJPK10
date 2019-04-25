package com.rx.mvp.zyzj.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.common.Constants;
import com.rx.mvp.zyzj.entity.resp.LoginRespEntity;
import com.rx.mvp.zyzj.entity.resp.LoginServerEntity;
import com.rx.mvp.zyzj.entity.resp.LotteryListEntity;
import com.rx.mvp.zyzj.entity.resp.UserInfoEntity;
import com.rx.mvp.zyzj.iface.ILoginView;
import com.rx.mvp.zyzj.iface.ILotteryView;
import com.rx.mvp.zyzj.presenter.LoginPresenter;
import com.rx.mvp.zyzj.presenter.LotteryPresenter;
import com.rx.mvp.zyzj.utils.DES3;
import com.rx.mvp.zyzj.utils.SPUtils;
import com.rx.mvp.zyzj.utils.StringUtils;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/7/29.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, ILoginView, ILotteryView{
    @BindView(R.id.login_edit_user)
    EditText editUser;
    @BindView(R.id.login_img_userClear)
    ImageView imgClearUser;
    @BindView(R.id.login_edit_psw)
    EditText editPsw;
    @BindView(R.id.login_img_pswClear)
    ImageView imgClearPsw;
    @BindView(R.id.login_btn)
    Button btnLogin;
    @BindView(R.id.login_tv_register)
    TextView tvRegister;

    private LoginPresenter loginPresenter = new LoginPresenter(this, this);
    private LotteryPresenter lotteryPresenter = new LotteryPresenter(this, this);
    private int errorCount = 5;

    private String userName, password;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initBundleData() {
        String name = SPUtils.getString(this, Constants.CONFIG, Constants.LOGIN_NAME);
        try {
            String pass = DES3.decode(SPUtils.getString(this, Constants.CONFIG, Constants.PSW));
            if(!StringUtils.isEmpty(pass)){
                editPsw.setText(pass);
                imgClearPsw.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!StringUtils.isEmpty(name)){
            editUser.setText(name);
            editUser.setSelection(name.length());
            imgClearUser.setVisibility(View.VISIBLE);
        }
        editUser.setFocusable(true);
        editUser.setFocusableInTouchMode(true);
        editUser.requestFocus();
    }

    @Override
    protected void initView() {
        imgClearUser.setOnClickListener(this);
        imgClearPsw.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

        editUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    imgClearUser.setVisibility(View.VISIBLE);
                else
                    imgClearUser.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    imgClearPsw.setVisibility(View.VISIBLE);
                else
                    imgClearPsw.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_img_userClear:
                editUser.setText("");
                break;
            case R.id.login_img_pswClear:
                editPsw.setText("");
                break;
            case R.id.login_btn:
                userName = editUser.getText().toString().trim();
                password = editPsw.getText().toString().trim();
                toLogin(userName, password);
                break;
            case R.id.login_tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    private void toLogin(String userName, String password) {
        if(userName.length() <= 0){
            showToast("请输入用户名");
            return;
        }else if(password.length() <= 0){
            showToast("请输入密码");
            return;
        }

        loginPresenter.login(userName, password);
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
    public void loginSuccess(LoginRespEntity bean) {
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

            if(ZyzjApplication.getApplication().getLotteryEntity() == null){
                lotteryPresenter.getLottery();
            }else{
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }
        }
    }

    @Override
    public void getLotterySuccess(LotteryListEntity listEntity) {
        if (listEntity.getList() != null && listEntity.getList().size() > 0) {
            ZyzjApplication.getApplication().setLotteryEntity(listEntity.getList().get(0));
        }
        finish();
        startActivity(new Intent(this, MainActivity.class));
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
}
