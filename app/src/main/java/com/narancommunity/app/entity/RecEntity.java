package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2018/4/18 19:53
 * Email：120760202@qq.com
 * FileName :
 */

public class RecEntity implements Serializable {
    String average, createTime, orderAuthor, orderContent, orderImgs, orderTitle, orderType, typeName, updateTime;
    Integer orderId;
    private Integer initiatorId;
    private String initiatorImg;
    private String initiatorNike;
    private String province, city, county;

    @Override
    public String toString() {
        return "RecEntity{" +
                "average='" + average + '\'' +
                ", createTime='" + createTime + '\'' +
                ", orderAuthor='" + orderAuthor + '\'' +
                ", orderContent='" + orderContent + '\'' +
                ", orderImgs='" + orderImgs + '\'' +
                ", orderTitle='" + orderTitle + '\'' +
                ", orderType='" + orderType + '\'' +
                ", typeName='" + typeName + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", orderId=" + orderId +
                ", initiatorId=" + initiatorId +
                ", initiatorImg='" + initiatorImg + '\'' +
                ", initiatorNike='" + initiatorNike + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                '}';
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public Integer getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Integer initiatorId) {
        this.initiatorId = initiatorId;
    }

    public String getInitiatorImg() {
        return initiatorImg;
    }

    public void setInitiatorImg(String initiatorImg) {
        this.initiatorImg = initiatorImg;
    }

    public String getInitiatorNike() {
        return initiatorNike;
    }

    public void setInitiatorNike(String initiatorNike) {
        this.initiatorNike = initiatorNike;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderAuthor() {
        return orderAuthor;
    }

    public void setOrderAuthor(String orderAuthor) {
        this.orderAuthor = orderAuthor;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }

    public String getOrderImgs() {
        return orderImgs;
    }

    public void setOrderImgs(String orderImgs) {
        this.orderImgs = orderImgs;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
