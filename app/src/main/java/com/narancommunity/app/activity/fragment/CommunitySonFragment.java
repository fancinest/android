package com.narancommunity.app.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narancommunity.app.R;
import com.narancommunity.app.activity.BookDetailAct;
import com.narancommunity.app.adapter.BookCommentAdapter;
import com.narancommunity.app.adapter.BookListAdapter;
import com.narancommunity.app.adapter.CommunityYSHYAdapter;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.entity.BookComment;
import com.narancommunity.app.entity.BookCommunityEntity;
import com.narancommunity.app.entity.BookEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2017/12/19 17:18
 * Email：120760202@qq.com
 * FileName : 寻一本书子片段
 * 参考{@link com.narancommunity.app.activity.fragment.MyCollectionSonFragment}
 */

public class CommunitySonFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    CommunityYSHYAdapter adapter;
    List<BookCommunityEntity> list = new ArrayList<>();

    public static CommunitySonFragment newInstance() {
        CommunitySonFragment fragment = new CommunitySonFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    int type = 0;

    public void setType(int type) {
        this.type = type;
    }

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            //这里就是一个通用的recyclerview
            rootView = inflater.inflate(R.layout.fragment_find_book_son, container, false);
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
        BookCommunityEntity entity;
        for (int i = 0; i < 9; i++) {
            entity = new BookCommunityEntity();
            entity.setName("小赵");
            entity.setPic("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523510442785&di=3b3a3a709ef91d5c13e4d4ed8cb8e456&imgtype=0&src=http%3A%2F%2Fimg1.gamersky.com%2Fimage2016%2F02%2F20160202_xtn_162_1%2Fgamersky_08small_16_20162210592B0.jpg");
            entity.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523618850752&di=a6b09ac03895f1e6c7598c75571e7cac&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D124379812%2C4079903765%26fm%3D214%26gp%3D0.jpg");
            entity.setCreateTime("2018-4-13 09:20:22");
            entity.setContent("「求分享」有哪些让你感叹「亏作者 想的出」的树种细节？");
            entity.setCount("31");
            entity.setLikes("108");
            list.add(entity);
        }
    }


    private void setView() {

        final LinearLayoutManager lm_latest = new LinearLayoutManager(getContext());
        lm_latest.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new CommunityYSHYAdapter(getContext(), list);
        if (type == 0)
            adapter.setTag(true);
        else adapter.setTag(false);
        recyclerView.setLayoutManager(lm_latest);
        recyclerView.setAdapter(adapter);

        recyclerView.setBackgroundColor(getResources().getColor(R.color.white));
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
