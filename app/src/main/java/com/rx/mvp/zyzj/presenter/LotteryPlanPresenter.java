package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.LotteryBiz;
import com.rx.mvp.zyzj.entity.resp.LotteryCurrentInfoEntity;
import com.rx.mvp.zyzj.iface.ILotteryPlanView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/8/13.
 */

public class LotteryPlanPresenter extends BasePresenter<ILotteryPlanView, LifecycleProvider> {
    private final String TAG = LotteryPlanPresenter.class.getSimpleName();

    public LotteryPlanPresenter(ILotteryPlanView view, LifecycleProvider activity) {
        super(view, activity);
    }

    public void getCurrentLottery(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "getLotteryCurrentInfo", LotteryCurrentInfoEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                if(getView() != null){
                    getView().closeLoading();
                    getView().getCurrentLottery((LotteryCurrentInfoEntity) resp);
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

        LotteryBiz.getInstance().getCurrentLotteryInfo(getActivity(), httpRxCallback);
    }

}
