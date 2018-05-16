package com.narancommunity.app.net;

/**
 * @version V1.0
 * @description: ${TODO}(用一句话描述该文件做什么)
 * @create 2016/12/5
 */
public interface ResultCallback<Result extends com.narancommunity.app.net.Result<?>> {

    void onSuccess(Result result);

    void onFailure(Throwable throwable);

}
