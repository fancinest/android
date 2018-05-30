package com.narancommunity.app.activity.mine;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.BookDetailAct;
import com.narancommunity.app.adapter.MyWishAdapter;
import com.narancommunity.app.adapter.OnItemClickListener;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.RecyclerItemClickListener;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.MyWishData;
import com.narancommunity.app.entity.WishEntity;
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
 * FileName : 我的心愿
 */

public class MyWishFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    MyWishAdapter adapter;
    List<WishEntity> listData = new ArrayList<>();
    int pageSize = 5;
    int pageNum = 1;
    private int TOTAL_PAGE = 1;

    public static MyWishFragment newInstance() {
        MyWishFragment fragment = new MyWishFragment();

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
            rootView = inflater.inflate(R.layout.fragment_sort_book_son, container, false);
            ButterKnife.bind(this, rootView);

            getData();
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

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", pageSize);
        map.put("pageNum", pageNum);
        map.put("accessToken", MApplication.getAccessToken());
        map.put("accountId", MApplication.getAccountId(getContext()));
        if (type == 0) {
            NRClient.getMyWishWaitingList(map, new ResultCallback<Result<MyWishData>>() {
                @Override
                public void onSuccess(Result<MyWishData> result) {
                    LoadDialog.dismiss(getContext());
                    setData(result.getData());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    Utils.showErrorToast(getContext(), throwable);
                }
            });
        } else if (type == 1) {
            NRClient.getMyWishGoingList(map, new ResultCallback<Result<MyWishData>>() {
                @Override
                public void onSuccess(Result<MyWishData> result) {
                    LoadDialog.dismiss(getContext());
                    setData(result.getData());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    Utils.showErrorToast(getContext(), throwable);
                }
            });
        } else if (type == 2) {
            NRClient.getMyWishFinishList(map, new ResultCallback<Result<MyWishData>>() {
                @Override
                public void onSuccess(Result<MyWishData> result) {
                    LoadDialog.dismiss(getContext());
                    setData(result.getData());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    Utils.showErrorToast(getContext(), throwable);
                }
            });
        }
    }

    private void setData(MyWishData data) {
        if (pageNum == 1)
            listData.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getOrders() != null) {
            listData.addAll(data.getOrders());
            pageNum++;
        }
        adapter.setDataList(listData);
        adapter.notifyDataSetChanged();
    }

    private void setView() {
        final LinearLayoutManager lmYSHY = new LinearLayoutManager(getContext());
        lmYSHY.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new MyWishAdapter(getContext());
        adapter.setDataList(listData);
        adapter.setType(type);
        adapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(getContext(), BookDetailAct.class)
                        .putExtra("bookId", listData.get(position).getOrderId()));
            }
        });
        recyclerView.setLayoutManager(lmYSHY);
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundColor(getResources().getColor(R.color.white));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager linearLayoutManager;
                    if (layoutManager instanceof LinearLayoutManager) {
                        linearLayoutManager = (LinearLayoutManager) layoutManager;
                        int lastVisibleItemPosition = 0;
                        if (type == 0) {
                            lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                            Log.i("fancy", "最后的可见位置:" + lastVisibleItemPosition);
                        } else {
                            lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                            Log.i("fancy", "最后的可见位置:" + lastVisibleItemPosition);
                        }
                        if (lastVisibleItemPosition + 1 == listData.size()) {
                            if (pageNum <= TOTAL_PAGE) {
                                getData();
                            } else
                                Toaster.toast(getContext(), "已无更多数据");
                        }
                    }
                }
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Log.i("fancy", "position= " + position);
                        showDelPop(position);
                    }
                }));

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

    PopupWindow mPop;

    private void showDelPop(final int position) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.normal_pop, null);

        if (mPop == null) {
            mPop = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mPop.setFocusable(true);
            mPop.setOutsideTouchable(true);
            mPop.setBackgroundDrawable(new BitmapDrawable());
            TextView tv_dial = (TextView) v.findViewById(R.id.dial);
            tv_dial.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tv_dial.setText("是否确认删除？");
            tv_dial.setTextColor(getResources().getColor(R.color.black));
            Button btnOk = v.findViewById(R.id.ok);
            Button btnCancel = v.findViewById(R.id.cancel);
            btnCancel.setTextColor(getResources().getColor(R.color.appBlue));
            btnOk.setTextColor(getResources().getColor(R.color.appBlue));
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    deleteItem(position);
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mPop.dismiss();
                }
            });
        }
        if (mPop != null && !mPop.isShowing())
            mPop.showAtLocation(recyclerView, Gravity.CENTER, 0, 0);
    }

    private void deleteItem(int position) {
        adapter.remove(position);
        adapter.notifyDataSetChanged();
        mPop.dismiss();
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart("MyWishFragment");
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("MyWishFragment");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
