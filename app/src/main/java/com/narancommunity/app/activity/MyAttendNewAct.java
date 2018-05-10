package com.narancommunity.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.flyco.tablayout.listener.OnTabSelectListener;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.AssistantAdapter;
import com.narancommunity.app.adapter.CommonwealActivityAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.AssistantEntity;
import com.narancommunity.app.entity.AssistantMissionEntity;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2018/5/3 10:51
 * Email：120760202@qq.com
 * FileName :
 */

public class MyAttendNewAct extends BaseActivity {

    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    AssistantAdapter adapter;
    private int TOTAL_PAGE = 1;
    int pageSize = 5;
    int pageNum = 1;

    List<AssistantMissionEntity> listData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_normal_list_view_with_swipe_refresh);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("我的参与");
        setView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAssistantData();
    }

    private void getAssistantData() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        NRClient.getMyAttendAssistantList(map, new ResultCallback<Result<AssistantEntity>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<AssistantEntity> result) {
                setAssistantListData(result.getData());
                LoadDialog.dismiss(getContext());
            }
        });
    }

    private void setAssistantListData(AssistantEntity data) {
        if (pageNum == 1)
            listData.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getActivitys() != null && data.getActivitys().size() > 0) {
            listData.addAll(data.getActivitys());
            adapter.setList(listData);
            pageNum++;
        }
        adapter.notifyDataSetChanged();
    }

    private void setView() {
        final LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItemPosition = linearLayout.findLastVisibleItemPosition();
                    Log.i("fancy", "最后的可见位置:" + lastVisibleItemPosition);
                    int myCount = adapter.getItemCount();
                    if (lastVisibleItemPosition + 1 == myCount) {
                        if (pageNum <= TOTAL_PAGE) {
                            getAssistantData();
                        } else
                            Toaster.toast(getContext(), "已无更多数据");
                    }
                }
            }
        });

        adapter = new AssistantAdapter(getContext(), listData);
        adapter.setListener(new com.narancommunity.app.MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                startActivity(new Intent(getContext(), AssistantDetailAct.class));
            }

            @Override
            public void OnDelClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        pageNum = 1;
                        getAssistantData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

}
