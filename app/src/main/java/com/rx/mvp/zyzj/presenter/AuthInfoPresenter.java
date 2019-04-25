package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.LotteryBiz;
import com.rx.mvp.zyzj.entity.resp.BuyAuthorEntity;
import com.rx.mvp.zyzj.entity.resp.BuyAuthorListEntity;
import com.rx.mvp.zyzj.entity.resp.OrderEntity;
import com.rx.mvp.zyzj.iface.IAuthInfoView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;

/**
 * Created by huangtuo on 2018/8/13.
 */

public class AuthInfoPresenter extends BasePresenter<IAuthInfoView, LifecycleProvider> {
    private final String TAG =AuthInfoPresenter.class.getSimpleName();

    public AuthInfoPresenter(IAuthInfoView view, LifecycleProvider activity) {
        super(view, activity);
    }

    public void getAuthInfo(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "authInfo", BuyAuthorListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                BuyAuthorListEntity listEntity = (BuyAuthorListEntity) resp;
                getView().getAuthInfoSuccess(listEntity.getList());
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
        LotteryBiz.getInstance().getAuthInfo(getActivity(), httpRxCallback);
    }

    public void createOrder(String orderType, String goodsId, String lotteryType, String payType){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "createOrder", OrderEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                getView().createOrder((OrderEntity) resp);
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
        LotteryBiz.getInstance().createOrder(orderType, goodsId, lotteryType, payType, getActivity(), httpRxCallback);

    }

    public void findOrder(String orderNo){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "findOrder", OrderEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                getView().findOrder((OrderEntity) resp);
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
        LotteryBiz.getInstance().toFindOrder(orderNo, getActivity(), httpRxCallback);

    }

}
