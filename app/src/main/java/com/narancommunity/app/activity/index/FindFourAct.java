package com.narancommunity.app.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.activity.general.HtmlFiveAct;
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
 * Writer：fancy on 2017/12/26 14:19
 * Email：120760202@qq.com
 * FileName : 首页发现下方四个标签
 */

public class FindFourAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.recyclerView)
    XRecyclerView recyclerView;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    public WeekRecAdapter mWeekAdapter;
    public AssistantAdapter mAssistantAdapter;

    List<AssistantMissionEntity> listAssistantData = new ArrayList<>();
    List<WeekRecEntity> listRecData = new ArrayList<>();

    int tag;
    int pageNum = 1;
    int pageSize = 10;

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
        MobclickAgent.onResume(getContext());
        getData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
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
                    recyclerView.loadMoreComplete();
                    recyclerView.refreshComplete();
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
                    recyclerView.loadMoreComplete();
                    recyclerView.refreshComplete();
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
                    recyclerView.loadMoreComplete();
                    recyclerView.refreshComplete();
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
        if (pageNum == 1)
            listRecData.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getContents() != null && data.getContents().size() > 0) {
            listRecData.addAll(data.getContents());
            pageNum++;
            tvNoData.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }
        mWeekAdapter.setList(listRecData);
        mWeekAdapter.notifyDataSetChanged();
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
    }

    private void setRecListData(WeekEntity data) {
        if (pageNum == 1)
            listRecData.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getContents() != null && data.getContents().size() > 0) {
            listRecData.addAll(data.getContents());
            pageNum++;
            tvNoData.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }
        mWeekAdapter.setList(listRecData);
        mWeekAdapter.notifyDataSetChanged();
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
    }

    private void setAssistantListData(AssistantEntity data) {
        if (pageNum == 1)
            listAssistantData.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getActivitys() != null && data.getActivitys().size() > 0) {
            listAssistantData.addAll(data.getActivitys());
            pageNum++;
            tvNoData.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }
        mAssistantAdapter.setList(listAssistantData);
        mAssistantAdapter.notifyDataSetChanged();
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
    }


    int current_position = 1;
    int TOTAL_PAGE = 1;

    private void setView() {
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        final LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayout);

        switch (tag) {
            case 0:
                mWeekAdapter = new WeekRecAdapter(getContext(), listRecData, 0);
                mWeekAdapter.setListener(new MeItemInterface() {
                    @Override
                    public void OnItemClick(int position) {
                        startActivity(new Intent(getContext(), HtmlFiveAct.class)
                                .putExtra("url", NRConfig.HTML_WEEK_REC +
                                        "?contentId=" + listRecData.get(position).getContentId()
                                        + "&accessToken=" + MApplication.getAccessToken())
                                .putExtra("title", "每周推荐"));
//                        startActivity(new Intent(getContext(), RecommDetailAct.class)
//                                .putExtra("data", listRecData.get(position)));
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
//                        ?accessToken=f82a5275e546408ba35c75e828af4467&activityid=8
                        startActivity(new Intent(getContext(), HtmlFiveAct.class)
                                .putExtra("url", NRConfig.HTML_AIXIN_WORK +
                                        "?accessToken=" + MApplication.getAccessToken()
                                        + "&activityId=" + listAssistantData.get(position).getActivityId())
                                .putExtra("title", "爱心行动"));
//                        startActivity(new Intent(getContext(), AssistantDetailAct.class));
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
                        startActivity(new Intent(getContext(), HtmlFiveAct.class)
                                .putExtra("url", NRConfig.HTML_REPORT +
                                        "?accessToken=" + MApplication.getAccessToken()
                                        + "&contentId=" + listRecData.get(position).getContentId())
                                .putExtra("title", "专题报道"));
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

    private String getMyTitle(int tag) {
        switch (tag) {
            case 0:
                return "每周推荐";
            case 1:
                return "爱心行动";
            case 2:
                return "专题报道";
            case 3:
                return "排行榜";
            default:
                return "";
        }
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
