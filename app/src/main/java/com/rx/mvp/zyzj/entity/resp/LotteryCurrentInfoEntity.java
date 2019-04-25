package com.rx.mvp.zyzj.entity.resp;

import java.util.List;

/**
 * Created by huangtuo on 2018/8/13.
 */

public class LotteryCurrentInfoEntity extends BaseResp {
    private LotteryEntity lottery;
    private String currentIssue; //当前期号
    private List<String> results;
    private int nextOpenRestTime; //距离下棋开奖时间 单位：s
    private String nextIssue; //下期期号
    private String status;  //1 开奖中  2已开奖
    private String statusDes;
    private String popMsg;


    public LotteryEntity getLottery() {
        return lottery;
    }

    public void setLottery(LotteryEntity lottery) {
        this.lottery = lottery;
    }

    public String getCurrentIssue() {
        return currentIssue;
    }

    public void setCurrentIssue(String currentIssue) {
        this.currentIssue = currentIssue;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    public int getNextOpenRestTime() {
        return nextOpenRestTime;
    }

    public void setNextOpenRestTime(int nextOpenRestTime) {
        this.nextOpenRestTime = nextOpenRestTime;
    }

    public String getNextIssue() {
        return nextIssue;
    }

    public void setNextIssue(String nextIssue) {
        this.nextIssue = nextIssue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDes() {
        return statusDes;
    }

    public void setStatusDes(String statusDes) {
        this.statusDes = statusDes;
    }

    public String getPopMsg() {
        return popMsg;
    }

    public void setPopMsg(String popMsg) {
        this.popMsg = popMsg;
    }

    @Override
    public String toString() {
        return "LotteryCurrentInfoEntity{" +
                "lottery=" + lottery +
                ", currentIssue='" + currentIssue + '\'' +
                ", results=" + results +
                ", nextOpenRestTime=" + nextOpenRestTime +
                ", nextIssue='" + nextIssue + '\'' +
                ", status='" + status + '\'' +
                ", statusDes='" + statusDes + '\'' +
                ", popMsg='" + popMsg + '\'' +
                '}';
    }
}
