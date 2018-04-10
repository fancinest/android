package com.narancommunity.app.entity;

import java.util.List;

/**
 * Writer：fancy on 2018/1/6 17:48
 * Email：120760202@qq.com
 * FileName :
 */

public class Address {
    Integer totalPageNum;//
    Integer totalCount;// 6;
    Integer pageSize;// 3
    Integer pageNum;// 3

    List<AddressEntity> mails;

    @Override
    public String toString() {
        return "Address{" +
                "totalPageNum=" + totalPageNum +
                ", totalCount=" + totalCount +
                ", pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", mails=" + mails +
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

    public List<AddressEntity> getMails() {
        return mails;
    }

    public void setMails(List<AddressEntity> mails) {
        this.mails = mails;
    }
}
