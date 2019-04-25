package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.AnalysisConfigParentEntity;

import java.util.List;

/**
 * Created by huangtuo on 2018/9/5.
 */

public interface IAnalysisConfigView extends IBaseView {
    void missConfigSuccess(List<AnalysisConfigParentEntity> configParentEntityList);
    void hotConfigSuccess(List<AnalysisConfigParentEntity> configParentEntityList);
    void targetMissConfigSuccess(List<AnalysisConfigParentEntity> configParentEntityList);
    void targetHotConfigSuccess(List<AnalysisConfigParentEntity> configParentEntityList);
}
