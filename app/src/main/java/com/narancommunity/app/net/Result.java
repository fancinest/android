package com.narancommunity.app.net;

import android.util.Log;

import com.narancommunity.app.MApplication;

import java.net.SocketTimeoutException;

import retrofit2.Response;

/**
 * @version V1.0
 * @description: ${TODO}(用一句话描述该文件做什么)
 * @create 2016/12/2
 */
public class Result<T> {
    public String code;
    public String msg;
    private T data;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static <T> void onResponse(Response<Result<T>> response, ResultCallback<Result<T>> callback) {
//    public static <Data extends Result<Object>> void onResponse (Response<Data> response, ResultCallback<Data> callback) {

        if (response == null) {
            if (callback != null) {
                callback.onFailure(new Throwable("cannot response"));
            }
            return;
        }
        Result<T> result = response.body();
//        Data result = response.body();
        if (result != null) {
            Log.e("fancy", result.toString());
            if (result.getCode() != null) {
                if (result.getCode().equals(AppConstants.CODE_SUCCESS)) {
                    if (callback != null) {
                        callback.onSuccess(result);
                    }
                } else if (result.getCode().equals(AppConstants.CODE_AUTH_ERR)) {
                    MApplication.logout(true);
                    callback.onFailure(new Throwable(result.getMsg()));
                } else if (result.getCode().equals(AppConstants.CODE_NEED_AUTHORISE)) {
                    callback.onFailure(new Throwable("此操作需要实名认证哦！"));
                } else
                    callback.onFailure(new Throwable(result.getMsg()));
            } else {
                callback.onFailure(new Throwable(result.getMsg()));
            }
        } else {
            if (callback != null) {
                callback.onFailure(new Throwable("啊哦，服务器开小差了"));
            }
        }

    }

    public static <Data> void onFailure(Throwable throwable, ResultCallback<Result<Data>> callback) {
//    public static <Data extends Result<Object>> void onFailure(Throwable throwable, ResultCallback<Data> callback) {

        if (throwable == null) {
            if (callback != null) {
                callback.onFailure(new Throwable("throwable == null"));
            }
            return;
        }

        if (callback != null) {
            if (throwable instanceof SocketTimeoutException)
                callback.onFailure(new Throwable("网络不给力！"));
            else
                callback.onFailure(throwable);
        }

    }

}
