package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangtuo on 2018/9/5.
 */

public class AnalysisConfigParentEntity implements Serializable {
    private String selectName;
    private List<AnalysisConfigGroupEntity> selectValues;

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }

    public List<AnalysisConfigGroupEntity> getSelectValues() {
        return selectValues;
    }

    public void setSelectValues(List<AnalysisConfigGroupEntity> selectValues) {
        this.selectValues = selectValues;
    }

    @Override
    public String toString() {
        return "AnalysisConfigParentEntity{" +
                "selectName='" + selectName + '\'' +
                ", selectValues=" + selectValues +
                '}';
    }
}
