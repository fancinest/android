package com.narancommunity.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Writer：fancy on 2018/1/30 14:10
 * Email：120760202@qq.com
 * FileName : 整体的对象
 */

public class WeekEntity implements Serializable {
    Integer pageNum, pageSize, totalCount, totalPageNum;//
    List<WeekRecEntity> contents;

    @Override
    public String toString() {
        return "AssistantEntity{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPageNum=" + totalPageNum +
                ", contents=" + contents +
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

    public List<WeekRecEntity> getContents() {
        return contents;
    }

    public void setContents(List<WeekRecEntity> contents) {
        this.contents = contents;
    }
}
