package com.rx.mvp.zyzj.model.other.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.model.other.entity.AddressBean;

/**
 * 手机归属地页面view接口
 *
 * @author ZhongDaFeng
 */

public interface IPhoneAddressView extends IBaseView {

    //显示结果
    void showResult(AddressBean bean);

}
