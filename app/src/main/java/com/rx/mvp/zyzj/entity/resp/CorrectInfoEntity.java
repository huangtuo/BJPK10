package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;

/**
 * Created by huangtuo on 2018/9/8.
 */

public class CorrectInfoEntity implements Serializable {
    private boolean correct;
    private String des;

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "CorrectInfoEntity{" +
                "correct=" + correct +
                ", des='" + des + '\'' +
                '}';
    }
}
