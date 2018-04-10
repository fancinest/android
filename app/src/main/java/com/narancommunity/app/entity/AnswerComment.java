package com.narancommunity.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Writer：fancy on 2018/1/16 17:33
 * Email：120760202@qq.com
 * FileName : 回复的评论整体对象
 */

public class AnswerComment implements Serializable{
    Integer pageNum, pageSize, totalCount, totalPageNum;
    List<Comments> comments;

    @Override
    public String toString() {
        return "AnswerComment{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPageNum=" + totalPageNum +
                ", comments=" + comments +
                '}';
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(Integer totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }
}
