package com.rx.mvp.zyzj.entity.resp;

import java.util.List;

/**
 * Created by huangtuo on 2018/8/15.
 */

public class MyAuthorListEntity extends BaseResp {
    private List<MyAuthorEntity> list;

    public List<MyAuthorEntity> getList() {
        return list;
    }

    public void setList(List<MyAuthorEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "MyAuthorListEntity{" +
                "list=" + list +
                '}';
    }
}
