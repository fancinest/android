package com.narancommunity.app.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.BookDetailAct;
import com.narancommunity.app.adapter.BookListAdapter;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.RecData;
import com.narancommunity.app.entity.RecEntity;
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
 * Writer：fancy on 2017/12/19 17:18
 * Email：120760202@qq.com
 * FileName : 寻一本书子片段
 * 参考{@link com.narancommunity.app.activity.fragment.MyCollectionSonFragment}
 */

public class BookSortSonFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    BookListAdapter adapter;
    List<RecEntity> list = new ArrayList<>();
    int pageSize = 9;
    int pageNum = 1;
    private int TOTAL_PAGE = 1;
    String bookType;

    public static BookSortSonFragment newInstance() {
        BookSortSonFragment fragment = new BookSortSonFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    int type = 0;

    public void setType(int type, String bookType) {
        this.type = type;
        this.bookType = bookType;
    }

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_sort_book_son, container, false);
            ButterKnife.bind(this, rootView);

            setView();
            getData();
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

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("bookClassify", bookType);
        map.put("pageSize", pageSize);
        map.put("pageNum", pageNum);
        NRClient.getBookBySortList(map, new ResultCallback<Result<RecData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<RecData> result) {
                LoadDialog.dismiss(getContext());
                setBookData(result.getData());
            }
        });
    }

    private void setBookData(RecData data) {
        if (pageNum == 1)
            list.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getOrders() != null && data.getOrders().size() > 0) {
            list.addAll(data.getOrders());
            pageNum++;
        } else {
            Toaster.toast(getContext(), "暂无任何书籍");
        }
        adapter.notifyDataSetChanged();
    }

    private void setView() {
        final GridLayoutManager linearLayout = new GridLayoutManager(getContext(), 3);
        linearLayout.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        adapter = new BookListAdapter(getContext(), list);
        adapter.setListener(new com.narancommunity.app.MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                Toaster.toast(getContext(), "准备跳转");
                startActivity(new Intent(getContext(), BookDetailAct.class)
                        .putExtra("bookId", list.get(position).getOrderId()));
            }

            @Override
            public void OnDelClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItemPosition = linearLayout.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                        if (pageNum <= TOTAL_PAGE) {
                            getData();
                        } else
                            Toaster.toast(getContext(), "已无更多数据");
                    }
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        pageNum = 1;
                        getData();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
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
