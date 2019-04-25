package com.rx.mvp.zyzj.entity.resp;

import java.util.List;

/**
 * Created by huangtuo on 2018/9/5.
 */

public class AnalysisConfigListEntity extends BaseResp {
    private List<AnalysisConfigParentEntity> list;

    public List<AnalysisConfigParentEntity> getList() {
        return list;
    }

    public void setList(List<AnalysisConfigParentEntity> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "AnalysisConfigListEntity{" +
                "list=" + list +
                '}';
    }
}
