package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.PlanConfigEntity;
import com.rx.mvp.zyzj.entity.resp.PlanConfigGroupEntity;

import java.util.List;

/**
 * Created by huangtuo on 2018/9/7.
 */

public interface IPlanConfigView extends IBaseView{
    void userPlanConfig(List<PlanConfigEntity> planConfigEntityList);
    void allPlanConfig(List<PlanConfigGroupEntity> planConfigGroupEntityList);
    void updatePlanConfig();
}
