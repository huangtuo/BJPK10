package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangtuo on 2018/8/13.
 */

public class LotteryEntity implements Serializable {
    /**
     *  {
     "id": "508347ce72564ec580c23ab721d720a6",
     "abbreviation": "bjpk10",
     "name": "北京pk10",
     "lotteryType": "高频彩",
     "range": [
     1,
     10
     ],
     "ballNum": 10
     }
     */
    private String id;
    private String abbreviation;
    private String name;
    private String lotteryType;
    private List<String> range;
    private String ballNum;
    private String serialNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public List<String> getRange() {
        return range;
    }

    public void setRange(List<String> range) {
        this.range = range;
    }

    public String getBallNum() {
        return ballNum;
    }

    public void setBallNum(String ballNum) {
        this.ballNum = ballNum;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "LotteryEntity{" +
                "id='" + id + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", name='" + name + '\'' +
                ", lotteryType='" + lotteryType + '\'' +
                ", range=" + range +
                ", ballNum='" + ballNum + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
