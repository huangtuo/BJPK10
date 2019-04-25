package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangtuo on 2018/7/20.
 */

public class PlanDetailsGuessEntity2 implements Serializable {

    private String correctIndex; //圆圈中的数字
    private CorrectInfoEntity correctInfo; //对错
    private String correctIssue; //预测期数
    private List<String> correctIssueBalls; //开奖的数字
    private String startIssue; //开始期数
    private String endIssue; //结束期数
    private List<List<String>> guessResult; //预期 猜的数字

    public String getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(String correctIndex) {
        this.correctIndex = correctIndex;
    }

    public CorrectInfoEntity getCorrectInfo() {
        return correctInfo;
    }

    public void setCorrectInfo(CorrectInfoEntity correctInfo) {
        this.correctInfo = correctInfo;
    }

    public String getCorrectIssue() {
        return correctIssue;
    }

    public void setCorrectIssue(String correctIssue) {
        this.correctIssue = correctIssue;
    }

    public List<String> getCorrectIssueBalls() {
        return correctIssueBalls;
    }

    public void setCorrectIssueBalls(List<String> correctIssueBalls) {
        this.correctIssueBalls = correctIssueBalls;
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

    @Override
    public String toString() {
        return "PlanDetailsGuessEntity2{" +
                "correctIndex='" + correctIndex + '\'' +
                ", correctInfo=" + correctInfo +
                ", correctIssue='" + correctIssue + '\'' +
                ", correctIssueBalls=" + correctIssueBalls +
                ", startIssue='" + startIssue + '\'' +
                ", endIssue='" + endIssue + '\'' +
                ", guessResult=" + guessResult +
                '}';
    }
}
