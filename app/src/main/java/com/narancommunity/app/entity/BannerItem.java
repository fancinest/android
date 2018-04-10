package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2017/3/23 21:21
 * Email：120760202@qq.com
 * FileName : 首页轮播图数据对象
 */

public class BannerItem implements Serializable {
    String pubId;//"D33F01A9085244A0993EDE83D2B548C2"
    String pubUrl;//"111"
    String pubCover;//"/files/userfile/111/image/20170316/02818D0E732144BEA37ACF661A6212F2.jpg"
    String pubDesc;//"11"
    long createTime;//1489632314271
    int pubType;//2
    String pubOrder;//1

    @Override
    public String toString() {
        return "BannerItem{" +
                "pubId='" + pubId + '\'' +
                ", pubUrl='" + pubUrl + '\'' +
                ", pubCover='" + pubCover + '\'' +
                ", pubDesc='" + pubDesc + '\'' +
                ", createTime=" + createTime +
                ", pubType=" + pubType +
                ", pubOrder='" + pubOrder + '\'' +
                '}';
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    public String getPubUrl() {
        return pubUrl;
    }

    public void setPubUrl(String pubUrl) {
        this.pubUrl = pubUrl;
    }

    public String getPubCover() {
        return pubCover;
    }

    public void setPubCover(String pubCover) {
        this.pubCover = pubCover;
    }

    public String getPubDesc() {
        return pubDesc;
    }

    public void setPubDesc(String pubDesc) {
        this.pubDesc = pubDesc;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getPubType() {
        return pubType;
    }

    public void setPubType(int pubType) {
        this.pubType = pubType;
    }

    public String getPubOrder() {
        return pubOrder;
    }

    public void setPubOrder(String pubOrder) {
        this.pubOrder = pubOrder;
    }
}
