package com.narancommunity.app.entity;

import java.util.List;

/**
 * Writer：fancy on 2018/5/17 17:32
 * Email：120760202@qq.com
 * FileName :
 */

public class FootPrintData {
    Integer totalPageNum;//
    Integer totalCount;// 6;
    Integer pageSize;// 3
    Integer pageNum;// 3

    List<FootPrintEntity> footprints;

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

    public List<FootPrintEntity> getFootprints() {
        return footprints;
    }

    public void setFootprints(List<FootPrintEntity> footprints) {
        this.footprints = footprints;
    }
}
