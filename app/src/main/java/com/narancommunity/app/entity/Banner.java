package com.narancommunity.app.entity;

import java.io.Serializable;

/**
 * Writer：fancy on 2017/12/20 17:10
 * Email：120760202@qq.com
 * FileName :  banner图对象
 */

public class Banner implements Serializable {
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
