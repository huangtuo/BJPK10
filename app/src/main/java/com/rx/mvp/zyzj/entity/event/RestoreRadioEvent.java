package com.rx.mvp.zyzj.entity.event;

/**
 * Created by huangtuo on 2018/7/27.
 */

public class RestoreRadioEvent {
    private int index;

    public RestoreRadioEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
