package com.narancommunity.app.entity;

/**
 * Writer：fancy on 2018/4/26 14:49
 * Email：120760202@qq.com
 * FileName :
 */

public class Zan {
    String accountId;
    boolean isMe;
    Integer recordId;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public String getAccountId() {

        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
