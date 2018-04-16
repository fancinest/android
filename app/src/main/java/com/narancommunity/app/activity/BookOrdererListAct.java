package com.narancommunity.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.BookOrderListAdapter;
import com.narancommunity.app.adapter.OnItemClickListener;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.entity.BookOrderer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/4/16 14:12
 * Email：120760202@qq.com
 * FileName : 预约想看的人列表
 */

public class BookOrdererListAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    BookOrderListAdapter adapter;
    List<BookOrderer> listData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_normal_list_view);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("想看的人");

        setView();
    }

    private void setView() {
        setData();
        final LinearLayoutManager lm_latest = new LinearLayoutManager(getContext());
        lm_latest.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new BookOrderListAdapter(getContext());
        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        adapter.setDataList(listData);
        recyclerView.setLayoutManager(lm_latest);
        recyclerView.setAdapter(adapter);
    }

    private void setData() {
        BookOrderer entity;
        for (int i = 0; i < 9; i++) {
            entity = new BookOrderer();
            entity.setDate("10");
            entity.setDistance(10 + "");
            entity.setUrl("http://img.qqzhi.com/upload/img_3_1897173975D2024083860_27.jpg");
            listData.add(entity);
        }
    }

    @OnClick(R.id.recyclerView)
    public void onViewClicked() {
    }
}
