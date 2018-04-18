package com.narancommunity.app.entity;

/**
 * Writer：fancy on 2018/4/17 14:53
 * Email：120760202@qq.com
 * FileName : 那然快报
 */

public class TopLines {
    String createTime, toplineContent, toplineTitle, toplineType;
    Integer toplineId;

    @Override
    public String toString() {
        return "TopLines{" +
                "createTime='" + createTime + '\'' +
                ", toplineContent='" + toplineContent + '\'' +
                ", toplineTitle='" + toplineTitle + '\'' +
                ", toplineType='" + toplineType + '\'' +
                ", toplineId=" + toplineId +
                '}';
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getToplineContent() {
        return toplineContent;
    }

    public void setToplineContent(String toplineContent) {
        this.toplineContent = toplineContent;
    }

    public String getToplineTitle() {
        return toplineTitle;
    }

    public void setToplineTitle(String toplineTitle) {
        this.toplineTitle = toplineTitle;
    }

    public String getToplineType() {
        return toplineType;
    }

    public void setToplineType(String toplineType) {
        this.toplineType = toplineType;
    }

    public Integer getToplineId() {
        return toplineId;
    }

    public void setToplineId(Integer toplineId) {
        this.toplineId = toplineId;
    }
}
