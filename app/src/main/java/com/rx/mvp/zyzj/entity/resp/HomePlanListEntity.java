package com.rx.mvp.zyzj.entity.resp;

import java.util.List;

/**
 * Created by huangtuo on 2018/9/8.
 */

public class HomePlanListEntity extends BaseResp {
    private List<HomePlanEntity> list;

    public List<HomePlanEntity> getList() {
        return list;
    }

    public void setList(List<HomePlanEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "HomePlanListEntity{" +
                "list=" + list +
                '}';
    }
}
