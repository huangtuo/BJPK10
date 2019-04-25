package com.rx.mvp.zyzj.entity.resp;

import java.util.List;

/**
 * Created by huangtuo on 2018/9/3.
 */

public class MissListEntity extends BaseResp {
    private List<MissEntity> list;

    public List<MissEntity> getList() {
        return list;
    }

    public void setList(List<MissEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "MissListEntity{" +
                "list=" + list +
                '}';
    }
}
