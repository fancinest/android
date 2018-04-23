package com.narancommunity.app.net;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * 网络配置地址
 */
public class NRConfig {
    public static final String NORMAL_TOPIC_CONTENT_JUMP = "/rest/common/topic/view/";//跳转H5  参数
    public static final String NORMAL_PARAMETER_JUMP = "/rest/common/product/parameter/view/";//跳转H5  参数
    public static final String NORMAL_PRODUCT_JUMP = "/rest/common/product/view/";//跳转H5  产品的通用路径
    public static final String NORMAL_FARM_JUMP = "/rest/common/farm/view/";//跳转H5  FARM的通用路径
    public static final String NORMAL_JUMP = "/rest/common/article/view/";//跳转H5的通用路径
    public static final String NORMAL_NOTICE_JUMP = "/rest/common/notice/view/";//跳转公告H5的通用路径
    public static final String INDEX_INDEX = "rest/home";
    public static final String INDEX_FARM_LIST = "/rest/home/farm/list";//首页列表
    public static final String INDEX_ACTIVITY_LIST = "/rest/activity/list";//首页活动列表
    public static final String INDEX_ABOUT = "/rest/home/about_us";//关于
    public static final String INDEX_NEWS = "rest/news/list";//首页新闻列表获取
    public static final String INDEX_INDEX_WILLFARM_MORE = "rest/farm/list";//首页即将入住的更多
    public static final String INDEX_INDEX_WILLFARM_COUNT = "rest/farm/count";//首页即将入住数量
    public static final String INDEX_FARM_DETAIL = "rest/farm/details";//生产者详情获取
    public static final String INDEX_FARM_PRODUCT = "rest/farm/product";//生产者详情中产品
    public static final String INDEX_FARM_NOTICE = "rest/farm/notice/list";//生产者详情中notice
    public static final String INDEX_FARM_DYNAMIC = "rest/farm/dynamic";//生产者详情中动态
    public static final String INDEX_FARM_ADD_ATTENTION = "rest/farm/concern/add";//生产者详情中添加关注
    public static final String INDEX_FARM_DEL_ATTENTION = "rest/farm/concern/del";//生产者详情中取消关注
    public static final String INDEX_FARM_PRODUCT_DETAIL = "rest/farm/product/details";//生产者详情获取
    public static final String FARM_HOME = "rest/farm/home";//生产者主页获取
    public static final String FARM_HOME_ALL_NOTICE = "rest/farm/notice/list";//生产者所有notice获取
    public static final String FARM_HOME_ALL_FARM = "rest/farm/list";//生产者所有Farm获取
    public static final String FARM_PRODUCT_LIST = "rest/farm/product/list";//分类产品获取

    public static final String SELECT_FARM_LIST = "rest/optional/list";//自选Farm获取
    public static final String SELECT_DRAG_LIST = "rest/optional/drag";//自选对象拖动
    public static final String SELECT_SEARCH_LIST = "rest/optional/list/farm";//自选生产者搜索列表
    public static final String SELECT_ADD_ATTENTION = "rest/optional/add";//自选生产者搜索列表
    public static final String SELECT_DEL_ATTENTION_LIST = "rest/optional/del/some";//自选删除（包括多选）

    public static final String COMMUNITY_TOPIC_OR_MEMBER_LIST = "rest/community/list";//社区话题列表/社区监督列表
    public static final String MINE_LIST = "rest/user/list/participate";//我的列表
    public static final String LOGIN_LOGOUT = "/api/account/logout";//登出

    public static final String RELEASE_TOPIC = "rest/community/publish";//发布话题

    public static final String FARM_JUBAO = "rest/farm/report";//发布话题

    public static final String MINE_FOLLOW_LIST = "rest/user/follow";//关注列表
    public static final String MINE_COLLECT_LIST = "rest/user/collect";//收藏列表

    public static final String PRODUCT_ADD_ATTENTION = "rest/farm/product/concern/add";//产品详情中添加关注
    public static final String PRODUCT_DEL_ATTENTION = "rest/farm/product/concern/del";//产品详情中取消关注

    public static final String DYNAMIC_COMMENT_LIST = "rest/comment/list";//动态详情里的评论列表啊
    public static final String DYNAMIC_ADD_COMMENT = "rest/comment/publish";//评论

    public static final String COMMENT_ZAN = "rest/comment/concern/add";//给评论点赞
    public static final String COMMENT_CANCEL_ZAN = "rest/comment/concern/del";//取消评论点赞

    public static final String DYNAMIC_ZAN = "rest/usercenter/article/concern/add";//文章点赞
    public static final String DYNAMIC_CANCEL_ZAN = "rest/usercenter/article/concern/del";//取消评论点赞
    public static final String USER_SAVE_INFO = "rest/usercenter/saveInfo";//保存用户信息

    public static final String FEED_BACK = "rest/user/feedback/add";//反馈
    public static final String SEARCH = "rest/common/search";//首页搜索

    public static final String CHECK_CONTENT_RELATION = "rest/common/article/relation";//查询本文的收藏点赞状态
    public static final String GET_SEARCH_PRODUCT_HOT = "rest/common/keyword/list";//获得搜索关键字

    public static final String DEL_COMMENT = "rest/comment/delete";//删除评论

    public static final String INDEX_SEARCH_FARM_OR_PRODUCT = "rest/farm/home/search";//删除评论

    public static final String UPDATE = "rest/default/checkForUpdates";//软件更新

    public static final String SHAREADDTIMES = "rest/farm/share";//分享


    public static final String URL_BASE = ""/*"http://" + IP*/;

    /**
     * 借阅图书
     */
    public static final String URL_BOOK_LEND = "/api/book/apply/get";
    /**
     * 想要看
     */
    public static final String URL_BOOK_WANT_SEE = "/api/book/apply/want";

    /**
     * 评论详情
     */
    public static final String URL_COMMENT_DETAIL = "api/record/comment/detail/page";
    /**
     * 图书书评添加
     */
    public static final String URL_BOOKREVIEW_ADD_COMMENT = "/api/book/review/add";
    /**
     * 图书书评列表
     */
    public static final String URL_BOOKREVIEW_LIST = "/api/book/review/page";
    /**
     * 图书相关书籍推荐(三个那个)
     */
    public static final String URL_BOOK_RELATIVE_REC = "/api/book/relevant/page";
    /**
     * 图书借书卡
     */
    public static final String URL_BOOK_LEND_CARD = "/api/book/apply/page";
    /**
     * 图书想要看的人
     */
    public static final String URL_BOOK_ORDERER = "/api/book/apply/want/page";
    /**
     * 图书详情
     */
    public static final String URL_BOOK_DETAIL = "/api/book/order/by/id";
    /**
     * 爱心书屋书单推荐
     */
    public static final String URL_HOUSE_REC = "/api/book/recommend/page";
    /**
     * 爱心书屋热门推荐
     */
    public static final String URL_HOUSE_HOT_REC = "/api/book/hot/page";
    /**
     * 爱心书屋快报
     */
    public static final String URL_HOUSE_TOPLINES = "/api/topline/book/page";
    /**
     * 爱心书屋banner
     */
    public static final String URL_HOUSE_BANNER = "/api/publicity/book/page";
    /**
     * 捐书
     */
    public static final String URL_DONATE_BOOK = "/api/book/donation";
    /**
     * 扫描获取书库中的书的信息
     */
    public static final String URL_GET_BOOK_DETAIL = "/api/book/by/isbn";
    /**
     * 获取首页最新发布书籍列表
     */
    public static final String URL_BOOK_LIST = "/api/book/order/page";
    /**
     * 获取首页那然快报列表
     */
    public static final String URL_TOPLINE_LIST = "/api/topline/page";
    /**
     * 获取首页banner列表
     */
    public static final String URL_BANNER_LIST = "/api/publicity/page";
    /**
     * 获取专题报道列表
     */
    public static final String URL_COLLECT_LIST = "/api/record/collection/content";

    /**
     * 获取专题报道列表
     */
    public static final String URL_GET_ESSAY_LIST = "/api/record/collection/content";

    /**
     * 获取专题报道列表
     */
    public static final String URL_SUBJECT_REPORT_LIST = "/api/content/special/coverage/page";
    /**
     * 获取每周推荐列表
     */
    public static final String URL_WEEK_REC_LIST = "/api/content/weekly/recommendation/page";
    /**
     * 获取援助任务列表
     */
    public static final String URL_GET_ASSISTANT_LIST = "/api/activity/aid/page";
    /**
     * 反馈
     */
    public static final String URL_FEEDBACK = "/api/order/feedback/change";
    /**
     * 心愿捐赠确认
     */
    public static final String URL_WISH_DONATE_CONFIRM = "/api/order/wish/confirm";
    /**
     * 撤回心愿的捐赠
     */
    public static final String URL_WISH_DONATE_WITHDRAW = "/api/order/apply/wish/withdraw";
    /**
     * 心愿，捐赠的人
     */
    public static final String URL_WISH_DONATE = "/api/order/apply/wish/detail";
    /**
     * 帮他实现
     */
    public static final String URL_HELP_IT = "/api/order/apply/wish";
    /**
     * 是否收藏
     */
    public static final String URL_IS_COLLECT = "/api/record/collection/order/with/me";
    /**
     * 心愿确认发货
     */
    public static final String URL_WISH_SEND_CONFIRM = "/api/order/apply/wish/deliver";
    /**
     * 捐赠申请详细确认
     */
    public static final String URL_DONATE_WISH_CONFIRM = "/api/order/apply/donation/confirm";
    /**
     * 捐赠单申请人的订单撤销
     */
    public static final String URL_DONATE_APPLY_WITHDRAW = "/api/order/apply/donation/withdraw";
    /**
     * 申请人的订单详细
     */
    public static final String URL_ORDER_APPLY_DETAIL = "/api/order/apply/detail";
    /**
     * 我与订单的关系
     */
    public static final String URL_ORDER_RELATION = "/api/order/with/me";
    /**
     * 对捐赠的表示想要
     */
    public static final String URL_I_WANT = "/api/order/apply/donation";
    /**
     * 获取评论列表/图书评论列表
     */
    public static final String URL_GET_COMMENT_LIST = "/api/record/comment/order/page";
    /**
     * 获取想要的人的列表(捐赠)
     */
    public static final String URL_GET_WANT_LIST = "/api/order/apply/donation/page";
    /**
     * 获取捐赠详情
     */
    public static final String URL_GET_DONATE_DETAIL = "/api/order/donation/detail";
    /**
     * 收藏
     */
    public static final String URL_COLLECT = "/api/record/collection/order";
    /**
     * 新增评论
     */
    public static final String URL_ADD_COMMENT = "/api/record/comment/order";
    /**
     * 获取心愿详情
     */
    public static final String URL_GET_WISH_DETAIL = "/api/order/wish/detail";
    /**
     * 获取问题
     */
    public static final String URL_GET_QUESTIONS = "/api/questionnaire/page/5";
    /**
     * 心愿墙列表
     */
    public static final String URL_WISH_WALL = "/api/order/wish/page";
    /**
     * 捐赠墙列表
     */
    public static final String URL_DONATE_WALL = "/api/order/donation/page";
    /**
     * 发布捐赠
     */
    public static final String URL_DONATE = "/api/order/donation";
    /**
     * 发布心愿
     */
    public static final String URL_MAKE_WISH = "/api/order/wish";
    /**
     * 获取信纸
     */
    public static final String URL_GET_STATIONERY = "/api/order/stationery";

    // 上传照片 http://zuns.iask.in:8082/rest/usercenter/photos/add
    public static final String URL_ADD_PHOTO = URL_BASE
            + "/api/file/upload";

    //轮播图
    public static final String URL_ROUND_PIC = "/api/publicity/page";
    //地址列表
    public static final String URL_ADDRESS_LIST = "/api/mail/page";
    //常用地址
    public static final String URL_DEFAULT_ADDRESS = "/api/mail/now";
    //新增/修改地址
    public static final String URL_ADD_MODIFY_ADDRESS = "/api/mail/change";
    //删除地址
    public static final String URL_DEL_ADDRESS = "/api/mail/delete";
    //设置常用地址
    public static final String URL_SET_DEFAULT_ADDRESS = "/api/mail/change/now";

    //那然快报
    public static final String URL_EXPRESS = "/api/topline/page";
    //订单
    public static final String URL_ORDER = "/api/order/page";
    //实名认证
    public static final String URL_AUTHORISE = "/api/account/certification";
    // 获取验证码
    public static final String URL_GET_CODE = URL_BASE
            + "/api/account/register/verifycode";
    // 注册
    public static final String URL_REG = URL_BASE
            + "/api/account/register";
    // 登录http://nrc.guru/rest/account/login
    public static final String URL_LOGIN = URL_BASE + "/api/account/login";
    // 用户中心http://nrc.guru/rest/u
    public static final String URL_USER_INFO = URL_BASE + "/rest/u";
    // 用户关注 http://nrc.guru/rest/u/GZ
    public static final String URL_USER_ATTEND = URL_BASE + "/rest/u/GZ";
    // 用户发布 http://nrc.guru/rest/u/FB
    public static final String URL_USER_PUBLISH = URL_BASE + "/rest/u/FB";
    // 用户参与 http://nrc.guru/rest/u/CY/{contentType}/{page}
    public static final String URL_USER_JOIN = URL_BASE + "/rest/u/CY";
    // 个人信息http://lazycheng.com:8787/rest/u/info/{userId}
    public static final String URL_USER_INFO_DETAIL = URL_USER_INFO + "/info";
    // 修改个人信息 http://lazycheng.com:8787/rest/usercenter/saveInfo
    public static final String URL_FIX_USER_INFO = URL_BASE
            + "/rest/usercenter/saveInfo";
    // 入驻基地 http://zuns.iask.in:8082/rest/usercenter/save_farm
    public static final String URL_CREAT_FARM = URL_BASE
            + "/rest/usercenter/save_farm";
    // 入驻基地http://zuns.iask.in:8082/rest/farm/getArea
    public static final String URL_AREA = URL_BASE + "/rest/farm/getArea";
    // 入驻基地http://lazycheng.com:8787/rest/usercenter/farm/apply/{id}
    public static final String URL_JIDI = URL_BASE + "/rest/usercenter/farm";
    // 我的基地 URL: http://nrc.guru/rest/u/farm/{type}/{page}
    public static final String URL_MY_JIDI_LIST = URL_USER_INFO + "/farm";
    // 基地详情 http://nrc.guru/rest/farm/details/{farmId}
    public static final String URL_MY_JIDI_DETAILS = URL_BASE
            + "/rest/farm/details";
    public static final String APPLY = "apply";
    // 基地相册 http://zuns.iask.in:8888/rest/farm/photos/{farmId}/{page}
    public static final String URL_JIDI_PHOTOS = URL_BASE + "/rest/farm/photos";
    // 删除照片 http://nrc.guru/rest/usercenter/photos/{id}/del
    public static final String URL_DEL_PHOTO = URL_BASE
            + "/rest/usercenter/photos";

    // 发布基地爆料评价 http://115.199.107.154:8082/rest/usercenter/save_comment
    public static final String URL_SAVE_COMMENT = URL_BASE
            + "/rest/usercenter/save_comment";
    // 基地产品
    // http://zuns.iask.in:8888/rest/farm/product/68140279266943F49BEBB753FA6366F5/1
    public static final String URL_JIDI_PRODUCTS = URL_BASE
            + "/rest/farm/product";
    // 发布产品
    // http://zuns.iask.in:8082/rest/usercenter/product/add
    public static final String URL_JIDI_ADD_PRODUCT = URL_BASE
            + "/rest/usercenter/product/add";
    // 删除产品 http://zuns.iask.in:8082/rest/usercenter/product/{id}/del
    public static final String URL_JIDI_DEL_PRODUCT = URL_BASE
            + "/rest/usercenter/product";
    // 基地成员http://zuns.iask.in:8888/rest/farm/users/{farmId}/{page}
    public static final String URL_JIDI_USERS = URL_BASE + "/rest/farm/users";
    // 基地管理-社员列表
    // http://zuns.iask.in:8082/rest/usercenter/farm/manage/{farmId}/{page}
    public static final String URL_MY_JIDI_USERS = URL_BASE
            + "/rest/usercenter/farm/manage";
    // 成员删除 http://zuns.iask.in:8082/rest/usercenter/farm/del/{farmId}/{userId}
    public static final String URL_DEL_USER = URL_BASE
            + "/rest/usercenter/farm/del";
    // 审核基地社员http://zuns.iask.in:8082/rest/usercenter/farm/fr/{farmId}/{userId}/{status}
    public static final String URL_CHECK_USER = URL_BASE
            + "/rest/usercenter/farm/fr";
    // 基地活动 http://zuns.iask.in:8082/rest/farm/activity/{farmId}/{page}
    public static final String URL_JIDI_ACTIVITY = URL_BASE
            + "/rest/farm/activity";
    // 参加活动成员 http://zuns.iask.in:8082/rest/activity/get_apply_users/{id}
    public static final String URL_JOIN_ACTIVITY_USERS = URL_BASE
            + "/rest/activity/get_apply_users";
    // 基地公告--即信息披露 http://zuns.iask.in:8082/rest/farm/notice/{farmId}/{page}
    public static final String URL_JIDI_NOTICE = URL_BASE + "/rest/farm/notice";
    // 基地公告详情--即信息披露详情
    // http://zuns.iask.in:8082/rest/farm/notice/detailsHtml/{id}
    public static final String URL_JIDI_NOTICE_DETAILS_HTML = URL_JIDI_NOTICE
            + "/detailsHtml";

    // 基地粉丝--http://zuns.iask.in:8082/rest/farm/fans/{farmId}/{page}
    public static final String URL_JIDI_FANS = URL_BASE + "/rest/farm/fans";
    // 基地产品分类---即经营范围 http://zuns.iask.in:8082/rest/farm/farm_product_type
    public static final String URL_JIDI_PRODUCT_TYPE = URL_BASE
            + "/rest/farm/farm_product_type";
    // 用户博客 http://nrc.guru/rest/u/MY/{contentType}/{page}
    public static final String URL_USER_BLOG = URL_BASE + "/rest/u/MY";
    // 用户收藏博客 http://nrc.guru/rest/u/SC
    public static final String URL_USER_COLLECTION = URL_BASE + "/rest/u/SC";
    // 获得文章分类 http://zuns.iask.in:8082/rest/common/getClassifys
    // 锁定文章 http://nrc.guru/rest/usercenter/article/{contentId}/locked/{locked}
    public static final String URL_USER_SUO = URL_BASE
            + "/rest/usercenter/article";
    // 删除文章 URL: http://nrc.guru/rest/usercenter/article/{contentId}/delete
    public static final String URL_USER_DELETE = URL_BASE
            + "/rest/usercenter/article";

    // 我的粉丝 http://nrc.guru/rest/u/fans
    public static final String URL_USER_FANS = URL_BASE + "/rest/u/fans";
    // .取消,关注http://nrc.guru/rest/usercenter/user/concern/{objectId}/{add}
    public static final String URL_USER_FANS_ATTENT = URL_BASE
            + "/rest/usercenter/user/concern";

    // 我的那然币 http://nrc.guru/rest/usercenter/grow
    public static final String URL_GROW = URL_BASE + "/rest/usercenter/grow";
    // 成长足迹 http://nrc.guru/rest/usercenter/grow/footmark/{page}
    public static final String URL_FOOTMARK = URL_GROW + "/footmark";
    // 规则说明 http://zuns.iask.in:8888/rest/usercenter/growHtml
    public static final String URL_GROWHTML = URL_BASE
            + "/rest/usercenter/growHtml";

    // 文章分类 http://zuns.iask.in:8082/rest/common/getClassifys
    public static final String URL_CONTENT_TYPE = URL_BASE
            + "/rest/common/getClassifys";
    // 文章列表http://zuns.iask.in:8082/rest/doc/{contentType}/{contentClassify}/{keyword}/{page}
    public static final String URL_CONTENT_LIST = URL_BASE + "/rest/doc";

    // 提交图片http://zuns.iask.in:8082/rest/fileservice/upload
    public static final String URL_UPLOAD = URL_BASE
            + "/rest/fileservice/upload";
    // 发表博客 http://zuns.iask.in:8082/rest/usercenter/article/{contentType}/save
    public static final String URL_UPLOAD_BLOG = URL_BASE
            + "/rest/usercenter/article";
    // 资讯列表 :
    // http://zuns.iask.in:8082/rest/doc/{contentClassify}/{keyword}/{page}
    public static final String URL_ARTIC = URL_BASE + "/rest/doc";

    // 资讯列表 : http://zuns.iask.in:8082/rest/common/q/{qtype}/{keyword}/{page}
    public static final String URL_SEARCH = URL_BASE + "/rest/common/q";
    public static final String URL_USER_GROUP = URL_BASE
            + "/rest/huanxin/getUserGroups";

    // 社区首页 http://zuns.iask.in:8082/rest/community/list/{page}
    public static final String URL_FORUM_HOME = URL_BASE
            + "/rest/community/list";
    // 活跃社员列表 http://zuns.iask.in:8082/rest/community/active_member/{page}
    public static final String URL_ACTIVE_MEMBER = URL_BASE
            + "/rest/community/active_member";
    // 确认报名
    // http://115.199.107.154:8082/rest/activity/activity_apply/confirm/{id}/{userId}
    public static final String URL_ACTIVE_MEMBER_CONFIRM = URL_BASE
            + "/rest/activity/activity_apply/confirm";
    // 社区活动列表
    // http://zuns.iask.in:8082/rest/community/community_activity/{keyword}/{page}
    public static final String URL_COMMUNITY_ACTIVITY = URL_BASE
            + "/rest/community/community_activity";
    // 社区论坛 http://zuns.iask.in:8082/rest/community/community_forum/{page}
    public static final String URL_COMMUNITY_FORUM = URL_BASE
            + "/rest/community/community_forum";
    // 论坛版块列表
    // http://zuns.iask.in:8082/rest/community/topics/{topicId}/{topicType}/{page}
    public static final String URL_COMMUNITY_TOPICS = URL_BASE
            + "/rest/community/topics";
    // 社区论坛 置顶、加精、取消置顶、取消加精
    // http://zuns.iask.in:8082/rest/usercenter/community_handle/{contentId}/{handle}
    public static final String URL_COMMUNITY_HANDLE = URL_BASE
            + "/rest/usercenter/community_handle";

    // 资讯列表 http://zuns.iask.in:8082/rest/huanxin/getGroupUsers
    public static final String URL_GROUP_INFO = URL_BASE
            + "/rest/huanxin/getGroupInfo";
    public static final String URL_GROUP_Mem = URL_BASE
            + "/rest/huanxin/getGroupUsers";
    // 报名
    public static final String URL_BAOMING = URL_BASE
            + "/rest/activity/activity_apply";
    // 取消报名
    // URL: http://zuns.iask.in:8082/rest/activity/activity_apply/{id}/del
    public static final String URL_BAOMING_DEL = URL_BASE
            + "/rest/activity/activity_apply";

    // 充值http://zuns.iask.in:8082/rest/payment/recharge
    public static final String URL_PAY = URL_BASE + "/rest/payment/recharge";
    // 兑换产品列表 http://zuns.iask.in:8082/rest/usercenter/product/exchange_list/1
    public static final String URL_EXCHANGE_PRODUCT_LIST = URL_BASE
            + "/rest/usercenter/product/exchange_list";
    // 兑换产品http://zuns.iask.in:8082/rest/usercenter/product/do_exchange
    public static final String URL_EXCHANGE_PRODUCT = URL_BASE
            + "/rest/usercenter/product/do_exchange";

    public static final String URL_EDIT_GROUP = URL_BASE
            + "/rest/huanxin/modifyGroup";
    public static final String URL_GROUP_APPLY = URL_BASE
            + "/rest/huanxin/getAapplyUsersByGroupId";
    public static final String URL_GROUP_JOIN = URL_BASE
            + "/rest/huanxin/joinGroup";
    public static final String URL_USERINFO_HUANXIN = URL_BASE
            + "/rest/huanxin/getUserByHuanxinId";

    // 兑换产品列表记录
    // http://zuns.iask.in:8082/rest/usercenter/product/exchange_history/{page}
    public static final String URL_EXCHANGE_PRODUCT_HISTORY = URL_BASE
            + "/rest/usercenter/product/exchange_history";
    // 确认收货
    // http://115.199.107.154:8082/rest/usercenter/exchange_order/confirm
    public static final String URL_EXCHANGE_PRODUCT_CONFIRM = URL_BASE
            + "/rest/usercenter/exchange_order/confirm";

    // 重置//
    // http://zuns.iask.in:8082/rest/usercenter/editPassword/{oldpwd}/{newpwd}
    public static final String URL_RESET = URL_BASE
            + "/rest/usercenter/editPassword";
    // 忘记密码
    // http://zuns.iask.in:8082/rest/credentials/doForgetpw
    public static final String URL_FORGET_PWD = URL_BASE
            + "/rest/credentials/doForgetpw";

    // 活动日历 http://zuns.iask.in:8082/rest/activity/ac/{date}/{add}
    public static final String URL_AC = URL_BASE + "/rest/activity/ac";

    // http://zuns.iask.in:8082/rest/comment/publish
    public static final String URL_PINGLUN = URL_BASE + "/rest/comment/publish";

    // 搜索用户 http://zuns.iask.in:8082/rest/usercenter/user/list
    public static final String URL_SEARCH_USER = URL_BASE
            + "/rest/usercenter/user/list";

    // 用户注册协议http://zuns.iask.in:8082/rest/credentials/protocol
    public static final String URL_PROTOCOL = URL_BASE
            + "/rest/credentials/protocol";
    // http://zuns.iask.in:8082/rest/usercenter/vote/{objectId}/{optionId}/save
    public static final String URL_VOTE = URL_BASE + "/rest/usercenter/vote";
    // HTTP返回码定义
//	http://zuns.iask.in:8082/rest/comment/listHtml/{id}/{type}/{page}
    public static final String URL_ARTICLE_COMMENT = URL_BASE + "/rest/comment/listHtml";

    public static final String SUCCESS = "00000";// 请求成功
    public static final String E1002 = "E1002";// 用户在其它设备上登录
    public static final String EE000 = "EE000";// 网络不给力！
    public static final String EE001 = "EE001";// BaseResult解析错误！
    public static final String EE002 = "EE002";// 请求失败！
    public static final String EE003 = "EE003";// 具体类解析错误！
    public static final String E1001 = "E1001";// 用户验证错误
    // displayImage(NRConfig.getImagePath(xxx),iv,NRConfig.Options160);
//	public static DisplayImageOptions Options160 = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.loading_160)
//			.showImageForEmptyUri(R.drawable.loading_160)
//			.showImageOnFail(R.drawable.loading_160).cacheInMemory(true)
//			.cacheOnDisk(true).considerExifParams(true)
//			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//			.bitmapConfig(Bitmap.NRConfig.RGB_565).resetViewBeforeLoading(true)
//			.build();
//
//	public static DisplayImageOptions OptionsBig = new DisplayImageOptions.Builder()
//			.showImageOnLoading(R.drawable.loading_160)
//			.showImageForEmptyUri(R.drawable.loading_160)
//			.showImageOnFail(R.drawable.loading_160).cacheInMemory(true)
//			.cacheOnDisk(true).considerExifParams(true)
//			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//			.bitmapConfig(Bitmap.NRConfig.RGB_565).resetViewBeforeLoading(true)
//			.build();
//
//	// 圆角
//	public static DisplayImageOptions OptionRound = new DisplayImageOptions.Builder()
//			.showImageForEmptyUri(R.drawable.loading_160)
//			.displayer(new RoundedBitmapDisplayer(10))
//			.showImageOnFail(R.drawable.loading_160).cacheInMemory(true)
//			.cacheOnDisk(true).build();

    /**
     * 文档分类
     */
    public static final String NEWS = "news";// 新闻
    public static final String ACTI = "ACTI";// 活动<包含基地活动>
    public static final String ACT2 = "ACT2";// 社员活动
    public static final String FNOT = "FNOT";// 基地公告
    public static final String BLOG = "BLOG";// 博客
    public static final String TOPIC = "topic";// 话题
    public static final String FARM = "farm";// 基地
    public static final String VOTE = "vote";// 基地
    /**
     * 基地状态
     */
    public static final int JIDI_STATUS_NEW = 1;// 新加入
    public static final int JIDI_STATUS_CERTIFICATION = 2;// 认证中
    public static final int JIDI_STATUS_CERTIFIED = 3;// 认证完成
    public static final int JIDI_STATUS_DISABLE = 4;// 禁用
    /**
     * j活动状态
     */
    public static final int ACTIVITY_STATUS_NEW = 1;
    public static final int ACTIVITY_STATUS_END = 2;
    public static final int ACTIVITY_STATUS_PAST = 3;
    /**
     * 活动报名状态
     */
    public static final int ACTIVITY_MEMBER_STATUS_NEW = 1;
    public static final int ACTIVITY_MEMBER_STATUS_CHECKING = 2;
    public static final int ACTIVITY_MEMBER_STATUS_CHECKED = 3;
    public static final int ACTIVITY_MEMBER_STATUS_NOCHECKE = 4;
    public static final int ACTIVITY_MEMBER_STATUS_DISABLE = 5;
    /**
     * 基地社员状态
     */
    public static final int JIDI_MEMBER_STATUS_NEW = 1;
    public static final int JIDI_MEMBER_STATUS_CHECKING = 2;
    public static final int JIDI_MEMBER_STATUS_CHECKED = 3;
    public static final int JIDI_MEMBER_STATUS_NOCHECKE = 4;
    public static final int JIDI_MEMBER_STATUS_DISABLE = 5;

    /**
     * 订单状态
     */
    public static final int ORDER_STATUS_WFH = 0;// 未发货
    public static final int ORDER_STATUS_YFH = 1;// 已发货
    public static final int ORDER_STATUS_FIRM = 2;// 已确认收货
    public static final int ORDER_STATUS_CLOS = 3;// 已关闭

    public static final String BAOL = "BAOL";// 爆料
    public static final String FCOM = "FCOM";// 评价
    /**
     * 普通用户
     */
    public static final String Grade_0 = "普通用户";
    /**
     * 社员
     */
    public static final String Grade_1 = "社员";
    /**
     * 铜牌社员
     */
    public static final String Grade_2 = "铜牌社员";
    /**
     * 银牌社员
     */
    public static final String Grade_3 = "银牌社员";
    /**
     * 金牌社员
     */
    public static final String Grade_4 = "金牌社员";
    /**
     * 领秀社员
     */
    public static final String Grade_5 = "领秀社员";
    /**
     * 那然之星
     */
    public static final String Grade_6 = "那然之星";
    public static HashMap<String, Integer> userGrade;

    static {
        userGrade = new HashMap();
        userGrade.put(Grade_0, 0);
        userGrade.put(Grade_1, 1);
        userGrade.put(Grade_2, 2);
        userGrade.put(Grade_3, 3);
        userGrade.put(Grade_4, 4);
        userGrade.put(Grade_5, 5);
        userGrade.put(Grade_6, 6);
    }

    // 获取图片地址
    public static final String URL_IMAGE = URL_BASE;

    public static String getImagePath(String path) {
        if (!TextUtils.isEmpty(path) && !path.startsWith("http")) {
            path = URL_IMAGE + path;
        }
        return path;
    }
}
