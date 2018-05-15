package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * writer：fancy on 2017/2/16 17:04
 * classname : 用户信息
 */
public class UserInfo implements Serializable{
    Integer accountId;//
    String birthday;// "1989-05-24";//
    String certificationType;
    String city;// "杭州"//;
    String location;// "杭州仓前";
    String nickName;// 明天会更好
    String phone;// 13957046668
    String sex;// "男";
    String remark;// 当你努力到无能为力的时候,上天就会为你打开一扇窗。
    String photo;// "http://on7ih6qzl.bkt.clouddn.com/1490601021561_813.jpg";
    String accessToken;// "naran-b25c0ad4-91ab-4d2a-b385-9ac604aa7ed2";
    String identityCard;//身份证号
    Integer nowExperience, nowLove, topExperience;

    private String name;
    private String cardTime;
    private String province;// 省
    private String county;// 县
    private String cardPositive;// 身份证正面
    private String cardOpposite;// 身份证反面

    public UserInfo() {
    }

    public void UserInfo() {
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "accountId=" + accountId +
                ", birthday='" + birthday + '\'' +
                ", certificationType='" + certificationType + '\'' +
                ", city='" + city + '\'' +
                ", location='" + location + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", remark='" + remark + '\'' +
                ", photo='" + photo + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", identityCard='" + identityCard + '\'' +
                ", nowExperience=" + nowExperience +
                ", nowLove=" + nowLove +
                ", name='" + name + '\'' +
                ", cardTime='" + cardTime + '\'' +
                ", province='" + province + '\'' +
                ", county='" + county + '\'' +
                ", cardPositive='" + cardPositive + '\'' +
                ", cardOpposite='" + cardOpposite + '\'' +
                '}';
    }

    public Integer getTopExperience() {
        return topExperience;
    }

    public void setTopExperience(Integer topExperience) {
        this.topExperience = topExperience;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public Integer getNowExperience() {
        return nowExperience;
    }

    public void setNowExperience(Integer nowExperience) {
        this.nowExperience = nowExperience;
    }

    public Integer getNowLove() {
        return nowLove;
    }

    public void setNowLove(Integer nowLove) {
        this.nowLove = nowLove;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardTime() {
        return cardTime;
    }

    public void setCardTime(String cardTime) {
        this.cardTime = cardTime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCardPositive() {
        return cardPositive;
    }

    public void setCardPositive(String cardPositive) {
        this.cardPositive = cardPositive;
    }

    public String getCardOpposite() {
        return cardOpposite;
    }

    public void setCardOpposite(String cardOpposite) {
        this.cardOpposite = cardOpposite;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCertificationType() {
        return certificationType;
    }

    public void setCertificationType(String certificationType) {
        this.certificationType = certificationType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
