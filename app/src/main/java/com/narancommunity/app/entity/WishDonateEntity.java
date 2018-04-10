package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2018/1/18 21:22
 * Email：120760202@qq.com
 * FileName :
 */

public class WishDonateEntity implements Serializable {
    Integer accountId;//	number	无
    String accountImg;//	string	无
    String accountNike;//	string	无
    String applyAddress;//	string	无
    String applyContent;//	string	无
    Integer applyId;//	number	无
    String applyImgs;//	string	无
    String applyPrice;//	string	无
    String applyStatus;//	string	无
    String applyTitle;//	string	无
    String city;//	string	无
    String county;//	string	无
    String createTime;//string	无
    String estimateWeight;//	string	无
    Integer orderId;//number	无
    String province;//	string	无
    String updateTime;//	string	无
    boolean willing;//boolean	无
    String msg;//	string	无

    String commodityType;

    public String getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(String commodityType) {
        this.commodityType = commodityType;
    }

    @Override
    public String toString() {
        return "WishDonateEntity{" +
                "accountId=" + accountId +
                ", accountImg='" + accountImg + '\'' +
                ", accountNike='" + accountNike + '\'' +
                ", applyAddress='" + applyAddress + '\'' +
                ", applyContent='" + applyContent + '\'' +
                ", applyId=" + applyId +
                ", applyImgs='" + applyImgs + '\'' +
                ", applyPrice='" + applyPrice + '\'' +
                ", applyStatus='" + applyStatus + '\'' +
                ", applyTitle='" + applyTitle + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", createTime='" + createTime + '\'' +
                ", estimateWeight='" + estimateWeight + '\'' +
                ", orderId=" + orderId +
                ", province='" + province + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", willing=" + willing +
                ", msg='" + msg + '\'' +
                '}';
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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

    public String getApplyAddress() {
        return applyAddress;
    }

    public void setApplyAddress(String applyAddress) {
        this.applyAddress = applyAddress;
    }

    public String getApplyContent() {
        return applyContent;
    }

    public void setApplyContent(String applyContent) {
        this.applyContent = applyContent;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getApplyImgs() {
        return applyImgs;
    }

    public void setApplyImgs(String applyImgs) {
        this.applyImgs = applyImgs;
    }

    public String getApplyPrice() {
        return applyPrice;
    }

    public void setApplyPrice(String applyPrice) {
        this.applyPrice = applyPrice;
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

    public String getEstimateWeight() {
        return estimateWeight;
    }

    public void setEstimateWeight(String estimateWeight) {
        this.estimateWeight = estimateWeight;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public boolean isWilling() {
        return willing;
    }

    public void setWilling(boolean willing) {
        this.willing = willing;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
