package com.rx.mvp.zyzj.entity.resp;

import java.util.List;

/**
 * Created by huangtuo on 2018/7/19.
 */

public class LotteryGroupsResponse extends BaseResp {
    private List<LotteryGoupsEntity> Data;

    public List<LotteryGoupsEntity> getData()
    {
        return this.Data;
    }

    public void setData(List<LotteryGoupsEntity> paramList)
    {
        this.Data = paramList;
    }
}
