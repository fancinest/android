package com.narancommunity.app.entity;

/**
 * Writer：fancy on 2018/1/10 16:31
 * Email：120760202@qq.com
 * FileName :
 */

public class MOrders {
    String createTime;//	string	无
    Integer initiatorId;//	number	无
    Integer orderId;//number	无
    Integer collectionTimes, commentTimes, shareTimes;
    String initiatorImg, initiatorNike, orderContent, orderImgs, orderTitle, orderType, typeName, updateTime;
    String mailStatus;

    public String getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(String mailStatus) {
        this.mailStatus = mailStatus;
    }

    @Override
    public String toString() {
        return "MOrders{" +
                "createTime='" + createTime + '\'' +
                ", initiatorId=" + initiatorId +
                ", orderId=" + orderId +
                ", collectionTimes=" + collectionTimes +
                ", commentTimes=" + commentTimes +
                ", shareTimes=" + shareTimes +
                ", initiatorImg='" + initiatorImg + '\'' +
                ", initiatorNike='" + initiatorNike + '\'' +
                ", orderContent='" + orderContent + '\'' +
                ", orderImgs='" + orderImgs + '\'' +
                ", orderTitle='" + orderTitle + '\'' +
                ", orderType='" + orderType + '\'' +
                ", typeName='" + typeName + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }

    //    collectionTimes	number	无
//    commentTimes	number	无
//    createTime	string	无
//    initiatorId	number	无
//    initiatorImg	string	无
//    initiatorNike	string	无
//    orderContent	string	无
//    orderId	number	无
//    orderImgs	string	无
//    orderTitle	string	无
//    orderType	string	无
//    shareTimes	number	无
//    typeName	string	无
//    updateTime	string	无


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

    public Integer getShareTimes() {
        return shareTimes;
    }

    public void setShareTimes(Integer shareTimes) {
        this.shareTimes = shareTimes;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Integer initiatorId) {
        this.initiatorId = initiatorId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }

    public String getOrderImgs() {
        return orderImgs;
    }

    public void setOrderImgs(String orderImgs) {
        this.orderImgs = orderImgs;
    }

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
