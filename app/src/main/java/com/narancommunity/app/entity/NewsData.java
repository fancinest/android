package com.narancommunity.app.entity;

import java.util.List;

/**
 * Writer：fancy on 2018/4/17 15:28
 * Email：120760202@qq.com
 * FileName :
 */

public class NewsData {
    Integer totalPageNum;//
    Integer totalCount;// 6;
    Integer pageSize;// 3
    Integer pageNum;// 3
    List<TopLines> toplines;

    @Override
    public String toString() {
        return "NewsData{" +
                "totalPageNum=" + totalPageNum +
                ", totalCount=" + totalCount +
                ", pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", toplines=" + toplines +
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

    public List<TopLines> getToplines() {
        return toplines;
    }

    public void setToplines(List<TopLines> toplines) {
        this.toplines = toplines;
    }
}
