package com.narancommunity.app.entity;

/**
 * Writer：fancy on 2018/5/29 16;//49
 * Email：120760202@qq.com
 * FileName ; 更新对象
 */
public class UpdateInfo {
    String createtime;// 2017,
    String downloadUrl;// www.baidu.com,
    Integer id;// 1,
    Integer mustUpdate;// 0,
    String version;// 1.1,
    Integer versionCode;// 2,
    String versionDesc;// 版本升级

    @Override
    public String toString() {
        return "UpdateInfo{" +
                "createtime='" + createtime + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", id=" + id +
                ", mustUpdate=" + mustUpdate +
                ", version='" + version + '\'' +
                ", versionCode=" + versionCode +
                ", versionDesc='" + versionDesc + '\'' +
                '}';
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMustUpdate() {
        return mustUpdate;
    }

    public void setMustUpdate(Integer mustUpdate) {
        this.mustUpdate = mustUpdate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }
}
