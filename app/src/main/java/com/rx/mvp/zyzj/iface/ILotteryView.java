package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.LotteryListEntity;

/**
 * Created by huangtuo on 2018/8/13.
 */

public interface ILotteryView extends IBaseView {
    void getLotterySuccess(LotteryListEntity listEntity);
    void getLotteryError(String msg);
}
