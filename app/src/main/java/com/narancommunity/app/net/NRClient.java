package com.narancommunity.app.net;

import android.util.Log;
import android.util.Pair;

import com.narancommunity.app.MApplication;
import com.narancommunity.app.entity.Address;
import com.narancommunity.app.entity.AnswerComment;
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
import com.narancommunity.app.entity.CompanyData;
import com.narancommunity.app.entity.CompanyEntity;
import com.narancommunity.app.entity.DonateDetailData;
import com.narancommunity.app.entity.FootPrintData;
import com.narancommunity.app.entity.GradeData;
import com.narancommunity.app.entity.IsCollect;
import com.narancommunity.app.entity.NewsData;
import com.narancommunity.app.entity.OrderData;
import com.narancommunity.app.entity.RecData;
import com.narancommunity.app.entity.ShuzhaiData;
import com.narancommunity.app.entity.UpdateEntity;
import com.narancommunity.app.entity.WallListData;
import com.narancommunity.app.entity.Stationery;
import com.narancommunity.app.entity.UpdateFilesEntity;
import com.narancommunity.app.entity.UserInfo;
import com.narancommunity.app.entity.WantListEntity;
import com.narancommunity.app.entity.WeekEntity;
import com.narancommunity.app.entity.WishDetailEntity;
import com.narancommunity.app.entity.WishDonateEntity;
import com.narancommunity.app.entity.YSHYData;
import com.narancommunity.app.entity.Zan;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * writer：fancy on 2017/2/16 14:35
 * classname : 请求对象
 */
public class NRClient {
    /**
     * @param callback
     * @return
     */
    public static Call shareAddTimes(String id, final ResultCallback<Result<String>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();

        Pair<String, String> pair = MApplication.getUser();
        header.put("NARAN-ACCESS-USER", pair.first);
        header.put("NARAN-ACCESS-TOKEN", pair.second);
        Call<Result<String>> call = mService.shareAddTimes(header,
                id);
        Callback<Result<String>> cbk = new Callback<Result<String>>() {

            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }


    /**
     * @param baseData 发布心愿
     * @param callback
     * @return
     */
    public static Call authorise(Map<String, Object> baseData,
                                 final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        Call<Result<Void>> call = mService.authorise(baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call, Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * @param baseData 获取问题
     * @param callback
     * @return
     */
    public static Call getAskPaper(Map<String, Object> baseData,
                                   final ResultCallback<Result<AskPapers>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        Call<Result<AskPapers>> call = mService.getAskPaper(baseData);
        Callback<Result<AskPapers>> cbk = new Callback<Result<AskPapers>>() {

            @Override
            public void onResponse(Call<Result<AskPapers>> call, Response<Result<AskPapers>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<AskPapers>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    //TODO 以此为基础

    /**
     * 通用接受数据接口
     *
     * @param callback
     * @return
     */
    public static Call getRankBookDonateList(Map<String, Object> baseData,
                                  final ResultCallback<Result<GradeData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<GradeData>> call = mService.getRankBookDonateList( baseData);
        Callback<Result<GradeData>> cbk = new Callback<Result<GradeData>>() {

            @Override
            public void onResponse(Call<Result<GradeData>> call,
                                   Response<Result<GradeData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<GradeData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 获取机构zuji
     *
     * @param callback
     * @return
     */
    public static Call addFoot(Map<String, Object> baseData,
                               final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.addFoot(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取机构zuji
     *
     * @param callback
     * @return
     */
    public static Call getOrgFootList(Map<String, Object> baseData,
                                      final ResultCallback<Result<FootPrintData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<FootPrintData>> call = mService.getOrgFootList(header, baseData);
        Callback<Result<FootPrintData>> cbk = new Callback<Result<FootPrintData>>() {

            @Override
            public void onResponse(Call<Result<FootPrintData>> call,
                                   Response<Result<FootPrintData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<FootPrintData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取机构想起
     *
     * @param callback
     * @return
     */
    public static Call getOrgDetail(Map<String, Object> baseData,
                                    final ResultCallback<Result<CompanyEntity>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<CompanyEntity>> call = mService.getOrgDetail(header, baseData);
        Callback<Result<CompanyEntity>> cbk = new Callback<Result<CompanyEntity>>() {

            @Override
            public void onResponse(Call<Result<CompanyEntity>> call,
                                   Response<Result<CompanyEntity>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<CompanyEntity>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取我的求助列表
     *
     * @param callback
     * @return
     */
    public static Call modifyHead(Map<String, Object> baseData,
                                  final ResultCallback<Result<UserInfo>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<UserInfo>> call = mService.modifyHead(header, baseData);
        Callback<Result<UserInfo>> cbk = new Callback<Result<UserInfo>>() {

            @Override
            public void onResponse(Call<Result<UserInfo>> call,
                                   Response<Result<UserInfo>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<UserInfo>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 机构
     *
     * @param callback
     * @return
     */
    public static Call getOrgList(Map<String, Object> baseData,
                                  final ResultCallback<Result<CompanyData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<CompanyData>> call = mService.getOrgList(header, baseData);
        Callback<Result<CompanyData>> cbk = new Callback<Result<CompanyData>>() {

            @Override
            public void onResponse(Call<Result<CompanyData>> call,
                                   Response<Result<CompanyData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<CompanyData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 机构入驻
     *
     * @param callback
     * @return
     */
    public static Call orgSettleDown(Map<String, Object> baseData,
                                     final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.orgSettleDown(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取我的求助列表
     *
     * @param callback
     * @return
     */
    public static Call getQiuZhuList(Map<String, Object> baseData,
                                     final ResultCallback<Result<YSHYData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<YSHYData>> call = mService.getQiuZhuList(header, baseData);
        Callback<Result<YSHYData>> cbk = new Callback<Result<YSHYData>>() {

            @Override
            public void onResponse(Call<Result<YSHYData>> call,
                                   Response<Result<YSHYData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<YSHYData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取我的发帖列表
     *
     * @param callback
     * @return
     */
    public static Call getFaTieList(Map<String, Object> baseData,
                                    final ResultCallback<Result<YSHYData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<YSHYData>> call = mService.getFaTieList(header, baseData);
        Callback<Result<YSHYData>> cbk = new Callback<Result<YSHYData>>() {

            @Override
            public void onResponse(Call<Result<YSHYData>> call,
                                   Response<Result<YSHYData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<YSHYData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取等级榜列表
     *
     * @param callback
     * @return
     */
    public static Call getDarenAllList(Map<String, Object> baseData,
                                       final ResultCallback<Result<GradeData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<GradeData>> call = mService.getDarenAllList(header, baseData);
        Callback<Result<GradeData>> cbk = new Callback<Result<GradeData>>() {

            @Override
            public void onResponse(Call<Result<GradeData>> call,
                                   Response<Result<GradeData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<GradeData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * @param callback
     * @return
     */
    public static Call getDarenMonthList(Map<String, Object> baseData,
                                         final ResultCallback<Result<GradeData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<GradeData>> call = mService.getDarenMonthList(header, baseData);
        Callback<Result<GradeData>> cbk = new Callback<Result<GradeData>>() {

            @Override
            public void onResponse(Call<Result<GradeData>> call,
                                   Response<Result<GradeData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<GradeData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * @param callback
     * @return
     */
    public static Call getDarenWeekList(Map<String, Object> baseData,
                                        final ResultCallback<Result<GradeData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<GradeData>> call = mService.getDarenWeekList(header, baseData);
        Callback<Result<GradeData>> cbk = new Callback<Result<GradeData>>() {

            @Override
            public void onResponse(Call<Result<GradeData>> call,
                                   Response<Result<GradeData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<GradeData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * @param callback
     * @return
     */
    public static Call getDarenDayList(Map<String, Object> baseData,
                                       final ResultCallback<Result<GradeData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<GradeData>> call = mService.getDarenDayList(header, baseData);
        Callback<Result<GradeData>> cbk = new Callback<Result<GradeData>>() {

            @Override
            public void onResponse(Call<Result<GradeData>> call,
                                   Response<Result<GradeData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<GradeData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取等级榜列表
     *
     * @param callback
     * @return
     */
    public static Call getRankList(Map<String, Object> baseData,
                                   final ResultCallback<Result<GradeData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<GradeData>> call = mService.getRankList(header, baseData);
        Callback<Result<GradeData>> cbk = new Callback<Result<GradeData>>() {

            @Override
            public void onResponse(Call<Result<GradeData>> call,
                                   Response<Result<GradeData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<GradeData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取成就榜列表
     *
     * @param callback
     * @return
     */
    public static Call getChengjiuList(Map<String, Object> baseData,
                                       final ResultCallback<Result<GradeData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<GradeData>> call = mService.getChengjiuList(header, baseData);
        Callback<Result<GradeData>> cbk = new Callback<Result<GradeData>>() {

            @Override
            public void onResponse(Call<Result<GradeData>> call,
                                   Response<Result<GradeData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<GradeData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取我参与的公益活动列表
     *
     * @param callback
     * @return
     */
    public static Call getMyAttendCommonWealList(Map<String, Object> baseData,
                                                 final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.getMyAttendCommonWealList(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 是否收藏捐赠
     *
     * @param callback
     * @return
     */
    public static Call isCollectDonateThings(Map<String, Object> baseData,
                                             final ResultCallback<Result<IsCollect>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<IsCollect>> call = mService.isCollectDonateThings(header, baseData);
        Callback<Result<IsCollect>> cbk = new Callback<Result<IsCollect>>() {

            @Override
            public void onResponse(Call<Result<IsCollect>> call,
                                   Response<Result<IsCollect>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<IsCollect>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 是否收藏文章帖子
     *
     * @param callback
     * @return
     */
    public static Call isCollectEssayTiezi(Map<String, Object> baseData,
                                           final ResultCallback<Result<IsCollect>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<IsCollect>> call = mService.isCollectEssayTiezi(header, baseData);
        Callback<Result<IsCollect>> cbk = new Callback<Result<IsCollect>>() {

            @Override
            public void onResponse(Call<Result<IsCollect>> call,
                                   Response<Result<IsCollect>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<IsCollect>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 收藏捐赠
     *
     * @param callback
     * @return
     */
    public static Call collectDonateThings(Map<String, Object> baseData,
                                           final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.collectDonateThings(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 收藏文章帖子
     *
     * @param callback
     * @return
     */
    public static Call collectEssay(Map<String, Object> baseData,
                                    final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.collectEssay(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 我的捐赠列表
     *
     * @param callback
     * @return
     */
    public static Call getCollectDonateList(Map<String, Object> baseData,
                                            final ResultCallback<Result<ShuzhaiData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<ShuzhaiData>> call = mService.getCollectDonateList(header, baseData);
        Callback<Result<ShuzhaiData>> cbk = new Callback<Result<ShuzhaiData>>() {

            @Override
            public void onResponse(Call<Result<ShuzhaiData>> call,
                                   Response<Result<ShuzhaiData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<ShuzhaiData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 书摘列表
     *
     * @param callback
     * @return
     */
    public static Call getShuzhaiList(Map<String, Object> baseData,
                                      final ResultCallback<Result<ShuzhaiData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<ShuzhaiData>> call = mService.getShuzhaiList(header, baseData);
        Callback<Result<ShuzhaiData>> cbk = new Callback<Result<ShuzhaiData>>() {

            @Override
            public void onResponse(Call<Result<ShuzhaiData>> call,
                                   Response<Result<ShuzhaiData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<ShuzhaiData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取分类列表（爱心书屋）
     *
     * @param callback
     * @return
     */
    public static Call getBookBySortList(Map<String, Object> baseData,
                                         final ResultCallback<Result<RecData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<RecData>> call = mService.getBookBySortList(header, baseData);
        Callback<Result<RecData>> cbk = new Callback<Result<RecData>>() {

            @Override
            public void onResponse(Call<Result<RecData>> call,
                                   Response<Result<RecData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<RecData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 书评
     *
     * @param callback
     * @return
     */
    public static Call isLikeBookReview(Map<String, Object> baseData,
                                        final ResultCallback<Result<Zan>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Zan>> call = mService.isLikeBookReview(header, baseData);
        Callback<Result<Zan>> cbk = new Callback<Result<Zan>>() {

            @Override
            public void onResponse(Call<Result<Zan>> call,
                                   Response<Result<Zan>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Zan>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 文章
     *
     * @param callback
     * @return
     */
    public static Call isLikeEssay(Map<String, Object> baseData,
                                   final ResultCallback<Result<Zan>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Zan>> call = mService.isLikeEssay(header, baseData);
        Callback<Result<Zan>> cbk = new Callback<Result<Zan>>() {

            @Override
            public void onResponse(Call<Result<Zan>> call,
                                   Response<Result<Zan>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Zan>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 评论like
     *
     * @param callback
     * @return
     */
    public static Call isLikeComment(Map<String, Object> baseData,
                                     final ResultCallback<Result<Zan>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Zan>> call = mService.isLikeComment(header, baseData);
        Callback<Result<Zan>> cbk = new Callback<Result<Zan>>() {

            @Override
            public void onResponse(Call<Result<Zan>> call,
                                   Response<Result<Zan>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Zan>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 书籍
     *
     * @param callback
     * @return
     */
    public static Call isLikeBook(Map<String, Object> baseData,
                                  final ResultCallback<Result<Zan>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Zan>> call = mService.isLikeBook(header, baseData);
        Callback<Result<Zan>> cbk = new Callback<Result<Zan>>() {

            @Override
            public void onResponse(Call<Result<Zan>> call,
                                   Response<Result<Zan>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Zan>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }


    /**
     * 取消点赞
     *
     * @param callback
     * @return
     */
    public static Call dontLike(Map<String, Object> baseData,
                                final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.dontLike(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 书评点赞
     *
     * @param callback
     * @return
     */
    public static Call likeBookReview(Map<String, Object> baseData,
                                      final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.likeBookReview(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 文章点赞
     *
     * @param callback
     * @return
     */
    public static Call likeEssay(Map<String, Object> baseData,
                                 final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.likeEssay(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 评论like
     *
     * @param callback
     * @return
     */
    public static Call likeComment(Map<String, Object> baseData,
                                   final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.likeComment(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 书籍like
     *
     * @param callback
     * @return
     */
    public static Call likeBook(Map<String, Object> baseData,
                                final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.likeBook(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * YSHY add
     *
     * @param callback
     * @return
     */
    public static Call addEssayComment(Map<String, Object> baseData,
                                       final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.addEssayComment(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * YSHY add
     *
     * @param callback
     * @return
     */
    public static Call getEssayCommentList(Map<String, Object> baseData,
                                           final ResultCallback<Result<CommentListEntity>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<CommentListEntity>> call = mService.getEssayCommentList(header, baseData);
        Callback<Result<CommentListEntity>> cbk = new Callback<Result<CommentListEntity>>() {

            @Override
            public void onResponse(Call<Result<CommentListEntity>> call,
                                   Response<Result<CommentListEntity>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<CommentListEntity>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * YSHY add
     *
     * @param callback
     * @return
     */
    public static Call addYSHY(Map<String, Object> baseData,
                               final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.addYSHY(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }


    /**
     * SHHZ add
     *
     * @param callback
     * @return
     */
    public static Call addSHHZ(Map<String, Object> baseData,
                               final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.addSHHZ(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * SHHZ列表
     *
     * @param callback
     * @return
     */
    public static Call getSHHZList(Map<String, Object> baseData,
                                   final ResultCallback<Result<YSHYData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<YSHYData>> call = mService.getSHHZList(header, baseData);
        Callback<Result<YSHYData>> cbk = new Callback<Result<YSHYData>>() {

            @Override
            public void onResponse(Call<Result<YSHYData>> call,
                                   Response<Result<YSHYData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<YSHYData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * YSHY列表
     *
     * @param callback
     * @return
     */
    public static Call getYSHYList(Map<String, Object> baseData,
                                   final ResultCallback<Result<YSHYData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<YSHYData>> call = mService.getYSHYList(header, baseData);
        Callback<Result<YSHYData>> cbk = new Callback<Result<YSHYData>>() {

            @Override
            public void onResponse(Call<Result<YSHYData>> call,
                                   Response<Result<YSHYData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<YSHYData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 书评评论列表
     *
     * @param callback
     * @return
     */
    public static Call addBookReviewComment(Map<String, Object> baseData,
                                            final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.addBookReviewComment(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 书评评论列表
     *
     * @param callback
     * @return
     */
    public static Call getBookReviewCommentList(Map<String, Object> baseData,
                                                final ResultCallback<Result<AnswerComment>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<AnswerComment>> call = mService.getBookReviewCommentList(header, baseData);
        Callback<Result<AnswerComment>> cbk = new Callback<Result<AnswerComment>>() {

            @Override
            public void onResponse(Call<Result<AnswerComment>> call,
                                   Response<Result<AnswerComment>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<AnswerComment>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 预约想看
     *
     * @param callback
     * @return
     */
    public static Call lendBook(Map<String, Object> baseData,
                                final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.lendBook(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 预约想看
     *
     * @param callback
     * @return
     */
    public static Call wantSee(Map<String, Object> baseData,
                               final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.wantSee(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取图书借书卡
     *
     * @param callback
     * @return
     */
    public static Call getCommentDetail(Map<String, Object> baseData,
                                        final ResultCallback<Result<CommentDetail>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<CommentDetail>> call = mService.getCommentDetail(header, baseData);
        Callback<Result<CommentDetail>> cbk = new Callback<Result<CommentDetail>>() {

            @Override
            public void onResponse(Call<Result<CommentDetail>> call,
                                   Response<Result<CommentDetail>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<CommentDetail>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取图书借书卡
     *
     * @param callback
     * @return
     */
    public static Call addBookCommentComment(Map<String, Object> baseData,
                                             final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.addBookCommentComment(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取图书书评列表
     *
     * @param callback
     * @return
     */
    public static Call getBookReviewList(Map<String, Object> baseData,
                                         final ResultCallback<Result<BookCommentData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<BookCommentData>> call = mService.getBookReviewList(header, baseData);
        Callback<Result<BookCommentData>> cbk = new Callback<Result<BookCommentData>>() {

            @Override
            public void onResponse(Call<Result<BookCommentData>> call,
                                   Response<Result<BookCommentData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<BookCommentData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }


    /**
     * 获取图书借书卡
     *
     * @param callback
     * @return
     */
    public static Call getBookRelativeRecList(Map<String, Object> baseData,
                                              final ResultCallback<Result<BookRelativeRecData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<BookRelativeRecData>> call = mService.getBookRelativeRecList(header, baseData);
        Callback<Result<BookRelativeRecData>> cbk = new Callback<Result<BookRelativeRecData>>() {

            @Override
            public void onResponse(Call<Result<BookRelativeRecData>> call,
                                   Response<Result<BookRelativeRecData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<BookRelativeRecData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取图书借书卡
     *
     * @param callback
     * @return
     */
    public static Call getBookLendCard(Map<String, Object> baseData,
                                       final ResultCallback<Result<BookLendCardData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<BookLendCardData>> call = mService.getBookLendCard(header, baseData);
        Callback<Result<BookLendCardData>> cbk = new Callback<Result<BookLendCardData>>() {

            @Override
            public void onResponse(Call<Result<BookLendCardData>> call,
                                   Response<Result<BookLendCardData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<BookLendCardData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

//    /**
//     * 获取图书评论列表哦
//     *
//     * @param callback
//     * @return
//     */
//    public static Call getBookCommentList(Map<String, Object> baseData,
//                                      final ResultCallback<Result<BookCommentData>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//        header.put("accessToken", MApplication.getAccessToken());
//        Log.i("fancy", header.get("accessToken") + "");
//        Call<Result<BookCommentData>> call = mService.getBookCommentList(header, baseData);
//        Callback<Result<BookCommentData>> cbk = new Callback<Result<BookCommentData>>() {
//
//            @Override
//            public void onResponse(Call<Result<BookCommentData>> call,
//                                   Response<Result<BookCommentData>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<BookCommentData>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//
//    }

    /**
     * 获取图书预订者
     *
     * @param callback
     * @return
     */
    public static Call getBookOrderer(Map<String, Object> baseData,
                                      final ResultCallback<Result<OrderData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<OrderData>> call = mService.getBookOrderer(header, baseData);
        Callback<Result<OrderData>> cbk = new Callback<Result<OrderData>>() {

            @Override
            public void onResponse(Call<Result<OrderData>> call,
                                   Response<Result<OrderData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<OrderData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取图书详情，（）区别扫描获取的信息
     *
     * @param callback
     * @return
     */
    public static Call getBookInfo(Map<String, Object> baseData,
                                   final ResultCallback<Result<BookInfo>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<BookInfo>> call = mService.getBookInfo(header, baseData);
        Callback<Result<BookInfo>> cbk = new Callback<Result<BookInfo>>() {

            @Override
            public void onResponse(Call<Result<BookInfo>> call,
                                   Response<Result<BookInfo>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<BookInfo>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 爱心书屋书单推荐
     *
     * @param callback
     * @return
     */
    public static Call getHouseBookRec(Map<String, Object> baseData,
                                       final ResultCallback<Result<RecData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<RecData>> call = mService.getHouseBookRec(header, baseData);
        Callback<Result<RecData>> cbk = new Callback<Result<RecData>>() {

            @Override
            public void onResponse(Call<Result<RecData>> call,
                                   Response<Result<RecData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<RecData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 爱心书屋热门推荐
     *
     * @param callback
     * @return
     */
    public static Call getHouseHotRec(Map<String, Object> baseData,
                                      final ResultCallback<Result<RecData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<RecData>> call = mService.getHouseHotRec(header, baseData);
        Callback<Result<RecData>> cbk = new Callback<Result<RecData>>() {

            @Override
            public void onResponse(Call<Result<RecData>> call,
                                   Response<Result<RecData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<RecData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取扫描条形码的结果
     *
     * @param callback
     * @return
     */
    public static Call getBookTopLinesList(Map<String, Object> baseData,
                                           final ResultCallback<Result<NewsData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<NewsData>> call = mService.getBookTopLinesList(header, baseData);
        Callback<Result<NewsData>> cbk = new Callback<Result<NewsData>>() {

            @Override
            public void onResponse(Call<Result<NewsData>> call,
                                   Response<Result<NewsData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<NewsData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取扫描条形码的结果
     *
     * @param callback
     * @return
     */
    public static Call donateBook(Map<String, Object> baseData,
                                  final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.donateBook(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取扫描条形码的结果
     * 扫描获取书库中的书的信息
     *
     * @param callback
     * @return
     */
    public static Call getBookDetail(Map<String, Object> baseData,
                                     final ResultCallback<Result<BookDetail>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<BookDetail>> call = mService.getBookDetail(header, baseData);
        Callback<Result<BookDetail>> cbk = new Callback<Result<BookDetail>>() {

            @Override
            public void onResponse(Call<Result<BookDetail>> call,
                                   Response<Result<BookDetail>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<BookDetail>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取首页最新发布图书列表
     *
     * @param callback
     * @return
     */
    public static Call getBookList(Map<String, Object> baseData,
                                   final ResultCallback<Result<BookListData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<BookListData>> call = mService.getBookList(header, baseData);
        Callback<Result<BookListData>> cbk = new Callback<Result<BookListData>>() {

            @Override
            public void onResponse(Call<Result<BookListData>> call,
                                   Response<Result<BookListData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<BookListData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取快报列表
     *
     * @param callback
     * @return
     */
    public static Call getNewsList(Map<String, Object> baseData,
                                   final ResultCallback<Result<NewsData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<NewsData>> call = mService.getTopLinesList(header, baseData);
        Callback<Result<NewsData>> cbk = new Callback<Result<NewsData>>() {

            @Override
            public void onResponse(Call<Result<NewsData>> call,
                                   Response<Result<NewsData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<NewsData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取爱心书屋轮播图列表
     *
     * @param callback
     * @return
     */
    public static Call getBannerHouseList(Map<String, Object> baseData,
                                          final ResultCallback<Result<BannerData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<BannerData>> call = mService.getBannerHouseList(header, baseData);
        Callback<Result<BannerData>> cbk = new Callback<Result<BannerData>>() {

            @Override
            public void onResponse(Call<Result<BannerData>> call,
                                   Response<Result<BannerData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<BannerData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取轮播图列表
     *
     * @param callback
     * @return
     */
    public static Call getBannerList(Map<String, Object> baseData,
                                     final ResultCallback<Result<BannerData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<BannerData>> call = mService.getBannerList(header, baseData);
        Callback<Result<BannerData>> cbk = new Callback<Result<BannerData>>() {

            @Override
            public void onResponse(Call<Result<BannerData>> call,
                                   Response<Result<BannerData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<BannerData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }

    /**
     * 获取专题报道列表
     *
     * @param callback
     * @return
     */
    public static Call getEssayList(Map<String, Object> baseData,
                                    final ResultCallback<Result<CollectEssayItem>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<CollectEssayItem>> call = mService.getEssayList(header, baseData);
        Callback<Result<CollectEssayItem>> cbk = new Callback<Result<CollectEssayItem>>() {

            @Override
            public void onResponse(Call<Result<CollectEssayItem>> call,
                                   Response<Result<CollectEssayItem>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<CollectEssayItem>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;

    }


    /**
     * 获取专题报道列表
     *
     * @param callback
     * @return
     */
    public static Call getSubjectReportList(Map<String, Object> baseData,
                                            final ResultCallback<Result<WeekEntity>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<WeekEntity>> call = mService.getSubjectReportList(header, baseData);
        Callback<Result<WeekEntity>> cbk = new Callback<Result<WeekEntity>>() {

            @Override
            public void onResponse(Call<Result<WeekEntity>> call,
                                   Response<Result<WeekEntity>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<WeekEntity>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 获取每周推荐列表
     *
     * @param callback
     * @return
     */
    public static Call getWeekRecList(Map<String, Object> baseData,
                                      final ResultCallback<Result<WeekEntity>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<WeekEntity>> call = mService.getWeekRecList(header, baseData);
        Callback<Result<WeekEntity>> cbk = new Callback<Result<WeekEntity>>() {

            @Override
            public void onResponse(Call<Result<WeekEntity>> call,
                                   Response<Result<WeekEntity>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<WeekEntity>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }


    /**
     * 获取援助列表
     *
     * @param callback
     * @return
     */
    public static Call getMyAttendAssistantList(Map<String, Object> baseData,
                                                final ResultCallback<Result<AssistantEntity>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<AssistantEntity>> call = mService.getMyAttendAssistantList(header, baseData);
        Callback<Result<AssistantEntity>> cbk = new Callback<Result<AssistantEntity>>() {

            @Override
            public void onResponse(Call<Result<AssistantEntity>> call,
                                   Response<Result<AssistantEntity>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<AssistantEntity>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 获取援助列表
     *
     * @param callback
     * @return
     */
    public static Call getAssistantList(Map<String, Object> baseData,
                                        final ResultCallback<Result<AssistantEntity>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<AssistantEntity>> call = mService.getAssistantList(header, baseData);
        Callback<Result<AssistantEntity>> cbk = new Callback<Result<AssistantEntity>>() {

            @Override
            public void onResponse(Call<Result<AssistantEntity>> call,
                                   Response<Result<AssistantEntity>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<AssistantEntity>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 反馈
     *
     * @param callback
     * @return
     */
    public static Call feedback(Map<String, Object> baseData,
                                final ResultCallback<Result<Object>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Object>> call = mService.feedback(header, baseData);
        Callback<Result<Object>> cbk = new Callback<Result<Object>>() {

            @Override
            public void onResponse(Call<Result<Object>> call,
                                   Response<Result<Object>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Object>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }


    /**
     * 心愿捐赠确认
     *
     * @param callback
     * @return
     */
    public static Call wishDonateConfirm(Map<String, Object> baseData,
                                         final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.wishDonateConfirm(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 心愿捐赠撤回
     *
     * @param callback
     * @return
     */
    public static Call wishDonateWithDraw(Map<String, Object> baseData,
                                          final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.wishDonateWithDraw(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 心愿捐赠
     *
     * @param callback
     * @return
     */
    public static Call wishDonate(Map<String, Object> baseData,
                                  final ResultCallback<Result<WishDonateEntity>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<WishDonateEntity>> call = mService.wishDonate(header, baseData);
        Callback<Result<WishDonateEntity>> cbk = new Callback<Result<WishDonateEntity>>() {

            @Override
            public void onResponse(Call<Result<WishDonateEntity>> call,
                                   Response<Result<WishDonateEntity>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<WishDonateEntity>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 帮他实现
     *
     * @param callback
     * @return
     */
    public static Call helpIt(Map<String, Object> baseData,
                              final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.helpIt(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 捐赠申请详细确认
     *
     * @param callback
     * @return
     */
    public static Call isCollect(Map<String, Object> baseData,
                                 final ResultCallback<Result<Map<String, Object>>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Map<String, Object>>> call = mService.isCollect(header, baseData);
        Callback<Result<Map<String, Object>>> cbk = new Callback<Result<Map<String, Object>>>() {

            @Override
            public void onResponse(Call<Result<Map<String, Object>>> call,
                                   Response<Result<Map<String, Object>>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Map<String, Object>>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 心愿列表→确认发货
     *
     * @param callback
     * @return
     */
    public static Call WishSendConfirm(Map<String, Object> baseData,
                                       final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.WishSendConfirm(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 捐赠申请详细确认
     *
     * @param callback
     * @return
     */
    public static Call donateWishConfirm(Map<String, Object> baseData,
                                         final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.donateWishConfirm(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 撤销
     *
     * @param callback
     * @return
     */
    public static Call donateApplyWithDraw(Map<String, Object> baseData,
                                           final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Log.i("fancy", header.get("accessToken") + "");
        Call<Result<Void>> call = mService.donateApplyWithDraw(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call,
                                   Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 订单申请人的订单详细
     *
     * @param callback
     * @return
     */
    public static Call applyDetail(Map<String, Object> baseData,
                                   final ResultCallback<Result<ApplyDetailEntity>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Call<Result<ApplyDetailEntity>> call = mService.applyDetail(header, baseData);
        Callback<Result<ApplyDetailEntity>> cbk = new Callback<Result<ApplyDetailEntity>>() {

            @Override
            public void onResponse(Call<Result<ApplyDetailEntity>> call,
                                   Response<Result<ApplyDetailEntity>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<ApplyDetailEntity>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 与订单的关系
     *
     * @param callback
     * @return
     */
    public static Call getOrderRelation(Map<String, Object> baseData,
                                        final ResultCallback<Result<Map<String, Object>>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Call<Result<Map<String, Object>>> call = mService.getOrderRelation(header, baseData);
        Callback<Result<Map<String, Object>>> cbk = new Callback<Result<Map<String, Object>>>() {

            @Override
            public void onResponse(Call<Result<Map<String, Object>>> call,
                                   Response<Result<Map<String, Object>>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Map<String, Object>>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 获得评论列表
     *
     * @param callback
     * @return
     */
    public static Call iWant(Map<String, Object> baseData,
                             final ResultCallback<Result<Object>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Call<Result<Object>> call = mService.iWant(header, baseData);
        Callback<Result<Object>> cbk = new Callback<Result<Object>>() {

            @Override
            public void onResponse(Call<Result<Object>> call,
                                   Response<Result<Object>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Object>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }


    /**
     * 获得评论列表
     *
     * @param callback
     * @return
     */
    public static Call getCommentList(Map<String, Object> baseData,
                                      final ResultCallback<Result<CommentListEntity>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Call<Result<CommentListEntity>> call = mService.getCommentList(header, baseData);
        Callback<Result<CommentListEntity>> cbk = new Callback<Result<CommentListEntity>>() {

            @Override
            public void onResponse(Call<Result<CommentListEntity>> call,
                                   Response<Result<CommentListEntity>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<CommentListEntity>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }


    /**
     * 获得想要的列表
     *
     * @param callback
     * @return
     */
    public static Call getWantList(Map<String, Object> baseData, final ResultCallback<Result<WantListEntity>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Call<Result<WantListEntity>> call = mService.getWantList(header, baseData);
        Callback<Result<WantListEntity>> cbk = new Callback<Result<WantListEntity>>() {

            @Override
            public void onResponse(Call<Result<WantListEntity>> call, Response<Result<WantListEntity>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<WantListEntity>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 获取捐赠详细
     *
     * @param callback
     * @return
     */
    public static Call getDonateDetail(Map<String, Object> baseData, final ResultCallback<Result<DonateDetailData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Call<Result<DonateDetailData>> call = mService.getDonateDetail(header, baseData);
        Callback<Result<DonateDetailData>> cbk = new Callback<Result<DonateDetailData>>() {

            @Override
            public void onResponse(Call<Result<DonateDetailData>> call, Response<Result<DonateDetailData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<DonateDetailData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 收藏
     *
     * @param callback
     * @return
     */
    public static Call collect(Map<String, Object> baseData, final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Call<Result<Void>> call = mService.collect(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call, Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 评论
     *
     * @param callback
     * @return
     */
    public static Call addComment(Map<String, Object> baseData, final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Call<Result<Void>> call = mService.addComment(header, baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call, Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 获取心愿详情
     *
     * @param callback
     * @return
     */
    public static Call getWishDetail(Map<String, Object> baseData, final ResultCallback<Result<WishDetailEntity>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Call<Result<WishDetailEntity>> call = mService.getWishDetail(header, baseData);
        Callback<Result<WishDetailEntity>> cbk = new Callback<Result<WishDetailEntity>>() {

            @Override
            public void onResponse(Call<Result<WishDetailEntity>> call, Response<Result<WishDetailEntity>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<WishDetailEntity>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 获取捐赠墙列表
     *
     * @param callback
     * @return
     */
    public static Call getWishWall(Map<String, Object> baseData, final ResultCallback<Result<WallListData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Call<Result<WallListData>> call = mService.getWishWall(header, baseData);
        Callback<Result<WallListData>> cbk = new Callback<Result<WallListData>>() {

            @Override
            public void onResponse(Call<Result<WallListData>> call, Response<Result<WallListData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<WallListData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 获取捐赠墙列表
     *
     * @param callback
     * @return
     */
    public static Call getDonateWall(Map<String, Object> baseData, final ResultCallback<Result<WallListData>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("accessToken", MApplication.getAccessToken());
        Call<Result<WallListData>> call = mService.getDonateWall(header, baseData);
        Callback<Result<WallListData>> cbk = new Callback<Result<WallListData>>() {

            @Override
            public void onResponse(Call<Result<WallListData>> call, Response<Result<WallListData>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<WallListData>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * @param baseData 发布捐赠
     * @param callback
     * @return
     */
    public static Call donate(Map<String, Object> baseData,
                              final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        Call<Result<Void>> call = mService.donate(baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call, Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * @param baseData 发布心愿
     * @param callback
     * @return
     */
    public static Call makeWish(Map<String, Object> baseData,
                                final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        Call<Result<Void>> call = mService.makerWish(baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call, Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 这里的user和token都要传值了
     *
     * @param callback
     * @return
     */
    public static Call getStationeries(final ResultCallback<Result<Stationery>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        Call<Result<Stationery>> call = mService.getStationeries();
        Callback<Result<Stationery>> cbk = new Callback<Result<Stationery>>() {

            @Override
            public void onResponse(Call<Result<Stationery>> call, Response<Result<Stationery>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Stationery>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }


//    /**
//     * @param callback
//     * @return
//     */
//    public static Call updateOneFile(Map<String, RequestBody> type, File file, final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//
//        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
//
//        String descriptionString = "image";
//        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
//
//        Call<Result<String>> call = mService.updateOneFile(type, description, body);
//        Callback<Result<String>> cbk = new Callback<Result<String>>() {
//
//            @Override
//            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<String>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }


    /**
     * @param callback
     * @return
     */
    public static Call uploadOneFile(File file, final ResultCallback<Result<UpdateFilesEntity>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);

        String descriptionString = "image";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

        Call<Result<UpdateFilesEntity>> call = mService.uploadInfoFile(description, body);
        Callback<Result<UpdateFilesEntity>> cbk = new Callback<Result<UpdateFilesEntity>>() {

            @Override
            public void onResponse(Call<Result<UpdateFilesEntity>> call, Response<Result<UpdateFilesEntity>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<UpdateFilesEntity>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * @param baseData
     * @param callback
     * @return
     */
//    public static Call getUpdate(Map<String, Object> baseData,
//                                 final ResultCallback<Result<UpdateEntity>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = MApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<UpdateEntity>> call = mService.update(header, baseData);
//        Callback<Result<UpdateEntity>> cbk = new Callback<Result<UpdateEntity>>() {
//
//            @Override
//            public void onResponse(Call<Result<UpdateEntity>> call, Response<Result<UpdateEntity>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<UpdateEntity>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }


    /**
     * @param callback
     * @return
     */
    public static Call logout(Map<String, Object> baseData, final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);

        Call<Result<Void>> call = mService.logout(baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call, Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 这里的user和token都要传值了
     *
     * @param baseData email 联系邮箱;feedbackDesc 反馈内容
     * @param callback
     * @return
     */
    public static Call addFeedback(Map<String, Object> baseData,
                                   final ResultCallback<Result<String>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();

        Pair<String, String> pair = MApplication.getUser();
        header.put("NARAN-ACCESS-USER", pair.first);
        header.put("NARAN-ACCESS-TOKEN", pair.second);
        Call<Result<String>> call = mService.addFeedback(header, baseData);
        Callback<Result<String>> cbk = new Callback<Result<String>>() {

            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 这里的user和token都要传值了
     *
     * @param baseData
     * @param callback
     * @return
     */
    public static Call saveUserInfo(Map<String, Object> baseData,
                                    final ResultCallback<Result<UserInfo>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();

        Pair<String, String> pair = MApplication.getUser();
        header.put("NARAN-ACCESS-USER", pair.first);
        header.put("NARAN-ACCESS-TOKEN", pair.second);
        Call<Result<UserInfo>> call = mService.saveUserInfo(header, baseData);
        Callback<Result<UserInfo>> cbk = new Callback<Result<UserInfo>>() {

            @Override
            public void onResponse(Call<Result<UserInfo>> call, Response<Result<UserInfo>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<UserInfo>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 这里的user和token都要传值了
     *
     * @param baseData
     * @param callback
     * @return
     */
    public static Call setDefaultAddress(Map<String, Object> baseData,
                                         final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);

        Call<Result<Void>> call = mService.setDefaultAddress(baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call, Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }


    /**
     * 这里的user和token都要传值了
     *
     * @param callback
     * @return
     */
    public static Call modifyAddress(Map<String, Object> baseData,
                                     final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        Call<Result<Void>> call = mService.modifyAddress(baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call, Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 这里的user和token都要传值了
     *
     * @param callback
     * @return
     */
    public static Call addAddress(Map<String, Object> baseData,
                                  final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        Call<Result<Void>> call = mService.addAddress(baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call, Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 这里的user和token都要传值了
     *
     * @param callback
     * @return
     */
    public static Call delAddress(Map<String, Object> baseData,
                                  final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        Call<Result<Void>> call = mService.delAddress(baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {

            @Override
            public void onResponse(Call<Result<Void>> call, Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    /**
     * 这里的user和token都要传值了
     *
     * @param callback
     * @return
     */
    public static Call getAddressList(Map<String, Object> baseData,
                                      final ResultCallback<Result<Address>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        Call<Result<Address>> call = mService.getAddressList(baseData);
        Callback<Result<Address>> cbk = new Callback<Result<Address>>() {

            @Override
            public void onResponse(Call<Result<Address>> call, Response<Result<Address>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Address>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }


    /**
     * 这里的user和token都要传值了
     *
     * @param baseData
     * @param callback
     * @return
     */
    public static Call modifyPsd(Map<String, Object> baseData,
                                 final ResultCallback<Result<String>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();

        Pair<String, String> pair = MApplication.getUser();
        header.put("NARAN-ACCESS-USER", pair.first);
        header.put("NARAN-ACCESS-TOKEN", pair.second);
        Call<Result<String>> call = mService.forgetPsd(header, baseData);
        Callback<Result<String>> cbk = new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    public static Call forgetPsd(Map<String, Object> baseData,
                                 final ResultCallback<Result<String>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("NARAN-ACCESS-USER", "");
        header.put("NARAN-ACCESS-TOKEN", "");
        Call<Result<String>> call = mService.forgetPsd(header, baseData);
        Callback<Result<String>> cbk = new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    public static Call getVerifyCode(Map<String, Object> baseData,
                                     final ResultCallback<Result<Void>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        Call<Result<Void>> call = mService.getVerifyCode(baseData);
        Callback<Result<Void>> cbk = new Callback<Result<Void>>() {
            @Override
            public void onResponse(Call<Result<Void>> call, Response<Result<Void>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<Void>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    //
    public static Call register(Map<String, Object> baseData,
                                final ResultCallback<Result<HashMap<String, Object>>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("NARAN-ACCESS-USER", "");
        header.put("NARAN-ACCESS-TOKEN", "");
        Call<Result<HashMap<String, Object>>> call = mService.register(header, baseData);
        Callback<Result<HashMap<String, Object>>> cbk = new Callback<Result<HashMap<String, Object>>>() {
            @Override
            public void onResponse(Call<Result<HashMap<String, Object>>> call, Response<Result<HashMap<String, Object>>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<HashMap<String, Object>>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

    //
    public static Call login(Map<String, Object> baseData,
                             final ResultCallback<Result<UserInfo>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("NARAN-ACCESS-USER", "");
        header.put("NARAN-ACCESS-TOKEN", "");
        Call<Result<UserInfo>> call = mService.login(header, baseData);
        Callback<Result<UserInfo>> cbk = new Callback<Result<UserInfo>>() {
            @Override
            public void onResponse(Call<Result<UserInfo>> call, Response<Result<UserInfo>> response) {
                Result.onResponse(response, callback);
            }

            @Override
            public void onFailure(Call<Result<UserInfo>> call, Throwable t) {
                Result.onFailure(t, callback);
            }
        };
        call.enqueue(cbk);
        return call;
    }

}
