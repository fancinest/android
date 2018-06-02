package com.narancommunity.app.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.mine.MyCollectionSonFragment;
import com.narancommunity.app.adapter.CommunityYSHYAdapter;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.YSHYData;
import com.narancommunity.app.entity.YSHYEntity;
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
 * FileName : 以书会友和书荒互动片段
 * 参考{@link MyCollectionSonFragment}
 */

public class CommunitySonFragment extends Fragment {

    @BindView(R.id.recyclerView)
    XRecyclerView recyclerView;
//    @BindView(R.id.swipe_refresh)
//    SwipeRefreshLayout swipeRefreshLayout;
    CommunityYSHYAdapter adapterYSHY;
    CommunityYSHYAdapter adapterSHHZ;
    List<YSHYEntity> list = new ArrayList<>();
    int pageSize = 5;
    int pageNum = 1;
    private int TOTAL_PAGE = 1;

    public static CommunitySonFragment newInstance() {
        CommunitySonFragment fragment = new CommunitySonFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    int type = 0;//0 YSHY 1 SHHZ

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
        if (type == 0) {
            NRClient.getYSHYList(map, new ResultCallback<Result<YSHYData>>() {
                @Override
                public void onSuccess(Result<YSHYData> result) {
                    LoadDialog.dismiss(getContext());
                    setData(result.getData());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    recyclerView.loadMoreComplete();
                    recyclerView.refreshComplete();
                    Utils.showErrorToast(getContext(), throwable);
                }
            });
        } else if (type == 1) {
            NRClient.getSHHZList(map, new ResultCallback<Result<YSHYData>>() {
                @Override
                public void onSuccess(Result<YSHYData> result) {
                    LoadDialog.dismiss(getContext());
                    setData(result.getData());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    LoadDialog.dismiss(getContext());
                    recyclerView.loadMoreComplete();
                    recyclerView.refreshComplete();
                    Utils.showErrorToast(getContext(), throwable);
                }
            });
        }
    }

    private void setData(YSHYData data) {
        if (pageNum == 1)
            list.clear();
        TOTAL_PAGE = data.getTotalPageNum();
        if (data != null) {
            list.addAll(data.getContents());
            pageNum++;
        }
        if (type == 0)
            adapterYSHY.notifyDataSetChanged();
        else if (type == 1)
            adapterSHHZ.notifyDataSetChanged();
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
    }

    private void setView() {
        if (type == 0) {
            final LinearLayoutManager lmYSHY = new LinearLayoutManager(getContext());
            lmYSHY.setOrientation(LinearLayoutManager.VERTICAL);
            adapterYSHY = new CommunityYSHYAdapter(getContext(), list);
            adapterYSHY.setListener(new MeItemInterface() {
                @Override
                public void OnItemClick(int position) {

                }

                @Override
                public void OnDelClick(int position) {

                }
            });

            adapterYSHY.setTag(true);
            recyclerView.setLayoutManager(lmYSHY);
            recyclerView.setAdapter(adapterYSHY);
            recyclerView.setBackgroundColor(getResources().getColor(R.color.white));
        } else if (type == 1) {
            final LinearLayoutManager lmSHHZ = new LinearLayoutManager(getContext());
            lmSHHZ.setOrientation(LinearLayoutManager.VERTICAL);
            adapterSHHZ = new CommunityYSHYAdapter(getContext(), list);
            adapterSHHZ.setListener(new MeItemInterface() {
                @Override
                public void OnItemClick(int position) {

                }

                @Override
                public void OnDelClick(int position) {

                }
            });

            adapterSHHZ.setTag(false);
            recyclerView.setLayoutManager(lmSHHZ);
            recyclerView.setAdapter(adapterSHHZ);
            recyclerView.setBackgroundColor(getResources().getColor(R.color.white));
        }
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

    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart("CommunitySonFragment");
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("CommunitySonFragment");
    }

    @Override
    public void onDestroyView() {
        if (recyclerView != null) {
            recyclerView.destroy(); // this will totally release XR's memory
            recyclerView = null;
        }
        super.onDestroyView();
    }
}
