package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.LotteryBiz;
import com.rx.mvp.zyzj.entity.resp.PlanConfigGroupEntity;
import com.rx.mvp.zyzj.entity.resp.PlanConfigListEntity;
import com.rx.mvp.zyzj.entity.resp.PlanConfigParentEntity;
import com.rx.mvp.zyzj.entity.resp.PlanConfigUserListEntity;
import com.rx.mvp.zyzj.iface.IPlanConfigView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/9/7.
 */

public class PlanConfigPresenter extends BasePresenter<IPlanConfigView, LifecycleProvider> {
    private final String TAG = PlanConfigPresenter.class.getSimpleName();
    public PlanConfigPresenter(IPlanConfigView view, LifecycleProvider activity) {
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

    public void getAllPlan(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "allPlan", PlanConfigParentEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                PlanConfigParentEntity listEntity = (PlanConfigParentEntity) resp;
                if(listEntity != null){
                    getView().allPlanConfig(listEntity.getSelectValues());
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
        LotteryBiz.getInstance().getAllPlan(getActivity(), httpRxCallback);
    }

    public void updatePlan(String ids){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "updatePlan", String.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                getView().updatePlanConfig();

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
        LotteryBiz.getInstance().updatePlan(ids, getActivity(), httpRxCallback);
    }
}
