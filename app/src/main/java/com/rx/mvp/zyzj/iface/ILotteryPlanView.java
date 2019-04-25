package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.LotteryCurrentInfoEntity;

/**
 * Created by huangtuo on 2018/8/13.
 */

public interface ILotteryPlanView extends IBaseView {
    void getCurrentLottery(LotteryCurrentInfoEntity lotteryCurrentInfoEntity);
}
