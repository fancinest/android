package com.narancommunity.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Writer：fancy on 2017/4/1 14:41
 * Email：120760202@qq.com
 * FileName :
 */

public class SearchHistoryEntity implements Serializable {
    List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
