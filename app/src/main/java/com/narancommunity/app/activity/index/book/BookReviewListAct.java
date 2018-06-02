package com.narancommunity.app.activity.index.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.adapter.BookReviewAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.BookComment;
import com.narancommunity.app.entity.BookCommentData;
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
 * Writer：fancy on 2018/4/12 15:33
 * Email：120760202@qq.com
 * FileName : 书评列表
 */
public class BookReviewListAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.recyclerView)
    XRecyclerView recyclerView;
    @BindView(R.id.iv_release)
    ImageView ivRelease;
    //    @BindView(R.id.swipe_refresh)
//    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    BookReviewAdapter adapter;
    List<BookComment> list = new ArrayList<>();

    int bookId;
    int pageSize = 5;
    int pageNum = 1;
    private int TOTAL_PAGE = 1;

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
        LoadDialog.show(getContext(), "数据加载中...");
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", bookId);
        map.put("accessToken", MApplication.getAccessToken());
        map.put("pageSize", pageSize);
        map.put("pageNum", pageNum);
        NRClient.getBookReviewList(map, new ResultCallback<Result<BookCommentData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
                recyclerView.loadMoreComplete();
                recyclerView.refreshComplete();
            }

            @Override
            public void onSuccess(Result<BookCommentData> result) {
                LoadDialog.dismiss(getContext());
                setBookComment(result.getData());
            }
        });
    }

    private void setBookComment(BookCommentData data) {
        if (pageNum == 1)
            list.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getReviews() != null && data.getReviews().size() > 0) {
            list.addAll(data.getReviews());
            pageNum++;
            tvNoData.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
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
            }

            @Override
            public void OnDelClick(int position) {
                //跳转到新页面
                startActivity(new Intent(getContext(), BookReviewDetailAct.class)
                        .putExtra("data", list.get(position)).putExtra("bookId", bookId));
            }
        });
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                getData();
            }

            @Override
            public void onLoadMore() {
                if (pageNum <= TOTAL_PAGE) {
                    getData();
                } else {
                    recyclerView.loadMoreComplete();
                    recyclerView.refreshComplete();
                    Toaster.toast(getContext(), "已无更多数据");
                }
            }
        });
    }


    @OnClick(R.id.iv_release)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), AddBookCommentAct.class).putExtra("tag", 2)
                .putExtra("bookId", bookId));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recyclerView != null) {
            recyclerView.destroy(); // this will totally release XR's memory
            recyclerView = null;
        }
    }
}
