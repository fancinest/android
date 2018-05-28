package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2018/4/19 15:19
 * Email：120760202@qq.com
 * FileName : 预定的人（想要看的人的对象）
 */

public class OrderEntity implements Serializable {

    String accountImg, accountNike, applyStatus, getTime, returnTime;
    Integer accountId, applyId, orderId;
    String city, county, createTime, mailAddress, mailName, mailPhone, province, updateTime;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getMailName() {
        return mailName;
    }

    public void setMailName(String mailName) {
        this.mailName = mailName;
    }

    public String getMailPhone() {
        return mailPhone;
    }

    public void setMailPhone(String mailPhone) {
        this.mailPhone = mailPhone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getAccountImg() {
        return accountImg;
    }

    public void setAccountImg(String accountImg) {
        this.accountImg = accountImg;
    }

    public String getAccountNike() {
        return accountNike;
    }

    public void setAccountNike(String accountNike) {
        this.accountNike = accountNike;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "accountImg='" + accountImg + '\'' +
                ", accountNike='" + accountNike + '\'' +
                ", applyStatus='" + applyStatus + '\'' +
                ", getTime='" + getTime + '\'' +
                ", returnTime='" + returnTime + '\'' +
                ", accountId=" + accountId +
                ", applyId=" + applyId +
                ", orderId=" + orderId +
                '}';
    }
}

