package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2018/1/30 14:10
 * Email：120760202@qq.com
 * FileName : 具体的任务对象
 */

public class AssistantMissionEntity implements Serializable {
    //    {"activityId":8,"activityImg":"1516262858889_341.jpg","activityTitle":"测试7","commentTimes":0,"createTime":"2018-01-29 16:03:23","realApplyTimes":120,"shareTimes":2}
    Integer activityId, commentTimes, realApplyTimes, shareTimes, collectionTimes, likeTimes;
    ;//
    String activityImg, activityTitle, createTime;

    @Override
    public String toString() {
        return "AssistantMissionEntity{" +
                "activityId=" + activityId +
                ", commentTimes=" + commentTimes +
                ", realApplyTimes=" + realApplyTimes +
                ", shareTimes=" + shareTimes +
                ", collectionTimes=" + collectionTimes +
                ", likeTimes=" + likeTimes +
                ", activityImg='" + activityImg + '\'' +
                ", activityTitle='" + activityTitle + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public Integer getCollectionTimes() {
        return collectionTimes;
    }

    public void setCollectionTimes(Integer collectionTimes) {
        this.collectionTimes = collectionTimes;
    }

    public Integer getLikeTimes() {
        return likeTimes;
    }

    public void setLikeTimes(Integer likeTimes) {
        this.likeTimes = likeTimes;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getCommentTimes() {
        return commentTimes;
    }

    public void setCommentTimes(Integer commentTimes) {
        this.commentTimes = commentTimes;
    }

    public Integer getRealApplyTimes() {
        return realApplyTimes;
    }

    public void setRealApplyTimes(Integer realApplyTimes) {
        this.realApplyTimes = realApplyTimes;
    }

    public Integer getShareTimes() {
        return shareTimes;
    }

    public void setShareTimes(Integer shareTimes) {
        this.shareTimes = shareTimes;
    }

    public String getActivityImg() {
        return activityImg;
    }

    public void setActivityImg(String activityImg) {
        this.activityImg = activityImg;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
