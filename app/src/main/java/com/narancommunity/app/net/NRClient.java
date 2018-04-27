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
import com.narancommunity.app.entity.DonateDetailData;
import com.narancommunity.app.entity.NewsData;
import com.narancommunity.app.entity.OrderData;
import com.narancommunity.app.entity.RecData;
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
//
//
//    /**
//     * @param callback userId
//     * @return
//     */
//    public static Call jubaoFarm(Map<String, Object> baseData, final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.jubaoFarm(header, baseData);
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
//
//    /**
//     * @param baseData objectId  评论对象Id;criticAuthorName 被回复的留言的作者姓名
//     *                 ;criticId 被回复的留言Id;type 评论类型
//     *                 ;content 评论内容
//     * @param callback
//     * @return
//     */
//    public static Call getProductListBySort(Map<String, Object> baseData,
//                                            final ResultCallback<Result<NRFarmProduct>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRFarmProduct>> call = mService.getProductListBySort(header, baseData);
//        Callback<Result<NRFarmProduct>> cbk = new Callback<Result<NRFarmProduct>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRFarmProduct>> call, Response<Result<NRFarmProduct>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRFarmProduct>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * @param baseData objectId  评论对象Id;criticAuthorName 被回复的留言的作者姓名
//     *                 ;criticId 被回复的留言Id;type 评论类型
//     *                 ;content 评论内容
//     * @param callback
//     * @return
//     */
//    public static Call addComment(Map<String, Object> baseData,
//                                  final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.addComment(header, baseData);
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
//
//    /**
//     * @param baseData keyword;page;row
//     *                 =====map.get("datapage");
//     * @param callback
//     * @return
//     */
//    public static Call searchFarmOrProduct(Map<String, Object> baseData,
//                                           final ResultCallback<Result<Map<String, List<SearchFarmOrProduct>>>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<Map<String, List<SearchFarmOrProduct>>>> call = mService.searchFarmOrProduct(header, baseData);
//        Callback<Result<Map<String, List<SearchFarmOrProduct>>>> cbk = new Callback<Result<Map<String, List<SearchFarmOrProduct>>>>() {
//
//            @Override
//            public void onResponse(Call<Result<Map<String, List<SearchFarmOrProduct>>>> call, Response<Result<Map<String, List<SearchFarmOrProduct>>>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<Map<String, List<SearchFarmOrProduct>>>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//
//    /**
//     * @param baseData page  ;row
//     * @param callback
//     * @return
//     */
//    public static Call delComment(Map<String, Object> baseData,
//                                  final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.delComment(header, baseData);
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
//
//    /**
//     * @param baseData page  ;row
//     * @param callback
//     * @return
//     */
//    public static Call getSearchHotList(Map<String, Object> baseData,
//                                        final ResultCallback<Result<Map<String, NRBaseData<SearchKeyWord>>>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<Map<String, NRBaseData<SearchKeyWord>>>> call = mService.getSearchHot(header, baseData);
//        Callback<Result<Map<String, NRBaseData<SearchKeyWord>>>> cbk = new Callback<Result<Map<String, NRBaseData<SearchKeyWord>>>>() {
//
//            @Override
//            public void onResponse(Call<Result<Map<String, NRBaseData<SearchKeyWord>>>> call, Response<Result<Map<String, NRBaseData<SearchKeyWord>>>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<Map<String, NRBaseData<SearchKeyWord>>>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call getUpdate(Map<String, Object> baseData,
//                                 final ResultCallback<Result<UpdateEntity>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
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
//
//    /**
//     * @param baseData objectId  ID ;
//     *                 type FCOM 生产者评论 BAOL 举报 CONT	其他评论 ; page
//     * @param callback
//     * @return
//     */
//    public static Call getDynamicCommentList(Map<String, Object> baseData,
//                                             final ResultCallback<Result<NRComment>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRComment>> call = mService.getDynamicCommentList(header, baseData);
//        Callback<Result<NRComment>> cbk = new Callback<Result<NRComment>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRComment>> call, Response<Result<NRComment>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRComment>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * @param baseData userId 用户ID ; contentType 类型 NEWS:资讯，PRODUCT:产品，TOPIC:话题; page
//     * @param callback
//     * @return
//     */
//    public static Call getMineCollectNews(Map<String, Object> baseData, final ResultCallback<Result<NRNewsData>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRNewsData>> call = mService.getCollectNewsList(header, baseData);
//        Callback<Result<NRNewsData>> cbk = new Callback<Result<NRNewsData>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRNewsData>> call, Response<Result<NRNewsData>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRNewsData>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * @param baseData userId 用户ID ; contentType 类型 NEWS:资讯，PRODUCT:产品，TOPIC:话题; page
//     * @param callback
//     * @return
//     */
//    public static Call getMineCollectTopic(Map<String, Object> baseData, final ResultCallback<Result<NRMineTopic>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRMineTopic>> call = mService.getCollectTopicList(header, baseData);
//        Callback<Result<NRMineTopic>> cbk = new Callback<Result<NRMineTopic>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRMineTopic>> call, Response<Result<NRMineTopic>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRMineTopic>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * @param baseData userId 用户ID ; contentType 类型 NEWS:资讯，PRODUCT:产品，TOPIC:话题; page
//     * @param callback
//     * @return
//     */
//    public static Call getMineCollectProduct(Map<String, Object> baseData, final ResultCallback<Result<NRFarmProduct>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRFarmProduct>> call = mService.getCollectProductList(header, baseData);
//        Callback<Result<NRFarmProduct>> cbk = new Callback<Result<NRFarmProduct>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRFarmProduct>> call, Response<Result<NRFarmProduct>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRFarmProduct>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * @param baseData userId 用户ID ; contentType 类型 FARM:生产者 ; page
//     * @param callback
//     * @return
//     */
//    public static Call getMineFollowList(Map<String, Object> baseData, final ResultCallback<Result<NRMineFollows>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRMineFollows>> call = mService.getFollowLIst(header, baseData);
//        Callback<Result<NRMineFollows>> cbk = new Callback<Result<NRMineFollows>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRMineFollows>> call, Response<Result<NRMineFollows>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRMineFollows>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//

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

    //
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData title Required 标题; coverPath  ; body Required
//     * @param callback
//     * @return
//     */
//    public static Call releaseTopic(Map<String, Object> baseData,
//                                    final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.releaseTopic(header, baseData);
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
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData contentType 类型:ALL： 全部 ACTI:活动,DYNAMIC:生产者动态,All:全部 /page
//     * @param callback
//     * @return
//     */
//    public static Call getMineList(Map<String, Object> baseData,
//                                   final ResultCallback<Result<NRMineData>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRMineData>> call = mService.getMineList(header, baseData);
//        Callback<Result<NRMineData>> cbk = new Callback<Result<NRMineData>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRMineData>> call, Response<Result<NRMineData>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRMineData>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData contentType 类型:ALL： 全部 ACTI:活动,DYNAMIC:生产者动态,All:全部 /page
//     * @param callback
//     * @return
//     */
//    public static Call getMineActList(Map<String, Object> baseData,
//                                      final ResultCallback<Result<MyJoinActList>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<MyJoinActList>> call = mService.getMineActList(header, baseData);
//        Callback<Result<MyJoinActList>> cbk = new Callback<Result<MyJoinActList>>() {
//
//            @Override
//            public void onResponse(Call<Result<MyJoinActList>> call, Response<Result<MyJoinActList>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<MyJoinActList>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData topicType 类型：GZZD：互动社区，STAD：社员监督/ page  ; row 一行个数
//     * @param callback
//     * @return
//     */
//    public static Call getCommunityTopicList(Map<String, Object> baseData,
//                                             final ResultCallback<Result<NRCommunityTopic>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRCommunityTopic>> call = mService.getCommunityTopicList(header, baseData);
//        Callback<Result<NRCommunityTopic>> cbk = new Callback<Result<NRCommunityTopic>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRCommunityTopic>> call, Response<Result<NRCommunityTopic>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRCommunityTopic>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * @param baseData keyword, pages
//     * @param callback
//     * @return
//     */
//    public static Call getIndexFarmList(Map<String, Object> baseData,
//                                        final ResultCallback<Result<NRFarm>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRFarm>> call = mService.getIndexFarmList(header, baseData);
//        Callback<Result<NRFarm>> cbk = new Callback<Result<NRFarm>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRFarm>> call, Response<Result<NRFarm>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRFarm>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * @param baseData keyword, pages
//     * @param callback
//     * @return
//     */
//    public static Call getFarmList(Map<String, Object> baseData,
//                                   final ResultCallback<Result<IndexWillMoreList>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<IndexWillMoreList>> call = mService.getFarmList(header, baseData);
//        Callback<Result<IndexWillMoreList>> cbk = new Callback<Result<IndexWillMoreList>>() {
//
//            @Override
//            public void onResponse(Call<Result<IndexWillMoreList>> call, Response<Result<IndexWillMoreList>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<IndexWillMoreList>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return type 查询类型: PRODUCT:产品,FARM:生产者
//     * userId,previousId,optionalId,type
//     */
//    public static Call setSelectListOrder(Map<String, Object> baseData,
//                                          final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.setSelectListOrder(header, baseData);
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
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData {userId}/{type}/{page} type 查询类型: PRODUCT:产品,FARM:生产者
//     * @param callback
//     * @return
//     */
//    public static Call getSelectProductList(Map<String, Object> baseData,
//                                            final ResultCallback<Result<NRFarmProduct>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRFarmProduct>> call = mService.getSelectProductList(header, baseData);
//        Callback<Result<NRFarmProduct>> cbk = new Callback<Result<NRFarmProduct>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRFarmProduct>> call, Response<Result<NRFarmProduct>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRFarmProduct>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData {userId}/{type}/{page} type 查询类型: PRODUCT:产品,FARM:生产者
//     * @param callback
//     * @return
//     */
//    public static Call getSelectFarmList(Map<String, Object> baseData,
//                                         final ResultCallback<Result<NRSelectFarmData>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRSelectFarmData>> call = mService.getSelectFarmList(header, baseData);
//        Callback<Result<NRSelectFarmData>> cbk = new Callback<Result<NRSelectFarmData>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRSelectFarmData>> call, Response<Result<NRSelectFarmData>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRSelectFarmData>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call getFarmAllFarm(Map<String, Object> baseData,
//                                      final ResultCallback<Result<NRFarmHomeMoreFarm>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRFarmHomeMoreFarm>> call = mService.getFarmAllFarm(header,
//                "" + baseData.get("area"), "" + baseData.get("classify"),
//                "" + baseData.get("page"), "" + baseData.get("row"));
//        Callback<Result<NRFarmHomeMoreFarm>> cbk = new Callback<Result<NRFarmHomeMoreFarm>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRFarmHomeMoreFarm>> call, Response<Result<NRFarmHomeMoreFarm>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRFarmHomeMoreFarm>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData productClassify 产品类型，为文字，如，养殖加工类
//     *                 status  生产者状态
//     *                 keyword 关键字
//     *                 page
//     *                 row
//     * @param callback
//     * @return
//     */
//    public static Call getFarmByKeyword(Map<String, Object> baseData,
//                                        final ResultCallback<Result<NRFarmHomeMoreFarm>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRFarmHomeMoreFarm>> call = mService.getFarmByKeyword(header, baseData);
//        Callback<Result<NRFarmHomeMoreFarm>> cbk = new Callback<Result<NRFarmHomeMoreFarm>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRFarmHomeMoreFarm>> call, Response<Result<NRFarmHomeMoreFarm>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRFarmHomeMoreFarm>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call submitJubaoData(Map<String, Object> baseData,
//                                       final ResultCallback<Result<NRFarmNotices>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRFarmNotices>> call = mService.getFarmAllNotice(header,
//                "" + baseData.get("farmId"), "" + baseData.get("type"), "" + baseData.get("page"));
//        Callback<Result<NRFarmNotices>> cbk = new Callback<Result<NRFarmNotices>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRFarmNotices>> call, Response<Result<NRFarmNotices>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRFarmNotices>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData keyword 关键字 ;page;row
//     * @param callback
//     * @return
//     */
//    public static Call search(Map<String, Object> baseData,
//                              final ResultCallback<Result<SearchEntity>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<SearchEntity>> call = mService.search(header, baseData);
//        Callback<Result<SearchEntity>> cbk = new Callback<Result<SearchEntity>>() {
//
//            @Override
//            public void onResponse(Call<Result<SearchEntity>> call, Response<Result<SearchEntity>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<SearchEntity>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData email 联系邮箱;feedbackDesc 反馈内容
//     * @param callback
//     * @return
//     */
//    public static Call addFeedback(Map<String, Object> baseData,
//                                   final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.addFeedback(header, baseData);
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
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call saveUserInfo(Map<String, Object> baseData,
//                                    final ResultCallback<Result<UserInfo>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<UserInfo>> call = mService.saveUserInfo(header, baseData);
//        Callback<Result<UserInfo>> cbk = new Callback<Result<UserInfo>>() {
//
//            @Override
//            public void onResponse(Call<Result<UserInfo>> call, Response<Result<UserInfo>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<UserInfo>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call getFarmAllNotice(Map<String, Object> baseData,
//                                        final ResultCallback<Result<NRFarmNotices>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRFarmNotices>> call = mService.getFarmAllNotice(header,
//                "" + baseData.get("farmId"), "" + baseData.get("type"), "" + baseData.get("page"));
//        Callback<Result<NRFarmNotices>> cbk = new Callback<Result<NRFarmNotices>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRFarmNotices>> call, Response<Result<NRFarmNotices>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRFarmNotices>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call getFarmHome(Map<String, Object> baseData,
//                                   final ResultCallback<Result<FarmEntity>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<FarmEntity>> call = mService.getFarmHome(header,
//                "" + baseData.get("keyword"));
//        Callback<Result<FarmEntity>> cbk = new Callback<Result<FarmEntity>>() {
//
//            @Override
//            public void onResponse(Call<Result<FarmEntity>> call, Response<Result<FarmEntity>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<FarmEntity>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call getProductDetail(Map<String, Object> baseData,
//                                        final ResultCallback<Result<Map<String, NRFarmProductItem>>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<Map<String, NRFarmProductItem>>> call = mService.getProductDetail(header,
//                "" + baseData.get("id"));
//        Callback<Result<Map<String, NRFarmProductItem>>> cbk = new Callback<Result<Map<String, NRFarmProductItem>>>() {
//
//            @Override
//            public void onResponse(Call<Result<Map<String, NRFarmProductItem>>> call, Response<Result<Map<String, NRFarmProductItem>>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<Map<String, NRFarmProductItem>>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return optionalIds
//     * (java.lang.String) 自选Id（连接符,）
//     */
//    public static Call delSelectAttention(Map<String, Object> baseData,
//                                          final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.delSelectAttentionList(header, baseData);
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
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData optionalId 自选对象ID; otype Required 查询类型: PRODUCT:产品,FARM:生产者
//     * @param callback
//     * @return
//     **/
//    public static Call addSelectAttention(Map<String, Object> baseData,
//                                          final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.addSelectAttention(header, baseData);
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
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback 用type区分，为PRODUCT和FARM
//     * @return "" + baseData.get("userId"), "" + baseData.get("type")
//     */
//    public static Call delProductAttention(Map<String, Object> baseData,
//                                           final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.delProductAttention(header, baseData);
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
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData commentId 对象ID
//     * @param callback
//     * @return
//     */
//    public static Call zanComment(Map<String, Object> baseData,
//                                  final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.zanComment(header, baseData);
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
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData commentId 对象ID
//     * @param callback
//     * @return
//     */
//    public static Call zanCancelComment(Map<String, Object> baseData,
//                                        final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.zanCancelComment(header, baseData);
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
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData objectId 对象ID ; type  关系类型 SC、DC
//     * @param callback
//     * @return
//     */
//    public static Call zanCancelDynamic(Map<String, Object> baseData,
//                                        final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.zanCancelDynamic(header, baseData);
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
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData objectId 对象ID ; type  关系类型 SC、DC
//     * @param callback
//     * @return
//     */
//    public static Call zanDynamic(Map<String, Object> baseData,
//                                  final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.zanDynamic(header, baseData);
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
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return 用type区分，为PRODUCT和FARM
//     */
//    public static Call addProductAttention(Map<String, Object> baseData,
//                                           final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.addProductAttention(header, baseData);
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
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback 用type区分，为PRODUCT和FARM
//     * @return "" + baseData.get("userId"), "" + baseData.get("type")
//     */
//    public static Call delFarmAttention(Map<String, Object> baseData,
//                                        final ResultCallback<Result<Object>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<Object>> call = mService.delAttention(header, baseData);
//        Callback<Result<Object>> cbk = new Callback<Result<Object>>() {
//
//            @Override
//            public void onResponse(Call<Result<Object>> call, Response<Result<Object>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<Object>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return 用type区分，为PRODUCT和FARM
//     */
//    public static Call addFarmAttention(Map<String, Object> baseData,
//                                        final ResultCallback<Result<Object>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<Object>> call = mService.addAttention(header, baseData);
//        Callback<Result<Object>> cbk = new Callback<Result<Object>>() {
//
//            @Override
//            public void onResponse(Call<Result<Object>> call, Response<Result<Object>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<Object>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call getFarmDynamicByID(Map<String, Object> baseData,
//                                          final ResultCallback<Result<NRFarmDynamic>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRFarmDynamic>> call = mService.getFarmDynamicByID(header,
//                "" + baseData.get("farmId"), "" + baseData.get("page"));
//        Callback<Result<NRFarmDynamic>> cbk = new Callback<Result<NRFarmDynamic>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRFarmDynamic>> call, Response<Result<NRFarmDynamic>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRFarmDynamic>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData id:contentid
//     * @param callback
//     * @return
//     */
//    public static Call checkContentRelation(String baseData,
//                                            final ResultCallback<Result<Map<String, NRRelation>>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<Map<String, NRRelation>>> call = mService.checkContentRelation(header, baseData);
//        Callback<Result<Map<String, NRRelation>>> cbk = new Callback<Result<Map<String, NRRelation>>>() {
//
//            @Override
//            public void onResponse(Call<Result<Map<String, NRRelation>>> call, Response<Result<Map<String, NRRelation>>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<Map<String, NRRelation>>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call getFarmNoticeByID(Map<String, Object> baseData,
//                                         final ResultCallback<Result<NRFarmNotices>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRFarmNotices>> call = mService.getFarmNoticeByID(header,
//                "" + baseData.get("farmId"), "" + baseData.get("type"), "" + baseData.get("page"));
//        Callback<Result<NRFarmNotices>> cbk = new Callback<Result<NRFarmNotices>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRFarmNotices>> call, Response<Result<NRFarmNotices>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRFarmNotices>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call getFarmProductByID(Map<String, Object> baseData,
//                                          final ResultCallback<Result<NRFarmProduct>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<NRFarmProduct>> call = mService.getFarmProductByID(header,
//                "" + baseData.get("farmId"), "" + baseData.get("page"));
//        Callback<Result<NRFarmProduct>> cbk = new Callback<Result<NRFarmProduct>>() {
//
//            @Override
//            public void onResponse(Call<Result<NRFarmProduct>> call, Response<Result<NRFarmProduct>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<NRFarmProduct>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call getIndexFarmDetail(Map<String, Object> baseData,
//                                          final ResultCallback<Result<IndexFarmDetail>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<IndexFarmDetail>> call = mService.getIndexFarmDetail(header,
//                "" + baseData.get("farmId"));
//        Callback<Result<IndexFarmDetail>> cbk = new Callback<Result<IndexFarmDetail>>() {
//
//            @Override
//            public void onResponse(Call<Result<IndexFarmDetail>> call, Response<Result<IndexFarmDetail>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<IndexFarmDetail>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call getIndexWillFarmMore(Map<String, Object> baseData,
//                                            final ResultCallback<Result<IndexWillMoreList>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<IndexWillMoreList>> call = mService.getIndexWillFarmMore(header, baseData);
//        Callback<Result<IndexWillMoreList>> cbk = new Callback<Result<IndexWillMoreList>>() {
//
//            @Override
//            public void onResponse(Call<Result<IndexWillMoreList>> call, Response<Result<IndexWillMoreList>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<IndexWillMoreList>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call getIndexWillFarmCount(Map<String, Object> baseData,
//                                             final ResultCallback<Result<Integer>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<Integer>> call = mService.getIndexWillFarmCount(header, baseData);
//        Callback<Result<Integer>> cbk = new Callback<Result<Integer>>() {
//
//            @Override
//            public void onResponse(Call<Result<Integer>> call, Response<Result<Integer>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<Integer>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call getIndexNews(Map<String, Object> baseData,
//                                    final ResultCallback<Result<IndexNews>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<IndexNews>> call = mService.getIndexNews(header, baseData);
//        Callback<Result<IndexNews>> cbk = new Callback<Result<IndexNews>>() {
//
//            @Override
//            public void onResponse(Call<Result<IndexNews>> call, Response<Result<IndexNews>> response) {
//                Result.onResponse(response, callback);
//            }
//
//            @Override
//            public void onFailure(Call<Result<IndexNews>> call, Throwable t) {
//                Result.onFailure(t, callback);
//            }
//        };
//        call.enqueue(cbk);
//        return call;
//    }
//

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

    //
//    /**
//     * 这里的user和token都要传值了
//     *
//     * @param baseData
//     * @param callback
//     * @return
//     */
//    public static Call modifyPsd(Map<String, Object> baseData,
//                                 final ResultCallback<Result<String>> callback) {
//
//        if (callback == null) throw new NullPointerException("callback == null");
//        NRService mService = ServiceFactory.createNewService(NRService.class);
//        HashMap<String, Object> header = new HashMap<>();
//
//        Pair<String, String> pair = NRApplication.getUser();
//        header.put("NARAN-ACCESS-USER", pair.first);
//        header.put("NARAN-ACCESS-TOKEN", pair.second);
//        Call<Result<String>> call = mService.forgetPsd(header, baseData);
//        Callback<Result<String>> cbk = new Callback<Result<String>>() {
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
//
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
                                     final ResultCallback<Result<Object>> callback) {

        if (callback == null) throw new NullPointerException("callback == null");
        NRService mService = ServiceFactory.createNewService(NRService.class);
        HashMap<String, Object> header = new HashMap<>();
        header.put("NARAN-ACCESS-USER", "");
        header.put("NARAN-ACCESS-TOKEN", "");
        Call<Result<Object>> call = mService.getVerifyCode(header, baseData);
        Callback<Result<Object>> cbk = new Callback<Result<Object>>() {
            @Override
            public void onResponse(Call<Result<Object>> call, Response<Result<Object>> response) {
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
