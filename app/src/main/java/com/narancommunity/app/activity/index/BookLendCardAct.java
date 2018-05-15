package com.narancommunity.app.activity.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.BookLendCardAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.BookLendCardData;
import com.narancommunity.app.entity.BookLendCardEntity;
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
 * Writer：fancy on 2018/4/12 14:02
 * Email：120760202@qq.com
 * FileName : 借书卡
 */

public class BookLendCardAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    BookLendCardAdapter adapter;
    List<BookLendCardEntity> list = new ArrayList<>();
    Integer bookId = 0;//

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_book_lend_card);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("借书卡");

        LinearLayoutManager lm_latest = new LinearLayoutManager(getContext());
        lm_latest.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new BookLendCardAdapter(getContext(), list);
        recyclerView.setLayoutManager(lm_latest);
        recyclerView.setAdapter(adapter);

        bookId = getIntent().getIntExtra("bookId", 0);
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", bookId);
        map.put("accessToken", MApplication.getAccessToken());
        NRClient.getBookLendCard(map, new ResultCallback<Result<BookLendCardData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<BookLendCardData> result) {
                LoadDialog.dismiss(getContext());
                setData(result.getData());
            }
        });
    }

    private void setData(BookLendCardData data) {
        list.clear();
        if (data != null && data.getApplys() != null && data.getApplys().size() > 0) {
            list.addAll(data.getApplys());
        } else Toaster.toast(getContext(), "暂无数据");
        adapter.notifyDataSetChanged();
    }

}
