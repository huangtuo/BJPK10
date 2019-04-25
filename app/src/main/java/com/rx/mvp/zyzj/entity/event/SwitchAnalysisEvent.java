package com.rx.mvp.zyzj.entity.event;

/**
 * Created by huangtuo on 2018/7/26.
 */

public class SwitchAnalysisEvent {
    private int analysisFlag;

    public SwitchAnalysisEvent(int analysisFlag) {
        this.analysisFlag = analysisFlag;
    }

    public int getAnalysisFlag() {
        return analysisFlag;
    }
}
