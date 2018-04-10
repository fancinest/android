package com.narancommunity.app.entity;

/**
 * Writer：fancy on 2018/1/10 11:57
 * Email：120760202@qq.com
 * FileName : 问题选项
 */

public class MOptions {
    String optionContent;// : "我是一时兴起"
    String optionId;//: 15;
    Integer optionOrder;// : 1;
    String optionTitle;//: "A.一时兴起";
    String questionnaireId;//: 6;
    boolean isChecked = false;

    @Override
    public String toString() {
        return "MOptions{" +
                "optionContent='" + optionContent + '\'' +
                ", optionId='" + optionId + '\'' +
                ", optionOrder=" + optionOrder +
                ", optionTitle='" + optionTitle + '\'' +
                ", questionnaireId='" + questionnaireId + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public Integer getOptionOrder() {
        return optionOrder;
    }

    public void setOptionOrder(Integer optionOrder) {
        this.optionOrder = optionOrder;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }
}
