package com.narancommunity.app.entity;

/**
 * Writer：fancy on 2018/5/16 10;//47
 * Email：120760202@qq.com
 * FileName ;//
 */

public class CompanyEntity {
    String certificate;//"http;////orkypu4y4.bkt.clouddn.com/1526436818800_334.jpg,http;////orkypu4y4.bkt.clouddn.com/1526436818810_104.jpg"
    String charger;//"fancy";
    String companyContent;//"奔驰公司";
    Integer companyId;//11;
    String companyImg;//"http;////orkypu4y4.bkt.clouddn.com/1526436808594_219.jpg";
    String companyName;//"梅赛德斯奔驰";
    String companyType;//"慈善机构";
    String establishTime;//"1938-05-16";
    String phone;//"15666666666"
    String province;//	是	string	省
    String city;//	是	string	市
    String county;//	是	string	区县
    String address;//	是	string	地址
    String ordinate;//是	string	纵坐标
    String abscissa;//

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "certificate='" + certificate + '\'' +
                ", charger='" + charger + '\'' +
                ", companyContent='" + companyContent + '\'' +
                ", companyId=" + companyId +
                ", companyImg='" + companyImg + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyType='" + companyType + '\'' +
                ", establishTime='" + establishTime + '\'' +
                ", phone='" + phone + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", address='" + address + '\'' +
                ", ordinate='" + ordinate + '\'' +
                ", abscissa='" + abscissa + '\'' +
                '}';
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrdinate() {
        return ordinate;
    }

    public void setOrdinate(String ordinate) {
        this.ordinate = ordinate;
    }

    public String getAbscissa() {
        return abscissa;
    }

    public void setAbscissa(String abscissa) {
        this.abscissa = abscissa;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    public String getCompanyContent() {
        return companyContent;
    }

    public void setCompanyContent(String companyContent) {
        this.companyContent = companyContent;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyImg() {
        return companyImg;
    }

    public void setCompanyImg(String companyImg) {
        this.companyImg = companyImg;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(String establishTime) {
        this.establishTime = establishTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
