package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.MyBiz;
import com.rx.mvp.zyzj.entity.resp.MyFeedbackEntity;
import com.rx.mvp.zyzj.entity.resp.MyFeedbackListEntity;
import com.rx.mvp.zyzj.iface.IMyFeedbackView;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.util.List;

/**
 * Created by huangtuo on 2018/8/15.
 */

public class MyFeedbackPresenter extends BasePresenter<IMyFeedbackView, LifecycleProvider> {
    private final String TAG = MyFeedbackPresenter.class.getSimpleName();

    public MyFeedbackPresenter(IMyFeedbackView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 获取我的反馈列表
     */
    public void getMyFeedback(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "myFeedback", MyFeedbackListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                MyFeedbackListEntity list = (MyFeedbackListEntity) resp;
                getView().getMyFeedbackSuccess(list.getList());
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

        MyBiz.getInstance().getMyFeedback(getActivity(), httpRxCallback);
    }

    /**
     * 提交反馈信息
     * @param topic
     * @param content
     * @param base64String
     */
    public void submitFeedback(String topic, String content, String base64String){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "submitFeedback", String.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                getView().submitFeedbackSuccess((String) resp);
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

        MyBiz.getInstance().submitFeedback(topic, content, base64String, getActivity(), httpRxCallback);
    }

    /**
     * 获取反馈详情
     * @param id
     */
    public void getFeedbackDetails(String id){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "feedbackDetails", MyFeedbackEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                getView().getFeedbackDetails((MyFeedbackEntity) resp);
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

        MyBiz.getInstance().getFeedbackDetails(id, getActivity(), httpRxCallback);
    }

}
