package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.ZyzjApplication;
import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.LotteryBiz;
import com.rx.mvp.zyzj.entity.resp.PlanConfigUserListEntity;
import com.rx.mvp.zyzj.entity.resp.PlanDetailsEntity2;
import com.rx.mvp.zyzj.iface.IPlanDetailsView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/9/8.
 */

public class PlanDetailsPresenter extends BasePresenter<IPlanDetailsView, LifecycleProvider> {
    private final String TAG = PlanDetailsPresenter.class.getSimpleName();
    public PlanDetailsPresenter(IPlanDetailsView view, LifecycleProvider activity) {
        super(view, activity);
    }

    public void getUserPlan(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "userPlan", PlanConfigUserListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                PlanConfigUserListEntity listEntity = (PlanConfigUserListEntity) resp;
                if(listEntity != null){
                    getView().userPlanConfig(listEntity.getList());
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
        LotteryBiz.getInstance().getUserPlan(getActivity(), httpRxCallback);
    }

    public void getDetails(String id){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "planDetails"  + id, PlanDetailsEntity2.class) {
            @Override
            public void onSuccess(Object resp) {
                if(getView() != null){
                    getView().closeLoading();
                    getView().getPlanDetailsSuccess((PlanDetailsEntity2) resp);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if(getView() != null){
                    getView().closeLoading();
                    getView().onError(code, desc);
                }
            }

            @Override
            public void onCancel() {
                if(getView() != null){
                    getView().closeLoading();
                }
            }
        };
        LotteryBiz.getInstance().getPlanDetails(id, getActivity(), httpRxCallback);
    }
}
