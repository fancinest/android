package com.narancommunity.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.AssistantAdapter;
import com.narancommunity.app.adapter.WeekRecAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.ItemDecoration.DividerItemDecoration;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.AssistantEntity;
import com.narancommunity.app.entity.AssistantMissionEntity;
import com.narancommunity.app.entity.WeekEntity;
import com.narancommunity.app.entity.WeekRecEntity;
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
 * Writer：fancy on 2017/12/26 14:19
 * Email：120760202@qq.com
 * FileName : 首页发现下方四个标签
 */

public class FindFourAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public WeekRecAdapter mWeekAdapter;
    public AssistantAdapter mAssistantAdapter;

    List<AssistantMissionEntity> listAssistantData = new ArrayList<>();
    List<WeekRecEntity> listRecData = new ArrayList<>();

    int tag;
    int pageNum = 1;
    int pageSize = 10;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_find_four);
        ButterKnife.bind(this);

        setBar(toolbar);
        tag = getIntent().getIntExtra("tag", 0);
        toolbar.setTitle(getMyTitle(tag));

        setView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        Log.i("fancy", "当前位置：" + current_position);
        LoadDialog.show(getContext(), "数据加载中...");
        if (tag == 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("accessToken", MApplication.getAccessToken(getContext()));
            map.put("pageNum", pageNum);
            map.put("pageSize", pageSize);
            NRClient.getWeekRecList(map, new ResultCallback<Result<WeekEntity>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    Utils.showErrorToast(getContext(), throwable);
                }

                @Override
                public void onSuccess(Result<WeekEntity> result) {
                    setRecListData(result.getData());
                    LoadDialog.dismiss(getContext());
                }
            });
        } else if (tag == 1) {
            Map<String, Object> map = new HashMap<>();
            map.put("accessToken", MApplication.getAccessToken(getContext()));
            map.put("pageNum", pageNum);
            map.put("pageSize", pageSize);
            NRClient.getAssistantList(map, new ResultCallback<Result<AssistantEntity>>() {
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
        } else if (tag == 2) {
            Map<String, Object> map = new HashMap<>();
            map.put("accessToken", MApplication.getAccessToken(getContext()));
            map.put("pageNum", pageNum);
            map.put("pageSize", pageSize);
            NRClient.getSubjectReportList(map, new ResultCallback<Result<WeekEntity>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    Utils.showErrorToast(getContext(), throwable);
                }

                @Override
                public void onSuccess(Result<WeekEntity> result) {
                    setReportListData(result.getData());
                    LoadDialog.dismiss(getContext());
                }
            });
        }
    }

    private void setReportListData(WeekEntity data) {
        if (data != null && data.getContents().size() > 0) {
            TOTAL_PAGE = data.getTotalPageNum();
            listRecData.addAll(data.getContents());
            mWeekAdapter.setList(listRecData);
            mWeekAdapter.notifyDataSetChanged();
        }
    }

    private void setRecListData(WeekEntity data) {
        if (data == null)
            return;
        if (data != null && data.getContents().size() > 0) {
            TOTAL_PAGE = data.getTotalPageNum();
            listRecData.addAll(data.getContents());
            mWeekAdapter.setList(listRecData);
            mWeekAdapter.notifyDataSetChanged();
        }
    }

    private void setAssistantListData(AssistantEntity data) {
        if (data == null)
            return;
        if (data != null && data.getActivitys().size() > 0) {
            TOTAL_PAGE = data.getTotalPageNum();
            listAssistantData.addAll(data.getActivitys());
            mAssistantAdapter.setList(listAssistantData);
            mAssistantAdapter.notifyDataSetChanged();
        }
    }


    int current_position = 1;
    int TOTAL_PAGE = 1;

    private void setView() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
//                DividerItemDecoration divider = new DividerItemDecoration.Builder(getContext())
//                        .setHeight(R.dimen.default_divider_height)
//                        .setPadding(R.dimen.default_divider_padding)
//                        .setColorResource(R.color.color_eeeeee)
//                        .build();
        recyclerView.addItemDecoration(dividerItemDecoration);

        final LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayout);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("fancy", "滑动时候的position：" + current_position);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItemPosition = linearLayout.findLastVisibleItemPosition();
                    int myCount = 0;
                    switch (tag) {
                        case 0:
                            myCount = mWeekAdapter.getItemCount();
                            break;
                        case 1:
                            myCount = mAssistantAdapter.getItemCount();
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                    }
                    if (lastVisibleItemPosition + 1 == myCount) {
                        if (current_position < TOTAL_PAGE) {
                            current_position++;
                            getData();
                        } else
                            Toaster.toast(getContext(), "已无更多数据");
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        switch (tag) {
            case 0:
                mWeekAdapter = new WeekRecAdapter(getContext(), listRecData, 0);
                mWeekAdapter.setListener(new MeItemInterface() {
                    @Override
                    public void OnItemClick(int position) {
                        startActivity(new Intent(getContext(), RecommDetailAct.class)
                                .putExtra("data", listRecData.get(position)));
                    }

                    @Override
                    public void OnDelClick(int position) {

                    }

                });
                recyclerView.setAdapter(mWeekAdapter);
                break;
            case 1:
                mAssistantAdapter = new AssistantAdapter(getContext(), listAssistantData);
                mAssistantAdapter.setListener(new MeItemInterface() {
                    @Override
                    public void OnItemClick(int position) {
                        startActivity(new Intent(getContext(), AssistantDetailAct.class));
                    }

                    @Override
                    public void OnDelClick(int position) {

                    }
                });
                recyclerView.setAdapter(mAssistantAdapter);
                break;
            case 2:
                mWeekAdapter = new WeekRecAdapter(getContext(), listRecData, 2);
                mWeekAdapter.setListener(new MeItemInterface() {
                    @Override
                    public void OnItemClick(int position) {
                        Toaster.toast(getContext(), "准备跳转");
                    }

                    @Override
                    public void OnDelClick(int position) {

                    }

                });
                recyclerView.setAdapter(mWeekAdapter);
                break;
            case 3:
                break;
        }
    }

    private String getMyTitle(int tag) {
        switch (tag) {
            case 0:
                return "每周推荐";
            case 1:
                return "援助任务";
            case 2:
                return "专题报道";
            case 3:
                return "排行榜";
            default:
                return "";
        }
    }
}
