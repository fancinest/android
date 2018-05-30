package com.narancommunity.app.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.WallListAdapter;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.WallListData;
import com.narancommunity.app.entity.MOrders;
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

/**
 * Writer：fancy on 2017/12/27 17:17
 * Email：120760202@qq.com
 * FileName :心愿墙/捐赠墙
 */

public class WallListAct extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    WallListAdapter adapter;
    boolean isWish;
    @BindView(R.id.iv_bg)
    ImageView ivBg;

    List<MOrders> listData = new ArrayList<>();
    int pageSize = 10, pageNum = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wish_wall);
        ButterKnife.bind(this);
        setToolBar();

        isWish = getIntent().getBooleanExtra("isWish", false);
        setView();
    }

    private void setToolBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setElevation(0);
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
    }

    private void setView() {
        if (isWish) {
            ivBg.setImageResource(R.mipmap.xyq_bj);

        } else {
            ivBg.setImageResource(R.mipmap.axq_bj);

        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new WallListAdapter(getContext(), listData, isWish ? 0 : 1);
        adapter.setListener(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                if (isWish)
                    startActivity(new Intent(getContext(), WishDetailAct.class).putExtra("orderId", listData.get(position)
                            .getOrderId()));
                else
                    startActivity(new Intent(getContext(), DonateDetailAct.class).putExtra("orderId", listData.get(position)
                            .getOrderId()));
            }

            @Override
            public void OnDelClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_search) {
//            Toaster.toast(getContext(), "搜索");
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        LoadDialog.show(this, "正在获取数据...");
        if (isWish) {
            Map<String, Object> map = new HashMap<>();
            map.put("pageNum", pageNum);
            map.put("pageSize", pageSize);
            NRClient.getWishWall(map, new ResultCallback<Result<WallListData>>() {
                @Override
                public void onSuccess(Result<WallListData> result) {
                    setList(result.getData());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Utils.showErrorToast(getContext(), throwable);
                    LoadDialog.dismiss(getContext());
                }
            });
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("pageNum", pageNum);
            map.put("pageSize", pageSize);
            NRClient.getDonateWall(map, new ResultCallback<Result<WallListData>>() {
                @Override
                public void onSuccess(Result<WallListData> result) {
                    setList(result.getData());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Utils.showErrorToast(getContext(), throwable);
                    LoadDialog.dismiss(getContext());
                }
            });
        }
    }

    private void setList(WallListData data) {
        if (data != null && data.getOrders() != null && data.getOrders().size() > 0) {
            listData.clear();
            adapter.clear();
            listData.addAll(data.getOrders());
            adapter.setList(listData);
            adapter.notifyDataSetChanged();
        }
        LoadDialog.dismiss(getContext());
    }


    @OnClick(R.id.tv_select)
    public void onViewClicked() {
        Toaster.toast(getContext(), "筛选");
    }
}
