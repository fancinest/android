package com.narancommunity.app.entity;

import java.util.List;

/**
 * Writer：fancy on 2018/1/8 20:46
 * Email：120760202@qq.com
 * FileName :
 */

public class Stationery {
    Integer pageSize, totalPageNum;
    List<StationeryEntity> stationerys;

    @Override
    public String toString() {
        return "Stationery{" +
                "pageSize=" + pageSize +
                ", totalPageNum=" + totalPageNum +
                ", stationerys=" + stationerys +
                '}';
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(Integer totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public List<StationeryEntity> getStationerys() {
        return stationerys;
    }

    public void setStationerys(List<StationeryEntity> stationerys) {
        this.stationerys = stationerys;
    }
}
