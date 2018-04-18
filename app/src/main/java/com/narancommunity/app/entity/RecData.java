package com.narancommunity.app.entity;

import java.util.List;

/**
 * Writer：fancy on 2018/4/17 15:28
 * Email：120760202@qq.com
 * FileName :
 */

public class RecData {
    Integer totalPageNum;//
    Integer totalCount;// 6;
    Integer pageSize;// 3
    Integer pageNum;// 3
    List<RecEntity> orders;

    @Override
    public String toString() {
        return "NewsData{" +
                "totalPageNum=" + totalPageNum +
                ", totalCount=" + totalCount +
                ", pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", orders=" + orders +
                '}';
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

    public List<RecEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<RecEntity> orders) {
        this.orders = orders;
    }
}
