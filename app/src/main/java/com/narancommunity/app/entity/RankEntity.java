package com.narancommunity.app.entity;

/**
 * Writer：fancy on 2018/5/3 13:50
 * Email：120760202@qq.com
 * FileName :
 */

public class RankEntity {
    Integer accountId;//	number	无
    String accountImg;//	string	无
    String accountName;//	string	无
    String accountRemark;//	string	无
    Integer rankId;//number	无
    Integer rankNum;//
    Integer rankScore;//	最右的数字（包括星星个数）
    String rankName;//	那然使者这种东西

    @Override
    public String toString() {
        return "RankEntity{" +
                "accountId=" + accountId +
                ", accountImg='" + accountImg + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountRemark='" + accountRemark + '\'' +
                ", rankId=" + rankId +
                ", rankNum=" + rankNum +
                ", rankScore=" + rankScore +
                ", rankName=" + rankName +
                '}';
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountImg() {
        return accountImg;
    }

    public void setAccountImg(String accountImg) {
        this.accountImg = accountImg;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountRemark() {
        return accountRemark;
    }

    public void setAccountRemark(String accountRemark) {
        this.accountRemark = accountRemark;
    }

    public Integer getRankId() {
        return rankId;
    }

    public void setRankId(Integer rankId) {
        this.rankId = rankId;
    }

    public Integer getRankNum() {
        return rankNum;
    }

    public void setRankNum(Integer rankNum) {
        this.rankNum = rankNum;
    }

    public Integer getRankScore() {
        return rankScore;
    }

    public void setRankScore(Integer rankScore) {
        this.rankScore = rankScore;
    }


}
