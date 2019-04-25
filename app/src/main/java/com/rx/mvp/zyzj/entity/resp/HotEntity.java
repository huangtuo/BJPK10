package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;

/**
 * Created by huangtuo on 2018/9/3.
 */

public class HotEntity implements Serializable {
    private String ball;
    private double analyze;

    public String getBall() {
        return ball;
    }

    public void setBall(String ball) {
        this.ball = ball;
    }

    public double getAnalyze() {
        return analyze;
    }

    public void setAnalyze(double analyze) {
        this.analyze = analyze;
    }

    @Override
    public String toString() {
        return "HotEntity{" +
                "ball='" + ball + '\'' +
                ", analyze=" + analyze +
                '}';
    }
}
