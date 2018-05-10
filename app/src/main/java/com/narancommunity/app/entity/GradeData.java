package com.narancommunity.app.entity;

import java.util.List;

/**
 * Writer：fancy on 2018/5/3 11:58
 * Email：120760202@qq.com
 * FileName :
 */

public class GradeData {
    Integer totalPageNum;//
    Integer totalCount;// 6;
    Integer pageSize;// 3
    Integer pageNum;// 3

    RankEntity myRank;
    List<RankEntity> ranks;

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

    public RankEntity getMyRank() {
        return myRank;
    }

    public void setMyRank(RankEntity myRank) {
        this.myRank = myRank;
    }

    public List<RankEntity> getRanks() {
        return ranks;
    }

    public void setRanks(List<RankEntity> ranks) {
        this.ranks = ranks;
    }
}
