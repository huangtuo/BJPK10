package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangtuo on 2018/9/7.
 */

public class PlanConfigGroupEntity implements Serializable {
    private String name;
    private List<PlanConfigEntity> values;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlanConfigEntity> getValues() {
        return values;
    }

    public void setValues(List<PlanConfigEntity> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "PlanConfigGroupEntity{" +
                "name='" + name + '\'' +
                ", values=" + values +
                '}';
    }
}
