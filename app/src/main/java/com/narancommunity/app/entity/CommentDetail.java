package com.narancommunity.app.entity;

/**
 * Writer：fancy on 2018/4/23 14:20
 * Email：120760202@qq.com
 * FileName :
 */

public class CommentDetail {
    String commentContent, createTime, initiatorImg, initiatorNike;
    Integer commentId, initiatorId;

    AnswerComment records;

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

    public AnswerComment getRecords() {
        return records;
    }

    public void setRecords(AnswerComment records) {
        this.records = records;
    }
}
