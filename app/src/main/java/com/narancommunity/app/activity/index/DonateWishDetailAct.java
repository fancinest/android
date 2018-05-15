package com.narancommunity.app.activity.index;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.picbroswer.ImagePagerActivity;
import com.narancommunity.app.adapter.GridImageAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.ApplyDetailEntity;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/1/17 15:24
 * Email：120760202@qq.com
 * FileName : 捐赠内想要的心愿详情页面
 */
public class DonateWishDetailAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.iv_wish_icon)
    SelectableRoundedImageView ivWishIcon;
    @BindView(R.id.tv_wish_grades)
    TextView tvWishGrades;
    @BindView(R.id.rb_wish_star)
    RatingBar rbWishStar;
    @BindView(R.id.tv_wish_name)
    TextView tvWishName;
    @BindView(R.id.tv_wish_location)
    TextView tvWishLocation;
    @BindView(R.id.tv_wish_title)
    TextView tvWishTitle;
    @BindView(R.id.tv_wish_type)
    TextView tvWishType;
    @BindView(R.id.tv_wish_content)
    TextView tvWishContent;
    @BindView(R.id.iv_wish_bg)
    ImageView ivWishBg;
    @BindView(R.id.recyclerView_wish)
    RecyclerView recyclerViewWish;
    @BindView(R.id.iv_wish_one_pic)
    ImageView ivWishOnePic;
    @BindView(R.id.tv_wish_times)
    TextView tvWishTimes;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.tv_wish_share)
    TextView tvWishShare;
    @BindView(R.id.ln_share)
    LinearLayout lnShare;
    @BindView(R.id.tv_wish_collect)
    TextView tvWishCollect;
    @BindView(R.id.ln_collect)
    LinearLayout lnCollect;
    @BindView(R.id.tv_wish_comment)
    TextView tvWishComment;
    @BindView(R.id.ln_comment)
    LinearLayout lnComment;
    @BindView(R.id.ln_tool)
    LinearLayout lnTool;
    @BindView(R.id.ln_wish_detail)
    LinearLayout lnWishDetail;
    @BindView(R.id.btn_first)
    Button btnFirst;
    @BindView(R.id.btn_second)
    Button btnSecond;

    Integer applyId;
    String type;
    String orderType;
    Boolean myOrder;
    //订单确认状态 = going 开始配送了，就可以查看物流了 ，= success 确认收获了，完成了订单 = initial没有填写订单
    String mailStatus;
    Integer orderId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_i_want_detail);
        ButterKnife.bind(this);
        lnTool.setVisibility(View.GONE);
        viewLine.setVisibility(View.GONE);
        toolbar.setTitle("心愿详情");
        setBar(toolbar);
        applyId = getIntent().getIntExtra("wantId", 0);
        type = getIntent().getStringExtra("type");
        orderType = getIntent().getStringExtra("orderType");
        myOrder = getIntent().getBooleanExtra("myOrder", false);
        orderId = getIntent().getIntExtra("orderId", 0);
        MApplication.putActivity("IWantDetail", getContext());
    }

    @OnClick({R.id.iv_wish_icon, R.id.btn_first, R.id.btn_second})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_wish_icon:
                break;
            case R.id.btn_first:
                if (btnFirst.getText().toString().equals("撤回")) {
                    showPopView(view);
                } else if (btnFirst.getText().toString().contains("我已经找到主人")) {
                    finish();
                } else if (btnFirst.getText().toString().equals("查看物流")) {
                    startActivity(new Intent(getContext(), TransitAct.class)
                            .putExtra("orderId", orderId));
                }
                break;
            case R.id.btn_second:
                startActivity(new Intent(getContext(), StartFahuoAct.class)
                        .putExtra("applyId", applyId)
                        .putExtra("isWish", false));
                break;
        }
    }

    PopupWindow mPop;

    private void showPopView(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.normal_pop, null);

        if (mPop == null) {
            mPop = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mPop.setFocusable(true);
            mPop.setOutsideTouchable(true);
            mPop.setBackgroundDrawable(new BitmapDrawable());
            TextView tv_dial = (TextView) v.findViewById(R.id.dial);
            tv_dial.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv_dial.setText("确定撤回吗？");
            tv_dial.setTextColor(getResources().getColor(R.color.black));
            v.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    withdraw();
                }
            });
            v.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mPop.dismiss();
                }
            });
        }
        if (mPop != null && !mPop.isShowing())
            mPop.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private void withdraw() {
        LoadDialog.show(getContext(), "撤回中...");
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("applyId", applyId);
        NRClient.donateApplyWithDraw(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                Toaster.toast(getContext(), "撤回成功！");
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadDialog.show(getContext(), "加载中...");
        Map<String, Object> map = new HashMap<>();
        map.put("applyId", applyId);
        NRClient.applyDetail(map, new ResultCallback<Result<ApplyDetailEntity>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<ApplyDetailEntity> result) {
                LoadDialog.dismiss(getContext());
                setDataView(result.getData());
            }
        });
    }

    private void setDataView(ApplyDetailEntity data) {
        if (data == null)
            return;
        tvWishType.setText(type);
        viewLine.setVisibility(View.GONE);
        lnTool.setVisibility(View.GONE);
        tvWishTitle.setText("" + Utils.getValue(data.getApplyTitle()));
        tvWishName.setText("" + Utils.getValue(data.getAccountNike()));
        tvWishLocation.setText("" + Utils.getValue(data.getCity()));
        tvWishContent.setText("" + Utils.getValue(data.getApplyContent()));
        if (data.getCreateTime() != null)
            tvWishTimes.setText(Utils.dateDiff(Utils.stringTimeToMillion(data.getCreateTime())) + "");
        if (data.getAccountImg() != null && !data.getAccountImg().equals(""))
            Utils.setImgF(getContext(), data.getAccountImg(), ivWishIcon);
        final String imgs = data.getApplyImgs();
        if (imgs != null) {
            if (imgs.contains(",")) {
                String arr[] = imgs.split(",", -1);
                if (arr.length > 1) {
                    recyclerViewWish.setVisibility(View.VISIBLE);
                    ivWishOnePic.setVisibility(View.GONE);
                    GridImageAdapter adapter = new GridImageAdapter(getContext(), Utils.arrayToList(arr));
                    GridLayoutManager gridlayout = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
                    recyclerViewWish.setLayoutManager(gridlayout);

                    recyclerViewWish.setAdapter(adapter);
                    recyclerViewWish.setNestedScrollingEnabled(false);
                } else {
                    ivWishOnePic.setVisibility(View.VISIBLE);
                    recyclerViewWish.setVisibility(View.GONE);
                    Utils.setImgF(getContext(), imgs, ivWishOnePic);
                    ivWishOnePic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(), ImagePagerActivity.class);
//                        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, new String[]{imgs});
                            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
                            getContext().startActivity(intent);
                        }
                    });
                }
            } else {
                recyclerViewWish.setVisibility(View.GONE);
                ivWishOnePic.setVisibility(View.VISIBLE);
                Utils.setImgF(getContext(), imgs, ivWishOnePic);
                ivWishOnePic.setVisibility(View.VISIBLE);
                recyclerViewWish.setVisibility(View.GONE);
                Utils.setImgF(getContext(), imgs, ivWishOnePic);
                ivWishOnePic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ImagePagerActivity.class);
//                        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, new String[]{imgs});
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
                        getContext().startActivity(intent);
                    }
                });
            }
        } else {
            recyclerViewWish.setVisibility(View.GONE);
            ivWishOnePic.setVisibility(View.GONE);
        }

        if (myOrder) {
            if (orderType.equals("WAITING") && mailStatus.equals("GOING")) {
                btnFirst.setText("我已经找到主人");
                btnFirst.setVisibility(View.VISIBLE);
                btnFirst.setBackgroundColor(getResources().getColor(R.color.color_bbbbbb));
                btnSecond.setVisibility(View.GONE);
            } else if (orderType.equals("INITIAL")) {
                btnFirst.setText("再考虑一下");
                btnFirst.setBackgroundColor(getResources().getColor(R.color.color_999999));
                btnFirst.setVisibility(View.VISIBLE);
                btnSecond.setText("捐赠给他");
                btnSecond.setBackgroundColor(getResources().getColor(R.color.login_gray));
                btnSecond.setVisibility(View.VISIBLE);
            }
        } else {
            //判断是不是我申请的
            if (data.getAccountId().equals(MApplication.getAccountId(getContext()))) {
                if (data.getApplyStatus().equals("SUCCESS") && orderType.equals("WAITING")) {
                    //我已找到主人
                    btnFirst.setText("查看物流");
                    btnFirst.setVisibility(View.VISIBLE);
                    btnFirst.setBackgroundColor(getResources().getColor(R.color.green_tag));
                    btnSecond.setVisibility(View.GONE);
                } else {
                    //说明是我的发布的申情
                    //不成功之前都可以撤回
                    if (!orderType.equals("SUCCESS")) {
                        btnFirst.setText("撤回");
                        btnFirst.setBackgroundColor(getResources().getColor(R.color.blue_tag));
                        btnSecond.setVisibility(View.GONE);
                    } else {
                        btnFirst.setText("我已找到主人");
                        btnFirst.setBackgroundColor(getResources().getColor(R.color.color_bbbbbb));
                        btnSecond.setVisibility(View.GONE);
                    }
                }
            } else {
                //返回
                btnFirst.setText("返回");
                btnFirst.setBackgroundColor(getResources().getColor(R.color.blue_tag));
                btnFirst.setVisibility(View.VISIBLE);
                btnSecond.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tip_offs, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_tip_offs) {
            startActivity(new Intent(getContext(), JubaoAct.class).putExtra("orderId", orderId));
        }
        return super.onOptionsItemSelected(item);
    }
}
