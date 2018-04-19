package com.narancommunity.app.entity;

import java.util.List;

/**
 * Writer：fancy on 2018/4/19 16:22
 * Email：120760202@qq.com
 * FileName :
 */

public class BookLendCardData {
    Integer totalPageNum;//
    Integer totalCount;// 6;
    Integer pageSize;// 3
    Integer pageNum;// 3

    List<BookLendCardEntity> applys;

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

    public List<BookLendCardEntity> getApplys() {
        return applys;
    }

    public void setApplys(List<BookLendCardEntity> applys) {
        this.applys = applys;
    }
}
