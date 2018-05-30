package com.narancommunity.app.activity.mine;

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

import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.CollectDonateAdapter;
import com.narancommunity.app.adapter.CollectEssayAdapter;
import com.narancommunity.app.adapter.CollectTieziAdapter;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.RecData;
import com.narancommunity.app.entity.RecEntity;
import com.narancommunity.app.entity.YSHYData;
import com.narancommunity.app.entity.YSHYEntity;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Wirter：fancy on 2018/5/2 13:33
 * Mail：120760202@qq.com
 * FileName：我的收藏
 */
public class MyCollectionSonFragment extends Fragment {
    ArrayList<YSHYEntity> listEssayData = new ArrayList<>();
    ArrayList<RecEntity> listDonateData = new ArrayList<>();
    ArrayList<YSHYEntity> listTieziData = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    int type = 0;

    public void setType(int type) {
        this.type = type;
    }

    public static MyCollectionSonFragment newInstance() {

        MyCollectionSonFragment fragment = new MyCollectionSonFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    CollectEssayAdapter essayAdapter;
    CollectDonateAdapter donateAdapter;
    CollectTieziAdapter tieziAdapter;

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_info_son, container, false);
            ButterKnife.bind(this, rootView);
            recyclerView.setBackgroundColor(getResources().getColor(R.color.color_f5f5f5));
            switch (type) {
                case 0:
                    essayAdapter = new CollectEssayAdapter(getContext(), listEssayData);
                    LinearLayoutManager linearLayout1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayout1);
                    recyclerView.setAdapter(essayAdapter);
                    break;
                case 1:
                    donateAdapter = new CollectDonateAdapter(getContext());
                    donateAdapter.setDataList(listDonateData);
                    LinearLayoutManager linearLayout2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayout2);
                    recyclerView.setAdapter(donateAdapter);
                    break;
                case 2:
                    tieziAdapter = new CollectTieziAdapter(getContext(), listTieziData);
                    LinearLayoutManager linearLayout3 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayout3);
                    recyclerView.setAdapter(tieziAdapter);
                    break;
            }
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

    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart("MyCollectSonFragment");
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("MyCollectSonFragment");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getNetData();
    }

    private void setView() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    Log.i("fancy", "最后的可见位置:" + lastVisibleItemPosition);
                    if (lastVisibleItemPosition + 1 == getCount()) {
                        if (pageNum <= TOTAL_PAGE) {
                            getNetData();
                        } else
                            Toaster.toast(getContext(), "已无更多数据");
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
                        getNetData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    private int getCount() {
        if (type == 0)
            return essayAdapter.getItemCount();
        else if (type == 1)
            return donateAdapter.getItemCount();
        else return tieziAdapter.getItemCount();
    }

    int pageSize = 5;
    int pageNum = 1;
    private int TOTAL_PAGE = 1;

    private void getNetData() {
        if (type == 0) {
            getEssayData();
        } else if (type == 1) {
            getDonateData();
        } else if (type == 2) {
            getTieziData();
        }
    }

    private void getDonateData() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("accountId", MApplication.getAccountId(getContext()));
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        NRClient.getCollectDonate(map, new ResultCallback<Result<RecData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<RecData> result) {
                if (result.getData() != null)
                    setDonateData(result.getData());
            }
        });
    }

    private void setDonateData(RecData data) {
        if (pageNum == 1)
            listDonateData.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getOrders() != null && data.getOrders().size() > 0) {
            listDonateData.addAll(data.getOrders());
            donateAdapter.setDataList(listDonateData);
            pageNum++;
        }
        donateAdapter.notifyDataSetChanged();
    }

    private void getTieziData() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("accountId", MApplication.getAccountId(getContext()));
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        NRClient.getCollectTiezi(map, new ResultCallback<Result<YSHYData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<YSHYData> result) {
                if (result.getData() != null)
                    setTieziData(result.getData());
            }
        });
    }

    private void setTieziData(YSHYData data) {
        if (pageNum == 1)
            listTieziData.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getContents() != null && data.getContents().size() > 0) {
            listTieziData.addAll(data.getContents());
            pageNum++;
        }
        tieziAdapter.notifyDataSetChanged();
    }

    private void getEssayData() {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("accountId", MApplication.getAccountId(getContext()));
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        NRClient.getCollectEssay(map, new ResultCallback<Result<YSHYData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<YSHYData> result) {
                if (result.getData() != null)
                    setEssayData(result.getData());
            }
        });
    }

    private void setEssayData(YSHYData data) {
        if (pageNum == 1)
            listEssayData.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getContents() != null && data.getContents().size() > 0) {
            listEssayData.addAll(data.getContents());
            pageNum++;
        }
        essayAdapter.notifyDataSetChanged();
    }

}
