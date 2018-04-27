package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2018/1/16 17:36
 * Email：120760202@qq.com
 * FileName : 回复他人的评论的具体的对象
 */

public class Comments implements Serializable {
    String commentContent, createTime, initiatorImg, initiatorNike, recipientNike;
    Integer commentId, initiatorId, recipientId, likeTimes;

    @Override
    public String toString() {
        return "Comments{" +
                "commentContent='" + commentContent + '\'' +
                ", createTime='" + createTime + '\'' +
                ", initiatorImg='" + initiatorImg + '\'' +
                ", initiatorNike='" + initiatorNike + '\'' +
                ", recipientNike='" + recipientNike + '\'' +
                ", commentId=" + commentId +
                ", initiatorId=" + initiatorId +
                ", recipientId=" + recipientId +
                ", likeTimes=" + likeTimes +
                '}';
    }

    public Integer getLikeTimes() {
        return likeTimes;
    }

    public void setLikeTimes(Integer likeTimes) {
        this.likeTimes = likeTimes;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getRecipientNike() {
        return recipientNike;
    }

    public void setRecipientNike(String recipientNike) {
        this.recipientNike = recipientNike;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Integer initiatorId) {
        this.initiatorId = initiatorId;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }
}
