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
import android.util.Log;
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
import android.widget.CheckedTextView;
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
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.picbroswer.ImagePagerActivity;
import com.narancommunity.app.adapter.CommentAdapter;
import com.narancommunity.app.adapter.GridImageAdapter;
import com.narancommunity.app.adapter.WantListAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.CommentEntity;
import com.narancommunity.app.entity.CommentListEntity;
import com.narancommunity.app.entity.DonateDetailData;
import com.narancommunity.app.entity.WantEntity;
import com.narancommunity.app.entity.WantListEntity;
import com.narancommunity.app.interfaces.CommentInterfaces;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
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
 * Writer：fancy on 2018/1/15 13:53
 * Email：120760202@qq.com
 * FileName :
 */

public class DonateDetailAct extends BaseActivity {
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
    @BindView(R.id.tv_donate_times)
    TextView tvDonateTimes;
    @BindView(R.id.tv_donate_share)
    TextView tvDonateShare;
    @BindView(R.id.tv_donate_comment)
    TextView tvDonateComment;
    @BindView(R.id.tv_donate_collect)
    TextView tvDonateCollect;
    @BindView(R.id.iv_feedback_icon)
    SelectableRoundedImageView ivFeedbackIcon;
    @BindView(R.id.tv_feedback_grades)
    TextView tvFeedbackGrades;
    @BindView(R.id.rb_feedback_star)
    RatingBar rbFeedbackStar;
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
    @BindView(R.id.recyclerViewWant)
    RecyclerView recyclerViewWant;
    @BindView(R.id.ln_donate_want)
    LinearLayout lnDonateWant;
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

    Integer orderId;
    @BindView(R.id.iv_one_pic)
    ImageView ivOnePic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_donate_detail);
        ButterKnife.bind(this);
        toolbar.setTitle("捐赠详情");
        setBar(toolbar);

        orderId = getIntent().getIntExtra("orderId", 0);
        setView();
        setPopView();
        MApplication.putActivity("DonateDetail", getContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDetail();
        getWantList();
        getCommentList();
        getRelation();
        getCollectState();
        getFeedBack();
    }

    private void getFeedBack() {
        lnIncludeFeedback.setVisibility(View.GONE);
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
                    String accountId = result.getData().get("accountId") + "";
                    setIsCollect(isCollect);
                }
            }
        });
    }

    private void setIsCollect(Boolean isCollect) {
        if (isCollect) {
            Drawable top = getResources().getDrawable(R.mipmap.list_btn_shoucang_pre);
            tvDonateCollect.setCompoundDrawablesWithIntrinsicBounds(top, null, null, null);
        } else {
            Drawable top = getResources().getDrawable(R.mipmap.list_btn_shoucang_gre);
            tvDonateCollect.setCompoundDrawablesWithIntrinsicBounds(top, null, null, null);
        }
    }

//    /**
//     * 订单申请状态
//     */
//    String applyStatus;
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
                    btnState.setText(getState(orderStatus));
                } else {
                    isApply = (boolean) result.getData().get("myApply");
                    applyStatus = result.getData().get("applyStatus") + "";
                    btnState.setText(getState(applyStatus));
                }
            }
        });
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

    private void getWantList() {
        LoadDialog.show(getContext(), "正在获取想要的人...");
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        NRClient.getWantList(map, new ResultCallback<Result<WantListEntity>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<WantListEntity> result) {
                LoadDialog.dismiss(getContext());
                setWantList(result.getData());
//                Toaster.toastLong(getContext(), );
            }
        });
    }

    private void setWantList(WantListEntity data) {
        final List<WantEntity> list;
        if (data != null && data.getApplys() != null && data.getApplys().size() > 0) {
            recyclerViewWant.setVisibility(View.VISIBLE);
            lnDonateWant.setVisibility(View.VISIBLE);
            list = data.getApplys();
        } else {
            lnDonateWant.setVisibility(View.GONE);
            recyclerViewWant.setVisibility(View.GONE);
            return;
        }
        WantListAdapter adapter = new WantListAdapter(getContext(), data.getApplys());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerViewWant.setLayoutManager(linearLayoutManager);

        adapter.setListener(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                Integer wantId = list.get(position).getApplyId();
                startActivity(new Intent(getContext(), DonateWishDetailAct.class)
                        .putExtra("wantId", wantId)
                        .putExtra("type", type)
                        .putExtra("orderType", orderStatus)
                        .putExtra("myOrder", myOrder)
                        .putExtra("orderId", orderId));
            }

            @Override
            public void OnDelClick(int position) {

            }
        });
        recyclerViewWant.setAdapter(adapter);
        recyclerViewWant.setNestedScrollingEnabled(false);
    }

    private void getDetail() {
        LoadDialog.show(getContext(), "正在获取订单详细...");
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        NRClient.getDonateDetail(map, new ResultCallback<Result<DonateDetailData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<DonateDetailData> result) {
                LoadDialog.dismiss(getContext());
                setData(result.getData());
                setTagView(result.getData().getQuestionnaireOptions());
//                Toaster.toastLong(getContext(), );
            }
        });
    }

    String hisLocation = "", weight = "";
    String type;
    String orderStatus;//订单状态

    private void setData(DonateDetailData data) {
        if (data == null)
            return;
        tvDonateCollect.setText(Utils.getValue(data.getCollectionTimes()) + "");
        tvDonateComment.setText(Utils.getValue(data.getCommentTimes()) + "");
        tvDonateShare.setText(Utils.getValue(data.getShareTimes()) + "");
        tvDonateContent.setText(Utils.getValue(data.getOrderContent()) + "");
        tvDonateLocation.setText("" + Utils.getValue(data.getCity()));
        tvDonateName.setText(Utils.getValue(data.getInitiatorNike()) + "");
        tvDonateTitle.setText(Utils.getValue(data.getOrderTitle()) + "");
        type = Utils.getValue(data.getCommodityType()) + "";
        tvDonateType.setText(type);
        orderStatus = Utils.getValue(data.getOrderStatus());

        hisLocation = "" + Utils.getValue(data.getCity());
        weight = "" + Utils.getValue(data.getEstimateWeight());

        String icon = data.getInitiatorImg();
        if (icon != null && !icon.equals("")) {
            Utils.setImgF(getContext(), icon, ivDonateIcon);
        }

        final String imgs = data.getOrderImgs();
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
        tvComment.setText("评论" + Utils.getValue(data.getCommentTimes()));
    }

    private String getState(String states) {
        if (myOrder) {
            Log.i("fancy", "发布人是我自己");
            //说明发布人是我自己
            btnState.setClickable(false);
            if (states.equals("INITIAL")) {
                return "待匹配";
            } else if (states.equals("GOING")) {
                return "匹配成功";
            } else if (states.equals("WAITING")) {
                return "等待中";
            } else if (states.equals("SUCCESS")) {
                return "订单已完成";
            } else if (states.equals("REVOKE")) {
                return "撤回";
            } else if (states.equals("FAIL")) {
                return "匹配失败";
            } else {
                Log.i("fancy", "我的订单，最后的否则是待匹配");
                return "待匹配";
            }
        } else if (isApply) {
            btnState.setClickable(false);
            Log.i("fancy", "我申请过");
            //如果不是我，但是申请过
            if (states.equals("INITIAL")) {
                return "待匹配";
            } else if (states.equals("GOING")) {
                return "匹配成功";
            } else if (states.equals("WAITING")) {
                return "等待中";
            } else if (states.equals("SUCCESS")) {
                return "订单已完成";
            } else if (states.equals("REVOKE")) {
                btnState.setClickable(true);
                return "重新申请";
            } else if (states.equals("FAIL")) {
                return "匹配失败";
            } else {
                Log.i("fancy", "我申请的订单，最后的否则是待匹配");
                return "待匹配";
            }
        } else {
            btnState.setClickable(true);
            Log.i("fancy", "如果不是我，也没有申请过");
            //如果不是我，也没有申请过
            if (!states.equals("SUCCESS")) {
                return "我很需要";
            } else {
                btnState.setClickable(false);
                return "匹配失败";
            }
        }
    }

    @OnClick({R.id.iv_donate_icon, R.id.iv_feedback_icon, R.id.btn_state
            , R.id.ln_share, R.id.ln_collect, R.id.ln_comment})
    public void onViewClicked(View view) {
        if (MApplication.getUserInfo(getContext()) == null) {
            startActivity(new Intent(getContext(), LoginAct.class));
            Toaster.toast(getContext(), "请先登录！");
            return;
        }
        switch (view.getId()) {
            case R.id.ln_share:
                break;
            case R.id.ln_comment:
                showInputDialog(0, "");
                break;
            case R.id.ln_collect:
                doCollect();
                break;
            case R.id.iv_donate_icon:
                break;
            case R.id.iv_feedback_icon:
                break;
            case R.id.btn_state:
                if (orderStatus.equals("INITIAL")) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("hislocation", hisLocation);
                    map.put("weight", weight);
                    map.put("orderId", orderId);
                    startActivity(new Intent(getContext(), IWantAct.class)
                            .putExtra("data", (Serializable) map));
                }
                break;
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
                tvDonateCollect.setCompoundDrawablesWithIntrinsicBounds(top, null, null, null);
                tvDonateCollect.setText((Integer.parseInt(tvDonateCollect.getText().toString()) + 1) + "");
                Toaster.toastLong(getContext(), "收藏成功");
            }
        });
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

    int[] bg = new int[]{R.drawable.round_corner_blue_line, R.drawable.round_corner_yellow_line,
            R.drawable.round_corner_red_line, R.drawable.round_corner_greens_line, R.drawable.round_corner_blue_line};

    int[] txtColor = new int[]{R.color.blue_tag, R.color.yellow_tag,
            R.color.red_tag, R.color.green_tag, R.color.blue_tag};

    private void setTagView(final List<String> questionnaireOptions) {
        donateTagFlow.setAdapter(new TagAdapter(questionnaireOptions) {

            @Override
            public View getView(FlowLayout parent, int position, Object o) {
                LayoutInflater mInflater = LayoutInflater.from(getContext());
                LinearLayout linearLayout = (LinearLayout) mInflater.inflate(R.layout.item_question_answer_item_two,
                        donateTagFlow, false);
                final CheckedTextView checkedTextView = linearLayout.findViewById(R.id.ctv);
                checkedTextView.setText(questionnaireOptions.get(position));
                checkedTextView.setBackground(getResources().getDrawable(bg[position]));
                checkedTextView.setTextColor(getResources().getColor(txtColor[position]));
                return linearLayout;
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPop != null)
            mPop.dismiss();
        if (mWindowDialog != null)
            mWindowDialog.dismiss();
    }

    private void showPop() {
        if (mPop != null && !mPop.isShowing()) {
            mPop.showAtLocation(btnState, Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
        }
    }

}
