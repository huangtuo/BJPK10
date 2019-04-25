package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.MyBiz;
import com.rx.mvp.zyzj.entity.resp.LoginRespEntity;
import com.rx.mvp.zyzj.iface.IRegisterView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/8/3.
 */

public class RegisterPresenter extends BasePresenter<IRegisterView, LifecycleProvider> {

    private final String TAG = RegisterPresenter.class.getSimpleName();

    public RegisterPresenter(IRegisterView view, LifecycleProvider activity) {
        super(view, activity);
    }

    public void register(String userName, String password) {
        if (getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "register", LoginRespEntity.class) {
            @Override
            public void onSuccess(Object object) {
                getView().closeLoading();
                getView().showResult((LoginRespEntity) object);

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

        MyBiz.getInstance().register(userName, password, getActivity(), httpRxCallback);
    }

}
