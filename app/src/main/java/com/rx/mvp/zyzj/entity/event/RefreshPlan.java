package com.rx.mvp.zyzj.entity.event;

import com.rx.mvp.zyzj.entity.resp.PlanDetailsEntity2;

import java.io.Serializable;

/**
 * Created by huangtuo on 2018/7/25.
 */

public class RefreshPlan implements Serializable {
    private PlanDetailsEntity2 planDetailsEntity;
    private String planId;

    public PlanDetailsEntity2 getPlanDetailsEntity() {
        return planDetailsEntity;
    }

    public String getPlanId() {
        return planId;
    }

    public RefreshPlan(String planId, PlanDetailsEntity2 planDetailsEntity) {
        this.planId = planId;
        this.planDetailsEntity = planDetailsEntity;
    }
}
