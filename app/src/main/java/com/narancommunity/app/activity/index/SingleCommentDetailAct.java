package com.narancommunity.app.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.CommentSonAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.CommentDetail;
import com.narancommunity.app.entity.CommentEntity;
import com.narancommunity.app.entity.Comments;
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
 * Writer：fancy on 2018/4/23 11:49
 * Email：120760202@qq.com
 * FileName : 单条评论的详细(子评论翻页的)
 */

public class SingleCommentDetailAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.iv_icon)
    SelectableRoundedImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    int commentId = 0;
    int bookId = 0;
    CommentEntity data;
    int pageSize = 10;
    int pageNum = 1;
    private int TOTAL_PAGE = 1;
    List<Comments> list = new ArrayList<>();
    CommentSonAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_single_comment_detail);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("查看全部回复");

        commentId = getIntent().getIntExtra("commentId", 0);
        bookId = getIntent().getIntExtra("bookId", 0);
        data = (CommentEntity) getIntent().getSerializableExtra("data");
        setView();
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
        map.put("commentedId", commentId);
        map.put("pageSize", pageSize);
        map.put("pageNum", pageNum);
        map.put("accessToken", MApplication.getAccessToken());
        NRClient.getCommentDetail(map, new ResultCallback<Result<CommentDetail>>() {
            @Override
            public void onSuccess(Result<CommentDetail> result) {
                LoadDialog.dismiss(getContext());
                setData(result.getData());
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }
        });
    }

    private void setData(CommentDetail data) {
        if (pageNum == 1)
            list.clear();
        TOTAL_PAGE = data.getRecords().getTotalPageNum();
        if (data != null && data.getRecords() != null) {
            list.addAll(data.getRecords().getComments());
            pageNum++;
        }
        adapter.notifyDataSetChanged();
    }

    private void setView() {
        tvComment.setText(Utils.getValue(data.getCommentContent()) + "");
        tvName.setText(Utils.getValue(data.getInitiatorNike()) + "");
        String url = data.getInitiatorImg();
        if (!url.equals(""))
            Utils.setImgF(getContext(), url, ivIcon);
        int commentCount = 0;
        if (data.getRecords() != null && data.getRecords().getTotalCount() > 0)
            commentCount = Utils.getValue(data.getRecords().getTotalCount());
        tvCommentCount.setText(commentCount == 0 ? "0" : (commentCount + ""));
        int likeCount = Utils.getValue(data.getLikeTimes());
        tvLike.setText(likeCount == 0 ? "0" : (likeCount + ""));

        adapter = new CommentSonAdapter(getContext(), list);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter.setListener(new CommentInterfaces() {
            @Override
            public void OnItemClick(int position) {

            }

            @Override
            public void OnAnswer(int id, String name) {
                startActivity(new Intent(getContext(), AddBookCommentAct.class)
                        .putExtra("tag", 1).putExtra("bookId", bookId)
                        .putExtra("commentedId", id)
                        .putExtra("replyName", name));
            }
        });
        adapter.setLimited(false);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                        if (pageNum <= TOTAL_PAGE) {
                            getData();
                        } else
                            Toaster.toast(getContext(), "已无更多数据");
                    }
                }
            }
        });
    }

    @OnClick({R.id.tv_comment_count, R.id.tv_like, R.id.tv_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_comment_count:
                break;
            case R.id.tv_like:
                break;
            case R.id.tv_comment:
                break;
        }
    }
}
