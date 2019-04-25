package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/9/8.
 */

public class HomePlanEntity implements Serializable {
    private String playType;
    private String lotteryPlanName;
    private String lotteryPlanId;
    private String startIssue;
    private String endIssue;
    private List<List<String>> guessResult;
    private String correctIssue;
    private String correctIndex;
    private List<Boolean> correctInfos;
    private double correctRatio;

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public String getLotteryPlanName() {
        return lotteryPlanName;
    }

    public void setLotteryPlanName(String lotteryPlanName) {
        this.lotteryPlanName = lotteryPlanName;
    }

    public String getLotteryPlanId() {
        return lotteryPlanId;
    }

    public void setLotteryPlanId(String lotteryPlanId) {
        this.lotteryPlanId = lotteryPlanId;
    }

    public String getStartIssue() {
        return startIssue;
    }

    public void setStartIssue(String startIssue) {
        this.startIssue = startIssue;
    }

    public String getEndIssue() {
        return endIssue;
    }

    public void setEndIssue(String endIssue) {
        this.endIssue = endIssue;
    }

    public List<List<String>> getGuessResult() {
        return guessResult;
    }

    public void setGuessResult(List<List<String>> guessResult) {
        this.guessResult = guessResult;
    }

    public String getCorrectIssue() {
        return correctIssue;
    }

    public void setCorrectIssue(String correctIssue) {
        this.correctIssue = correctIssue;
    }

    public String getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(String correctIndex) {
        this.correctIndex = correctIndex;
    }

    public List<Boolean> getCorrectInfos() {
        return correctInfos;
    }

    public void setCorrectInfos(List<Boolean> correctInfos) {
        this.correctInfos = correctInfos;
    }

    public double getCorrectRatio() {
        return correctRatio;
    }

    public void setCorrectRatio(double correctRatio) {
        this.correctRatio = correctRatio;
    }

    @Override
    public String toString() {
        return "HomePlanEntity{" +
                "playType='" + playType + '\'' +
                ", lotteryPlanName='" + lotteryPlanName + '\'' +
                ", lotteryPlanId='" + lotteryPlanId + '\'' +
                ", startIssue='" + startIssue + '\'' +
                ", endIssue='" + endIssue + '\'' +
                ", guessResult=" + guessResult +
                ", correctIssue='" + correctIssue + '\'' +
                ", correctIndex='" + correctIndex + '\'' +
                ", correctInfos=" + correctInfos +
                ", correctRatio=" + correctRatio +
                '}';
    }
}
