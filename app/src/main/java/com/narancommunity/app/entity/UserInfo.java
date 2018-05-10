package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * writer：fancy on 2017/2/16 17:04
 * classname : 用户信息
 */
public class UserInfo implements Serializable {
    //    String userPhotoId;//  A045F40E2FBB4C8798F0E2B5F6B6E3BA
    String remark;// 当你努力到无能为力的时候,上天就会为你打开一扇窗。
//    String userScWeibo;// ,
    String photo;// "http://on7ih6qzl.bkt.clouddn.com/1490601021561_813.jpg";
    //    String userId;//: 21B3FB57729E44D5824DFF903A873F04 ,
//    String username;// cncxm ,
//    String password;// 8ddcff3a80f4189ca1c9d4d902c3c909
    String accessToken;// "naran-b25c0ad4-91ab-4d2a-b385-9ac604aa7ed2";
    Integer accountId;//
    String nickName;// 明天会更好
    String phone;// 13957046668

    String birthday;// "1989-05-24";//
    String sex;// "男";
    String location;// "杭州仓前";
    String city;// "杭州"//;
    String certificationType;
//"accessToken":"091980a35dfd4534b7b40593bd2b9b4b","accountId":5,"nickName":"156****7367","phone":"15669097367"
//    "accessToken":"c552b7e726dc4e0591997ebdc15b8a04","accountId":1,"birthday":"1989-05-24","city":"杭州","location":"仓前","nickName":"zefeng.xu","phone":"18868732793","sex":"0"

    public UserInfo() {
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "photo='" + photo + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", accountId=" + accountId +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", location='" + location + '\'' +
                ", city='" + city + '\'' +
                ", certificationType='" + certificationType + '\'' +
                '}';
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
