package com.rx.mvp.zyzj.entity.event;

import com.rx.mvp.zyzj.entity.resp.LotteryCurrentInfoEntity;

/**
 * Created by Administrator on 2018/9/8.
 */

public class RefreshTimes {
    private LotteryCurrentInfoEntity entity;

    public LotteryCurrentInfoEntity getEntity() {
        return entity;
    }

    public RefreshTimes(LotteryCurrentInfoEntity entity) {
        this.entity = entity;
    }
}
