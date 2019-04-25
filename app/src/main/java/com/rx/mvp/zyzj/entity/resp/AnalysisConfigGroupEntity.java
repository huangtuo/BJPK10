package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangtuo on 2018/9/5.
 */

public class AnalysisConfigGroupEntity implements Serializable {
    private String name;
    private List<AnalysisConfigEntity> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AnalysisConfigEntity> getValues() {
        return values;
    }

    public void setValues(List<AnalysisConfigEntity> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "AnalysisConfigGroupEntity{" +
                "name='" + name + '\'' +
                ", values=" + values +
                '}';
    }
}
