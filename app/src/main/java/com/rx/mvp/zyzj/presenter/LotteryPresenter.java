package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.LotteryBiz;
import com.rx.mvp.zyzj.entity.resp.LotteryListEntity;
import com.rx.mvp.zyzj.iface.ILotteryView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/8/13.
 */

public class LotteryPresenter extends BasePresenter<ILotteryView, LifecycleProvider> {
    private final String TAG = LotteryPresenter.class.getSimpleName();

    public LotteryPresenter(ILotteryView view, LifecycleProvider activity) {
        super(view, activity);
    }

    public void getLottery(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "lottery", LotteryListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                getView().getLotterySuccess((LotteryListEntity) resp);
            }

            @Override
            public void onError(int code, String desc) {
                getView().closeLoading();
                getView().getLotteryError(desc);
            }

            @Override
            public void onCancel() {
                getView().closeLoading();
            }
        };

        LotteryBiz.getInstance().getLottery(getActivity(), httpRxCallback);
    }

}
