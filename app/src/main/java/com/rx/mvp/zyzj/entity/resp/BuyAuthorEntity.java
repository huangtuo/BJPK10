package com.rx.mvp.zyzj.entity.resp;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/29.
 */

public class BuyAuthorEntity implements Serializable {
    private String id;
    private String goodsId;
    private String goodsName; //名称
    private String goodsPrice;  //特价
    private String originalPrice;//原价
    private String goodsTime;//天数
    private String remark;//提示
    private boolean enable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getGoodsTime() {
        return goodsTime;
    }

    public void setGoodsTime(String goodsTime) {
        this.goodsTime = goodsTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "BuyAuthorEntity{" +
                "id='" + id + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", originalPrice='" + originalPrice + '\'' +
                ", goodsTime='" + goodsTime + '\'' +
                ", remark='" + remark + '\'' +
                ", enable=" + enable +
                '}';
    }
}
