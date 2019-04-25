package com.rx.mvp.zyzj.entity.resp;

import java.util.List;

/**
 * Created by huangtuo on 2018/9/3.
 */

public class HotListEntity extends BaseResp {
    private List<HotEntity> list;

    public List<HotEntity> getList() {
        return list;
    }

    public void setList(List<HotEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "HotListEntity{" +
                "list=" + list +
                '}';
    }
}
