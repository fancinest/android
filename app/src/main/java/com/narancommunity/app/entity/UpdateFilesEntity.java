package com.narancommunity.app.entity;

import java.util.List;

/**
 * Writer：fancy on 2017/4/5 11:59
 * Email：120760202@qq.com
 * FileName :
 */

public class UpdateFilesEntity {
    List<String> data;

    @Override
    public String toString() {
        return "UpdateFilesEntity{" +
                "data=" + data +
                '}';
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
