package com.narancommunity.app.activity.love;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.narancommunity.app.R;
import com.narancommunity.app.adapter.FindLatestAdapter;
import com.narancommunity.app.adapter.LoveAdapter;
import com.narancommunity.app.adapter.OnItemClickListener;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.RecEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Writer：fancy on 2017/12/19 14:41
 * Email：120760202@qq.com
 * FileName : 首页片段
 */

public class LoveFragment extends Fragment {
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_add_love)
    ImageView ivAddLove;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.ln_top)
    RelativeLayout top;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    Unbinder unbinder;

    LoveAdapter adapter;
    List<RecEntity> listData = new ArrayList<>();

    public static LoveFragment newInstance() {

        LoveFragment fragment = new LoveFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_love, container, false);
            unbinder = ButterKnife.bind(this, rootView);

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) top.getLayoutParams();
                linearParams.height = Utils.dip2px(getContext(), 48);
                top.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
                top.setPadding(0, 0, 0, 0);
            }
            setView();
            mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getData();
                        }
                    }, 1000);
                }
            });

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
        LinearLayoutManager lm_latest = new LinearLayoutManager(getContext());
        lm_latest.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new LoveAdapter(getContext());
        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView.setLayoutManager(lm_latest);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        adapter.setDataList(listData);
        adapter.notifyDataSetChanged();
    }

    private void getData() {

    }

    @Override
    public void onResume() {
        super.onResume();
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
        unbinder.unbind();
    }

    @OnClick({R.id.iv_add_love, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_love:
                startActivity(new Intent(getContext(),SettleDownAct.class));
                break;
            case R.id.iv_search:
                break;
        }
    }
}
