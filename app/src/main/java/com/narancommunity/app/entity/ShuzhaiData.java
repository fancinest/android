package com.narancommunity.app.entity;

import java.util.List;

/**
 * Writer：fancy on 2018/4/28 11:45
 * Email：120760202@qq.com
 * FileName :
 */

public class ShuzhaiData {
    Integer totalPageNum;//
    Integer totalCount;// 6;
    Integer pageSize;// 3
    Integer pageNum;// 3

    List<ShuzhaiItem> contents;

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

    public List<ShuzhaiItem> getContents() {
        return contents;
    }

    public void setContents(List<ShuzhaiItem> contents) {
        this.contents = contents;
    }
}
