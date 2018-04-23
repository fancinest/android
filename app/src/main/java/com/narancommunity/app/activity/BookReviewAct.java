package com.narancommunity.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.BookCommentCommentAdapter;
import com.narancommunity.app.adapter.BookReviewAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.BookComment;
import com.narancommunity.app.entity.BookCommentData;
import com.narancommunity.app.entity.BookRelativeRecData;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/4/12 15:33
 * Email：120760202@qq.com
 * FileName : 书评列表
 */
public class BookReviewAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_release)
    ImageView ivRelease;

    BookReviewAdapter adapter;
    List<BookComment> list = new ArrayList<>();
    int bookId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_book_comment);
        ButterKnife.bind(this);
        toolbar.setTitle("书评");
        setBar(toolbar);

        bookId = getIntent().getIntExtra("bookId", 0);

        setView();
        getData();
    }

    private void getData() {
        LoadDialog.show(getContext(),"数据加载中...");
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", bookId);
        map.put("accessToken", MApplication.getAccessToken());
        NRClient.getBookCommentCommentList(map, new ResultCallback<Result<BookCommentData>>() {
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
        if (data != null && data.getReviews() != null) {
            list.addAll(data.getReviews());
            adapter.notifyDataSetChanged();
        } else {
            Toaster.toast(getContext(), "暂无任何评论");
        }
    }

    private void setView() {
        final LinearLayoutManager lm_latest = new LinearLayoutManager(getContext());
        lm_latest.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new BookReviewAdapter(getContext(), list);
        recyclerView.setLayoutManager(lm_latest);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                //TODO 点赞
//                喜欢
            }

            @Override
            public void OnDelClick(int position) {
            }
        });
    }


    @OnClick(R.id.iv_release)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), AddBookCommentAct.class).putExtra("tag", 2)
                .putExtra("bookId", bookId));
    }
}
