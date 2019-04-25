package com.rx.mvp.zyzj.entity.resp;

import java.util.List;

/**
 * Created by huangtuo on 2018/9/4.
 */

public class HistoryListEntity extends BaseResp {
    private List<HistoryEntity> list;

    public List<HistoryEntity> getList() {
        return list;
    }

    public void setList(List<HistoryEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "HistoryListEntity{" +
                "list=" + list +
                '}';
    }
}
