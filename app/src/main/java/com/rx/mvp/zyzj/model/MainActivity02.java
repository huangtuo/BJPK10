package com.rx.mvp.zyzj.model;

import android.content.Intent;
import android.view.View;


import com.rx.mvp.zyzj.R;
import com.rx.mvp.zyzj.base.BaseActivity;
import com.rx.mvp.zyzj.model.account.activity.LoginActivity;
import com.rx.mvp.zyzj.model.multiple.MultipleActivity;
import com.rx.mvp.zyzj.model.other.activity.PhoneAddressActivity;

import butterknife.OnClick;

public class MainActivity02 extends BaseActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main02;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.login, R.id.phone_address, R.id.multiple})
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.login:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.phone_address:
                intent = new Intent(this, PhoneAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.multiple:
                intent = new Intent(this, MultipleActivity.class);
                startActivity(intent);
                break;
        }

    }

}

