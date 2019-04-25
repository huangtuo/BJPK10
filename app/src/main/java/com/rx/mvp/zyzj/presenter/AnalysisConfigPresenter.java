package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.AnalysisBiz;
import com.rx.mvp.zyzj.entity.resp.AnalysisConfigListEntity;
import com.rx.mvp.zyzj.iface.IAnalysisConfigView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/9/5.
 */

public class AnalysisConfigPresenter extends BasePresenter<IAnalysisConfigView, LifecycleProvider> {

    private final String TAG = AnalysisConfigPresenter.class.getSimpleName();

    public AnalysisConfigPresenter(IAnalysisConfigView view, LifecycleProvider activity) {
        super(view, activity);
    }

    public void getMissConfig(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "missConfig", AnalysisConfigListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                AnalysisConfigListEntity listEntity = (AnalysisConfigListEntity) resp;
                getView().missConfigSuccess(listEntity != null ? listEntity.getList() : null);
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

        AnalysisBiz.getInstance().getMissConfig(getActivity(), httpRxCallback);
    }

    public void getHotConfig(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "hotConfig", AnalysisConfigListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                AnalysisConfigListEntity listEntity = (AnalysisConfigListEntity) resp;
                getView().hotConfigSuccess(listEntity != null ? listEntity.getList() : null);
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

        AnalysisBiz.getInstance().getHotConfig(getActivity(), httpRxCallback);
    }

    public void getTargetMissConfig(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "targetMissConfig", AnalysisConfigListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                AnalysisConfigListEntity listEntity = (AnalysisConfigListEntity) resp;
                getView().targetMissConfigSuccess(listEntity != null ? listEntity.getList() : null);
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

        AnalysisBiz.getInstance().getTargetMissConfig(getActivity(), httpRxCallback);
    }

    public void getTargetHotConfig(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "targetHotConfig", AnalysisConfigListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                AnalysisConfigListEntity listEntity = (AnalysisConfigListEntity) resp;
                getView().targetHotConfigSuccess(listEntity != null ? listEntity.getList() : null);
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

        AnalysisBiz.getInstance().getTargetHotConfig(getActivity(), httpRxCallback);
    }

}
