package com.rx.mvp.zyzj.model.account.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.model.account.entity.UserBean;

/**
 * 登录view
 *
 * @author ZhongDaFeng
 */

public interface ILoginView extends IBaseView {

    //显示结果
    void showResult(UserBean bean);

}
