package com.rx.mvp.zyzj.entity.resp;

import java.util.List;

/**
 * Created by Administrator on 2018/9/7.
 */

public class PlanConfigUserListEntity extends BaseResp {
    private List<PlanConfigEntity> list;

    public List<PlanConfigEntity> getList() {
        return list;
    }

    public void setList(List<PlanConfigEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PlanConfigUserListEntity{" +
                "list=" + list +
                '}';
    }
}
