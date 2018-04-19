package com.narancommunity.app.entity;

/**
 * Writer：fancy on 2018/4/19 16:23
 * Email：120760202@qq.com
 * FileName : 借书卡的具体某个记录
 */

public class BookLendCardEntity {
    String accountImg, accountNike, applyStatus, getTime, returnTime;
    Integer accountId, applyId, orderId;

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
}
