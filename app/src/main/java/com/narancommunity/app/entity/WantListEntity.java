package com.narancommunity.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Writer：fancy on 2018/1/17 15:26
 * Email：120760202@qq.com
 * FileName :
 */

public class WantListEntity implements Serializable{
    Integer pageNum, pageSize, totalCount, totalPageNum;
    List<WantEntity> applys;

    @Override
    public String toString() {
        return "WantListEntity{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPageNum=" + totalPageNum +
                ", applys=" + applys +
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

    public List<WantEntity> getApplys() {
        return applys;
    }

    public void setApplys(List<WantEntity> applys) {
        this.applys = applys;
    }
}
