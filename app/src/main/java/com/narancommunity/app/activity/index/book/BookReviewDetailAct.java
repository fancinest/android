package com.narancommunity.app.activity.index.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.BookReviewCommentAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.ObservableScrollView;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.AnswerComment;
import com.narancommunity.app.entity.BookComment;
import com.narancommunity.app.entity.Comments;
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
 * Writer：fancy on 2018/4/12 17:18
 * Email：120760202@qq.com
 * FileName :
 */

public class BookReviewDetailAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.iv_icon)
    SelectableRoundedImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
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
    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;

    BookReviewCommentAdapter adapter;
    List<Comments> list = new ArrayList<>();
    BookComment data;
    int bookId = 0;
    int pageSize = 5;
    int pageNum = 1;
    private int TOTAL_PAGE = 1;
    int total_count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_book_comment_detail);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("书评");

        data = (BookComment) getIntent().getSerializableExtra("data");
        bookId = data.getBookId();

        setView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", pageSize);
        map.put("pageNum", pageNum);
        map.put("reviewId", data.getReviewId());
        map.put("accessToken", MApplication.getAccessToken());
        NRClient.getBookReviewCommentList(map, new ResultCallback<Result<AnswerComment>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<AnswerComment> result) {
                LoadDialog.dismiss(getContext());
                setComment(result.getData());
            }
        });
    }

    private void setComment(AnswerComment data) {
        if (pageNum == 1)
            list.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getComments() != null && data.getComments().size() > 0) {
            total_count = data.getTotalCount();
            list.addAll(data.getComments());
            pageNum++;
        } else {
            Toaster.toast(getContext(), "暂无任何评论");
        }
        setCount();
        adapter.notifyDataSetChanged();
    }

    private void setCount() {
        tvComment.setText(total_count + "");
        tvCommentCount.setText("评论数(" + total_count + ")");
    }

    private void setView() {
        tvContent.setText(Utils.getValue(data.getContent()) + "");
        tvName.setText(Utils.getValue(data.getAuthor()) + "");
        String url = Utils.getValue(data.getAuthorImg());
        if (!"".equals(url))
            Utils.setImgF(getContext(), url, ivIcon);
        else
            Utils.setImgF(getContext(), R.mipmap.zw_morentouxiang, ivIcon);

        recyclerView.setHasFixedSize(true);
//        recyclerView.setNestedScrollingEnabled(false);
        tvLike.setText(Utils.getValue(data.getLikeTimes()) + "");
        tvDate.setText(Utils.dateDiff(Utils.stringTimeToMillion(Utils.getValue(data.getCreateTime()))) + "");

        final LinearLayoutManager lm_latest = new LinearLayoutManager(getContext());
//        lm_latest.setSmoothScrollbarEnabled(true);
//        lm_latest.setAutoMeasureEnabled(true);
        lm_latest.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new BookReviewCommentAdapter(getContext(), list);
        recyclerView.setLayoutManager(lm_latest);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {

            }

            @Override
            public void OnDelClick(int position) {

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItemPosition = lm_latest.findLastCompletelyVisibleItemPosition();
                    Log.i("fancy", "最后的可见位置:" + lastVisibleItemPosition);

                    if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                        if (pageNum <= TOTAL_PAGE) {
                            getData();
                        } else {
                            //这个地方很恶心，不知道为什么没办法直接判断它是否为最后一条，往上拉也是最后一条，很离谱
                            View v = lm_latest.findViewByPosition(list.size() - 1);
                            boolean is = !isViewCovered(v);
                            if (is)
                                Toaster.toast(getContext(), "已无更多数据");
                        }
                    }
                }

            }
        });
    }

    @OnClick({R.id.iv_icon, R.id.ln_comment, R.id.ln_like})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_icon:
                break;
            case R.id.ln_comment:
                startActivity(new Intent(getContext(), AddBookCommentAct.class)
                        .putExtra("bookId", bookId).putExtra("tag", 3)
                        .putExtra("reviewId", data.getReviewId()));
                break;
            case R.id.ln_like:
                break;
        }
    }
}
