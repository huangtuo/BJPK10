package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.LotteryBiz;
import com.rx.mvp.zyzj.entity.resp.PlanDetailsEntity2;
import com.rx.mvp.zyzj.iface.IFormulaView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/9/8.
 */

public class FormulaPresenter extends BasePresenter<IFormulaView, LifecycleProvider> {
    private final String TAG = FormulaPresenter.class.getSimpleName();

    public FormulaPresenter(IFormulaView view, LifecycleProvider activity) {
        super(view, activity);
    }

    public void submitFormula(String id, String resultNum, String chaseNum, String giveUpNum, String scoreDown,
                              String scoreUp, String continuousCorrectNumDown, String continuousCorrectNumUp, String continuousWrongNumDown,
                              String continuousWrongNumUp, String currentContinuousNumDown, String currentContinuousNumUp) {
        if (getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "formula", PlanDetailsEntity2.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                getView().submitSuccess((PlanDetailsEntity2) resp);
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
        LotteryBiz.getInstance().submitFormula(id, resultNum, chaseNum, giveUpNum, scoreDown,
                scoreUp, continuousCorrectNumDown, continuousCorrectNumUp, continuousWrongNumDown,
                continuousWrongNumUp, currentContinuousNumDown, currentContinuousNumUp, getActivity(), httpRxCallback);
    }
}
