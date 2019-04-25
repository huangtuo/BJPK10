package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.LoginRespEntity;

/**
 * Created by huangtuo on 2018/8/3.
 */

public interface IRegisterView extends IBaseView {
    void showResult(LoginRespEntity bean);
}
