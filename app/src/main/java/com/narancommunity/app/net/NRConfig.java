package com.narancommunity.app.net;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * 网络配置地址，动态地址需要这里配置
 */
public class NRConfig {
    public static final String URL_BASE = "http://47.98.218.205:8082";
    public static final String URL_RECORD = "http://47.98.218.205:8083";//记录用到的

    /**
     * 发布-发帖列表
     */
    public static final String URL_MY_FATIE_LIST = URL_RECORD + "/api/record/publish/book/friends/page";
    /**
     * 发布-求助列表
     */
    public static final String URL_MY_QIUZHU_LIST = URL_RECORD + "/api/record/publish/book/assist/page";
    /**
     * 达人总榜
     */
    public static final String URL_DAREN_ALL_LIST = URL_BASE + "/api/rank/welfare/all/page";
    /**
     * 达人月榜
     */
    public static final String URL_DAREN_MONTH_LIST = URL_BASE + "/api/rank/welfare/month/page";
    /**
     * 达人周榜
     */
    public static final String URL_DAREN_WEEK_LIST = URL_BASE + "/api/rank/welfare/week/page";
    /**
     * 达人日榜
     */
    public static final String URL_DAREN_DAY_LIST = URL_BASE + "/api/rank/welfare/day/page";

    /**
     * 等级榜
     */
    public static final String URL_RANK_LIST = URL_BASE + "/api/rank/grade/page";
    /**
     * 成就榜
     */
    public static final String URL_CHENGJIU_LIST = URL_BASE + "/api/rank/achievement/page";
    /**
     * 我参与的援助任务列表
     */
    public static final String URL_MY_ATTEND_COMMONWEAL_LIST = URL_RECORD + "/api/record/partake/volunteer/page";
    /**
     * 我参与的援助任务列表
     */
    public static final String URL_MY_ATTEND_ASSISTANT_LIST = URL_RECORD + "/api/record/partake/aid/page";
    /**
     * 捐赠（书籍什么的）是否收藏
     */
    public static final String URL_IS_COLLECT_DONATE_THINGS = URL_RECORD + "/api/record/collection/order/with/me";
    /**
     * 文章帖子 是否收藏
     */
    public static final String URL_IS_COLLECT_ESSAY_TIEZI = URL_RECORD + "/api/record/collection/content/with/me";
    /**
     * 捐赠（书籍什么的） 收藏
     */
    public static final String URL_COLLECT_DONATE_THINGS = URL_RECORD + "/api/record/collection/order";
    /**
     * 捐赠列表
     */
    public static final String URL_COLLECTION_DONATE_LIST = URL_RECORD + "/api/record/collection/order/page";
    /**
     * 文章-帖子 收藏
     */
    public static final String URL_COLLECT_ESSAY = URL_RECORD + "/api/record/collection/content";
    /**
     * 书摘列表
     */
    public static final String URL_GET_SHUZHAI_LIST = URL_BASE + "/api/content/book/digest/page";
    /**
     * 图书列表/分类查询
     */
    public static final String URL_GET_BOOK_BY_SORT = URL_BASE + "/api/book/order/page";
    /**
     * 取消点赞
     */
    public static final String URL_DONT_LIKE = URL_RECORD + "/api/record/like/delete";
    /**
     * 书籍-是否点赞
     */
    public static final String URL_BOOK_IS_LIKE = URL_RECORD + "/api/record/like/order/with/me";
    /**
     * 书评-是否点赞
     */
    public static final String URL_BOOKREVIEW_IS_LIKE = URL_RECORD + "/api/record/like/book/review/with/me";
    /**
     * 文章-是否点赞
     */
    public static final String URL_ESSAY_IS_LIKE = URL_RECORD + "/api/record/like/content/with/me";
    /**
     * 评论-是否点赞
     */
    public static final String URL_COMMENT_IS_LIKE = URL_RECORD + "/api/record/like/comment/with/me";
    /**
     * 文章-点赞
     */
    public static final String URL_ESSAY_LIKE = URL_RECORD + "/api/record/like/content";
    /**
     * 书评-点赞
     */
    public static final String URL_BOOKREVIEW_LIKE = URL_RECORD + "/api/record/like/book/review";
    /**
     * 订单-点赞
     */
    public static final String URL_BOOK_LIKE = URL_RECORD + "/api/record/like/order";
    /**
     * 评论-点赞
     */
    public static final String URL_COMMENT_LIKE = URL_RECORD + "/api/record/like/comment";
    /**
     * 文章评论(书荒，以书会友之类)
     */
    public static final String URL_ESSAY_ADD_COMMENT = URL_RECORD + "/api/record/comment/content";

    /**
     * 文章评论列表（书荒，互助之类的）
     */
    public static final String URL_ESSAY_COMMENT_LIST = URL_RECORD + "/api/record/comment/content/page";
    /**
     * 书荒互助新增
     */
    public static final String URL_ADD_SHHZ = URL_BASE + "/api/content/book/assist/add";
    /**
     * 以书会友新增
     */
    public static final String URL_ADD_YSHY = URL_BASE + "/api/content/book/friends/add";
    /**
     * 书荒互助-列表
     */
    public static final String URL_SHHZ_LIST = URL_BASE + "/api/content/book/assist/page";
    /**
     * 以书会友-列表
     */
    public static final String URL_YSHY_LIST = URL_BASE + "/api/content/book/friends/page";
    /**
     * 书评-评论
     */
    public static final String URL_BOOKREVIEW_ADD_COMMENT = URL_RECORD + "/api/record/comment/book/review";
    /**
     * 书评评论列表
     */
    public static final String URL_BOOKREVIEW_COMMENT_LIST = URL_RECORD + "/api/record/comment/book/review/page";
    /**
     * 借阅图书
     */
    public static final String URL_BOOK_LEND = URL_BASE + "/api/book/apply/get";
    /**
     * 想要看
     */
    public static final String URL_BOOK_WANT_SEE = URL_BASE + "/api/book/apply/want";

    /**
     * 评论详情
     */
    public static final String URL_COMMENT_DETAIL = URL_RECORD + "api/record/comment/detail/page";
    /**
     * 图书书评添加
     */
    public static final String URL_BOOKREVIEW_ADD = URL_BASE + "/api/book/review/add";
    /**
     * 图书书评列表
     */
    public static final String URL_BOOKREVIEW_LIST = URL_BASE + "/api/book/review/page";
    /**
     * 图书相关书籍推荐(三个那个)
     */
    public static final String URL_BOOK_RELATIVE_REC = URL_BASE + "/api/book/relevant/page";
    /**
     * 图书借书卡
     */
    public static final String URL_BOOK_LEND_CARD = URL_BASE + "/api/book/apply/page";
    /**
     * 图书想要看的人
     */
    public static final String URL_BOOK_ORDERER = URL_BASE + "/api/book/apply/want/page";
    /**
     * 图书详情
     */
    public static final String URL_BOOK_DETAIL = URL_BASE + "/api/book/order/by/id";
    /**
     * 爱心书屋书单推荐
     */
    public static final String URL_HOUSE_REC = URL_BASE + "/api/book/recommend/page";
    /**
     * 爱心书屋热门推荐
     */
    public static final String URL_HOUSE_HOT_REC = URL_BASE + "/api/book/hot/page";
    /**
     * 爱心书屋快报
     */
    public static final String URL_HOUSE_TOPLINES = URL_BASE + "/api/topline/book/page";
    /**
     * 爱心书屋banner
     */
    public static final String URL_HOUSE_BANNER = URL_BASE + "/api/publicity/book/page";
    /**
     * 捐书
     */
    public static final String URL_DONATE_BOOK = URL_BASE + "/api/book/donation";
    /**
     * 扫描获取书库中的书的信息
     */
    public static final String URL_GET_BOOK_DETAIL = URL_BASE + "/api/book/by/isbn";
    /**
     * 获取首页最新发布书籍列表
     */
    public static final String URL_BOOK_LIST = URL_BASE + "/api/book/order/page";
    /**
     * 获取首页那然快报列表
     */
    public static final String URL_TOPLINE_LIST = URL_BASE + "/api/topline/page";
    /**
     * 获取首页banner列表
     */
    public static final String URL_BANNER_LIST = URL_BASE + "/api/publicity/page";
    /**
     * 获取专题报道列表
     */
    public static final String URL_COLLECT_LIST = URL_RECORD + "/api/record/collection/content";

    /**
     * 获取专题报道列表
     */
    public static final String URL_GET_ESSAY_LIST = URL_RECORD + "/api/record/collection/content";

    /**
     * 获取专题报道列表
     */
    public static final String URL_SUBJECT_REPORT_LIST = URL_BASE + "/api/content/special/coverage/page";
    /**
     * 获取每周推荐列表
     */
    public static final String URL_WEEK_REC_LIST = URL_BASE + "/api/content/weekly/recommendation/page";
    /**
     * 获取首页援助任务列表
     */
    public static final String URL_GET_ASSISTANT_LIST = URL_BASE + "/api/activity/aid/page";
    /**
     * 反馈
     */
    public static final String URL_FEEDBACK = URL_BASE + "/api/order/feedback/change";
    /**
     * 心愿捐赠确认
     */
    public static final String URL_WISH_DONATE_CONFIRM = URL_BASE + "/api/order/wish/confirm";
    /**
     * 撤回心愿的捐赠
     */
    public static final String URL_WISH_DONATE_WITHDRAW = URL_BASE + "/api/order/apply/wish/withdraw";
    /**
     * 心愿，捐赠的人
     */
    public static final String URL_WISH_DONATE = URL_BASE + "/api/order/apply/wish/detail";
    /**
     * 帮他实现
     */
    public static final String URL_HELP_IT = URL_BASE + "/api/order/apply/wish";
    /**
     * 是否收藏
     */
    public static final String URL_IS_COLLECT = URL_RECORD + "/api/record/collection/order/with/me";
    /**
     * 心愿确认发货
     */
    public static final String URL_WISH_SEND_CONFIRM = URL_BASE + "/api/order/apply/wish/deliver";
    /**
     * 捐赠申请详细确认
     */
    public static final String URL_DONATE_WISH_CONFIRM = URL_BASE + "/api/order/apply/donation/confirm";
    /**
     * 捐赠单申请人的订单撤销
     */
    public static final String URL_DONATE_APPLY_WITHDRAW = URL_BASE + "/api/order/apply/donation/withdraw";
    /**
     * 申请人的订单详细
     */
    public static final String URL_ORDER_APPLY_DETAIL = URL_BASE + "/api/order/apply/detail";
    /**
     * 我与订单的关系
     */
    public static final String URL_ORDER_RELATION = URL_BASE + "/api/order/with/me";
    /**
     * 对捐赠的表示想要
     */
    public static final String URL_I_WANT = URL_BASE + "/api/order/apply/donation";
    /**
     * 获取评论列表/图书评论列表
     */
    public static final String URL_GET_COMMENT_LIST = URL_RECORD + "/api/record/comment/order/page";
    /**
     * 获取想要的人的列表(捐赠)
     */
    public static final String URL_GET_WANT_LIST = URL_BASE + "/api/order/apply/donation/page";
    /**
     * 获取捐赠详情
     */
    public static final String URL_GET_DONATE_DETAIL = URL_BASE + "/api/order/donation/detail";
    /**
     * 收藏
     */
    public static final String URL_COLLECT = URL_RECORD + "/api/record/collection/order";
    /**
     * 新增评论
     */
    public static final String URL_ADD_COMMENT = URL_RECORD + "/api/record/comment/order";
    /**
     * 获取心愿详情
     */
    public static final String URL_GET_WISH_DETAIL = URL_BASE + "/api/order/wish/detail";
    /**
     * 获取问题
     */
    public static final String URL_GET_QUESTIONS = URL_BASE + "/api/questionnaire/page/5";
    /**
     * 心愿墙列表
     */
    public static final String URL_WISH_WALL = URL_BASE + "/api/order/wish/page";
    /**
     * 捐赠墙列表
     */
    public static final String URL_DONATE_WALL = URL_BASE + "/api/order/donation/page";
    /**
     * 发布捐赠
     */
    public static final String URL_DONATE = URL_BASE + "/api/order/donation";
    /**
     * 发布心愿
     */
    public static final String URL_MAKE_WISH = URL_BASE + "/api/order/wish";
    /**
     * 获取信纸
     */
    public static final String URL_GET_STATIONERY = URL_BASE + "/api/order/stationery";

    // 上传照片 http://zuns.iask.in:8082/rest/usercenter/photos/add
    public static final String URL_ADD_PHOTO = URL_BASE
            + "/api/file/upload";

    //轮播图
    public static final String URL_ROUND_PIC = URL_BASE + "/api/publicity/page";
    //地址列表
    public static final String URL_ADDRESS_LIST = URL_BASE +  "/api/mail/page";
    //常用地址
    public static final String URL_DEFAULT_ADDRESS = URL_BASE + "/api/mail/now";
    //新增/修改地址
    public static final String URL_ADD_MODIFY_ADDRESS = URL_BASE +  "/api/mail/change";
    //删除地址
    public static final String URL_DEL_ADDRESS = URL_BASE +  "/api/mail/delete";
    //设置常用地址
    public static final String URL_SET_DEFAULT_ADDRESS = URL_BASE +  "/api/mail/change/now";

    //实名认证
    public static final String URL_AUTHORISE = URL_BASE + "/api/account/certification";
    // 获取验证码
    public static final String URL_GET_CODE = URL_BASE
            + "/api/account/register/verifycode";
    // 注册
    public static final String URL_REG = URL_BASE
            + "/api/account/register";
    // 登录http://nrc.guru/rest/account/login
    public static final String URL_LOGIN = URL_BASE + "/api/account/login";
    // 用户中心http://nrc.guru/rest/u
    public static final String URL_RESET = URL_BASE
            + "/rest/usercenter/editPassword";
    // 忘记密码
    // http://zuns.iask.in:8082/rest/credentials/doForgetpw
    public static final String URL_FORGET_PWD = URL_BASE
            + "/rest/credentials/doForgetpw";

    // 用户注册协议http://zuns.iask.in:8082/rest/credentials/protocol
    public static final String URL_PROTOCOL = URL_BASE
            + "/rest/credentials/protocol";

    public static final String INDEX_INDEX_WILLFARM_COUNT = URL_BASE + "rest/farm/count";//首页即将入住数量
    public static final String INDEX_FARM_ADD_ATTENTION = URL_BASE + "rest/farm/concern/add";//生产者详情中添加关注
    public static final String INDEX_FARM_DEL_ATTENTION = URL_BASE + "rest/farm/concern/del";//生产者详情中取消关注
    public static final String SELECT_DRAG_LIST = URL_BASE + "rest/optional/drag";//自选对象拖动
    public static final String SELECT_ADD_ATTENTION = URL_BASE + "rest/optional/add";//自选生产者搜索列表
    public static final String SELECT_DEL_ATTENTION_LIST = URL_BASE + "rest/optional/del/some";//自选删除（包括多选）

    public static final String LOGIN_LOGOUT = URL_BASE +  "/api/account/logout";//登出

    public static final String RELEASE_TOPIC = URL_BASE + "rest/community/publish";//发布话题

    public static final String FARM_JUBAO = URL_BASE + "rest/farm/report";//发布话题

    public static final String PRODUCT_ADD_ATTENTION = URL_BASE + "rest/farm/product/concern/add";//产品详情中添加关注
    public static final String PRODUCT_DEL_ATTENTION = URL_BASE + "rest/farm/product/concern/del";//产品详情中取消关注

    public static final String COMMENT_ZAN = URL_BASE + "rest/comment/concern/add";//给评论点赞
    public static final String COMMENT_CANCEL_ZAN = URL_BASE + "rest/comment/concern/del";//取消评论点赞

    public static final String DYNAMIC_ZAN = URL_BASE + "rest/usercenter/article/concern/add";//文章点赞
    public static final String DYNAMIC_CANCEL_ZAN = URL_BASE + "rest/usercenter/article/concern/del";//取消评论点赞
    public static final String USER_SAVE_INFO = URL_BASE + "rest/usercenter/saveInfo";//保存用户信息

    public static final String FEED_BACK = URL_BASE + "rest/user/feedback/add";//反馈

    public static final String DEL_COMMENT = URL_BASE + "rest/comment/delete";//删除评论


    public static final String SHAREADDTIMES = URL_BASE + "rest/farm/share";//分享
}
