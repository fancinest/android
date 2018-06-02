package com.narancommunity.app.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.activity.general.HtmlFiveAct;
import com.narancommunity.app.adapter.AssistantAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.AssistantEntity;
import com.narancommunity.app.entity.AssistantMissionEntity;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.NRConfig;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
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
    XRecyclerView recyclerView;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;

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
        MobclickAgent.onResume(this);
        getAssistantData();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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
                recyclerView.loadMoreComplete();
                recyclerView.refreshComplete();
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
            tvNoData.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
    }

    private void setView() {
        final LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayout);
        adapter = new AssistantAdapter(getContext(), listData);
        adapter.setListener(new com.narancommunity.app.MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                startActivity(new Intent(getContext(), HtmlFiveAct.class)
                        .putExtra("url", NRConfig.HTML_AIXIN_WORK +
                                "?accessToken=" + MApplication.getAccessToken()
                                + "&activityId=" + listData.get(position).getActivityId())
                        .putExtra("title", "爱心行动"));
            }

            @Override
            public void OnDelClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                getAssistantData();
            }

            @Override
            public void onLoadMore() {
                if (pageNum <= TOTAL_PAGE) {
                    getAssistantData();
                } else {
                    recyclerView.loadMoreComplete();
                    recyclerView.refreshComplete();
                    Toaster.toast(getContext(), "已无更多数据");
                }
            }
        });
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
