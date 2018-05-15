package com.narancommunity.app.activity.index;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

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
                showPopView(recyclerView);
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

    PopupWindow mPop;

    private void showPopView(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.normal_pop, null);

        if (mPop == null) {
            mPop = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mPop.setFocusable(true);
            mPop.setOutsideTouchable(true);
            mPop.setBackgroundDrawable(new BitmapDrawable());
            TextView tv_content = (TextView) v.findViewById(R.id.dial);
            TextView cancel = v.findViewById(R.id.cancel);
            TextView ok = v.findViewById(R.id.ok);

            ok.setTextColor(getResources().getColor(R.color.appBlue));
            ok.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            cancel.setTextColor(getResources().getColor(R.color.appBlue));
            cancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);


            tv_content.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv_content.setText("传书给TA？");
            tv_content.setTextColor(getResources().getColor(R.color.black));
            v.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //TODO
                    mPop.dismiss();
                }
            });
            v.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mPop.dismiss();
                }
            });
        }
        if (mPop != null && !mPop.isShowing())
            mPop.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
