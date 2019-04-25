package com.rx.mvp.zyzj.entity.resp;

import java.util.List;

/**
 * Created by huangtuo on 2018/8/13.
 */

public class BuyAuthorListEntity extends BaseResp {
    List<BuyAuthorEntity> list;

    public List<BuyAuthorEntity> getList() {
        return list;
    }

    public void setList(List<BuyAuthorEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "BuyAuthorListEntity{" +
                "list=" + list +
                '}';
    }
}
