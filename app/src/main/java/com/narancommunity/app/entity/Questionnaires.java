package com.narancommunity.app.entity;

import java.util.List;

/**
 * Writer：fancy on 2018/1/10 11:54
 * Email：120760202@qq.com
 * FileName :
 */

public class Questionnaires {
    List<MOptions> options;
    String orderType;// : "all"
    String questionnaireContent;// : "拥2有该物品的时间是多久？"
    String questionnaireId;//: 6
    String questionnaireOrder;// : 2
    String questionnaireRequired;// : True

    @Override
    public String toString() {
        return "Questionnaires{" +
                "options=" + options +
                ", orderType='" + orderType + '\'' +
                ", questionnaireContent='" + questionnaireContent + '\'' +
                ", questionnaireId='" + questionnaireId + '\'' +
                ", questionnaireOrder='" + questionnaireOrder + '\'' +
                ", questionnaireRequired='" + questionnaireRequired + '\'' +
                '}';
    }

    public List<MOptions> getOptions() {
        return options;
    }

    public void setOptions(List<MOptions> options) {
        this.options = options;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getQuestionnaireContent() {
        return questionnaireContent;
    }

    public void setQuestionnaireContent(String questionnaireContent) {
        this.questionnaireContent = questionnaireContent;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getQuestionnaireOrder() {
        return questionnaireOrder;
    }

    public void setQuestionnaireOrder(String questionnaireOrder) {
        this.questionnaireOrder = questionnaireOrder;
    }

    public String getQuestionnaireRequired() {
        return questionnaireRequired;
    }

    public void setQuestionnaireRequired(String questionnaireRequired) {
        this.questionnaireRequired = questionnaireRequired;
    }
}
