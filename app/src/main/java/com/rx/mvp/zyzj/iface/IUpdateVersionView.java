package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.UpdateEntity;

/**
 * Created by huangtuo on 2018/8/14.
 */

public interface IUpdateVersionView extends IBaseView {
    void getUpdateSuccess(UpdateEntity updateEntity);
}
