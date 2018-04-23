package com.narancommunity.app.entity;

import java.util.List;

/**
 * Writer：fancy on 2018/4/19 15:39
 * Email：120760202@qq.com
 * FileName :
 */

public class BookCommentData {
    Integer totalPageNum;//
    Integer totalCount;// 6;
    Integer pageSize;// 3
    Integer pageNum;// 3
    List<BookComment> comments;
    List<BookComment> reviews;

    public List<BookComment> getReviews() {
        return reviews;
    }

    public void setReviews(List<BookComment> reviews) {
        this.reviews = reviews;
    }

    public Integer getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(Integer totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public List<BookComment> getComments() {
        return comments;
    }

    public void setComments(List<BookComment> comments) {
        this.comments = comments;
    }
}
