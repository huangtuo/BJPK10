package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.AnalysisBiz;
import com.rx.mvp.zyzj.entity.resp.HotListEntity;
import com.rx.mvp.zyzj.entity.resp.MissListEntity;
import com.rx.mvp.zyzj.iface.IAnalysisView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/9/3.
 */

public class AnalysisPresenter extends BasePresenter<IAnalysisView, LifecycleProvider> {
    private String TAG = AnalysisPresenter.class.getSimpleName();
    public AnalysisPresenter(IAnalysisView view, LifecycleProvider activity) {
        super(view, activity);
    }

    public void analysisMiss(String position){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "analysisMiss", MissListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                MissListEntity listEntity = (MissListEntity) resp;
                getView().getAnalysisMiss(listEntity != null ? listEntity.getList() : null);
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

        AnalysisBiz.getInstance().analysisMiss(position, getActivity(), httpRxCallback);
    }

    public void analysisHot(String position, String count){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "analysissHot", HotListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                HotListEntity listEntity = (HotListEntity) resp;
                getView().getAnalysisHot(listEntity != null ? listEntity.getList() : null);
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

        AnalysisBiz.getInstance().analysisHot(position, count, getActivity(), httpRxCallback);
    }

    public void targetMiss(String id){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "analysisTargetMiss", MissListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                MissListEntity listEntity = (MissListEntity) resp;
                getView().getTargetMiss(listEntity != null ? listEntity.getList() : null);
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

        AnalysisBiz.getInstance().targetMiss(id, getActivity(), httpRxCallback);
    }

    public void targetHot(String id, String count){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "analysisTargetHot", HotListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                HotListEntity listEntity = (HotListEntity) resp;
                getView().getTargetHot(listEntity != null ? listEntity.getList() : null);
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

        AnalysisBiz.getInstance().targetHot(id, count, getActivity(), httpRxCallback);
    }

}
