package com.narancommunity.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.BookLendCardAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.entity.LendRec;

import java.util.ArrayList;
import java.util.List;

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
    List<LendRec> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_book_lend_card);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("借书卡");
        setData();

        final LinearLayoutManager lm_latest = new LinearLayoutManager(getContext());
        lm_latest.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new BookLendCardAdapter(getContext(), list);
        recyclerView.setLayoutManager(lm_latest);
        recyclerView.setAdapter(adapter);
    }

    private void setData() {
        LendRec lendRec;
        for (int i = 0; i < 20; i++) {
            lendRec = new LendRec();
            lendRec.setName("李爽");
            lendRec.setState("2018-5-10");
            list.add(lendRec);
        }
    }
}
