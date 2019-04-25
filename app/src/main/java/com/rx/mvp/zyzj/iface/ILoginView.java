package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.LoginRespEntity;

/**
 * Created by huangtuo on 2018/8/6.
 */

public interface ILoginView extends IBaseView {
    void loginSuccess(LoginRespEntity entity);
}
