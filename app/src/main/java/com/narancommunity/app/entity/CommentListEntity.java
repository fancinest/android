package com.narancommunity.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Writer：fancy on 2018/1/16 16:01
 * Email：120760202@qq.com
 * FileName :
 */

public class CommentListEntity implements Serializable{
    Integer pageNum, pageSize, totalCount, totalPageNum;
    List<CommentEntity> comments;

    @Override
    public String toString() {
        return "CommentListEntity{" +
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

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }
}
