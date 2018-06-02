package com.narancommunity.app.activity.index.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.CommentAdapter;
import com.narancommunity.app.adapter.EssayPicAdapter;
import com.narancommunity.app.adapter.OnItemClickListener;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.CommentEntity;
import com.narancommunity.app.entity.CommentListEntity;
import com.narancommunity.app.entity.YSHYEntity;
import com.narancommunity.app.interfaces.CommentInterfaces;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/4/13 16:50
 * Email：120760202@qq.com
 * FileName : 以书会友详情界面
 * 参考{@link com.narancommunity.app.adapter.CommentAdapter}
 */

public class BookYSHYDetailAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_icon)
    SelectableRoundedImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.recyclerView_pic)
    RecyclerView recyclerViewPic;
    @BindView(R.id.iv_one_pic)
    ImageView ivOnePic;
    @BindView(R.id.ln_pic)
    LinearLayout lnPic;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.ln_date)
    LinearLayout lnDate;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.ln_comment)
    LinearLayout lnComment;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.ln_like)
    LinearLayout lnLike;
    @BindView(R.id.recyclerView_comment)
    RecyclerView recyclerViewComment;
    @BindView(R.id.ln_tool)
    LinearLayout lnTool;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.btn_more)
    Button btn_more;

    List<CommentEntity> list = new ArrayList<>();
    YSHYEntity mData;
    EssayPicAdapter picAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_act_book_yshy);
        ButterKnife.bind(this);
        toolbar.setTitle("以书会友");
        setBar(toolbar);

        mData = (YSHYEntity) getIntent().getSerializableExtra("data");
        setView();
        setPopView();
        getData();
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

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("contentId", mData.getContentId());
        map.put("accessToken", MApplication.getAccessToken());
        NRClient.getEssayCommentList(map, new ResultCallback<Result<CommentListEntity>>() {
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

    private void setCommentList(final CommentListEntity data) {
        if (data == null || data.getComments() == null) {
            btn_more.setVisibility(View.GONE);
            return;
        }
        list.addAll(data.getComments());
        if (data.getComments().size() > 3)
            btn_more.setVisibility(View.VISIBLE);
        else
            btn_more.setVisibility(View.GONE);

        CommentAdapter adapter = new CommentAdapter(getContext(), list);
        adapter.setLimited(true);

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
                startActivity(new Intent(getContext(), AddBookCommentAct.class)
                        .putExtra("tag", 0).putExtra("bookId", mData.getContentId())
                        .putExtra("commentedId", list.get(position).getCommentId())
                        .putExtra("replyName", list.get(position).getInitiatorNike()));
            }

            @Override
            public void OnAnswer(int id, String name) {
//                showInputDialog(id, name);
                startActivity(new Intent(getContext(), AddBookCommentAct.class)
                        .putExtra("tag", 0).putExtra("bookId", mData.getContentId())
                        .putExtra("commentedId", id)
                        .putExtra("replyName", name));
            }
        });
        adapter.setClickLike(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                clickLike(position);
            }

            @Override
            public void OnDelClick(int position) {

            }
        }, mData.getContentId());
        recyclerViewComment.setAdapter(adapter);
        recyclerViewComment.setNestedScrollingEnabled(false);
    }

    private void clickLike(int position) {

    }


    private void setView() {
        tvTitle.setText(Utils.getValue(mData.getContentTitle()) + "");
        tvName.setText("" + Utils.getValue(mData.getAccountNike()));
        tvContent.setText("" + Utils.getValue(mData.getContentBody()));
        tvDate.setText(Utils.dateDiff(Utils.stringTimeToMillion(mData.getCreateTime())) + "");
        tvComment.setText("" + Utils.getValue(mData.getCommentTimes()));
        tvLike.setText("" + Utils.getValue(mData.getLikeTimes()));
        String icon = Utils.getValue(mData.getAccountImg());
        if ("".equals(icon))
            Utils.setImgF(getContext(), R.mipmap.zw_morentouxiang, ivIcon);
        else
            Utils.setImgF(getContext(), icon, ivIcon);
        String pic = Utils.getValue(mData.getContentImg());
        if (!"".equals(pic)) {
            if (pic.contains(",")) {
                String[] arr = pic.split(",", -1);
                ivOnePic.setVisibility(View.GONE);
                recyclerViewPic.setVisibility(View.VISIBLE);
                setMultiImg(arr);
            } else {
                Utils.setImgF(getContext(), pic, ivOnePic);
                ivOnePic.setVisibility(View.VISIBLE);
                recyclerViewPic.setVisibility(View.GONE);
            }
        }

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

    private void setMultiImg(String[] arr) {
        GridLayoutManager linearLayout = new GridLayoutManager(getContext(), 3);
        linearLayout.setOrientation(GridLayoutManager.VERTICAL);
        recyclerViewPic.setLayoutManager(linearLayout);

        picAdapter = new EssayPicAdapter(getContext());
        picAdapter.setDataList(Utils.arrayToList(arr));
        picAdapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //TODO 查看大图
            }
        });
        recyclerViewPic.setAdapter(picAdapter);
        picAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.iv_icon, R.id.iv_one_pic, R.id.ln_comment, R.id.ln_like, R.id.btn_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_icon:
                break;
            case R.id.iv_one_pic:
                break;
            case R.id.ln_comment:
                startActivity(new Intent(getContext(), AddBookCommentAct.class)
                        .putExtra("tag", 0)
                        .putExtra("bookId", mData.getContentId())
                        .putExtra("commentedId", "")
                        .putExtra("replyName", ""));
                break;
            case R.id.ln_like:
                break;
            case R.id.btn_more:
                startActivity(new Intent(getContext(), MoreCommentAct.class).putExtra("bookId", mData.getContentId())
                        .putExtra("tag", 1));
                break;
        }
    }

    private void addCollect() {
        LoadDialog.show(getContext());
        Map<String, Object> map = new HashMap<>();
        map.put("contentId", mData.getContentId());
        map.put("accessToken", MApplication.getAccessToken());
        NRClient.collectEssay(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                Toaster.toast(getContext(), "收藏成功！");
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_jubao_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    PopupWindow mPop;

    private void setPopView() {
        if (mPop != null)
            mPop.dismiss();
        LayoutInflater inflater = LayoutInflater.from(this);
        final View v = inflater.inflate(R.layout.pop_function_new, null);

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
            final ImageView view = v.findViewById(R.id.iv_collect);
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    String state = MApplication.getAuthorisedState(getContext());
                    if (state.equals(MApplication.AUTH_INITIAL)) {
                        showPopView(view, "分享赠送陌生人\n实名认证更安全");
                    } else if (state.equals(MApplication.AUTH_FAIL)) {
                        showPopView(view, "您的实名认证失败，无法操作，是否重新提交？");
                    } else if (state.equals(MApplication.AUTH_SUCCESS)) {
                        addCollect();
                    } else if (state.equals(MApplication.AUTH_GOING)) {
                        Toaster.toastLong(getContext(), "您尚未通过实名认证,请等待认证完成再操作！");
                    }
                }
            });
            v.findViewById(R.id.iv_add_comment).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
//                    showInputDialog(0, "");
                    String state = MApplication.getAuthorisedState(getContext());
                    if (state.equals(MApplication.AUTH_INITIAL)) {
                        showPopView(view, "分享赠送陌生人\n实名认证更安全");
                    } else if (state.equals(MApplication.AUTH_FAIL)) {
                        showPopView(view, "您的实名认证失败，无法操作，是否重新提交？");
                    } else if (state.equals(MApplication.AUTH_SUCCESS)) {
                        startActivity(new Intent(getContext(), AddBookCommentAct.class)
                                .putExtra("tag", 0)
                                .putExtra("bookId", mData.getContentId())
                                .putExtra("commentedId", "")
                                .putExtra("replyName", ""));
                    } else if (state.equals(MApplication.AUTH_GOING)) {
                        Toaster.toastLong(getContext(), "您尚未通过实名认证,请等待认证完成再操作！");
                    }
                }
            });
        }
    }

    private void showPop() {
        if (mPop != null && !mPop.isShowing()) {
            mPop.dismiss();
            mPop.showAtLocation(lnComment, Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
        }
    }

}
