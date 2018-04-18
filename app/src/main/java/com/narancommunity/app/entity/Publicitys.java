package com.narancommunity.app.entity;

/**
 * Writer：fancy on 2018/4/17 14:55
 * Email：120760202@qq.com
 * FileName : 轮播图Banner
 */

public class Publicitys {
    String publicityCategory, createTime, publicityImg, publicityName, publicityType;
    Integer publicityId;

    @Override
    public String toString() {
        return "Publicitys{" +
                "createTime='" + createTime + '\'' +
                ", publicityImg='" + publicityImg + '\'' +
                ", publicityName='" + publicityName + '\'' +
                ", publicityCategory=" + publicityCategory +
                ", publicityId=" + publicityId +
                ", publicityType=" + publicityType +
                '}';
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPublicityImg() {
        return publicityImg;
    }

    public void setPublicityImg(String publicityImg) {
        this.publicityImg = publicityImg;
    }

    public String getPublicityName() {
        return publicityName;
    }

    public void setPublicityName(String publicityName) {
        this.publicityName = publicityName;
    }

    public String getPublicityCategory() {
        return publicityCategory;
    }

    public void setPublicityCategory(String publicityCategory) {
        this.publicityCategory = publicityCategory;
    }

    public Integer getPublicityId() {
        return publicityId;
    }

    public void setPublicityId(Integer publicityId) {
        this.publicityId = publicityId;
    }

    public String getPublicityType() {
        return publicityType;
    }

    public void setPublicityType(String publicityType) {
        this.publicityType = publicityType;
    }
}
