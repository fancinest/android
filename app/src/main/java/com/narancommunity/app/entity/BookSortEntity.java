package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2018/5/31 16:37
 * Email：120760202@qq.com
 * FileName :
 */
public class BookSortEntity implements Serializable{
    Integer classifyId, fatherId, priority;
    String classifyImg, classifyName, classifyType, classifyValue, remark;

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getClassifyImg() {
        return classifyImg;
    }

    public void setClassifyImg(String classifyImg) {
        this.classifyImg = classifyImg;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getClassifyType() {
        return classifyType;
    }

    public void setClassifyType(String classifyType) {
        this.classifyType = classifyType;
    }

    public String getClassifyValue() {
        return classifyValue;
    }

    public void setClassifyValue(String classifyValue) {
        this.classifyValue = classifyValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
