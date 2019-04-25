package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.PlanConfigEntity;
import com.rx.mvp.zyzj.entity.resp.PlanDetailsEntity2;

import java.util.List;

/**
 * Created by huangtuo on 2018/9/8.
 */

public interface IPlanDetailsView extends IBaseView {
    void getPlanDetailsSuccess(PlanDetailsEntity2 planDetailsEntity);
    void userPlanConfig(List<PlanConfigEntity> planConfigEntityList);
}
