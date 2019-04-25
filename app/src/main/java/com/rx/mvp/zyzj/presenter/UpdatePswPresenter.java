package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.MyBiz;
import com.rx.mvp.zyzj.iface.IUpdatePswView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/8/6.
 */

public class UpdatePswPresenter extends BasePresenter<IUpdatePswView, LifecycleProvider> {
    private final String TAG = UpdatePswPresenter.class.getSimpleName();

    public UpdatePswPresenter(IUpdatePswView view, LifecycleProvider activity) {
        super(view, activity);
    }

    public void updatePsw(String oldPassword, String newPassword){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "updatePsw", String.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                getView().updatePsw((String) resp);
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

        MyBiz.getInstance().updatePsw(oldPassword, newPassword, getActivity(), httpRxCallback);

    }
}
