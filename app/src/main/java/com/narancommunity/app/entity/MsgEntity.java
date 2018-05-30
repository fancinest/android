package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2018/5/29 20:41
 * Email：120760202@qq.com
 * FileName :
 */
public class MsgEntity implements Serializable{
    String createTime;//string	无
    String newsContent;//	string	无
    Integer newsId;//number	无
    String newsTitle;//

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
}
