package com.narancommunity.app.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.picbroswer.ImagePagerActivity;
import com.narancommunity.app.adapter.CommentAdapter;
import com.narancommunity.app.adapter.GridImageAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.CommentEntity;
import com.narancommunity.app.entity.CommentListEntity;
import com.narancommunity.app.entity.WishDetailEntity;
import com.narancommunity.app.entity.WishDonateEntity;
import com.narancommunity.app.interfaces.CommentInterfaces;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/1/11 18:55
 * Email：120760202@qq.com
 * FileName :
 */
public class WishDetailAct extends BaseActivity {
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
    @BindView(R.id.tv_wish_times)
    TextView tvWishTimes;
    @BindView(R.id.tv_wish_share)
    TextView tvWishShare;
    @BindView(R.id.tv_wish_comment)
    TextView tvWishComment;
    @BindView(R.id.tv_wish_collect)
    TextView tvWishCollect;
    @BindView(R.id.ln_wish_detail)
    LinearLayout lnWishDetail;
    @BindView(R.id.iv_feedback_icon)
    SelectableRoundedImageView ivFeedbackIcon;
    @BindView(R.id.tv_feedback_name)
    TextView tvFeedbackName;
    @BindView(R.id.tv_feedback_time)
    TextView tvFeedbackTime;
    @BindView(R.id.tv_feedback_content)
    TextView tvFeedbackContent;
    @BindView(R.id.recycleViewSon_feedback)
    RecyclerView recycleViewSonFeedback;
    @BindView(R.id.ln_include_feedback)
    LinearLayout lnIncludeFeedback;
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
    @BindView(R.id.iv_donate_state)
    ImageView ivDonateState;
    @BindView(R.id.tv_donate_title)
    TextView tvDonateTitle;
    @BindView(R.id.tv_donate_type)
    TextView tvDonateType;
    @BindView(R.id.tv_donate_content)
    TextView tvDonateContent;
    @BindView(R.id.tv_donate_time)
    TextView tvDonateTime;
    @BindView(R.id.ln_wish_donate)
    LinearLayout lnWishDonate;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.recyclerView_comment)
    RecyclerView recyclerViewComment;
    @BindView(R.id.ln_wish_comment)
    LinearLayout lnWishComment;
    @BindView(R.id.btn_state)
    Button btnState;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.ln_tool)
    LinearLayout lnTool;
    @BindView(R.id.recyclerView_wish)
    RecyclerView recyclerViewWish;
    @BindView(R.id.iv_wish_one_pic)
    ImageView ivWishOnePic;
    @BindView(R.id.ln_share)
    LinearLayout lnShare;
    @BindView(R.id.ln_collect)
    LinearLayout lnCollect;
    @BindView(R.id.ln_comment)
    LinearLayout lnComment;
    @BindView(R.id.tv_feedback_grades)
    TextView tvFeedbackGrades;
    @BindView(R.id.rb_feedback_star)
    RatingBar rbFeedbackStar;

    @BindView(R.id.recycleViewSon_donate)
    RecyclerView recycleViewSonDonate;
    @BindView(R.id.iv_donate_one_pic)
    ImageView ivDonateOnePic;

    Integer orderId;
    String commodityType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wish_detail);
        ButterKnife.bind(this);
        toolbar.setTitle("心愿详情");
        setBar(toolbar);
        orderId = getIntent().getIntExtra("orderId", 0);
        setView();
        setPopView();
    }

    private void setView() {
        if (scrollView != null) {
            scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    try {
                        if (isViewCovered(lnTool))
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

    @Override
    protected void onResume() {
        super.onResume();
        getDetailData();
        getCommentList();
        getCollectState();
        getDonate();
        getFeedBack();
        getRelation();
    }


    private void getFeedBack() {
        lnIncludeFeedback.setVisibility(View.GONE);
    }

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

    private void getRelation() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        map.put("orderId", orderId);
        NRClient.getOrderRelation(map, new ResultCallback<Result<Map<String, Object>>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Map<String, Object>> result) {
                LoadDialog.dismiss(getContext());
                //"myApply":true,"myOrder":false,"applyStatus":"INITIAL"
                myOrder = (boolean) result.getData().get("myOrder");
                if (myOrder) {
                    //TODO 暂时不处理这个
                    btnState.setVisibility(View.GONE);
                } else {
                    isApply = (boolean) result.getData().get("myApply");
                    applyStatus = result.getData().get("applyStatus") + "";
                    btnState.setVisibility(View.VISIBLE);
                    if (orderState.equals("SUCCESS")) {
                        btnState.setText("心愿已达成");
                        btnState.setBackgroundColor(getResources().getColor(R.color.color_bbbbbb));
                        btnState.setClickable(false);
                    } else if (orderState.equals("INITIAL")) {
                        btnState.setBackgroundColor(getResources().getColor(R.color.login_gray));
                        if (myOrder) {
                            btnState.setClickable(false);
                            btnState.setText("待匹配");
                        } else {
                            btnState.setClickable(true);
                            btnState.setText("帮TA实现");
                        }
                    } else {
                        btnState.setClickable(false);
                        btnState.setText("心愿实现中");
                    }
                }

            }
        });
    }

    private void getDonate() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("orderId", orderId);
        NRClient.wishDonate(map, new ResultCallback<Result<WishDonateEntity>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<WishDonateEntity> result) {
                LoadDialog.dismiss(getContext());
                setDonateDetail(result.getData());
            }
        });
    }

    WishDonateEntity donateData;

    private void setDonateDetail(WishDonateEntity data) {
        if (null != data.getApplyId() && !data.getApplyId().equals("")) {
            lnWishDonate.setVisibility(View.VISIBLE);
            donateData = data;
        } else {
            lnWishDonate.setVisibility(View.GONE);
            return;
        }
        tvDonateType.setText("" + commodityType);
        tvDonateContent.setText("" + Utils.getValue(data.getApplyContent()));
        tvDonateLocation.setText("" + Utils.getValue(data.getCity()));
        tvDonateName.setText("" + Utils.getValue(data.getAccountNike()));
        tvDonateTitle.setText("" + Utils.getValue(data.getApplyTitle()));
        tvDonateTime.setText(Utils.dateDiff(Utils.stringTimeToMillion(data.getCreateTime())) + "");
        String icon = data.getAccountImg();
        if (icon != null && !icon.equals("")) {
            Utils.setImgF(getContext(), icon, ivDonateIcon);
        }
        donateData.setCommodityType(commodityType);

        final String imgs = data.getApplyImgs();
        if (imgs.contains(",")) {
            ivDonateOnePic.setVisibility(View.GONE);
            recycleViewSonDonate.setVisibility(View.VISIBLE);
            String[] arr = imgs.split(",", -1);
            List<String> listPic = new ArrayList<>();
            listPic.addAll(Utils.arrayToList(arr));
            GridImageAdapter mAdapter = new GridImageAdapter(getContext(), listPic);
            mAdapter.setLimited(true);
            mAdapter.setIsShowBigImg(true);

            GridLayoutManager gridlayout = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
            recycleViewSonDonate.setLayoutManager(gridlayout);

            recycleViewSonDonate.setAdapter(mAdapter);
            recycleViewSonDonate.setNestedScrollingEnabled(false);
        } else {
            ivDonateOnePic.setVisibility(View.VISIBLE);
            recycleViewSonDonate.setVisibility(View.GONE);
            Utils.setImgF(getContext(), imgs, ivDonateOnePic);
            ivDonateOnePic.setOnClickListener(new View.OnClickListener() {
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

    private void getCommentList() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        NRClient.getCommentList(map, new ResultCallback<Result<CommentListEntity>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<CommentListEntity> result) {
                LoadDialog.dismiss(getContext());
                setCommentList(result.getData());
            }
        });
    }

    private void setCommentList(final CommentListEntity data) {
        final List<CommentEntity> list = data.getComments();
        CommentAdapter adapter = new CommentAdapter(getContext(), list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerViewComment.setLayoutManager(linearLayoutManager);

        adapter.setListener(new CommentInterfaces() {
            @Override
            public void OnItemClick(int position) {
                showInputDialog(list.get(position).getCommentId(), list.get(position).getInitiatorNike());
            }

            @Override
            public void OnAnswer(int id, String name) {
                showInputDialog(id, name);
            }
        });
        recyclerViewComment.setAdapter(adapter);
        recyclerViewComment.setNestedScrollingEnabled(false);
    }

    private void getCollectState() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        map.put("orderId", orderId);
        NRClient.isCollect(map, new ResultCallback<Result<Map<String, Object>>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Map<String, Object>> result) {
                LoadDialog.dismiss(getContext());
                if (result != null && result.getData() != null) {
                    Boolean isCollect = (Boolean) result.getData().get("isMe");
//                    if (isCollect)
//                        tvWishCollect.setText((Integer.parseInt(tvWishCollect.getText().toString()) + 1) + "");
//                    else
//                        tvWishCollect.setText((Integer.parseInt(tvWishCollect.getText().toString()) - 1) + "");
                    String accountId = result.getData().get("accountId") + "";
                    setIsCollect(isCollect);
                }
            }
        });
    }

    private void setIsCollect(Boolean isCollect) {
        if (isCollect) {
            Drawable top = getResources().getDrawable(R.mipmap.list_btn_shoucang_pre);
            tvWishCollect.setCompoundDrawablesWithIntrinsicBounds(top, null, null, null);
        } else {
            Drawable top = getResources().getDrawable(R.mipmap.list_btn_shoucang_gre);
            tvWishCollect.setCompoundDrawablesWithIntrinsicBounds(top, null, null, null);
        }
    }

    PopupWindow mPop;

    private void setPopView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.pop_function, null);

        if (mPop == null) {
            mPop = new PopupWindow(v, LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mPop.setFocusable(false);
            mPop.setAnimationStyle(R.style.popwin_anim_style);
            v.findViewById(R.id.iv_share).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Toaster.toast(getContext(), "点击分享");
                }
            });
            v.findViewById(R.id.iv_collect).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Toaster.toast(getContext(), "点击收藏");
                    doCollect();
                }
            });
            v.findViewById(R.id.iv_add_comment).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    showInputDialog(0, "");
                }
            });
        }
    }

    private void doCollect() {
        LoadDialog.show(getContext(), "正在为您收藏中...");
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        map.put("orderId", orderId);
        NRClient.collect(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                Drawable top = getResources().getDrawable(R.mipmap.list_btn_shoucang_pre);
                tvWishCollect.setCompoundDrawablesWithIntrinsicBounds(top, null, null, null);
                Toaster.toastLong(getContext(), "收藏成功");
            }
        });
    }

    private void showPop() {
        if (mPop != null && !mPop.isShowing()) {
            mPop.showAtLocation(btnState, Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
        }
    }

    private void addComment(String content, Integer otherId) {
        LoadDialog.show(getContext(), "正在评论...");
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        map.put("orderId", orderId);
        map.put("commentContent", content);
        if (otherId != 0)
            map.put("commentedId", otherId);
        NRClient.addComment(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                mWindowDialog.dismiss();
                Toaster.toast(getContext(), "评论成功!");
            }
        });
    }

    private void getDetailData() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        NRClient.getWishDetail(map, new ResultCallback<Result<WishDetailEntity>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<WishDetailEntity> result) {
                LoadDialog.dismiss(getContext());
                setDataToView(result.getData());
                getRelation();
//                Toaster.toastLong(getContext(), "发布成功，您可至心愿列表查看您的心愿");
            }
        });
    }

    String orderState;
    String mailStatus;//订单确认状态 = going 开始配送了，就可以查看物流了 ，= success 确认收获了，完成了订单 = initial没有填写订单

    private void setDataToView(WishDetailEntity data) {
//        Utils.setImgF(getContext(),data.geti,ivWishIcon);
        if (data == null)
            return;
        mailStatus = data.getMailStatus();
        tvWishTitle.setText(Utils.getValue(data.getOrderTitle()) + "");
        tvWishName.setText(Utils.getValue(data.getInitiatorNike()) + "");
        tvWishContent.setText(Utils.getValue(data.getOrderContent()) + "");
        tvWishLocation.setText(Utils.getValue(data.getCity()) + "");
        String icon = data.getInitiatorImg();
        if (icon != null && !icon.equals("")) {
            Utils.setImgF(getContext(), icon, ivDonateIcon);
        }
        commodityType = Utils.getValue(data.getCommodityType());

        tvWishType.setText("" + commodityType);
        tvWishTimes.setText(Utils.dateDiff(Utils.stringTimeToMillion(data.getCreateTime())) + "");
        tvWishShare.setText("" + Utils.getValue(data.getShareTimes()));
        tvWishComment.setText("" + Utils.getValue(data.getCommentTimes()));
        tvWishCollect.setText("" + Utils.getValue(data.getCollectionTimes()));
        tvComment.setText("评论" + Utils.getValue(data.getCommentTimes()));
//        tvWishGrades.setText(Utils.getValue(data.get));
        orderState = data.getOrderStatus();
        if (orderState.equals("SUCCESS"))
            ivDonateState.setVisibility(View.VISIBLE);
        else
            ivDonateState.setVisibility(View.GONE);

        final String imgs = data.getOrderImgs();
        if (imgs.contains(",")) {
            ivWishOnePic.setVisibility(View.GONE);
            recyclerViewWish.setVisibility(View.VISIBLE);
            String[] arr = imgs.split(",", -1);
            List<String> listPic = new ArrayList<>();
            listPic.addAll(Utils.arrayToList(arr));
            GridImageAdapter mAdapter = new GridImageAdapter(getContext(), listPic);
            mAdapter.setLimited(true);
            mAdapter.setIsShowBigImg(true);

            GridLayoutManager gridlayout = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
            recyclerViewWish.setLayoutManager(gridlayout);

            recyclerViewWish.setAdapter(mAdapter);
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
    }

    @OnClick({R.id.iv_wish_icon, R.id.tv_wish_content, R.id.iv_feedback_icon,
            R.id.tv_feedback_content, R.id.iv_donate_icon, R.id.tv_donate_content, R.id.btn_state
            , R.id.ln_share, R.id.ln_collect, R.id.ln_comment, R.id.tv_donate_title})
    public void onViewClicked(View view) {
        if (MApplication.getUserInfo(getContext()) == null) {
            startActivity(new Intent(getContext(), LoginAct.class));
            Toaster.toast(getContext(), "请先登录！");
            return;
        }
        switch (view.getId()) {
            case R.id.iv_wish_icon:
                break;
            case R.id.tv_wish_content:
                break;
            case R.id.iv_feedback_icon:
                break;
            case R.id.tv_feedback_content:
                break;
            case R.id.iv_donate_icon:
                break;
            case R.id.tv_donate_content:
            case R.id.tv_donate_title:
                startActivity(new Intent(getContext(), WishDonateDetailAct.class).putExtra("data", donateData)
                        .putExtra("myOrder", myOrder)
                        .putExtra("isApply", isApply)
                        .putExtra("applyStatus", applyStatus)
                        .putExtra("mailStatus", mailStatus)
                        .putExtra("orderStatus", orderState));
                break;
            case R.id.btn_state:
                if (myOrder)
                    Toaster.toast(getContext(), "别调皮，这是你自己的！");
                else
                    startActivity(new Intent(getContext(), HelpItAct.class).putExtra("orderId", orderId));
                break;
            case R.id.ln_share:
                break;
            case R.id.ln_comment:
                showInputDialog(0, "");
                break;
            case R.id.ln_collect:
                LoadDialog.show(getContext(), "收藏中...");
                Map<String, Object> map = new HashMap<>();
                map.put("accessToken", MApplication.getAccessToken(getContext()));
                map.put("orderId", orderId);
                NRClient.collect(map, new ResultCallback<Result<Void>>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        LoadDialog.dismiss(getContext());
                        Utils.showErrorToast(getContext(), throwable);
                    }

                    @Override
                    public void onSuccess(Result<Void> result) {
                        LoadDialog.dismiss(getContext());
                        Drawable top = getResources().getDrawable(R.mipmap.list_btn_shoucang_pre);
                        tvWishCollect.setCompoundDrawablesWithIntrinsicBounds(top, null, null, null);
                        tvWishCollect.setText((Integer.parseInt(tvWishCollect.getText().toString()) + 1) + "");
                        Toaster.toastLong(getContext(), "收藏成功");
                    }
                });
                break;
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

    public boolean isViewCovered(final View view) {
        View currentView = view;

        Rect currentViewRect = new Rect();
        boolean partVisible = currentView.getGlobalVisibleRect(currentViewRect);
        boolean totalHeightVisible = (currentViewRect.bottom - currentViewRect.top) >= view.getMeasuredHeight();
        boolean totalWidthVisible = (currentViewRect.right - currentViewRect.left) >= view.getMeasuredWidth();
        boolean totalViewVisible = partVisible && totalHeightVisible && totalWidthVisible;
        if (!totalViewVisible)//if any part of the view is clipped by any of its parents,return true
            return true;

        while (currentView.getParent() instanceof ViewGroup) {
            ViewGroup currentParent = (ViewGroup) currentView.getParent();
            if (currentParent.getVisibility() != View.VISIBLE)//if the parent of view is not visible,return true
                return true;

            int start = indexOfViewInParent(currentView, currentParent);
            for (int i = start + 1; i < currentParent.getChildCount(); i++) {
                Rect viewRect = new Rect();
                view.getGlobalVisibleRect(viewRect);
                View otherView = currentParent.getChildAt(i);
                Rect otherViewRect = new Rect();
                otherView.getGlobalVisibleRect(otherViewRect);
                if (Rect.intersects(viewRect, otherViewRect))//if view intersects its older brother(covered),return true
                    return true;
            }
            currentView = currentParent;
        }
        return false;
    }


    private int indexOfViewInParent(View view, ViewGroup parent) {
        int index;
        for (index = 0; index < parent.getChildCount(); index++) {
            if (parent.getChildAt(index) == view)
                break;
        }
        return index;
    }

    Dialog mWindowDialog;

    public void showInputDialog(final Integer otherId, String toName) {

        mWindowDialog = new Dialog(this, R.style.more_dialog);
        View view = LinearLayout.inflate(this, R.layout.dialog_add_comment, null);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWindowDialog != null) {
                    mWindowDialog.dismiss();
                }
            }
        });
        mWindowDialog.setContentView(view);
        mWindowDialog.setCanceledOnTouchOutside(true);
        view.getLayoutParams().width = getWindow().getWindowManager().getDefaultDisplay().getWidth();

        final EditText editText = view.findViewById(R.id.et_comment);
        if (toName.equals(""))
            editText.setHint("评论");
        else
            editText.setHint("回复" + toName);
        mWindowDialog.show();
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean isOK = true;
                switch (actionId) {
                    case EditorInfo.IME_ACTION_NONE:
//                        Toast.makeText(mContext, "点击-->NONE", Toast.LENGTH_SHORT).show();
                        break;
                    case EditorInfo.IME_ACTION_GO:
                    case EditorInfo.IME_ACTION_DONE:
                        addComment(editText.getText().toString(), otherId);
                        break;
                    case EditorInfo.IME_ACTION_SEARCH:
//                        Toast.makeText(mContext, "点击-->SEARCH", Toast.LENGTH_SHORT).show();
                        break;
                    case EditorInfo.IME_ACTION_SEND:
//                        Toast.makeText(mContext, "点击-->SEND", Toast.LENGTH_SHORT).show();
                        break;
                    case EditorInfo.IME_ACTION_NEXT:
//                        Toast.makeText(mContext, "点击-->NEXT", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        isOK = false;
                        break;
                }
                return isOK;
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showKeyboard(editText);
            }
        }, 200);
    }

    public void showKeyboard(EditText editText) {
        if (editText != null) {
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) editText
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }


}
