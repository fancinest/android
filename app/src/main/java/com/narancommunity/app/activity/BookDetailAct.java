package com.narancommunity.app.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.BookListAdapter;
import com.narancommunity.app.adapter.BookOrdererAdapter;
import com.narancommunity.app.adapter.CommentAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.BookInfo;
import com.narancommunity.app.entity.BookRelativeRecData;
import com.narancommunity.app.entity.CommentEntity;
import com.narancommunity.app.entity.CommentListEntity;
import com.narancommunity.app.entity.OrderData;
import com.narancommunity.app.entity.OrderEntity;
import com.narancommunity.app.entity.RecEntity;
import com.narancommunity.app.entity.Zan;
import com.narancommunity.app.interfaces.CommentInterfaces;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import simplezxing.activity.CaptureActivity;

/**
 * Writer：fancy on 2018/4/12 10:50
 * Email：120760202@qq.com
 * FileName :
 */

public class BookDetailAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_writer)
    TextView tvWriter;
    @BindView(R.id.iv_lend_card)
    ImageView ivLendCard;
    @BindView(R.id.iv_donater)
    SelectableRoundedImageView ivDonater;
    @BindView(R.id.tv_donater)
    TextView tvDonater;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.rating)
    RatingBar rating;
    @BindView(R.id.tv_shuping)
    TextView tvShuping;
    @BindView(R.id.iv_watcher)
    SelectableRoundedImageView ivWatcher;
    @BindView(R.id.tv_watcher)
    TextView tvWatcher;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.tv_publisher)
    TextView tvPublisher;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_pages)
    TextView tvPages;
    @BindView(R.id.tv_wish_collect)
    TextView tvWishCollect;
    @BindView(R.id.ln_collect)
    LinearLayout lnCollect;
    @BindView(R.id.tv_wish_comment)
    TextView tvWishComment;
    @BindView(R.id.ln_comment)
    LinearLayout lnComment;
    @BindView(R.id.tv_wish_share)
    TextView tvWishLike;
    @BindView(R.id.ln_like)
    LinearLayout lnLike;
    @BindView(R.id.tv_orders)
    TextView tvOrders;
    @BindView(R.id.recyclerView_order)
    RecyclerView recyclerViewOrder;
    @BindView(R.id.tv_comments)
    TextView tvComments;
    @BindView(R.id.recyclerView_comment)
    RecyclerView recyclerViewComment;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_jianjie)
    TextView tvJianjie;
    @BindView(R.id.recyclerView_rec)
    RecyclerView recyclerViewRec;
    @BindView(R.id.ln_hot_switch)
    LinearLayout lnHotSwitch;
    @BindView(R.id.btn_operate)
    Button btnOperate;
    @BindView(R.id.ln_Orders)
    LinearLayout lnOrders;
    @BindView(R.id.ln_comment_view)
    LinearLayout lnCommentView;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.ln_top)
    LinearLayout lnTop;
    @BindView(R.id.ln_tool)
    LinearLayout lnTool;
    @BindView(R.id.btn_more)
    Button btn_more;

    BookOrdererAdapter orderAdapter;
    BookListAdapter recAdapter;
    List<OrderEntity> listOrder = new ArrayList<>();
    List<RecEntity> listRec = new ArrayList<>();
    Integer bookId = 0;
    String desc;
    boolean isLike = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_book_detail);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("图书详情");

        bookId = getIntent().getIntExtra("bookId", 0);
        setView();
        setPopView();
        getData();
    }

    private void getData() {
        getBookData();
        getOrderData();
        getComment();
        getRec();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLikeState();
    }

    int pageNumRec = 1;
    int totalPageRec = 1;

    private void getLikeState() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", bookId);
        map.put("accessToken", MApplication.getAccessToken());
        NRClient.isLikeBook(map, new ResultCallback<Result<Zan>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Zan> result) {
                LoadDialog.dismiss(getContext());
                isLike = result.getData().isMe();
                setBookLike();
            }
        });
    }

    private void getRec() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", bookId);
        map.put("pageNum", pageNumRec);
        map.put("pageSize", 3);
        NRClient.getBookRelativeRecList(map, new ResultCallback<Result<BookRelativeRecData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<BookRelativeRecData> result) {
                LoadDialog.dismiss(getContext());
                setRecData(result.getData());
                totalPageRec = result.getData().getTotalPageNum();
            }
        });
    }

    private void setRecData(BookRelativeRecData data) {
        listRec.clear();
        if (data != null && data.getOrders() != null && data.getOrders().size() > 0) {
            listRec.addAll(data.getOrders());
            recAdapter.notifyDataSetChanged();
        } else {
            Toaster.toast(getContext(), "暂无更多推荐");
        }
    }

    private void getBookData() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", bookId);
        NRClient.getBookInfo(map, new ResultCallback<Result<BookInfo>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<BookInfo> result) {
                LoadDialog.dismiss(getContext());
                setBookData(result.getData());
            }
        });
    }

    BookInfo mData;
    String orderStatus = "INITIAL";

    private void setBookData(BookInfo data) {
        mData = data;
        orderStatus = data.getOrderStatus();
        if (orderStatus.equals("INITIAL") || orderStatus.equals("WAITING"))
            btnOperate.setText("借阅");
        else btnOperate.setText("预约");
        if (data != null) {
            String watchIcon = Utils.getValue(data.getRecipientImg());
            if (watchIcon.equals("")) {
                Utils.setImgF(getContext(), R.mipmap.zw_morentouxiang, ivWatcher);
            } else {
                Utils.setImgF(getContext(), watchIcon, ivWatcher);
            }
            String donateIcon = Utils.getValue(data.getInitiatorImg());
            if (donateIcon.equals(""))
                Utils.setImgF(getContext(), R.mipmap.zw_morentouxiang, ivDonater);
            else
                Utils.setImgF(getContext(), donateIcon, ivDonater);
            String img = Utils.getValue(data.getOrderImgs());
            if (!img.equals(""))
                Utils.setImgF(getContext(), img, ivImg);

            float average = data.getAverage();
            String score = "";
            if (average > 0)
                score = average + "";
            else score = "" + 0.0;
            int size = score.length();
            score = score + "分";
            Spannable string = new SpannableString(score);
            // 字体大小
            string.setSpan(new AbsoluteSizeSpan(40), size, size + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            tvScore.setText(string);

            desc = Utils.getValue(data.getOrderContent());
            tvDesc.setText(Utils.getValue(data.getOrderContent()) + "");
            tvName.setText(Utils.getValue(data.getOrderTitle()) + "");
            tvWriter.setText(Utils.getValue(data.getOrderAuthor()) + "");
            tvWatcher.setText(Utils.getValue(data.getRecipientNike()) + "正在借阅中...");
            rating.setRating(Float.parseFloat(Utils.getValue(average)) / 2);
            tvDonater.setText(Utils.getValue(data.getInitiatorNike()) + "");
            tvPublisher.setText(Utils.getValue(data.getPublisher()) + "");
            tvPrice.setText(Utils.getValue(data.getPrice()) + "");
            tvWishCollect.setText(Utils.getValue(data.getCollectionTimes()) + "");
            tvWishComment.setText(Utils.getValue(data.getCommentTimes()) + "");
            tvWishLike.setText(Utils.getValue(data.getLikeTimes()) + "");
            tvPages.setText(Utils.getValue(data.getPages()) + "页");
        }
    }

    private void getOrderData() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("orderId", bookId);
        NRClient.getBookOrderer(map, new ResultCallback<Result<OrderData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<OrderData> result) {
                LoadDialog.dismiss(getContext());
                setOrdererData(result.getData());
            }
        });
    }

    private void setOrdererData(OrderData data) {
        listOrder.clear();
        if (data != null && data.getApplys() != null && data.getApplys().size() > 0) {
            listOrder.addAll(data.getApplys());
            orderAdapter.setDataList(listOrder);
            lnOrders.setVisibility(View.VISIBLE);
        } else lnOrders.setVisibility(View.GONE);
        orderAdapter.notifyDataSetChanged();
    }

    private void getComment() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", bookId);
        map.put("accessToken", MApplication.getAccessToken());
        NRClient.getCommentList(map, new ResultCallback<Result<CommentListEntity>>() {
            @Override
            public void onSuccess(Result<CommentListEntity> result) {
                LoadDialog.dismiss(getContext());
                setCommentList(result.getData());
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }
        });
    }

    List<CommentEntity> listComment = new ArrayList<>();
    CommentAdapter adapterComment;

    private void setCommentList(final CommentListEntity data) {
        if (data == null || data.getComments() == null) {
            lnCommentView.setVisibility(View.GONE);
            return;
        }
        if (data.getComments().size() > 3)
            btn_more.setVisibility(View.VISIBLE);
        else btn_more.setVisibility(View.GONE);
        tvComments.setText("评论(" + data.getComments().size() + "条)");
        lnCommentView.setVisibility(View.VISIBLE);
        listComment = data.getComments();
        adapterComment = new CommentAdapter(getContext(), listComment);
        adapterComment.setLimited(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerViewComment.setLayoutManager(linearLayoutManager);

        adapterComment.setListener(new CommentInterfaces() {
            @Override
            public void OnItemClick(int position) {
                startActivity(new Intent(getContext(), AddBookCommentAct.class)
                        .putExtra("tag", 1).putExtra("bookId", bookId)
                        .putExtra("commentedId", listComment.get(position).getCommentId())
                        .putExtra("replyName", listComment.get(position).getInitiatorNike()));
            }

            @Override
            public void OnAnswer(int id, String name) {
//                showInputDialog(id, name);
                startActivity(new Intent(getContext(), AddBookCommentAct.class)
                        .putExtra("tag", 1).putExtra("bookId", bookId)
                        .putExtra("commentedId", id)
                        .putExtra("replyName", name));
            }
        });
        adapterComment.setClickLike(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                clickLike(position);
            }

            @Override
            public void OnDelClick(int position) {

            }
        }, bookId);
        recyclerViewComment.setAdapter(adapterComment);
        recyclerViewComment.setNestedScrollingEnabled(false);
    }

    private void clickLike(final int position) {
        Map<String, Object> map = new HashMap<>();
        map.put("commentId", listComment.get(position).getCommentId());
        map.put("accessToken", MApplication.getAccessToken());
        NRClient.likeBook(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                setCommentLike(position);
            }
        });
    }

    private void setCommentLike(int position) {
        if (listComment.get(position).getLikeTag() == 0)
            listComment.get(position).setLikeTag(1);
        else
            listComment.get(position).setLikeTag(0);
        adapterComment.notifyItemChanged(position);
    }

    private void likeBook() {
        LoadDialog.show(getContext());
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", bookId);
        map.put("accessToken", MApplication.getAccessToken());
        NRClient.likeBook(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                isLike = true;
                setBookLike();
            }
        });
    }

//    void dontLike(int likeId) {
//        LoadDialog.show(getContext());
//        Map<String, Object> map = new HashMap<>();
//        map.put("likeId", likeId);
//        map.put("accessToken", MApplication.getAccessToken());
//        NRClient.dontLike(map, new ResultCallback<Result<Void>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                LoadDialog.dismiss(getContext());
//                Utils.showErrorToast(getContext(), throwable);
//            }
//
//            @Override
//            public void onSuccess(Result<Void> result) {
//                LoadDialog.dismiss(getContext());
//                isLike = true;
//                setBookLike();
//            }
//        });
//    }

    private void setBookLike() {
        if (isLike)
            tvWishLike.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.list_btn_zan_pre), null, null, null);
        else
            tvWishLike.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.list_btn_zan), null, null, null);
    }


//    private void getComment() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("orderId", bookId);
//        map.put("accessToken", MApplication.getAccessToken());
//        NRClient.getBookCommentList(map, new ResultCallback<Result<BookCommentData>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                LoadDialog.dismiss(getContext());
//                Utils.showErrorToast(getContext(), throwable);
//            }
//
//            @Override
//            public void onSuccess(Result<BookCommentData> result) {
//                LoadDialog.dismiss(getContext());
//                setBookComment(result.getData());
//            }
//        });
//    }
//
//    private void setBookComment(BookCommentData data) {
//        listComment.clear();
//        if (data != null && data.getComments() != null && data.getComments().size() > 0) {
//            listComment.addAll(data.getComments());
//            lnCommentView.setVisibility(View.VISIBLE);
//        } else {
//            lnCommentView.setVisibility(View.GONE);
//        }
//        commentAdapter.notifyDataSetChanged();
//    }

    private void setView() {
        GridLayoutManager linearLayout = new GridLayoutManager(getContext(), 5);
        linearLayout.setOrientation(GridLayoutManager.VERTICAL);
        orderAdapter = new BookOrdererAdapter(getContext());
        orderAdapter.setDataList(listOrder);
        recyclerViewOrder.setLayoutManager(linearLayout);
        recyclerViewOrder.setAdapter(orderAdapter);

        if (scrollView != null) {
            scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    try {
                        if (isViewCovered(tvJianjie) && isViewCovered(tvDesc) && isViewCovered(lnTool))
                            showPop();
                        else {
                            if (mPop != null)
                                mPop.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        LinearLayoutManager lm_latest = new LinearLayoutManager(getContext());
        lm_latest.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewComment.setLayoutManager(lm_latest);

        recAdapter = new BookListAdapter(getContext(), listRec);
        GridLayoutManager linearLayoutRec = new GridLayoutManager(getContext(), 3);
        linearLayoutRec.setOrientation(GridLayoutManager.VERTICAL);
        recyclerViewRec.setLayoutManager(linearLayoutRec);
        recyclerViewRec.setAdapter(recAdapter);
        recyclerViewRec.setNestedScrollingEnabled(false);
        recAdapter.setListener(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                startActivity(new Intent(getContext(), BookDetailAct.class)
                        .putExtra("bookId", listRec.get(position).getOrderId()));
            }

            @Override
            public void OnDelClick(int position) {

            }
        });
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        menu.findItem(R.id.action_search).setIcon(R.mipmap.topnav_btn_sousuo);
//        return super.onPrepareOptionsMenu(menu);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_jubao_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_scan) {
            startActivity(new Intent(getContext(), CaptureActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.iv_lend_card, R.id.iv_donater, R.id.tv_donater, R.id.tv_shuping, R.id.iv_watcher, R.id.tv_watcher, R.id.tv_distance, R.id.ln_collect, R.id.ln_comment, R.id.ln_like, R.id.ln_hot_switch, R.id.btn_operate, R.id.btn_more, R.id.tv_desc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_desc:
                showDesc();
                break;
            case R.id.iv_lend_card:
                startActivity(new Intent(getContext(), BookLendCardAct.class));
                break;
            case R.id.iv_donater:
                break;
            case R.id.tv_donater:
                break;
            case R.id.tv_shuping:
                startActivity(new Intent(getContext(), BookReviewListAct.class)
                        .putExtra("bookId", bookId));
                break;
            case R.id.iv_watcher:
                break;
            case R.id.tv_watcher:
                break;
            case R.id.tv_distance:
                break;
            case R.id.ln_collect:
                addCollect();
                break;
            case R.id.btn_more:
                startActivity(new Intent(getContext(), MoreCommentAct.class).putExtra("bookId", bookId));
                break;
            case R.id.ln_comment:
                addComment("", 0);
                break;
            case R.id.ln_like:
                likeBook();
                break;
            case R.id.ln_hot_switch:
                pageNumRec++;
                if (pageNumRec < totalPageRec) {
                    getRec();
                } else {
                    pageNumRec = 1;
                    Toaster.toast(getContext(), "暂无更多推荐");
                }
                break;
            case R.id.btn_operate:
                if (orderStatus.equals("INITIAL") || orderStatus.equals("WAITING"))
                    startActivity(new Intent(getContext(), OrderBookAct.class).putExtra("bookId", bookId)
                            .putExtra("data", mData));
                else
                    iWantOrder();
                break;
        }
    }

    private void iWantOrder() {
        LoadDialog.show(getContext(), "预约中...");
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", mData.getOrderId());
        map.put("accessToken", MApplication.getAccessToken());
        NRClient.wantSee(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                showOrderPop(btnOperate);
            }
        });
    }

    PopupWindow mOrderPop;

    private void showOrderPop(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.normal_pop, null);

        if (mOrderPop == null) {
            mOrderPop = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mOrderPop.setFocusable(true);
            mOrderPop.setOutsideTouchable(true);
            mOrderPop.setBackgroundDrawable(new BitmapDrawable());
            TextView tv_content = v.findViewById(R.id.dial);
            LinearLayout ln_function = v.findViewById(R.id.ln_function);
            ln_function.setVisibility(View.GONE);
            tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            tv_content.setTextColor(getResources().getColor(R.color.color_333333));
            tv_content.setText("预约成功， 可借阅时会通知您哦~");
        }
        if (mOrderPop != null && !mOrderPop.isShowing())
            mOrderPop.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    PopupWindow mDescPop;

    private void showDesc() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.pop_desc, null);

        if (mDescPop == null) {
            mDescPop = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mDescPop.setFocusable(true);
            mDescPop.setOutsideTouchable(true);
            mDescPop.setBackgroundDrawable(new BitmapDrawable());
            TextView tv_content = v.findViewById(R.id.tv_remark);
            tv_content.setText(desc);
            v.findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDescPop.dismiss();
                }
            });
        }
        if (mDescPop != null && !mDescPop.isShowing())
            mDescPop.showAtLocation(tvDesc, Gravity.CENTER, 0, 0);
    }

    private void addComment(String replyName, int commentId) {
        startActivity(new Intent(getContext(), AddBookCommentAct.class)
                .putExtra("replyName", replyName).putExtra("tag", 1)
                .putExtra("commentId", commentId).putExtra("bookId", bookId));
    }

    private void addCollect() {

    }


    public String[] getRandomFromArray(String[] array, int count) {
        // ArrayList<Integer>arrayList =null;
        String[] a = array;
        String[] result = new String[count];
        boolean r[] = new boolean[array.length];
        Random random = new Random();
        int m = count; // 要随机取的元素个数
        if (m > a.length || m < 0)
            return a;
        int n = 0;
        while (true) {
            int temp = random.nextInt(a.length);
            if (!r[temp]) {
                if (n == m) // 取到足量随机数后退出循环
                    break;
                n++;
                result[n - 1] = a[temp];
                r[temp] = true;
            }
        }
        return result;
    }

    private void showPop() {
        if (mPop != null && !mPop.isShowing()) {
            mPop.showAtLocation(btnOperate, Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
        }
    }

    PopupWindow mPop;

    private void setPopView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.pop_function_new, null);

        if (mPop == null) {
            mPop = new PopupWindow(v, LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mPop.setFocusable(false);
            mPop.setAnimationStyle(R.style.popwin_anim_style);
            v.findViewById(R.id.iv_like).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    likeBook();
                }
            });
            v.findViewById(R.id.iv_collect).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
//                    doCollect();
                    //TODO
                }
            });
            v.findViewById(R.id.iv_add_comment).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
//                    showInputDialog(0, "");
                    startActivity(new Intent(getContext(), AddBookCommentAct.class)
                            .putExtra("replyName", "").putExtra("tag", 1)
                            .putExtra("commentId", "").putExtra("bookId", bookId));
                }
            });
        }
    }

}
