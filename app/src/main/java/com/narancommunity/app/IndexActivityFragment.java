package com.narancommunity.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narancommunity.app.adapter.CommonwealActivityAdapter;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.entity.MeFunctionEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2017/12/19 17:18
 * Email：120760202@qq.com
 * FileName : 发现，公益活动，信任社区
 */

public class IndexActivityFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    CommonwealActivityAdapter adapter;
    List<MeFunctionEntity> list;

    public static IndexActivityFragment newInstance() {
        IndexActivityFragment fragment = new IndexActivityFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_index_activity, container, false);
            ButterKnife.bind(this, rootView);

            setData();
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

    private void setData() {
        int[] arr = new int[]{R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4, R.drawable.banner1,};
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MeFunctionEntity entity = new MeFunctionEntity();
            entity.setId(arr[i]);
            list.add(entity);
        }
    }

    private void setView() {
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayout);

//        switch (tag) {
//            case 0:
        adapter = new CommonwealActivityAdapter(getContext(), list);
        adapter.setListener(new com.narancommunity.app.MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                Toaster.toast(getContext(), "准备跳转");
            }

            @Override
            public void OnDelClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);
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
    }
}
