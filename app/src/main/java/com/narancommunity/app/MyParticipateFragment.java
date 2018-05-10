package com.narancommunity.app;

import android.content.Intent;
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

import com.narancommunity.app.activity.AssistantDetailAct;
import com.narancommunity.app.adapter.AssistantAdapter;
import com.narancommunity.app.adapter.CommonwealActivityAdapter;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.AssistantEntity;
import com.narancommunity.app.entity.AssistantMissionEntity;
import com.narancommunity.app.entity.MeFunctionEntity;
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
 * Writer：fancy on 2017/12/19 17:18
 * Email：120760202@qq.com
 * FileName : 援助任务，公益活动
 */
public class MyParticipateFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    AssistantAdapter mAssistantAdapter;
    CommonwealActivityAdapter commonwealAdapter;

    List<AssistantMissionEntity> listAssistantData = new ArrayList<>();
    List<MeFunctionEntity> listCommData = new ArrayList<>();

    int pageNum = 1;
    int pageSize = 10;
    int current_position = 1;
    int TOTAL_PAGE = 1;
    int tag;

    public static MyParticipateFragment newInstance() {
        MyParticipateFragment fragment = new MyParticipateFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_me_my_participate, container, false);
            ButterKnife.bind(this, rootView);

            setView();
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
            listAssistantData.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getActivitys() != null && data.getActivitys().size() > 0) {
            listAssistantData.addAll(data.getActivitys());
            mAssistantAdapter.setList(listAssistantData);
            pageNum++;
        }
        mAssistantAdapter.notifyDataSetChanged();
    }

    private void getActivityData() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        NRClient.getMyAttendCommonWealList(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
//                setActivityListData(result.getData());
                LoadDialog.dismiss(getContext());
            }
        });
    }

    private void setActivityListData(AssistantEntity data) {
        if (pageNum == 1)
            listCommData.clear();
        TOTAL_PAGE = data.getTotalPageNum();
//        if (data != null && data.getComments() != null && data.getComments().size() > 0) {
//            listCommData.addAll(data.getComments());
//            pageNum++;
//        }
        commonwealAdapter.notifyDataSetChanged();
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
                    int myCount = 0;
                    switch (tag) {
                        case 0:
                            myCount = mAssistantAdapter.getItemCount();
                            break;
                        case 1:
                            myCount = commonwealAdapter.getItemCount();
                            break;
                    }
                    if (lastVisibleItemPosition + 1 == myCount) {
                        if (pageNum <= TOTAL_PAGE) {
                            if (tag == 0) {
                                getAssistantData();
                            } else if (tag == 1) {
                                getActivityData();
                            }
                        } else
                            Toaster.toast(getContext(), "已无更多数据");
                    }
                }
            }
        });

        switch (tag) {
            case 1:
                commonwealAdapter = new CommonwealActivityAdapter(getContext(), listCommData);
                commonwealAdapter.setListener(new com.narancommunity.app.MeItemInterface() {
                    @Override
                    public void OnItemClick(int position) {
                        Toaster.toast(getContext(), "准备跳转");
                    }

                    @Override
                    public void OnDelClick(int position) {

                    }

                });
                recyclerView.setAdapter(commonwealAdapter);
                break;
            case 0:
                mAssistantAdapter = new AssistantAdapter(getContext(), listAssistantData);
                mAssistantAdapter.setListener(new com.narancommunity.app.MeItemInterface() {
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
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        pageNum = 1;
                        if (tag == 0) {
                            getAssistantData();
                        } else if (tag == 1) {
                            getActivityData();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    public void onResume() {
        super.onResume();
        if (tag == 0)
            getAssistantData();
        else if (tag == 1)
            getActivityData();
//        MobclickAgent.onPageStart("SplashScreen");
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("SplashScreen");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
