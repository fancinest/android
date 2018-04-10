package com.narancommunity.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Writer：fancy on 2018/1/15 20:20
 * Email：120760202@qq.com
 * FileName :
 */
public class DonateDetailData implements Serializable {
    Boolean anonymous;//	boolean	无
    String city;//	string
    Integer collectionTimes;//	number	无
    Integer commentTimes;//	number	无
    Integer shareTimes;//	number	无
    String commodityType;//string	无
    String county;//string	无
    String createTime;//string	无
    Integer initiatorId;//number	无
    String initiatorNike;//string	无
    String orderContent;//	string	无
    Integer orderId;//number	无
    String orderImgs;//string	无
    String estimateWeight;//
    String initiatorImg;
    /**
     * INITIAL	待匹配
     GOING	匹配中
     WAITING	等待中
     SUCCESS	匹配成功
     REVOKE	撤回
     FAIL	匹配失败
     */
    String orderStatus;//	string	无
    String orderTitle;//	string	无
    String orderType;//WISH	心愿 DONATION	捐赠
    String province;//	string	无
    List<String> questionnaireOptions;//	无
    String updateTime;//	string	无
    Boolean willing;//是否愿意付费
//    {"code":"0000","data":{"anonymous":false,"city":"全国","collectionTimes":78,"commentTimes":5,"commodityType":"汽车","county":"全国","createTime":"2018-01-08 20:52:32","initiatorId":4,"initiatorNike":"150****9133","orderContent":"312","orderId":5,"orderImgs":"http://orkypu4y4.bkt.clouddn.com/1515415936505_720.jpg","orderStatus":"INITIAL","orderTitle":"3123","orderType":"DONATION","province":"全国","questionnaireOptions":["我是一时兴起","我是有钱任性","我是为了那然公益","我是为了那然公益","我是为了那然公益"],"shareTimes":5,"updateTime":"2018-01-08 20:52:32","willing":true},"msg":"成功！"}


    public String getInitiatorImg() {
        return initiatorImg;
    }

    public void setInitiatorImg(String initiatorImg) {
        this.initiatorImg = initiatorImg;
    }

    public String getEstimateWeight() {
        return estimateWeight;
    }

    public void setEstimateWeight(String estimateWeight) {
        this.estimateWeight = estimateWeight;
    }

    @Override
    public String toString() {
        return "DonateDetailData{" +
                "anonymous=" + anonymous +
                ", city='" + city + '\'' +
                ", collectionTimes=" + collectionTimes +
                ", commentTimes=" + commentTimes +
                ", commodityType='" + commodityType + '\'' +
                ", county='" + county + '\'' +
                ", createTime='" + createTime + '\'' +
                ", initiatorId=" + initiatorId +
                ", initiatorNike='" + initiatorNike + '\'' +
                ", orderContent='" + orderContent + '\'' +
                ", orderId=" + orderId +
                ", orderImgs='" + orderImgs + '\'' +
                ", estimateWeight='" + estimateWeight + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderTitle='" + orderTitle + '\'' +
                ", orderType='" + orderType + '\'' +
                ", province='" + province + '\'' +
                ", questionnaireOptions=" + questionnaireOptions +
                ", shareTimes=" + shareTimes +
                ", updateTime='" + updateTime + '\'' +
                ", willing=" + willing +
                '}';
    }

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    /**
     * INITIAL	待匹配
     GOING	匹配中
     WAITING	等待中
     SUCCESS	匹配成功
     REVOKE	撤回
     FAIL	匹配失败
     */
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

    public List<String> getQuestionnaireOptions() {
        return questionnaireOptions;
    }

    public void setQuestionnaireOptions(List<String> questionnaireOptions) {
        this.questionnaireOptions = questionnaireOptions;
    }

    public Integer getShareTimes() {
        return shareTimes;
    }

    public void setShareTimes(Integer shareTimes) {
        this.shareTimes = shareTimes;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getWilling() {
        return willing;
    }

    public void setWilling(Boolean willing) {
        this.willing = willing;
    }
}
