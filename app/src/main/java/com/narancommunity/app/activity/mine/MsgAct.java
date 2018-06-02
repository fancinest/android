package com.narancommunity.app.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.adapter.MsgAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.MsgData;
import com.narancommunity.app.entity.MsgEntity;
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

/**
 * Writer：fancy on 2018/2/28 16:52
 * Email：120760202@qq.com
 * FileName : 系统消息界面
 */

public class MsgAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.recyclerView)
    XRecyclerView recyclerView;
    //    @BindView(R.id.swipe_refresh)
//    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    public MsgAdapter adapter;

    List<MsgEntity> listData = new ArrayList<>();

    int pageNum = 1;
    int pageSize = 10;

    int TOTAL_PAGE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_msg);
        ButterKnife.bind(this);

        setBar(toolbar);
        toolbar.setTitle("系统消息");
        setView();
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void getData() {
        LoadDialog.show(getContext(), "数据加载中...");
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        NRClient.getMsgList(map, new ResultCallback<Result<MsgData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
                recyclerView.loadMoreComplete();
                recyclerView.refreshComplete();
            }

            @Override
            public void onSuccess(Result<MsgData> result) {
                setData(result.getData());
                LoadDialog.dismiss(getContext());
            }
        });
    }

    private void setData(MsgData data) {
        if (pageNum == 1)
            listData.clear();
        if (data != null && data.getNewss() != null && data.getNewss().size() > 0) {
            listData.addAll(data.getNewss());
            pageNum++;
            tvNoData.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }
        adapter.setDataList(listData);
        adapter.notifyDataSetChanged();
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
    }

    private void setView() {
        final LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayout);
        adapter = new MsgAdapter(getContext());
        adapter.setDataList(listData);
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
    protected void onDestroy() {
        super.onDestroy();
        if (recyclerView != null) {
            recyclerView.destroy(); // this will totally release XR's memory
            recyclerView = null;
        }
    }
}
