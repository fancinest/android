package com.narancommunity.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Writer：fancy on 2018/1/10 11:52
 * Email：120760202@qq.com
 * FileName :
 */

public class AskPapers implements Serializable {
    Integer pageNum, totalPageNum, pageSize;
    List<Questionnaires> questionnaires;

    @Override
    public String toString() {
        return "AskPapers{" +
                "pageNum=" + pageNum +
                ", totalPageNum=" + totalPageNum +
                ", pageSize=" + pageSize +
                ", questionnaires=" + questionnaires +
                '}';
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(Integer totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<Questionnaires> getQuestionnaires() {
        return questionnaires;
    }

    public void setQuestionnaires(List<Questionnaires> questionnaires) {
        this.questionnaires = questionnaires;
    }
}
