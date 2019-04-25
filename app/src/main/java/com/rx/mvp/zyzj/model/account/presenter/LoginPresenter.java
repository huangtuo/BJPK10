package com.rx.mvp.zyzj.model.account.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.rx.mvp.zyzj.model.account.biz.UserBiz;
import com.rx.mvp.zyzj.model.account.entity.UserBean;
import com.rx.mvp.zyzj.model.other.presenter.PhoneAddressPresenter;
import com.rx.mvp.zyzj.model.account.iface.ILoginView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 登录Presenter
 *
 * @author ZhongDaFeng
 */

public class LoginPresenter extends BasePresenter<ILoginView, LifecycleProvider> {

    private final String TAG = PhoneAddressPresenter.class.getSimpleName();

    public LoginPresenter(ILoginView view, LifecycleProvider activity) {
        super(view, activity);
    }

    /**
     * 登录
     *
     * @author ZhongDaFeng
     */
    public void login(String userName, String password) {

        if (getView() != null)
            getView().showLoading();


        HttpRxCallback httpRxCallback = new HttpRxCallback(TAG + "login", UserBean.class) {
            @Override
            public void onSuccess(Object object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((UserBean) object);
                }
            }

            @Override
            public void onError(int code, String desc) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().onError(code, desc);
                }
            }

            @Override
            public void onCancel() {
                if (getView() != null) {
                    getView().closeLoading();
                }
            }
        };

//        new UserBiz().login(userName, password, getActivity(), httpRxCallback);
        new UserBiz().getTopNew( getActivity(), httpRxCallback);
        /**
         * ******此处代码为了测试取消请求,不是规范代码*****
         */
        /*try {
            Thread.sleep(50);
            //取消请求
            if (!httpRxCallback.isDisposed()) {
                httpRxCallback.cancel();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/


    }

}
