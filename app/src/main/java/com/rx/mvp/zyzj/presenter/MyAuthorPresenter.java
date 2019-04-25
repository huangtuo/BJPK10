package com.rx.mvp.zyzj.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.biz.MyBiz;
import com.rx.mvp.zyzj.entity.resp.MyAuthorListEntity;
import com.rx.mvp.zyzj.iface.IMyAuthor;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * Created by huangtuo on 2018/8/15.
 */

public class MyAuthorPresenter extends BasePresenter<IMyAuthor, LifecycleProvider> {
    private final String TAG = MyAuthorPresenter.class.getSimpleName();

    public MyAuthorPresenter(IMyAuthor view, LifecycleProvider activity) {
        super(view, activity);
    }

    public void getMyAuthor(){
        if(getView() == null)
            return;
        getView().showLoading();

        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "myAuthor", MyAuthorListEntity.class) {
            @Override
            public void onSuccess(Object resp) {
                getView().closeLoading();
                MyAuthorListEntity listEntity = (MyAuthorListEntity) resp;
                getView().getMyAuthor(listEntity.getList());
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

        MyBiz.getInstance().getMyAuthor(getActivity(), httpRxCallback);
    }

}
