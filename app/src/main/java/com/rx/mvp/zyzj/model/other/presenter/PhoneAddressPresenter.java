package com.rx.mvp.zyzj.model.other.presenter;

import com.rx.mvp.zyzj.base.BasePresenter;
import com.rx.mvp.zyzj.net.observer.HttpRxCallback;
import com.rx.mvp.zyzj.model.other.biz.OtherBiz;
import com.rx.mvp.zyzj.model.other.entity.AddressBean;
import com.rx.mvp.zyzj.model.other.iface.IPhoneAddressView;
import com.trello.rxlifecycle2.LifecycleProvider;

/**
 * 手机号归属地Presenter
 *
 * @author ZhongDaFeng
 */

public class PhoneAddressPresenter extends BasePresenter<IPhoneAddressView, LifecycleProvider> {

    private final String TAG = PhoneAddressPresenter.class.getSimpleName();

    public PhoneAddressPresenter(IPhoneAddressView view, LifecycleProvider activity) {
        super(view, activity);
    }


    /**
     * 获取信息
     *
     * @author ZhongDaFeng
     */
    public void phoneQuery(String phone) {

        //loading
        if (getView() != null)
            getView().showLoading();

        //请求
        new OtherBiz().phoneQuery(phone, getActivity(), new HttpRxCallback(AddressBean.class) {
            @Override
            public void onSuccess(Object object) {
                if (getView() != null) {
                    getView().closeLoading();
                    getView().showResult((AddressBean) object);
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
        });


    }


}
