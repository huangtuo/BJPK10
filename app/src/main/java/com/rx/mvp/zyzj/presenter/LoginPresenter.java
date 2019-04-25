package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.MyBiz;
import com.rx.mvp.zyzj.entity.resp.LoginRespEntity;
import com.rx.mvp.zyzj.iface.ILoginView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/8/6.
 */

public class LoginPresenter extends BasePresenter<ILoginView, LifecycleProvider> {
    private final String TAG = LoginPresenter.class.getSimpleName();

    public LoginPresenter(ILoginView view, LifecycleProvider activity) {
        super(view, activity);
    }

    public void login(String userName, String password){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "login", LoginRespEntity.class) {

            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                getView().loginSuccess((LoginRespEntity) resp);
            }

            @Override
            public void onError(int code, String desc) {
                getView().closeLoading();
                getView().onError(code, desc);
            }

            @Override
            public void onCancel() {
                getView().closeLoading();
            }
        };

        MyBiz.getInstance().login(userName, password, getActivity(), httpRxCallback);

    }


}
