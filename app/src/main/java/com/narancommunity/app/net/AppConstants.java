package com.narancommunity.app.net;

import com.narancommunity.app.R;

/**
 * writer：fancy on 2017/1/6 11:48
 * classname : XXX
 */
public class AppConstants {

    public static final int[] colors = new int[]{
            R.color.login_bg, R.color.login_bg,
            R.color.login_bg, R.color.login_bg
    };

    public static final String DB_NAME = "NaranComDB";
    public static final String USER_INFO = "user_info";//用户所有信息存在此处
    //    public static final String USER_ACCESS_TOKEN = "user_access_token";//用户token
    public static final String USER_SEARCH_HISTORY_LIST = "user_search_history_list";
    public static final String USER_SEARCH_HISTORY_LIST_OF_LOVE = "user_search_history_list_of_love";//爱心搜索
    public static final String USER_SEARCH_HISTORY_LIST_OF_INDEX = "user_search_history_list_of_index";//首页搜索
    public static final String IS_FIRST_INSTALL = "is_first_install";//是否是第一次安装

    public static final String CODE_SUCCESS = "0000";//	服务器响应成功
    public static final String CODE_SYS_ERR = "E0000";//	系统异常
    public static final String CODE_VER_ERR = "E1000";//	您的验证码错误或已过期，请重新获取！
    public static final String CODE_AUTH_ERR = "E1001";//	用户验证错误
    public static final String CODE_SESSION_TIMEOUT = "E1002";//	会话超时，需重新登录
    public static final String CODE_ILLEGAL_CONTENT = "E1003";//	内容非法
    public static final String CODE_SMS_ERR = "E2000";//	短信发送错误
    public static final String CODE_XINGE_ERR = "E2001";//	消息推送错误
    public static final String CODE_ILLEGAL_OPERATION = "E9999";//	非法操作
    public static final String CODE_PINGPP_EXCEPTION = "E8002";//	Pingpp支付异常
    public static final String CODE_EXIST = "F1001";//	基地报名、申请关系已存在
    public static final String CODE_FARM_CODE_EXIST = "F1201";//	基地代码已存在
    public static final String CODE_FARM_CODE_ERROR = "F1202";//	基地代码格式错误
    public static final String CODE_USER_INFOEXIST = "U1001";//	注册信息已存在
    public static final String CODE_USER_OLDPWDERR = "U1002";//	原密码验证错误
    public static final long TIME_KEEP = 60;//请求超时时间
    public static final int IMAGE_SIZE_LIMITED = 3000;//图片大小限制


}
