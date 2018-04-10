package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2018/1/16 15:59
 * Email：120760202@qq.com
 * FileName : 评论对象
 */

public class CommentEntity implements Serializable {
    //    {"commentContent":"不错不错","commentId":1,"createTime":"2018-01-16 15:54:54","initiatorId":5,"initiatorNike":"156****7367"}
    String commentContent, createTime, initiatorNike,initiatorImg;// : "http://orkypu4y4.bkt.clouddn.com/home1.jpg-60";
    Integer commentId, initiatorId;
    AnswerComment records;

    @Override
    public String toString() {
        return "CommentEntity{" +
                "commentContent='" + commentContent + '\'' +
                ", createTime='" + createTime + '\'' +
                ", initiatorNike='" + initiatorNike + '\'' +
                ", initiatorImg='" + initiatorImg + '\'' +
                ", commentId=" + commentId +
                ", initiatorId=" + initiatorId +
                ", records=" + records +
                '}';
    }

    public String getInitiatorImg() {
        return initiatorImg;
    }

    public void setInitiatorImg(String initiatorImg) {
        this.initiatorImg = initiatorImg;
    }

    public AnswerComment getRecords() {
        return records;
    }

    public void setRecords(AnswerComment records) {
        this.records = records;
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
}
