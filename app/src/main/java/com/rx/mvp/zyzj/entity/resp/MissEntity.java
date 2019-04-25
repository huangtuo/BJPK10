package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;

/**
 * Created by huangtuo on 2018/9/3.
 */

public class MissEntity implements Serializable {
    private String ball;
    private int missNum;

    public String getBall() {
        return ball;
    }

    public void setBall(String ball) {
        this.ball = ball;
    }

    public int getMissNum() {
        return missNum;
    }

    public void setMissNum(int missNum) {
        this.missNum = missNum;
    }

    @Override
    public String toString() {
        return "MissEntity{" +
                "ball='" + ball + '\'' +
                ", missNum='" + missNum + '\'' +
                '}';
    }
}
