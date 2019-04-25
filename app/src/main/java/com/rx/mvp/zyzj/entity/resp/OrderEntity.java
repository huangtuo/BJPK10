package com.rx.mvp.zyzj.entity.resp;

/**
 * Created by huangtuo on 2018/8/31.
 */

public class OrderEntity extends BaseResp {
    private String id;
    private String userId;
    private String orderNo;
    private String orderType;
    private String goodsId;
    private String lotteryType;
    private String money;
    private String tradeNo;
    private String payType;
    private String orderStatus;
    private String createTime;
    private String createBy;

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", orderType='" + orderType + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", lotteryType='" + lotteryType + '\'' +
                ", money='" + money + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", payType='" + payType + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createBy='" + createBy + '\'' +
                '}';
    }
}
