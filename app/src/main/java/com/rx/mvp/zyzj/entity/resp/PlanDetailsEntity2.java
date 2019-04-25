package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangtuo on 2018/7/24.
 */

public class PlanDetailsEntity2 implements Serializable {

    private String continuousCorrect; //最大连对
    private String continuousWrong; //最大连错
    private double correctRatio; //准确率
    private String currentContinuous; //当前对错
    private List<PlanDetailsGuessEntity2> results; //详情列表

    public String getContinuousCorrect() {
        return continuousCorrect;
    }

    public void setContinuousCorrect(String continuousCorrect) {
        this.continuousCorrect = continuousCorrect;
    }

    public String getContinuousWrong() {
        return continuousWrong;
    }

    public void setContinuousWrong(String continuousWrong) {
        this.continuousWrong = continuousWrong;
    }

    public double getCorrectRatio() {
        return correctRatio;
    }

    public void setCorrectRatio(double correctRatio) {
        this.correctRatio = correctRatio;
    }

    public String getCurrentContinuous() {
        return currentContinuous;
    }

    public void setCurrentContinuous(String currentContinuous) {
        this.currentContinuous = currentContinuous;
    }

    public List<PlanDetailsGuessEntity2> getResults() {
        return results;
    }

    public void setResults(List<PlanDetailsGuessEntity2> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "PlanDetailsEntity2{" +
                "continuousCorrect='" + continuousCorrect + '\'' +
                ", continuousWrong='" + continuousWrong + '\'' +
                ", correctRatio='" + correctRatio + '\'' +
                ", currentContinuous='" + currentContinuous + '\'' +
                ", results=" + results +
                '}';
    }
}
