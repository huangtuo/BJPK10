package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;

/**
 * Created by huangtuo on 2018/9/5.
 */

public class AnalysisConfigEntity implements Serializable {
    private String id;
    private String playTypeSerialNumber;
    private String resultSetSize;
    private String ruleName;
    private String serialNumber;
    private String playType;
    private String lotteryId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayTypeSerialNumber() {
        return playTypeSerialNumber;
    }

    public void setPlayTypeSerialNumber(String playTypeSerialNumber) {
        this.playTypeSerialNumber = playTypeSerialNumber;
    }

    public String getResultSetSize() {
        return resultSetSize;
    }

    public void setResultSetSize(String resultSetSize) {
        this.resultSetSize = resultSetSize;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

    @Override
    public String toString() {
        return "AnalysisConfigEntity{" +
                "id='" + id + '\'' +
                ", playTypeSerialNumber='" + playTypeSerialNumber + '\'' +
                ", resultSetSize='" + resultSetSize + '\'' +
                ", ruleName='" + ruleName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", playType='" + playType + '\'' +
                ", lotteryId='" + lotteryId + '\'' +
                '}';
    }
}
