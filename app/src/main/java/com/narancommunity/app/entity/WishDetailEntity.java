package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2018/1/15 13:31
 * Email：120760202@qq.com
 * FileName :
 */

public class WishDetailEntity implements Serializable {
    boolean anonymous;    //boolean	无
    String backdropImg;//string	无
    String city;//string	无
    String commodityType;//	string	无
    String county;//string	无
    String createTime;//	string	无
    Integer initiatorId;//number	无
    String initiatorNike;//	string	无
    String mailAddress;//string	无
    String mailName;//string	无
    String mailPhone;//string	无
    String orderContent;//string	无
    Integer orderId;//number	无
    String orderImgs;//string	无
    String orderStatus;//	string	无
    String orderTitle;//string	无
    String orderType;//string	无
    String province;//string	无
    String updateTime;//string	无
    boolean willing;//boolean	无
    String initiatorImg;
    Integer collectionTimes;//	number	无
    Integer commentTimes;//	number	无
    Integer shareTimes;//	number	无
    String mailStatus;//订单确认状态 = going 开始配送了，就可以查看物流了 ，= success 确认收获了，完成了订单
    String mailCode;

//    "anonymous":true,"backdropImg":"http://orkypu4y4.bkt.clouddn.com/xz1.jpg","city":"杭州","commodityType":"家具","county":"滨江区","createTime":"2018-01-09 20:03:20","initiatorId":5,"initiatorNike":"156****7367","mailAddress":"浙江省杭州滨江区我在学校","mailName":"彭于晏","mailPhone":"15669097777","orderContent":"我叫王晗旭，我的家里非常困难，从小我的妈妈就告诉我，穷人的孩子早当家，所以我今天来到这个舞台上，大家帮帮忙","orderId":8,"orderImgs":"http://orkypu4y4.bkt.clouddn.com/1515499326704_622.jpg","orderStatus":"INITIAL","orderTitle":"我想要一个子衣柜","orderType":"WISH","province":"浙江省","updateTime":"2018-01-09 20:03:20","willing":true


    public String getMailCode() {
        return mailCode;
    }

    public void setMailCode(String mailCode) {
        this.mailCode = mailCode;
    }

    public String getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(String mailStatus) {
        this.mailStatus = mailStatus;
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

    public Integer getShareTimes() {
        return shareTimes;
    }

    public void setShareTimes(Integer shareTimes) {
        this.shareTimes = shareTimes;
    }

    @Override
    public String toString() {
        return "WishDetailEntity{" +
                "anonymous=" + anonymous +
                ", backdropImg='" + backdropImg + '\'' +
                ", city='" + city + '\'' +
                ", commodityType='" + commodityType + '\'' +
                ", county='" + county + '\'' +
                ", createTime='" + createTime + '\'' +
                ", initiatorId=" + initiatorId +
                ", initiatorNike='" + initiatorNike + '\'' +
                ", mailAddress='" + mailAddress + '\'' +
                ", mailName='" + mailName + '\'' +
                ", mailPhone='" + mailPhone + '\'' +
                ", orderContent='" + orderContent + '\'' +
                ", orderId=" + orderId +
                ", orderImgs='" + orderImgs + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderTitle='" + orderTitle + '\'' +
                ", orderType='" + orderType + '\'' +
                ", province='" + province + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", willing=" + willing +
                ", initiatorImg='" + initiatorImg + '\'' +
                '}';
    }

    public String getInitiatorImg() {
        return initiatorImg;
    }

    public void setInitiatorImg(String initiatorImg) {
        this.initiatorImg = initiatorImg;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getBackdropImg() {
        return backdropImg;
    }

    public void setBackdropImg(String backdropImg) {
        this.backdropImg = backdropImg;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(String commodityType) {
        this.commodityType = commodityType;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
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

    public String getInitiatorNike() {
        return initiatorNike;
    }

    public void setInitiatorNike(String initiatorNike) {
        this.initiatorNike = initiatorNike;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getMailName() {
        return mailName;
    }

    public void setMailName(String mailName) {
        this.mailName = mailName;
    }

    public String getMailPhone() {
        return mailPhone;
    }

    public void setMailPhone(String mailPhone) {
        this.mailPhone = mailPhone;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isWilling() {
        return willing;
    }

    public void setWilling(boolean willing) {
        this.willing = willing;
    }
}
