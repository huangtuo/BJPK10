package com.rx.mvp.zyzj.entity.resp;

import java.util.List;

/**
 * Created by huangtuo on 2018/8/13.
 */

public class LotteryListEntity extends BaseResp {
    private List<LotteryEntity> list;

    public List<LotteryEntity> getList() {
        return list;
    }

    public void setList(List<LotteryEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "LotteryListEntity{" +
                "list=" + list +
                '}';
    }
}
