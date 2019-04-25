package com.rx.mvp.zyzj.entity.resp;

import java.util.List;

/**
 * Created by huangtuo on 2018/8/15.
 */

public class MyFeedbackListEntity extends BaseResp {
    private List<MyFeedbackEntity> list;

    public List<MyFeedbackEntity> getList() {
        return list;
    }

    public void setList(List<MyFeedbackEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "MyFeedbackListEntity{" +
                "list=" + list +
                '}';
    }
}
