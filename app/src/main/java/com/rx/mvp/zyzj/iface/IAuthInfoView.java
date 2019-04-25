package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.BuyAuthorEntity;
import com.rx.mvp.zyzj.entity.resp.OrderEntity;

import java.util.List;

/**
 * Created by huangtuo on 2018/8/13.
 */

public interface IAuthInfoView extends IBaseView {
    void getAuthInfoSuccess(List<BuyAuthorEntity> list);
    void createOrder(OrderEntity orderEntity);
    void findOrder(OrderEntity orderEntity);
}
