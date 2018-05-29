package com.narancommunity.app.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.narancommunity.app.entity.WishDonateEntity;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.umeng.analytics.MobclickAgent;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/1/18 21:52
 * Email：120760202@qq.com
 * FileName :
 */

public class WishDonateDetailAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.iv_donate_icon)
    SelectableRoundedImageView ivDonateIcon;
    @BindView(R.id.tv_donate_grades)
    TextView tvDonateGrades;
    @BindView(R.id.rb_donate_star)
    RatingBar rbDonateStar;
    @BindView(R.id.tv_donate_name)
    TextView tvDonateName;
    @BindView(R.id.tv_donate_location)
    TextView tvDonateLocation;
    @BindView(R.id.tv_donate_title)
    TextView tvDonateTitle;
    @BindView(R.id.tv_donate_type)
    TextView tvDonateType;
    @BindView(R.id.tv_donate_content)
    TextView tvDonateContent;
    @BindView(R.id.donateTagFlow)
    TagFlowLayout donateTagFlow;
    @BindView(R.id.recyclerView_donate)
    RecyclerView recyclerViewDonate;
    @BindView(R.id.iv_one_pic)
    ImageView ivOnePic;
    @BindView(R.id.tv_donate_times)
    TextView tvDonateTimes;
    @BindView(R.id.tv_donate_share)
    TextView tvDonateShare;
    @BindView(R.id.ln_share)
    LinearLayout lnShare;
    @BindView(R.id.tv_donate_collect)
    TextView tvDonateCollect;
    @BindView(R.id.ln_collect)
    LinearLayout lnCollect;
    @BindView(R.id.tv_donate_comment)
    TextView tvDonateComment;
    @BindView(R.id.ln_comment)
    LinearLayout lnComment;
    @BindView(R.id.ln_tool)
    LinearLayout lnTool;
    @BindView(R.id.btn_first)
    Button btnFirst;
    @BindView(R.id.btn_second)
    Button btnSecond;

    WishDonateEntity entity;

    /**
     * 我是否申请过
     */
    boolean isApply = false;
    /**
     * 是否是我的订单
     */
    boolean myOrder = false;
    /**
     * 我申请的状态
     */
    String applyStatus;
    //订单确认状态 = going 开始配送了，就可以查看物流了 ，= success 确认收获了，完成了订单 = initial没有填写订单
    String mailStatus;
    String orderStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wish_donate_detail);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("心愿详情");
        entity = (WishDonateEntity) getIntent().getSerializableExtra("data");
        isApply = getIntent().getBooleanExtra("isApply", false);
        myOrder = getIntent().getBooleanExtra("myOrder", false);
        mailStatus = getIntent().getStringExtra("mailStatus");
        applyStatus = getIntent().getStringExtra("applyStatus");
        orderStatus = getIntent().getStringExtra("orderStatus");
        setView();
        lnTool.setVisibility(View.GONE);
        MApplication.putActivity("", getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void setView() {
        tvDonateType.setText("" + Utils.getValue(entity.getCommodityType()));
        tvDonateContent.setText("" + Utils.getValue(entity.getApplyContent()));
        tvDonateLocation.setText("" + Utils.getValue(entity.getCity()));
        tvDonateName.setText("" + Utils.getValue(entity.getAccountNike()));
        tvDonateTitle.setText("" + Utils.getValue(entity.getApplyTitle()));
        tvDonateTimes.setText(Utils.dateDiff(Utils.stringTimeToMillion(entity.getCreateTime())) + "");
        String icon = entity.getAccountImg();
        if (icon != null && !icon.equals("")) {
            Utils.setImgF(getContext(), icon, ivDonateIcon);
        }
        //String mailStatus;//订单确认状态 = going 开始配送了，就可以查看物流了 ，= success 确认收获了，完成了订单 or initial没有填写订单
        if (myOrder) {
            if (orderStatus.equals("GOING")) {
                btnFirst.setText("再考虑一下");
                btnFirst.setVisibility(View.VISIBLE);
                btnFirst.setBackgroundColor(getResources().getColor(R.color.color_bbbbbb));
                btnSecond.setText("接受捐赠");
                btnSecond.setVisibility(View.VISIBLE);
            } else if (mailStatus.equals("GOING")) {
                //如果是成了，则要查看物流
                btnFirst.setText("查询物流");
                btnFirst.setBackgroundColor(getResources().getColor(R.color.green_tag));
                btnFirst.setVisibility(View.VISIBLE);
                btnSecond.setVisibility(View.GONE);
            } else if (mailStatus.equals("SUCCESS")) {
                //订单已完成
                btnFirst.setText("捐赠完成");
                btnFirst.setVisibility(View.VISIBLE);
                btnSecond.setVisibility(View.GONE);
            } else if (orderStatus.equals("WAITING") && mailStatus.equals("INITIAL")) {
                //等待卖家发货
                btnFirst.setText("等待发货");
                btnFirst.setVisibility(View.VISIBLE);
                btnSecond.setVisibility(View.GONE);
            }
        } else {
            if (isApply) {
                if (orderStatus.equals("WAITING") && mailStatus.equals("INITIAL")) {
                    btnFirst.setVisibility(View.VISIBLE);
                    btnFirst.setText("填写物流信息");
                    btnFirst.setBackgroundColor(getResources().getColor(R.color.blue_tag));
                    btnSecond.setVisibility(View.GONE);
                } else if (mailStatus.equals("GOING")) {
                    btnFirst.setVisibility(View.VISIBLE);
                    btnFirst.setText("查看物流");
                    btnFirst.setBackgroundColor(getResources().getColor(R.color.green_tag));
                    btnSecond.setVisibility(View.GONE);
                } else if (orderStatus.equals("GOING")) {
                    btnFirst.setVisibility(View.VISIBLE);
                    btnFirst.setText("撤回");
                    btnFirst.setBackgroundColor(getResources().getColor(R.color.blue_tag));
                    btnSecond.setVisibility(View.GONE);
                } else {
                    Toaster.toast(getContext(), "不是我发布的，我申请了的其他情况");
                }
            } else {
                btnFirst.setVisibility(View.VISIBLE);
                btnFirst.setText("返回");
                btnFirst.setBackgroundColor(getResources().getColor(R.color.blue_tag));
                btnSecond.setVisibility(View.GONE);
            }
        }

        final String imgs = entity.getApplyImgs();
        if (imgs.contains(",")) {
            ivOnePic.setVisibility(View.GONE);
            recyclerViewDonate.setVisibility(View.VISIBLE);
            String[] arr = imgs.split(",", -1);
            List<String> listPic = new ArrayList<>();
            listPic.addAll(Utils.arrayToList(arr));
            GridImageAdapter mAdapter = new GridImageAdapter(getContext(), listPic);
            mAdapter.setLimited(true);
            mAdapter.setIsShowBigImg(true);

            GridLayoutManager gridlayout = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
            recyclerViewDonate.setLayoutManager(gridlayout);

            recyclerViewDonate.setAdapter(mAdapter);
            recyclerViewDonate.setNestedScrollingEnabled(false);
        } else {
            ivOnePic.setVisibility(View.VISIBLE);
            recyclerViewDonate.setVisibility(View.GONE);
            Utils.setImgF(getContext(), imgs, ivOnePic);
            ivOnePic.setOnClickListener(new View.OnClickListener() {
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
    }

    @OnClick({R.id.iv_donate_icon, R.id.btn_first, R.id.btn_second})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_donate_icon:
                break;
            case R.id.btn_first:
                String title = btnFirst.getText().toString();
                if (title.equals("填写物流信息")) {
                    startActivity(new Intent(getContext(), StartFahuoAct.class)
                            .putExtra("applyId", entity.getApplyId())
                            .putExtra("isWish", true)
                            .putExtra("orderId", entity.getOrderId()));
                } else if (title.equals("查询物流")) {
                    startActivity(new Intent(getContext(), TransitAct.class)
                            .putExtra("orderId", entity.getOrderId()));
                } else if (title.equals("捐赠完成")) {
                    finish();
                } else if (title.equals("返回")) {
                    finish();
                } else if (title.equals("再考虑一下")) {
                    clickOperate("FAIL");
                } else if (title.equals("撤回")) {
                    LoadDialog.show(getContext(), "正在撤回...");
                    Map<String, Object> map = new HashMap<>();
                    map.put("accessToken", MApplication.getAccessToken());
                    map.put("applyId", entity.getApplyId());
                    NRClient.wishDonateWithDraw(map, new ResultCallback<Result<Void>>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            LoadDialog.dismiss(getContext());
                            Utils.showErrorToast(getContext(), throwable);
                        }

                        @Override
                        public void onSuccess(Result<Void> result) {
                            LoadDialog.dismiss(getContext());
                            Toaster.toast(getContext(), "撤回成功");
                            finish();
                        }
                    });

                }
                break;
            case R.id.btn_second:
                clickOperate("SUCCESS");
                break;
        }
    }

    private void clickOperate(final String status) {
        LoadDialog.show(getContext(), "请稍后");
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("applyId", entity.getApplyId());
        map.put("applyStatus", status);
//                accessToken	是	String	登录标识
//                applyId	是	long	订单ID
//                applyStatus	是	String	确认状态（参照全局变量applyStatus）
        NRClient.wishDonateConfirm(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                if (status.equals("SUCCESS")) {
                    Toaster.toast(getContext(), "确认成功，请等待捐赠人发货！");
                } else {
                    Toaster.toast(getContext(), "操作成功");
                }
                finish();
            }
        });
    }
}
