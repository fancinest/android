package com.narancommunity.app.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.BookYSHYCommentAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.entity.CommentEntity;
import com.narancommunity.app.interfaces.CommentInterfaces;

import java.util.ArrayList;
import java.util.List;

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

    BookYSHYCommentAdapter adapter;
    List<CommentEntity> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_act_book_yshy);
        ButterKnife.bind(this);
        toolbar.setTitle("以书会友");
        setBar(toolbar);

        setData();
        setView();
        setPopView();
    }

    private void setView() {
        final LinearLayoutManager lm_latest = new LinearLayoutManager(getContext());
        lm_latest.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new BookYSHYCommentAdapter(getContext(), list);
        recyclerViewComment.setLayoutManager(lm_latest);
        recyclerViewComment.setAdapter(adapter);
        adapter.setListener(new CommentInterfaces() {
            @Override
            public void OnItemClick(int position) {

            }

            @Override
            public void OnAnswer(int id, String name) {

            }
        });

        recyclerViewComment.setHasFixedSize(true);
        recyclerViewComment.setNestedScrollingEnabled(false);

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

    private void setData() {
        CommentEntity bookComment;
        for (int i = 0; i < 10; i++) {
            bookComment = new CommentEntity();
//            bookComment.setName("李爽");
//            bookComment.setContent("那一日正当三月中浣，早饭后，宝玉携了一套《会真记》，走到沁芳闸桥边桃花底下一块石上坐着，展开《会真记》，从头细玩。正看到“落红成阵”，只…");
//            bookComment.setCount(46 + "");
//            bookComment.setLikes(101 + "");
//            bookComment.setUrl("http://img5.imgtn.bdimg.com/it/u=1294306278,3771827871&fm=27&gp=0.jpg");
            bookComment.setCreateTime("2018-3-10 13:20:20");
            list.add(bookComment);
        }
    }

    @OnClick({R.id.iv_icon, R.id.iv_one_pic, R.id.ln_comment, R.id.ln_like})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_icon:
                break;
            case R.id.iv_one_pic:
                break;
            case R.id.ln_comment:
                startActivity(new Intent(getContext(), AddBookCommentAct.class)
                        .putExtra("tag", 0));
                break;
            case R.id.ln_like:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_jubao_share, menu);
        return super.onCreateOptionsMenu(menu);
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

    private void showPop() {
        if (mPop != null && !mPop.isShowing()) {
            mPop.showAtLocation(lnComment, Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
        }
    }

}
