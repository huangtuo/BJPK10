package com.rx.mvp.zyzj.model.account.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.model.account.entity.UserBean;
import com.rx.mvp.zyzj.model.account.presenter.LoginPresenter;
import com.rx.mvp.zyzj.utils.ToastUtils;
import com.rx.mvp.zyzj.model.account.iface.ILoginView;
import com.rx.mvp.zyzj.widget.RLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录
 *
 * @author ZhongDaFeng
 */

public class LoginActivity extends BaseActivity implements ILoginView {

    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;

    private LoginPresenter mLoginPresenter = new LoginPresenter(this, this);

    private RLoadingDialog mLoadingDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login02;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(this, true);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    return;
                }

                mLoginPresenter.login(userName, password);
                break;
        }
    }


    @Override
    public void showResult(UserBean bean) {
        if (bean == null) {
            return;
        }
        showToast(bean.getUid());
    }

    @Override
    public void showLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void onError(int code, String msg) {
        ToastUtils.showToast(this, msg);
    }


}
