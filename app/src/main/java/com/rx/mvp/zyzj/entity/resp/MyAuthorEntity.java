package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;

/**
 * Created by huangtuo on 2018/8/15.
 */

public class MyAuthorEntity implements Serializable {
    private String id;
    private String userId;
    private String lotteryType;
    private String deadTime;
    private String createTime;
    private String flag;
    private String goodsId;
    private String goodsName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(String deadTime) {
        this.deadTime = deadTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Override
    public String toString() {
        return "MyAuthorEntity{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", lotteryType='" + lotteryType + '\'' +
                ", deadTime='" + deadTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", flag='" + flag + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                '}';
    }
}
