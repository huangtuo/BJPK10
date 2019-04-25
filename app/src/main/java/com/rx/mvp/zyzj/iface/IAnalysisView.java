package com.rx.mvp.zyzj.iface;

import com.rx.mvp.zyzj.base.IBaseView;
import com.rx.mvp.zyzj.entity.resp.HotEntity;
import com.rx.mvp.zyzj.entity.resp.MissEntity;

import java.util.List;

/**
 * Created by huangtuo on 2018/9/3.
 */

public interface IAnalysisView extends IBaseView {
    void getAnalysisMiss(List<MissEntity> missEntity);
    void getAnalysisHot(List<HotEntity> hotEntityList);
    void getTargetMiss(List<MissEntity> missEntity);
    void getTargetHot(List<HotEntity> hotEntityList);
}
