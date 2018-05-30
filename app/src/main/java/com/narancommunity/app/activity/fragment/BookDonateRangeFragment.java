package com.narancommunity.app.activity.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.BookRangeAdapter;
import com.narancommunity.app.common.ItemDecoration.DividerItemDecoration;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.GradeData;
import com.narancommunity.app.entity.RankEntity;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2017/12/27 11:49
 * Email：120760202@qq.com
 * FileName : 书籍赠书榜
 */

public class BookDonateRangeFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_alien)
    TextView tvAlien;
    @BindView(R.id.iv_level)
    ImageView ivLevel;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.iv_icon)
    SelectableRoundedImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_grades)
    TextView tvGrades;
    @BindView(R.id.rb_star)
    RatingBar rbStar;
    @BindView(R.id.tv_times)
    TextView tvTimes;

    List<RankEntity> listData = new ArrayList<>();
    BookRangeAdapter adapter;

    int pageSize = 5;
    int pageNum = 1;
    private int TOTAL_PAGE = 1;
    int tag = 0;

    public static BookDonateRangeFragment newInstance() {

        BookDonateRangeFragment fragment = new BookDonateRangeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_book_range, container, false);
            ButterKnife.bind(this, rootView);

            setListView();
            return rootView;
        } else {
            //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            return rootView;
        }
    }


    public void onResume() {
        super.onResume();
        getData();
//        MobclickAgent.onPageStart("BookDonateRangeFragment");
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("BookDonateRangeFragment");
    }

    private void getData() {
        if (tag == 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("accessToken", MApplication.getAccessToken());
            map.put("pageNum", pageNum);
            map.put("pageSize", pageSize);
            NRClient.getRankBookDonateList(map, new ResultCallback<Result<GradeData>>() {
                @Override
                public void onSuccess(Result<GradeData> result) {
                    LoadDialog.dismiss(getContext());
                    GradeData data = result.getData();
                    if (data != null)
                        setDataView(data);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    Utils.showErrorToast(getContext(), throwable);
                }
            });
        } else if (tag == 1) {
            Map<String, Object> map = new HashMap<>();
            map.put("accessToken", MApplication.getAccessToken());
            map.put("pageNum", pageNum);
            map.put("pageSize", pageSize);
            NRClient.getRankBookReadList(map, new ResultCallback<Result<GradeData>>() {
                @Override
                public void onSuccess(Result<GradeData> result) {
                    LoadDialog.dismiss(getContext());
                    GradeData data = result.getData();
                    if (data != null)
                        setDataView(data);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    Utils.showErrorToast(getContext(), throwable);
                }
            });
        } else if (tag == 2) {
            Map<String, Object> map = new HashMap<>();
            map.put("accessToken", MApplication.getAccessToken());
            map.put("pageNum", pageNum);
            map.put("pageSize", pageSize);
            NRClient.getRankBookReviewList(map, new ResultCallback<Result<GradeData>>() {
                @Override
                public void onSuccess(Result<GradeData> result) {
                    LoadDialog.dismiss(getContext());
                    GradeData data = result.getData();
                    if (data != null)
                        setDataView(data);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    Utils.showErrorToast(getContext(), throwable);
                }
            });
        }
    }

    private void setDataView(GradeData data) {
        if (pageNum == 1) {
            listData.clear();
        }
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getRanks() != null && data.getRanks().size() > 0) {
            listData.addAll(data.getRanks());
            adapter.setList(listData);
            pageNum++;
        }
        adapter.notifyDataSetChanged();
        setMyData(data.getMyRank());
    }

    private void setMyData(RankEntity myRank) {
        if (myRank == null)
            return;
        int rankNum = Utils.getValue(myRank.getRankNum());
        if (rankNum > 2) {
            tvLevel.setText("" + rankNum);
            ivLevel.setVisibility(View.GONE);
            tvLevel.setVisibility(View.VISIBLE);
        } else {
            ivLevel.setVisibility(View.VISIBLE);
            tvLevel.setVisibility(View.GONE);
        }

        String url = Utils.getValue(myRank.getAccountImg());
        if (!"".equals(url))
            Utils.setImgF(getContext(), url, ivIcon);
        else Utils.setImgF(getContext(), R.mipmap.zw_morentouxiang, ivIcon);

        tvName.setText("" + Utils.getValue(myRank.getAccountName()));
        tvRemark.setText("" + Utils.getValue(myRank.getAccountRemark()));
        tvGrades.setVisibility(View.GONE);
        rbStar.setVisibility(View.GONE);
        tvTimes.setVisibility(View.GONE);
    }

    private void setListView() {
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        final LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayout);

        adapter = new BookRangeAdapter(getContext(), listData, 0);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItemPosition = linearLayout.findLastVisibleItemPosition();
                    Log.i("fancy", "最后的可见位置:" + lastVisibleItemPosition);
                    if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                        if (pageNum <= TOTAL_PAGE) {
                            getData();
                        } else {
                            //这个地方很恶心，不知道为什么没办法直接判断它是否为最后一条，往上拉也是最后一条，很离谱
                            View v = linearLayout.findViewByPosition(listData.size() - 1);
                            boolean is = !isViewCovered(v);
                            if (is)
                                Toaster.toast(getContext(), "已无更多数据");
                        }
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        pageNum = 1;
                        getData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    public int indexOfViewInParent(View view, ViewGroup parent) {
        int index;
        for (index = 0; index < parent.getChildCount(); index++) {
            if (parent.getChildAt(index) == view)
                break;
        }
        return index;
    }
}
