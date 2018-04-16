package com.narancommunity.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
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
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.Toaster;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/4/16 11:23
 * Email：120760202@qq.com
 * FileName : 社区互助
 */

public class BookHelpDetailAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.iv_icon)
    SelectableRoundedImageView ivIcon;
    @BindView(R.id.tv_grades)
    TextView tvGrades;
    @BindView(R.id.rb_star)
    RatingBar rbStar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.recyclerView_pic)
    RecyclerView recyclerViewPic;
    @BindView(R.id.iv_one_pic)
    ImageView ivOnePic;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_times)
    TextView tvTimes;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.ln_collect)
    LinearLayout lnCollect;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.ln_comment)
    LinearLayout lnComment;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.ln_like)
    LinearLayout lnLike;
    @BindView(R.id.tv_orders)
    TextView tvOrders;
    @BindView(R.id.iv_donate_icon)
    SelectableRoundedImageView ivDonateIcon;
    @BindView(R.id.tv_donate_name)
    TextView tvDonateName;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.ln_Orders)
    LinearLayout lnOrders;
    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;
    @BindView(R.id.recyclerView_comment)
    RecyclerView recyclerViewComment;
    @BindView(R.id.btn_help)
    Button btnHelp;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.ln_tool)
    LinearLayout lnTool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_help_detail);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("社区互助");

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

    @OnClick({R.id.iv_one_pic, R.id.ln_collect, R.id.ln_comment, R.id.ln_like, R.id.btn_help, R.id.ln_donater})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_one_pic:
                break;
            case R.id.ln_collect:
                break;
            case R.id.ln_comment:
                startActivity(new Intent(getContext(), AddBookCommentAct.class)
                        .putExtra("tag", 1));
                break;
            case R.id.ln_like:
                break;
            case R.id.btn_help:
                break;
            case R.id.ln_donater:
                break;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_jubao_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showPop() {
        if (mPop != null && !mPop.isShowing()) {
            mPop.showAtLocation(btnHelp, Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
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
}
