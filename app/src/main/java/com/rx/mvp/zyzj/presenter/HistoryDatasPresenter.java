package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.LotteryBiz;
import com.rx.mvp.zyzj.entity.resp.HistoryListEntity;
import com.rx.mvp.zyzj.iface.IHistoryDatasView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/9/4.
 */

public class HistoryDatasPresenter extends BasePresenter<IHistoryDatasView, LifecycleProvider> {
    private final String TAG = HistoryDatasPresenter.class.getSimpleName();

    public HistoryDatasPresenter(IHistoryDatasView view, LifecycleProvider activity) {
        super(view, activity);
    }

    public void getHistoryDatas(String lotteryId, String page, String size){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "award_result", HistoryListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                getView().getHistoryDataSuccess(((HistoryListEntity) resp).getList());
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

        LotteryBiz.getInstance().getHistoryDatas(lotteryId, page, size, getActivity(), httpRxCallback);
    }
}
