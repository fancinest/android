package com.narancommunity.app.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.book.BookDetailAct;
import com.narancommunity.app.adapter.MyLoveAdapter;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.MyWishData;
import com.narancommunity.app.entity.OrderEntity;
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

import static android.app.Activity.RESULT_FIRST_USER;

/**
 * Writer：fancy on 2018/5/21 15:49
 * Email：120760202@qq.com
 * FileName :
 */
public class MyLoveFragment extends Fragment {

    @BindView(R.id.recyclerView)
    XRecyclerView recyclerView;
    //    @BindView(R.id.swipe_refresh)
//    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    MyLoveAdapter adapter;
    List<WishEntity> listData = new ArrayList<>();
    int pageSize = 5;
    int pageNum = 1;
    private int TOTAL_PAGE = 1;

    public static MyLoveFragment newInstance() {
        MyLoveFragment fragment = new MyLoveFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    int type = 0;

    public void setType(int type) {
        this.type = type;
    }

    View rootView;

    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart("MyLoveFragment");
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("MyLoveFragment");
    }

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
            NRClient.getMyLoveWaitingList(map, new ResultCallback<Result<MyWishData>>() {
                @Override
                public void onSuccess(Result<MyWishData> result) {
                    LoadDialog.dismiss(getContext());
                    setData(result.getData());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    Utils.showErrorToast(getContext(), throwable);
                    recyclerView.loadMoreComplete();
                    recyclerView.refreshComplete();
                }
            });
        } else if (type == 1) {
            NRClient.getMyLoveGoingList(map, new ResultCallback<Result<MyWishData>>() {
                @Override
                public void onSuccess(Result<MyWishData> result) {
                    LoadDialog.dismiss(getContext());
                    setData(result.getData());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    Utils.showErrorToast(getContext(), throwable);
                    recyclerView.loadMoreComplete();
                    recyclerView.refreshComplete();
                }
            });
        } else if (type == 2) {
            NRClient.getMyLoveFinishList(map, new ResultCallback<Result<MyWishData>>() {
                @Override
                public void onSuccess(Result<MyWishData> result) {
                    LoadDialog.dismiss(getContext());
                    setData(result.getData());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    Utils.showErrorToast(getContext(), throwable);
                    recyclerView.loadMoreComplete();
                    recyclerView.refreshComplete();
                }
            });
        }
    }

    private void setData(MyWishData data) {
        if (pageNum == 1)
            listData.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null && data.getOrders() != null && data.getOrders() != null) {
            listData.addAll(data.getOrders());
            pageNum++;
            tvNoData.setVisibility(View.GONE);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }
        adapter.setDataList(listData);
        adapter.notifyDataSetChanged();
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
    }

    private void setView() {
        final LinearLayoutManager lmYSHY = new LinearLayoutManager(getContext());
        lmYSHY.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new MyLoveAdapter(getContext());
        adapter.setDataList(listData);
        adapter.setType(type);
        adapter.setListener(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                if (type == 0) {
                    getOrdererInfo(position);
                } else startActivity(new Intent(getContext(), BookDetailAct.class)
                        .putExtra("bookId", listData.get(position).getOrderId()));
            }

            @Override
            public void OnDelClick(int position) {
                startActivity(new Intent(getContext(), BookDetailAct.class)
                        .putExtra("bookId", listData.get(position).getOrderId()));
            }
        });
        recyclerView.setLayoutManager(lmYSHY);
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundColor(getResources().getColor(R.color.white));

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                getData();
            }

            @Override
            public void onLoadMore() {
                if (pageNum <= TOTAL_PAGE) {
                    getData();
                } else {
                    recyclerView.loadMoreComplete();
                    recyclerView.refreshComplete();
                    Toaster.toast(getContext(), "已无更多数据");
                }
            }
        });

    }

    private void getOrdererInfo(final int position) {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("orderId", listData.get(position).getOrderId());
        NRClient.getBookOrdererInfo(map, new ResultCallback<Result<OrderEntity>>() {
            @Override
            public void onSuccess(Result<OrderEntity> result) {
                LoadDialog.dismiss(getContext());
                if (result != null && result.getData() != null) {
                    startActivityForResult(new Intent(getContext(), DeliverBookAct.class)
                            .putExtra("position", position)
                            .putExtra("data", listData.get(position))
                            .putExtra("address", result.getData()), RESULT_FIRST_USER);
                } else Toaster.toast(getContext(), "");
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("fancy", "子执行了1次");
        if (resultCode == 10010 && requestCode == RESULT_FIRST_USER) {
            int position = data.getIntExtra("position", 0);
            Log.i("fancy", "删除执行了1次" + " position = " + position);
            deleteItem(position);
        }
    }

    private void deleteItem(int position) {
        listData.remove(position);
        adapter.setDataList(listData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (recyclerView != null) {
            recyclerView.destroy(); // this will totally release XR's memory
            recyclerView = null;
        }
    }
}
