package com.narancommunity.app.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.narancommunity.app.R;
import com.narancommunity.app.adapter.BookRangeAdapter;
import com.narancommunity.app.adapter.RangeListRangeAdapter;
import com.narancommunity.app.common.ItemDecoration.DividerItemDecoration;
import com.narancommunity.app.entity.MeFunctionEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2017/12/27 11:49
 * Email：120760202@qq.com
 * FileName : 书籍赠书榜
 */

public class BookDonateRangeFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<MeFunctionEntity> list;
    BookRangeAdapter adapter;

    public static BookDonateRangeFragment newInstance() {

        BookDonateRangeFragment fragment = new BookDonateRangeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new MeFunctionEntity());
        }
    }

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_book_range, container, false);
            ButterKnife.bind(this, rootView);

            setListView();
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

    private void setListView() {
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayout);

//        switch (tag) {
//            case 0:
        adapter = new BookRangeAdapter(getContext(), list, 0);
//        adapter.setListener(new MeItemInterface() {
//            @Override
//            public void OnItemClick(int position) {
//                Toaster.toast(getContext(), "准备跳转");
//            }
//        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
