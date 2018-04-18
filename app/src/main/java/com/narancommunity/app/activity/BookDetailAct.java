package com.narancommunity.app.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.BookCommentAdapter;
import com.narancommunity.app.adapter.BookOrdererAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;

import java.util.ArrayList;
import java.util.List;
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
    TextView tvWishShare;
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
    @BindView(R.id.iv_hot_one)
    ImageView ivHotOne;
    @BindView(R.id.tv_hot_one)
    TextView tvHotOne;
    @BindView(R.id.iv_hot_two)
    ImageView ivHotTwo;
    @BindView(R.id.tv_hot_two)
    TextView tvHotTwo;
    @BindView(R.id.iv_hot_three)
    ImageView ivHotThree;
    @BindView(R.id.tv_hot_three)
    TextView tvHotThree;
    @BindView(R.id.ln_hot_switch)
    LinearLayout lnHotSwitch;
    @BindView(R.id.btn_operate)
    Button btnOperate;
    @BindView(R.id.ln_Orders)
    LinearLayout lnOrders;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.ln_top)
    LinearLayout lnTop;
    @BindView(R.id.ln_tool)
    LinearLayout lnTool;

    BookOrdererAdapter orderAdapter;
    List<String> listOrder = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_book_detail);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("图书详情");
        setView();
        setPopView();
    }

    private void setView() {
        Utils.setImgF(getContext(), R.mipmap.zw_morentouxiang, ivDonater);
        Utils.setImgF(getContext(), R.mipmap.zw_morentouxiang, ivWatcher);

//        Spannable string = new SpannableString("修改背景色、粗体、字体大小");
        Spannable string = new SpannableString("8.0分");
        // 字体大小
        string.setSpan(new AbsoluteSizeSpan(40), 3, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvScore.setText(string);

        setOrderData();
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
                        if (isViewCovered(tvDesc) && isViewCovered(lnTool))
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
    }

    private void setOrderData() {
        String s;
        for (int i = 0; i < 6; i++) {
            s = "http://img.qqzhi.com/upload/img_3_1897173975D2024083860_27.jpg";
            listOrder.add(s);
        }
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

    @OnClick({R.id.iv_lend_card, R.id.iv_donater, R.id.tv_donater, R.id.tv_shuping, R.id.iv_watcher, R.id.tv_watcher, R.id.tv_distance, R.id.ln_collect, R.id.ln_comment, R.id.ln_like, R.id.iv_hot_one, R.id.iv_hot_two, R.id.iv_hot_three, R.id.ln_hot_switch, R.id.btn_operate})
    public void onViewClicked(View view) {
        Toaster.toast(getContext(), "点击了View");
        switch (view.getId()) {
            case R.id.iv_lend_card:
                startActivity(new Intent(getContext(), BookLendCardAct.class));
                break;
            case R.id.iv_donater:
                break;
            case R.id.tv_donater:
                break;
            case R.id.tv_shuping:
                startActivity(new Intent(getContext(), BookCommentAct.class));
                break;
            case R.id.iv_watcher:
                break;
            case R.id.tv_watcher:
                break;
            case R.id.tv_distance:
                break;
            case R.id.ln_collect:
                break;
            case R.id.ln_comment:
                break;
            case R.id.ln_like:
                break;
            case R.id.iv_hot_one:
                startActivity(new Intent(getContext(), BookDetailAct.class));
                break;
            case R.id.iv_hot_two:
                startActivity(new Intent(getContext(), BookDetailAct.class));
                break;
            case R.id.iv_hot_three:
                startActivity(new Intent(getContext(), BookDetailAct.class));
                break;
            case R.id.ln_hot_switch:
                changeRec();
                break;
            case R.id.btn_operate:
                startActivity(new Intent(getContext(), OrderBookAct.class));
                break;
        }
    }


    private void changeRec() {
        String[] arr = new String[]{"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523521822134&di=8f8ad66862e43356d2f611c75e837232&imgtype=0&src=http%3A%2F%2Fs9.sinaimg.cn%2Fmw690%2F001oghI3gy6RP8blcDK18%26690"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523521840435&di=ccc40b13bce701c1243aa33e7ac8ccbf&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fbaike%2Fw%253D268%253Bg%253D0%2Fsign%3Df7c629d8b9014a90813e41bb914c5e2f%2Fe61190ef76c6a7ef27ca3e4cfdfaaf51f2de66e8.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523521857113&di=7c3fcec13a02545b16adf8064a80ab3f&imgtype=0&src=http%3A%2F%2Fimage.xinmin.cn%2F2016%2F10%2F31%2F1477875471441.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523521876495&di=5e2f1c14077b7fd644a085d531bafe47&imgtype=0&src=http%3A%2F%2Fimg13.360buyimg.com%2FpopWaterMark%2Fjfs%2Ft481%2F15%2F977914362%2F15133%2F32d03cb1%2F54a1175aN3785b77e.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523539651530&di=076380697cd9b09ecc6bd9f7b8cca7e7&imgtype=0&src=http%3A%2F%2Fp4.qhimg.com%2Ft0126b72b37e2c48a1c.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523522043547&di=2b57cbb4ffac6d93fd4044374ff73297&imgtype=0&src=http%3A%2F%2Fimg34.ddimg.cn%2F24%2F8%2F486114-1_o.jpg"};

        String[] newArr = getRandomFromArray(arr, 3);

        Log.i("fancy", "1=" + newArr[0] + "\n2=" + newArr[1] + "\n3=" + newArr[2]);
        Utils.setImgF(getContext(), newArr[0], ivHotOne);
        Utils.setImgF(getContext(), newArr[1], ivHotTwo);
        Utils.setImgF(getContext(), newArr[2], ivHotThree);

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
