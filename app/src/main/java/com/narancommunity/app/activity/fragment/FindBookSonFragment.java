package com.narancommunity.app.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narancommunity.app.R;
import com.narancommunity.app.activity.BookDetailAct;
import com.narancommunity.app.adapter.BookListAdapter;
import com.narancommunity.app.common.ItemDecoration.GridItemDecoration;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.entity.BookEntity;
import com.narancommunity.app.entity.MeFunctionEntity;

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

public class FindBookSonFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    BookListAdapter adapter;
    List<BookEntity> list = new ArrayList<>();

    public static FindBookSonFragment newInstance() {
        FindBookSonFragment fragment = new FindBookSonFragment();

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
        BookEntity entity = new BookEntity();
        for (int i = 0; i < 9; i++) {
            entity.setName("三国志");
            entity.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523510442785&di=3b3a3a709ef91d5c13e4d4ed8cb8e456&imgtype=0&src=http%3A%2F%2Fimg1.gamersky.com%2Fimage2016%2F02%2F20160202_xtn_162_1%2Fgamersky_08small_16_20162210592B0.jpg");
            list.add(entity);
        }
    }


    private void setView() {

        GridLayoutManager linearLayout = new GridLayoutManager(getContext(), 3);
        linearLayout.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);

//        switch (tag) {
//            case 0:
        adapter = new BookListAdapter(getContext(), list);
        adapter.setListener(new com.narancommunity.app.MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                Toaster.toast(getContext(), "准备跳转");
                startActivity(new Intent(getContext(),BookDetailAct.class));
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
