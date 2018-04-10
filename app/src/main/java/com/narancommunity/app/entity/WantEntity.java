package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2018/1/17 15:27
 * Email：120760202@qq.com
 * FileName :
 */

public class WantEntity implements Serializable {
    //":{"accountId":4,"accountImg":"http://orkypu4y4.bkt.clouddn.com/home1.png-60","accountNike":"150****9133","applyId":3,"applyStatus":"INITIAL","applyTitle":"哈哈哈哈哈哈","createTime":"2018-01-17 15:26:15","orderId":16,"updateTime":"2018-01-17 15:26:15"}
    String accountImg, accountNike, applyStatus, applyTitle, createTime, updateTime;
    Integer accountId,applyId,orderId;

    @Override
    public String toString() {
        return "WantEntity{" +
                "accountImg='" + accountImg + '\'' +
                ", accountNike='" + accountNike + '\'' +
                ", applyStatus='" + applyStatus + '\'' +
                ", applyTitle='" + applyTitle + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", accountId=" + accountId +
                ", applyId=" + applyId +
                ", orderId=" + orderId +
                '}';
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

    public String getApplyTitle() {
        return applyTitle;
    }

    public void setApplyTitle(String applyTitle) {
        this.applyTitle = applyTitle;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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
