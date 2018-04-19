package com.narancommunity.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
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
import com.narancommunity.app.adapter.BookCommentAdapter;
import com.narancommunity.app.adapter.BookListAdapter;
import com.narancommunity.app.adapter.BookOrdererAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.BookComment;
import com.narancommunity.app.entity.BookCommentData;
import com.narancommunity.app.entity.BookDetail;
import com.narancommunity.app.entity.BookInfo;
import com.narancommunity.app.entity.BookRelativeRecData;
import com.narancommunity.app.entity.OrderData;
import com.narancommunity.app.entity.OrderEntity;
import com.narancommunity.app.entity.RecEntity;
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

    BookOrdererAdapter orderAdapter;
    BookCommentAdapter commentAdapter;
    BookListAdapter recAdapter;
    List<OrderEntity> listOrder = new ArrayList<>();
    List<BookComment> listComment = new ArrayList<>();
    List<RecEntity> listRec = new ArrayList<>();
    Integer bookId = 0;

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

    int pageNumRec = 1;
    int totalPageRec = 1;

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

    private void setBookData(BookInfo data) {
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

            tvDesc.setText(Utils.getValue(data.getOrderContent()) + "");
            tvName.setText(Utils.getValue(data.getOrderTitle()) + "");
            tvWriter.setText(Utils.getValue(data.getOrderAuthor()) + "");
            tvWatcher.setText(Utils.getValue(data.getRecipientNike()) + "");
            rating.setRating(Float.parseFloat(Utils.getValue(average)) / 2);
            tvDonater.setText(Utils.getValue(data.getInitiatorNike()) + "");
            tvPublisher.setText(Utils.getValue(data.getPublisher()) + "");
            tvPrice.setText(Utils.getValue(data.getPrice()) + "元");
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
        NRClient.getBookCommentList(map, new ResultCallback<Result<BookCommentData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<BookCommentData> result) {
                LoadDialog.dismiss(getContext());
                setBookComment(result.getData());
            }
        });
    }

    private void setBookComment(BookCommentData data) {
        listComment.clear();
        if (data != null && data.getComments() != null && data.getComments().size() > 0F) {
            listComment.addAll(data.getComments());
            lnCommentView.setVisibility(View.VISIBLE);
        } else {
            lnCommentView.setVisibility(View.GONE);
        }
        commentAdapter.notifyDataSetChanged();
    }

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
        commentAdapter = new BookCommentAdapter(getContext(), listComment);
        recyclerViewComment.setAdapter(commentAdapter);

        recAdapter = new BookListAdapter(getContext(), listRec);
        GridLayoutManager linearLayoutRec = new GridLayoutManager(getContext(), 3);
        linearLayoutRec.setOrientation(GridLayoutManager.VERTICAL);
        recyclerViewRec.setLayoutManager(linearLayoutRec);
        recyclerViewRec.setAdapter(recAdapter);
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

    @OnClick({R.id.iv_lend_card, R.id.iv_donater, R.id.tv_donater, R.id.tv_shuping, R.id.iv_watcher, R.id.tv_watcher, R.id.tv_distance, R.id.ln_collect, R.id.ln_comment, R.id.ln_like, R.id.ln_hot_switch, R.id.btn_operate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_lend_card:
                startActivity(new Intent(getContext(), BookLendCardAct.class));
                break;
            case R.id.iv_donater:
                break;
            case R.id.tv_donater:
                break;
            case R.id.tv_shuping:
                startActivity(new Intent(getContext(), BookCommentAct.class)
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
            case R.id.ln_comment:
                addComment();
                break;
            case R.id.ln_like:
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
                startActivity(new Intent(getContext(), OrderBookAct.class));
                break;
        }
    }

    private void addComment() {

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
                    Toaster.toast(getContext(), "点击赞");
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
                    //TODO
                }
            });
        }
    }

}
