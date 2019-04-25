package com.rx.mvp.zyzj.model.multiple;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.BaseFragment;
import com.rx.mvp.zyzj.model.account.entity.UserBean;
import com.rx.mvp.zyzj.model.account.iface.ILoginView;
import com.rx.mvp.zyzj.model.account.presenter.LoginPresenter;
import com.rx.mvp.zyzj.utils.ToastUtils;
import com.rx.mvp.zyzj.widget.RLoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录Fragment
 *
 * @author ZhongDaFeng
 */
public class LoginFragment extends BaseFragment implements ILoginView {

    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;

    private LoginPresenter mLoginPresenter = new LoginPresenter(this, this);

    private RLoadingDialog mLoadingDialog;

    @Override
    protected View getContentView() {
        return LayoutInflater.from(mContext).inflate(R.layout.activity_login02, null);
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {
        mLoadingDialog = new RLoadingDialog(mContext, true);
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
        ToastUtils.showToast(mContext, msg);
    }

}
