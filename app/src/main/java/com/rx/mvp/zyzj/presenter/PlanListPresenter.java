package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.LotteryBiz;
import com.rx.mvp.zyzj.entity.resp.HomePlanListEntity;
import com.rx.mvp.zyzj.iface.IPlanListView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/9/8.
 */

public class PlanListPresenter extends BasePresenter<IPlanListView, LifecycleProvider> {
    private final String TAG = PlanListPresenter.class.getSimpleName();
    public PlanListPresenter(IPlanListView view, LifecycleProvider activity) {
        super(view, activity);
    }

    public void getHomePlans(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "homePlan", HomePlanListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                HomePlanListEntity listEntity = (HomePlanListEntity) resp;
                getView().getPlanListSuccess(listEntity != null ? listEntity.getList() : null);
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

        LotteryBiz.getInstance().getHomePlans(getActivity(), httpRxCallback);
    }

    public void refreshPlan(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "refreshPlan", HomePlanListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                HomePlanListEntity listEntity = (HomePlanListEntity) resp;
                getView().getPlanListSuccess(listEntity != null ? listEntity.getList() : null);
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

        LotteryBiz.getInstance().refreshPlan(getActivity(), httpRxCallback);
    }
}
