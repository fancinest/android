package com.narancommunity.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.BookCommentAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.entity.BookComment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/4/12 15:33
 * Email：120760202@qq.com
 * FileName :
 */

public class BookCommentAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_release)
    ImageView ivRelease;

    BookCommentAdapter adapter;
    List<BookComment> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_book_comment);
        ButterKnife.bind(this);
        toolbar.setTitle("书评");
        setBar(toolbar);

        setData();
        setView();
    }

    private void setView() {
        final LinearLayoutManager lm_latest = new LinearLayoutManager(getContext());
        lm_latest.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new BookCommentAdapter(getContext(), list);
        recyclerView.setLayoutManager(lm_latest);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                //TODO 评论
            }

            @Override
            public void OnDelClick(int position) {
                //TODO 点赞
            }
        });
    }

    private void setData() {
        BookComment bookComment;
        for (int i = 0; i < 20; i++) {
            bookComment = new BookComment();
            bookComment.setName("李爽");
            bookComment.setContent("那一日正当三月中浣，早饭后，宝玉携了一套《会真记》，走到沁芳闸桥边桃花底下一块石上坐着，展开《会真记》，从头细玩。正看到“落红成阵”，只…");
            bookComment.setCount(46 + "");
            bookComment.setLikes(101 + "");
            bookComment.setUrl("http://img5.imgtn.bdimg.com/it/u=1294306278,3771827871&fm=27&gp=0.jpg");
            bookComment.setCreateTime("2018-3-10 13:20:20");
            list.add(bookComment);
        }
    }

    @OnClick(R.id.iv_release)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), AddBookCommentAct.class).putExtra("tag", 2));
    }
}
