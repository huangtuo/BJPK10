package com.rx.mvp.zyzj.entity.resp;

import java.util.List;

/**
 * Created by huangtuo on 2018/9/7.
 */

public class PlanConfigListEntity extends BaseResp {

    private List<PlanConfigParentEntity> list;

    public List<PlanConfigParentEntity> getList() {
        return list;
    }

    public void setList(List<PlanConfigParentEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PlanConfigListEntity{" +
                "list=" + list +
                '}';
    }
}
