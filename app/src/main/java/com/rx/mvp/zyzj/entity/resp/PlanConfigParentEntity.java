package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangtuo on 2018/9/7.
 */

public class PlanConfigParentEntity implements Serializable {
    private String selectName;
    private List<PlanConfigGroupEntity> selectValues;

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }

    public List<PlanConfigGroupEntity> getSelectValues() {
        return selectValues;
    }

    public void setSelectValues(List<PlanConfigGroupEntity> selectValues) {
        this.selectValues = selectValues;
    }

    @Override
    public String toString() {
        return "PlanConfigParentEntity{" +
                "selectName='" + selectName + '\'' +
                ", selectValues=" + selectValues +
                '}';
    }
}
