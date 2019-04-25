package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.HomePlanEntity;

import java.util.List;

/**
 * Created by huangtuo on 2018/9/8.
 */

public interface IPlanListView extends IBaseView {
    void getPlanListSuccess(List<HomePlanEntity> homePlanEntityList);
    void refreshPlanSuccess(List<HomePlanEntity> homePlanEntityList);
}
