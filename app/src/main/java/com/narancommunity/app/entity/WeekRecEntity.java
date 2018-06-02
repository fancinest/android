package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2018/2/1 15:31
 * Email：120760202@qq.com
 * FileName :
 */

public class WeekRecEntity implements Serializable {
    //    "contentBody": "我很好！！！我很好！！！我很好！！！我很好！！！我很好！！！我很好！！！我很好！！！我很好！！！我很好！！！",
//            "contentId": 12,
//            "contentImg": "http://orkypu4y4.bkt.clouddn.com/home1.png",
//            "contentTitle": "yyyyyyy怎么样？",
//            "contentType": "WEEKLY",
//            "createTime": "2018-01-03 16:05:39",
//            "recommendTime": "01月第二周"
    String contentBody, contentImg, contentTitle, contentType, createTime, recommendTime;
    Integer contentId, collectionTimes, commentTimes, likeTimes;

    @Override
    public String toString() {
        return "WeekRecEntity{" +
                "contentBody='" + contentBody + '\'' +
                ", contentImg='" + contentImg + '\'' +
                ", contentTitle='" + contentTitle + '\'' +
                ", contentType='" + contentType + '\'' +
                ", createTime='" + createTime + '\'' +
                ", recommendTime='" + recommendTime + '\'' +
                ", contentId=" + contentId +
                ", collectionTimes=" + collectionTimes +
                ", commentTimes=" + commentTimes +
                ", likeTimes=" + likeTimes +
                '}';
    }

    public Integer getCollectionTimes() {
        return collectionTimes;
    }

    public void setCollectionTimes(Integer collectionTimes) {
        this.collectionTimes = collectionTimes;
    }

    public Integer getCommentTimes() {
        return commentTimes;
    }

    public void setCommentTimes(Integer commentTimes) {
        this.commentTimes = commentTimes;
    }

    public Integer getLikeTimes() {
        return likeTimes;
    }

    public void setLikeTimes(Integer likeTimes) {
        this.likeTimes = likeTimes;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
    }

    public String getContentImg() {
        return contentImg;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(String recommendTime) {
        this.recommendTime = recommendTime;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }
}
