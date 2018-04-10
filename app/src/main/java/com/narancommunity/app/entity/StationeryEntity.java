package com.narancommunity.app.entity;

/**
 * Writer：fancy on 2018/1/8 20:48
 * Email：120760202@qq.com
 * FileName :
 */

public class StationeryEntity {
    //    "stationeryImg":"http://orkypu4y4.bkt.clouddn.com/xz1.jpg","stationeryTitle":"经典2"
    String stationeryImg, stationeryTitle;
    boolean isChecked = false;

    @Override
    public String toString() {
        return "StationeryEntity{" +
                "stationeryImg='" + stationeryImg + '\'' +
                ", stationeryTitle='" + stationeryTitle + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getStationeryImg() {
        return stationeryImg;
    }

    public void setStationeryImg(String stationeryImg) {
        this.stationeryImg = stationeryImg;
    }

    public String getStationeryTitle() {
        return stationeryTitle;
    }

    public void setStationeryTitle(String stationeryTitle) {
        this.stationeryTitle = stationeryTitle;
    }
}

