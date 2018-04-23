package com.narancommunity.app.net;

import com.narancommunity.app.entity.Address;
import com.narancommunity.app.entity.ApplyDetailEntity;
import com.narancommunity.app.entity.AskPapers;
import com.narancommunity.app.entity.AssistantEntity;
import com.narancommunity.app.entity.BannerData;
import com.narancommunity.app.entity.BookCommentData;
import com.narancommunity.app.entity.BookDetail;
import com.narancommunity.app.entity.BookInfo;
import com.narancommunity.app.entity.BookLendCardData;
import com.narancommunity.app.entity.BookListData;
import com.narancommunity.app.entity.BookRelativeRecData;
import com.narancommunity.app.entity.CollectEssayItem;
import com.narancommunity.app.entity.CommentDetail;
import com.narancommunity.app.entity.CommentListEntity;
import com.narancommunity.app.entity.DonateDetailData;
import com.narancommunity.app.entity.NewsData;
import com.narancommunity.app.entity.OrderData;
import com.narancommunity.app.entity.OrderEntity;
import com.narancommunity.app.entity.RecData;
import com.narancommunity.app.entity.RecEntity;
import com.narancommunity.app.entity.WallListData;
import com.narancommunity.app.entity.Stationery;
import com.narancommunity.app.entity.UpdateFilesEntity;
import com.narancommunity.app.entity.UserInfo;
import com.narancommunity.app.entity.WantListEntity;
import com.narancommunity.app.entity.WeekEntity;
import com.narancommunity.app.entity.WishDetailEntity;
import com.narancommunity.app.entity.WishDonateEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * writer：fancy on 2017/2/16 14:36
 * classname : 服务方法
 */
public interface NRService {
    @FormUrlEncoded
    @POST(NRConfig.URL_GET_CODE)
    Call<Result<Object>> getVerifyCode(@HeaderMap Map<String, Object> headers, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.URL_REG)
    Call<Result<HashMap<String, Object>>> register(@HeaderMap Map<String, Object> headers, @FieldMap Map<String, Object> map);

    @POST(NRConfig.URL_LOGIN)
    Call<Result<UserInfo>> login(@HeaderMap Map<String, Object> headers, @QueryMap Map<String, Object> map);//登录

    @FormUrlEncoded
    @POST(NRConfig.URL_ADDRESS_LIST)
    Call<Result<Address>> getAddressList(/*@HeaderMap Map<String, Object> header,*/ @FieldMap Map<String, Object> map);//地址列表

    @FormUrlEncoded
    @POST(NRConfig.URL_DEL_ADDRESS)
    Call<Result<Void>> delAddress(/*@HeaderMap Map<String, Object> header,*/ @FieldMap Map<String, Object> map);//地址删除

    @FormUrlEncoded
    @POST(NRConfig.URL_ADD_MODIFY_ADDRESS)
    Call<Result<Void>> addAddress(/*@HeaderMap Map<String, Object> header,*/ @FieldMap Map<String, Object> map);//地址添加

    @FormUrlEncoded
    @POST(NRConfig.URL_ADD_MODIFY_ADDRESS)
    Call<Result<Void>> modifyAddress(/*@HeaderMap Map<String, Object> header,*/ @FieldMap Map<String, Object> map);//地址添加

    @FormUrlEncoded
    @POST(NRConfig.URL_SET_DEFAULT_ADDRESS)
    Call<Result<Void>> setDefaultAddress(/*@HeaderMap Map<String, Object> header,*/ @FieldMap Map<String, Object> map);//地址设置常用

    @FormUrlEncoded
    @POST(NRConfig.URL_ADD_PHOTO)
    Call<Result<String>> updateFiles(/*@HeaderMap Map<String, Object> header,*/ @FieldMap Map<String, Object> map);//上传图片

    @FormUrlEncoded
    @POST(NRConfig.LOGIN_LOGOUT)
    Call<Result<Void>> logout(@FieldMap Map<String, Object> map);

    @Multipart
    @POST(NRConfig.URL_ADD_PHOTO)
    Call<Result<List<String>>> updateFile(@Url String url,
                                          @Part("pic") String description,
                                          @PartMap() Map<String, RequestBody> maps);

    @Multipart
    @POST(NRConfig.URL_ADD_PHOTO)
    Call<Result<String>> updateOneFile(@PartMap Map<String, RequestBody> data, @Part("multipartFile") RequestBody description,
                                       @Part MultipartBody.Part file);

    @Multipart
    @POST(NRConfig.URL_ADD_PHOTO)
    Call<Result<UpdateFilesEntity>> uploadInfoFile(@Part("pic") RequestBody description,
                                                   @Part MultipartBody.Part file);

    @POST(NRConfig.URL_GET_STATIONERY)
    Call<Result<Stationery>> getStationeries();//获取信纸列表

    @FormUrlEncoded
    @POST(NRConfig.URL_MAKE_WISH)
    Call<Result<Void>> makerWish(@FieldMap Map<String, Object> map);//发布心愿

    @FormUrlEncoded
    @POST(NRConfig.URL_DONATE)
    Call<Result<Void>> donate(@FieldMap Map<String, Object> map);//发布捐赠

    @FormUrlEncoded
    @POST(NRConfig.URL_GET_QUESTIONS)
    Call<Result<AskPapers>> getAskPaper(@FieldMap Map<String, Object> map);//获取问卷

    @FormUrlEncoded
    @POST(NRConfig.URL_DONATE_WALL)
    Call<Result<WallListData>> getDonateWall(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取捐赠墙列表

    @FormUrlEncoded
    @POST(NRConfig.URL_WISH_WALL)
    Call<Result<WallListData>> getWishWall(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取心愿墙列表

    @FormUrlEncoded
    @POST(NRConfig.URL_GET_WISH_DETAIL)
    Call<Result<WishDetailEntity>> getWishDetail(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取心愿详情

    @FormUrlEncoded
    @POST(NRConfig.URL_ADD_COMMENT)
    Call<Result<Void>> addComment(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//评论

    @FormUrlEncoded
    @POST(NRConfig.URL_ORDER_APPLY_DETAIL)
    Call<Result<ApplyDetailEntity>> applyDetail(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//申请的人的订单详细

    //TODO 以此为参考
    @FormUrlEncoded
    @POST(NRConfig.URL_BANNER_LIST)
    Call<Result<BannerData>> getBannerList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取banner列表

    @FormUrlEncoded
    @POST(NRConfig.URL_BOOK_LEND)
    Call<Result<Void>> lendBook(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//借阅本书

    @FormUrlEncoded
    @POST(NRConfig.URL_BOOK_WANT_SEE)
    Call<Result<Void>> wantSee(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//想看这本书

    @FormUrlEncoded
    @POST(NRConfig.URL_COMMENT_DETAIL)
    Call<Result<CommentDetail>> getCommentDetail(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取评论详细

    @FormUrlEncoded
    @POST(NRConfig.URL_BOOKREVIEW_LIST)
    Call<Result<BookCommentData>> getBookCommentCommentList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取书评列表

    @FormUrlEncoded
    @POST(NRConfig.URL_BOOKREVIEW_ADD_COMMENT)
    Call<Result<Void>> addBookCommentComment(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//添加书评

    @FormUrlEncoded
    @POST(NRConfig.URL_BOOK_RELATIVE_REC)
    Call<Result<BookRelativeRecData>> getBookRelativeRecList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取图书相关推荐列表

//    @FormUrlEncoded
//    @POST(NRConfig.URL_BOOK_COMMENT_LIST)
//    Call<Result<BookCommentData>> getBookCommentList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取图书评论列表

    @FormUrlEncoded
    @POST(NRConfig.URL_BOOK_LEND_CARD)
    Call<Result<BookLendCardData>> getBookLendCard(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取图书评论列表

    @FormUrlEncoded
    @POST(NRConfig.URL_BOOK_ORDERER)
    Call<Result<OrderData>> getBookOrderer(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取预定列表

    @FormUrlEncoded
    @POST(NRConfig.URL_BOOK_DETAIL)
    Call<Result<BookInfo>> getBookInfo(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取图书详情，区别于扫码的图书详情

    @FormUrlEncoded
    @POST(NRConfig.URL_HOUSE_HOT_REC)
    Call<Result<RecData>> getHouseHotRec(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取爱心书屋热门榜

    @FormUrlEncoded
    @POST(NRConfig.URL_HOUSE_REC)
    Call<Result<RecData>> getHouseBookRec(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取爱心书屋书单推荐

    @FormUrlEncoded
    @POST(NRConfig.URL_HOUSE_TOPLINES)
    Call<Result<NewsData>> getBookTopLinesList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取banner列表

    @FormUrlEncoded
    @POST(NRConfig.URL_HOUSE_BANNER)
    Call<Result<BannerData>> getBannerHouseList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取banner列表

    @FormUrlEncoded
    @POST(NRConfig.URL_DONATE_BOOK)
    Call<Result<Void>> donateBook(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//捐书

    @FormUrlEncoded
    @POST(NRConfig.URL_GET_BOOK_DETAIL)
    Call<Result<BookDetail>> getBookDetail(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//扫描获取书库中的书的信息

    @FormUrlEncoded
    @POST(NRConfig.URL_BOOK_LIST)
    Call<Result<BookListData>> getBookList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取首页最新发布书籍列表

    @FormUrlEncoded
    @POST(NRConfig.URL_TOPLINE_LIST)
    Call<Result<NewsData>> getTopLinesList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取快报列表

    @FormUrlEncoded
    @POST(NRConfig.URL_GET_ESSAY_LIST)
    Call<Result<CollectEssayItem>> getEssayList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取援助列表

    @FormUrlEncoded
    @POST(NRConfig.URL_SUBJECT_REPORT_LIST)
    Call<Result<WeekEntity>> getSubjectReportList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取援助列表

    @FormUrlEncoded
    @POST(NRConfig.URL_WEEK_REC_LIST)
    Call<Result<WeekEntity>> getWeekRecList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取每周推荐列表

    @FormUrlEncoded
    @POST(NRConfig.URL_GET_ASSISTANT_LIST)
    Call<Result<AssistantEntity>> getAssistantList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取援助列表

    @FormUrlEncoded
    @POST(NRConfig.URL_FEEDBACK)
    Call<Result<Object>> feedback(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//反馈

    @FormUrlEncoded
    @POST(NRConfig.URL_WISH_DONATE_CONFIRM)
    Call<Result<Void>> wishDonateConfirm(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//撤回心愿捐赠

    @FormUrlEncoded
    @POST(NRConfig.URL_WISH_DONATE_WITHDRAW)
    Call<Result<Void>> wishDonateWithDraw(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//撤回心愿捐赠

    @FormUrlEncoded
    @POST(NRConfig.URL_WISH_DONATE)
    Call<Result<WishDonateEntity>> wishDonate(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//获取心愿申请捐赠的人详情

    @FormUrlEncoded
    @POST(NRConfig.URL_HELP_IT)
    Call<Result<Void>> helpIt(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//帮他实现

    @FormUrlEncoded
    @POST(NRConfig.URL_IS_COLLECT)
    Call<Result<Map<String, Object>>> isCollect(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//捐赠申请详细确认

    @FormUrlEncoded
    @POST(NRConfig.URL_WISH_SEND_CONFIRM)
    Call<Result<Void>> WishSendConfirm(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//捐赠申请详细确认

    @FormUrlEncoded
    @POST(NRConfig.URL_DONATE_WISH_CONFIRM)
    Call<Result<Void>> donateWishConfirm(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//捐赠申请详细确认

    @FormUrlEncoded
    @POST(NRConfig.URL_DONATE_APPLY_WITHDRAW)
    Call<Result<Void>> donateApplyWithDraw(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//捐赠申请人的订单撤销

    @FormUrlEncoded
    @POST(NRConfig.URL_ORDER_RELATION)
    Call<Result<Map<String, Object>>> getOrderRelation(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//订单的关系

    @FormUrlEncoded
    @POST(NRConfig.URL_I_WANT)
    Call<Result<Object>> iWant(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//我需要这个捐赠

    @FormUrlEncoded
    @POST(NRConfig.URL_GET_COMMENT_LIST)
    Call<Result<CommentListEntity>> getCommentList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//评论列表

    @FormUrlEncoded
    @POST(NRConfig.URL_GET_DONATE_DETAIL)
    Call<Result<DonateDetailData>> getDonateDetail(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//捐赠详情

    @FormUrlEncoded
    @POST(NRConfig.URL_GET_WANT_LIST)
    Call<Result<WantListEntity>> getWantList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//想要的列表

    @FormUrlEncoded
    @POST(NRConfig.URL_COLLECT)
    Call<Result<Void>> collect(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//收藏

    @FormUrlEncoded
    @POST(NRConfig.URL_AUTHORISE)
    Call<Result<Void>> authorise(@FieldMap Map<String, Object> map);//实名认证

    @FormUrlEncoded
    @POST(NRConfig.URL_FORGET_PWD)
    Call<Result<String>> forgetPsd(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//忘记密码

    @FormUrlEncoded
    @POST(NRConfig.URL_RESET + "{oldpwd}" + "/" + "{newpwd}")
    Call<Result<String>> modifyPsd(@HeaderMap Map<String, Object> header, @Path("oldpwd") String oldpwd, @Path("newpwd") String newpwd);

    //    @FormUrlEncoded
//    @POST(NRConfig.INDEX_INDEX)
//    Call<Result<IndexEntityNew>> getIndex(@HeaderMap Map<String, Object> header);//首页
//
//    @FormUrlEncoded
//    @POST(NRConfig.INDEX_FARM_LIST)
//    Call<Result<NRFarm>> getIndexFarmList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);//首页
//
//    @POST(NRConfig.INDEX_ACTIVITY_LIST + "/{classify}/{time}/{keyword}/{page}")
//    Call<Result<IndexActivityList>> getIndexActivityList(@HeaderMap Map<String, Object> header, @Path("classify") String classify
//            , @Path("time") String time, @Path("keyword") String keyword, @Path("page") String page);//首页公社活动
//
////    @POST(NRConfig.INDEX_NEWS + "/{classify}/{page}/{row}")  旧版资讯获取方式
////    Call<Result<IndexNews>> getIndexNews(@HeaderMap Map<String, Object> header, @Path("classify") String classify
////            , @Path("page") String page, @Path("row") String row);
//
//    @FormUrlEncoded
//    @POST(NRConfig.INDEX_NEWS)
//    Call<Result<IndexNews>> getIndexNews(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);
//
//    @FormUrlEncoded
//    @POST(NRConfig.INDEX_INDEX_WILLFARM_MORE)
//    Call<Result<IndexWillMoreList>> getIndexWillFarmMore(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.INDEX_INDEX_WILLFARM_COUNT)
    Call<Result<Integer>> getIndexWillFarmCount(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

//    @POST(NRConfig.INDEX_FARM_DETAIL + "/{farmId}")
//    Call<Result<IndexFarmDetail>> getIndexFarmDetail(@HeaderMap Map<String, Object> header, @Path("farmId") String farmId);
//
//    @POST(NRConfig.INDEX_FARM_PRODUCT + "/{farmId}/{page}")
//    Call<Result<NRFarmProduct>> getFarmProductByID(@HeaderMap Map<String, Object> header, @Path("farmId") String farmId
//            , @Path("page") String page);
//
//    @POST(NRConfig.INDEX_FARM_NOTICE + "/{farmId}/{type}/{page}")
//    Call<Result<NRFarmNotices>> getFarmNoticeByID(@HeaderMap Map<String, Object> header, @Path("farmId") String farmId
//            , @Path("type") String type, @Path("page") String page);
//
//    @POST(NRConfig.INDEX_FARM_DYNAMIC + "/{farmId}/{page}")
//    Call<Result<NRFarmDynamic>> getFarmDynamicByID(@HeaderMap Map<String, Object> header, @Path("farmId") String farmId
//            , @Path("page") String page);
//
//    @POST(NRConfig.CHECK_CONTENT_RELATION + "/{id}")
//    Call<Result<Map<String, NRRelation>>> checkContentRelation(@HeaderMap Map<String, Object> header, @Path("id") String id);

    @FormUrlEncoded
    @POST(NRConfig.INDEX_FARM_ADD_ATTENTION)
    Call<Result<Object>> addAttention(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.INDEX_FARM_DEL_ATTENTION)
    Call<Result<Object>> delAttention(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.PRODUCT_ADD_ATTENTION)
    Call<Result<String>> addProductAttention(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.PRODUCT_DEL_ATTENTION)
    Call<Result<String>> delProductAttention(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.SELECT_ADD_ATTENTION)
    Call<Result<String>> addSelectAttention(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.SELECT_DEL_ATTENTION_LIST)
    Call<Result<String>> delSelectAttentionList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

//    @POST(NRConfig.INDEX_FARM_PRODUCT_DETAIL + "/{id}")
//    Call<Result<Map<String, NRFarmProductItem>>> getProductDetail(@HeaderMap Map<String, Object> header, @Path("id") String id);
//
//    @POST(NRConfig.FARM_HOME + "/{keyword}")
//    Call<Result<FarmEntity>> getFarmHome(@HeaderMap Map<String, Object> header, @Path("keyword") String id);
//
//    @POST(NRConfig.FARM_HOME_ALL_NOTICE + "/{farmId}/{type}/{page}")
//    Call<Result<NRFarmNotices>> getFarmAllNotice(@HeaderMap Map<String, Object> header, @Path("farmId") String farmId
//            , @Path("type") String type
//            , @Path("page") String page);
//
//    @POST(NRConfig.FARM_HOME_ALL_FARM + "/{area}/{classify}/{page}/{row}")
//    Call<Result<NRFarmHomeMoreFarm>> getFarmAllFarm(@HeaderMap Map<String, Object> header, @Path("area") String area
//            , @Path("classify") String classify
//            , @Path("page") String page, @Path("row") String row);
//
//    @FormUrlEncoded
//    @POST(NRConfig.FARM_HOME_ALL_FARM)
//    Call<Result<NRFarmHomeMoreFarm>> getFarmByKeyword(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);
//
//    @FormUrlEncoded
//    @POST(NRConfig.SELECT_FARM_LIST)
//    Call<Result<NRSelectFarmData>> getSelectFarmList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);
//
//    @FormUrlEncoded
//    @POST(NRConfig.SELECT_FARM_LIST)
//    Call<Result<NRFarmProduct>> getSelectProductList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.SELECT_DRAG_LIST)
    Call<Result<String>> setSelectListOrder(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

//    @FormUrlEncoded
//    @POST(NRConfig.SELECT_SEARCH_LIST)
//    Call<Result<IndexWillMoreList>> getFarmList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);
//
//    @FormUrlEncoded
//    @POST(NRConfig.COMMUNITY_TOPIC_OR_MEMBER_LIST)
//    Call<Result<NRCommunityTopic>> getCommunityTopicList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);
//
//    @FormUrlEncoded
//    @POST(NRConfig.MINE_LIST)
//    Call<Result<NRMineData>> getMineList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);
//
//    @FormUrlEncoded
//    @POST(NRConfig.MINE_LIST)
//    Call<Result<MyJoinActList>> getMineActList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.RELEASE_TOPIC)
    Call<Result<String>> releaseTopic(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

//    @FormUrlEncoded
//    @POST(NRConfig.GET_CHANNEL_LIST_BY_CHANNEL)
//    Call<Result<NRCommunityTopic>> getActByChannel(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);
//
//    @FormUrlEncoded
//    @POST(NRConfig.GET_CHANNEL_LIST)
//    Call<Result<CommunityChannel>> getChannelList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.FARM_JUBAO)
    Call<Result<String>> jubaoFarm(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

//    @FormUrlEncoded
//    @POST(NRConfig.MINE_FOLLOW_LIST)
//    Call<Result<NRMineFollows>> getFollowLIst(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);
//
//    @FormUrlEncoded
//    @POST(NRConfig.MINE_COLLECT_LIST)
//    Call<Result<NRFarmProduct>> getCollectProductList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);
//
//    @FormUrlEncoded
//    @POST(NRConfig.MINE_COLLECT_LIST)
//    Call<Result<NRMineTopic>> getCollectTopicList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);
//
//    @FormUrlEncoded
//    @POST(NRConfig.MINE_COLLECT_LIST)
//    Call<Result<NRNewsData>> getCollectNewsList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);
//
//    @FormUrlEncoded
//    @POST(NRConfig.DYNAMIC_COMMENT_LIST)
//    Call<Result<NRComment>> getDynamicCommentList(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

//    @FormUrlEncoded
//    @POST(NRConfig.FARM_PRODUCT_LIST)
//    Call<Result<NRFarmProduct>> getProductListBySort(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.DYNAMIC_ZAN)
    Call<Result<String>> zanDynamic(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.DYNAMIC_CANCEL_ZAN)
    Call<Result<String>> zanCancelDynamic(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.COMMENT_ZAN)
    Call<Result<String>> zanComment(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.COMMENT_CANCEL_ZAN)
    Call<Result<String>> zanCancelComment(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.USER_SAVE_INFO)
    Call<Result<UserInfo>> saveUserInfo(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.FEED_BACK)
    Call<Result<String>> addFeedback(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

//    @FormUrlEncoded
//    @POST(NRConfig.SEARCH)
//    Call<Result<SearchEntity>> search(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);
//
//    @FormUrlEncoded
//    @POST(NRConfig.GET_SEARCH_PRODUCT_HOT)
//    Call<Result<Map<String, NRBaseData<SearchKeyWord>>>> getSearchHot(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST(NRConfig.DEL_COMMENT)
    Call<Result<String>> delComment(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

//    @FormUrlEncoded
//    @POST(NRConfig.INDEX_SEARCH_FARM_OR_PRODUCT)
//    Call<Result<Map<String, List<SearchFarmOrProduct>>>> searchFarmOrProduct(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    //
//    @FormUrlEncoded
//    @POST(NRConfig.UPDATE)
//    Call<Result<UpdateEntity>> update(@HeaderMap Map<String, Object> header, @FieldMap Map<String, Object> map);

    @POST(NRConfig.SHAREADDTIMES + "/{id}")
    Call<Result<String>> shareAddTimes(@HeaderMap Map<String, Object> header, @Path("id") String farmId);
}
