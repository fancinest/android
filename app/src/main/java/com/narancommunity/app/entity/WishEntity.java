package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2018/5/21 15:19
 * Email：120760202@qq.com
 * FileName :
 */
public class WishEntity implements Serializable{
    Integer initiatorId;// 5
    String initiatorImg;// "http;////orkypu4y4.bkt.clouddn.com/1526029618219_785.jpg-60"
    String initiatorNike;// "大大大大卷"
    String orderAuthor;// "中国证券投资基金业协会 组"
    String orderContent;// "生物入侵者生物入侵者三生三世枕上书呀"
    Integer orderId;// 61
    String orderImgs;// "http;////orkypu4y4.bkt.clouddn.com/1526468405658_268.jpg"
    String orderTitle;// "证券投资基金（第二版）下册"

    public Integer getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Integer initiatorId) {
        this.initiatorId = initiatorId;
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

    public String getOrderAuthor() {
        return orderAuthor;
    }

    public void setOrderAuthor(String orderAuthor) {
        this.orderAuthor = orderAuthor;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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


}
