package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.MyBiz;
import com.rx.mvp.zyzj.entity.resp.UpdateEntity;
import com.rx.mvp.zyzj.iface.IUpdateVersionView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/8/14.
 */

public class UpdateVersionPresenter extends BasePresenter<IUpdateVersionView, LifecycleProvider> {
    private final String TAG = UpdateVersionPresenter.class.getSimpleName();

    public UpdateVersionPresenter(IUpdateVersionView view, LifecycleProvider activity) {
        super(view, activity);
    }

    public void getUpdateVersion(String version){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "getVersion", UpdateEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                getView().getUpdateSuccess((UpdateEntity) resp);
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

        MyBiz.getInstance().updateVersion(version, getActivity(), httpRxCallback);

    }


}
