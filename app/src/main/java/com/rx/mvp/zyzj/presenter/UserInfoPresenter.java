package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.MyBiz;
import com.rx.mvp.zyzj.entity.resp.UserInfoEntity;
import com.rx.mvp.zyzj.iface.IUserInfoView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/8/6.
 */

public class UserInfoPresenter extends BasePresenter<IUserInfoView, LifecycleProvider> {
    private final String TAG = UserInfoPresenter.class.getSimpleName();

    public UserInfoPresenter(IUserInfoView view, LifecycleProvider activity) {
        super(view, activity);
    }


    public void logout(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "logout" , String.class) {
            @Override
            public void onSuccess(Object resp) {
                    getView().closeLoading();
                    getView().logoutSuccess((String) resp);
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

        MyBiz.getInstance().logout(getActivity(), httpRxCallback);
    }

    public void getUserInfo(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "getUserInfo" , UserInfoEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                UserInfoEntity userInfoEntity = (UserInfoEntity) resp;
                if(userInfoEntity != null){
                    ZyzjApplication.getApplication().setUserInfoBean(userInfoEntity);
                    getView().getUserInfo(userInfoEntity);
                }

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

        MyBiz.getInstance().getUserInfo(getActivity(), httpRxCallback);
    }

}
