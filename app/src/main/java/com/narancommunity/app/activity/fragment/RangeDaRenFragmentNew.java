package com.narancommunity.app.activity.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.RangeListRangeAdapter;
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
 * FileName : 公益达人
 */

public class RangeDaRenFragmentNew extends Fragment {

    @BindView(R.id.recyclerView)
    XRecyclerView recyclerView;
    @BindView(R.id.rb_day)
    RadioButton rbDay;
    @BindView(R.id.tab_rdo_grp)
    RadioGroup tabRdoGrp;
    //    @BindView(R.id.swipe_refresh)
//    SwipeRefreshLayout swipeRefreshLayout;
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
    @BindView(R.id.ln_me)
    LinearLayout lnMe;

    int pageSize = 20;
    int pageNum = 1;

    RangeListRangeAdapter adapter;
    List<RankEntity> listData = new ArrayList<>();//全部要传值给他
    private int TOTAL_PAGE = 1;
    int type = 0;

    public static RangeDaRenFragmentNew newInstance() {

        RangeDaRenFragmentNew fragment = new RangeDaRenFragmentNew();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart("BookSortSonFragment");
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("BookSortSonFragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_range_daren, container, false);
            ButterKnife.bind(this, rootView);

            setListView();
            getData();
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

    private void getData() {
        switch (type) {
            case 0:
                getDayData();
                break;
            case 1:
                getWeekData();
                break;
            case 2:
                getMonthData();
                break;
            case 3:
                getAllData();
                break;
        }
    }

    private void getDayData() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        NRClient.getDarenDayList(map, new ResultCallback<Result<GradeData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
                recyclerView.loadMoreComplete();
                recyclerView.refreshComplete();
            }

            @Override
            public void onSuccess(Result<GradeData> result) {
                LoadDialog.dismiss(getContext());
                setData(result.getData());
            }
        });
    }

    private void getWeekData() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        NRClient.getDarenWeekList(map, new ResultCallback<Result<GradeData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
                recyclerView.loadMoreComplete();
                recyclerView.refreshComplete();
            }

            @Override
            public void onSuccess(Result<GradeData> result) {
                LoadDialog.dismiss(getContext());
                setData(result.getData());
            }
        });
    }

    private void getMonthData() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        NRClient.getDarenMonthList(map, new ResultCallback<Result<GradeData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
                recyclerView.loadMoreComplete();
                recyclerView.refreshComplete();
            }

            @Override
            public void onSuccess(Result<GradeData> result) {
                LoadDialog.dismiss(getContext());
                setData(result.getData());
            }
        });
    }

    private void getAllData() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        NRClient.getDarenAllList(map, new ResultCallback<Result<GradeData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
                recyclerView.loadMoreComplete();
                recyclerView.refreshComplete();
            }

            @Override
            public void onSuccess(Result<GradeData> result) {
                LoadDialog.dismiss(getContext());
                setData(result.getData());
            }
        });
    }

    private void setData(GradeData data) {
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
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
    }

    private void setMyData(RankEntity myRank) {
        if (myRank == null) {
            lnMe.setVisibility(View.GONE);
            return;
        } else lnMe.setVisibility(View.VISIBLE);
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
        tvTimes.setText("" + Utils.getValue(myRank.getRankScore()));

    }

    private void setListView() {
        final LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayout);
        adapter = new RangeListRangeAdapter(getContext(), listData, 0);
        recyclerView.setAdapter(adapter);

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
