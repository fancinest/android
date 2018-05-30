package com.narancommunity.app.activity.love;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.CompanyFootAdapter;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.FootPrintData;
import com.narancommunity.app.entity.FootPrintEntity;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Writer：fancy on 2018/5/16 17:56
 * Email：120760202@qq.com
 * FileName :
 */

public class LoveFootPrintFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    CompanyFootAdapter adapter;
    int pageSize = 10;
    int pageNum = 1;
    private int TOTAL_PAGE = 1;
    List<FootPrintEntity> listData = new ArrayList<>();

    public static LoveFootPrintFragment newInstance() {

        LoveFootPrintFragment fragment = new LoveFootPrintFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    View rootView;
    int accountId;

    public void setId(int id, int accountId) {
        this.id = id;
        this.accountId = accountId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_love_footprint, container, false);
            unbinder = ButterKnife.bind(this, rootView);

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

    private void setView() {
        adapter = new CompanyFootAdapter(getContext());
        adapter.setDataList(listData);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    Log.i("fancy", "最后的可见位置:" + lastVisibleItemPosition);
                    if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                        if (pageNum <= TOTAL_PAGE) {
                            getData();
                        } else
                            Toaster.toast(getContext(), "已无更多数据");
                    }
                }
            }
        });
        int id = MApplication.getAccountId(getContext());
        if (id == accountId) {
            tvAdd.setVisibility(View.VISIBLE);
        } else tvAdd.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
        pageNum = 1;
        getData();
//        MobclickAgent.onPageStart("LoveFootPrintFragment");
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("LoveFootPrintFragment");
    }

    int id;

    public void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", id);
        map.put("pageSize", pageSize);
        map.put("pageNum", pageNum);
        NRClient.getOrgFootList(map, new ResultCallback<Result<FootPrintData>>() {
            @Override
            public void onSuccess(Result<FootPrintData> result) {
                LoadDialog.dismiss(getContext());
                setData(result.getData());
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }
        });
    }

    private void setData(FootPrintData data) {
        if (pageNum == 1)
            listData.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data == null || data.getFootprints() == null || data.getFootprints().size() <= 0) {
            setNoData();
        } else {
            listData.addAll(data.getFootprints());
            pageNum++;
        }
        adapter.setDataList(listData);
        adapter.notifyDataSetChanged();

    }

    private void setNoData() {
        FootPrintEntity item = new FootPrintEntity();
        item.setFootprintTime("2017-3-31 11:20:33");
        item.setFootprintContent("在帮助“孤贫儿童打败先天病”项目中，捐赠10万，捐赠10万捐赠10万捐赠10...");
        listData.add(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_add)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), AddFootPrintAct.class).putExtra("id", id));
    }
}
