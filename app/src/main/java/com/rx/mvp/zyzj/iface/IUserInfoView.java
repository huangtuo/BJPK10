package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.UserInfoEntity;

/**
 * Created by huangtuo on 2018/8/6.
 */

public interface IUserInfoView extends IBaseView {
    void logoutSuccess(String msg);
    void getUserInfo(UserInfoEntity entity);
}
