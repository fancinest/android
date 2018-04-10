package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2017/12/21 14:52
 * Email：120760202@qq.com
 * FileName :
 */

public class AddressEntity implements Serializable{
    boolean nowMail;
    //    {"accountId":4,"city":"杭州市","county":"余杭区","createTime":"2018-01-05 21:50:02","mailAddress":"香樟公寓","mailId":10,"mailName":"a测试002","mailPhone":"15090019133","nowMail":true,"province":"浙江省"}
    String accountId, city, county, mailAddress, mailId, mailName, mailPhone, province, createTime;

    @Override
    public String toString() {
        return "AddressEntity{" +
                "nowMail=" + nowMail +
                ", accountId='" + accountId + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", mailAddress='" + mailAddress + '\'' +
                ", mailId='" + mailId + '\'' +
                ", mailName='" + mailName + '\'' +
                ", mailPhone='" + mailPhone + '\'' +
                ", province='" + province + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public boolean isDefault() {
        return nowMail;
    }

    public void setDefault(boolean aDefault) {
        nowMail = aDefault;
    }

    public boolean isNowMail() {
        return nowMail;
    }

    public void setNowMail(boolean nowMail) {
        this.nowMail = nowMail;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getMailName() {
        return mailName;
    }

    public void setMailName(String mailName) {
        this.mailName = mailName;
    }

    public String getMailPhone() {
        return mailPhone;
    }

    public void setMailPhone(String mailPhone) {
        this.mailPhone = mailPhone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
