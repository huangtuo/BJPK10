package com.rx.mvp.zyzj.entity.event;

import com.rx.mvp.zyzj.entity.resp.AnalysisConfigEntity;

import java.util.List;

/**
 * Created by huangtuo on 2018/7/27.
 */

public class RefreshAnalysisEvent {
    private int flag;
    private String dataStr;
    private AnalysisConfigEntity targetStr;
    private AnalysisConfigEntity locations;

    public RefreshAnalysisEvent(int flag, String dataStr, AnalysisConfigEntity targetStr, AnalysisConfigEntity locations) {
        this.flag = flag;
        this.dataStr = dataStr;
        this.targetStr = targetStr;
        this.locations = locations;
    }

    public int getFlag() {
        return flag;
    }

    public String getDataStr() {
        return dataStr;
    }

    public AnalysisConfigEntity getTargetStr() {
        return targetStr;
    }

    public AnalysisConfigEntity getLocations() {
        return locations;
    }
}
