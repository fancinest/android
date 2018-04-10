package com.narancommunity.app.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.CollectEssayAdapter;
import com.narancommunity.app.common.ItemDecoration.GridItemDecoration;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.CollectDonateItem;
import com.narancommunity.app.entity.CollectEssayItem;
import com.narancommunity.app.entity.CollectTopicItem;
import com.narancommunity.app.entity.CollectWishItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Wirter：fancy on 2017/3/1 13:33
 * Mail：120760202@qq.com
 * FileName：我的收藏
 */
public class MyCollectionSonFragment extends Fragment {
    int type = 0;
    ArrayList<CollectEssayItem> listEssayData = new ArrayList<>();
    ArrayList<CollectDonateItem> listDonateData = new ArrayList<>();
    ArrayList<CollectWishItem> listWishData = new ArrayList<>();
    ArrayList<CollectTopicItem> listTopicData = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView recycleView;

    public void setType(int type) {
        this.type = type;
    }

    public static MyCollectionSonFragment newInstance() {

        MyCollectionSonFragment fragment = new MyCollectionSonFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    CollectEssayAdapter essayAdapter;
//    CollectTopicAdapter topicAdapter;
//    CollectNewsAdapter newsAdapter;
//    LRecyclerViewAdapter adapter;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_info_son, container, false);
            ButterKnife.bind(this, rootView);
            recycleView.setBackgroundColor(getResources().getColor(R.color.color_f5f5f5));
            switch (type) {
                case 0:
                    essayAdapter = new CollectEssayAdapter(getContext(), listEssayData);
                    essayAdapter.setList(listEssayData);
                    //根据需要选择使用GridItemDecoration还是SpacesItemDecoration
                    GridItemDecoration divider = new GridItemDecoration.Builder(getContext())
                            .setHorizontal(R.dimen.default_divider_padding)
                            .setVertical(R.dimen.default_divider_padding)
                            .setColorResource(R.color.transparent)
                            .build();
                    GridLayoutManager gridlayout = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
                    recycleView.setLayoutManager(gridlayout);
                    recycleView.setPadding(Utils.dip2px(getContext(), 10),
                            Utils.dip2px(getContext(), 10),
                            Utils.dip2px(getContext(), 10),
                            Utils.dip2px(getContext(), 10));
                    recycleView.addItemDecoration(divider);
                    recycleView.setAdapter(essayAdapter);
                    break;
//                case 1:
//                    topicAdapter = new CollectTopicAdapter(getContext());
//                    topicAdapter.setDataList(listDonateData);
//                    LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//                    recycleView.setLayoutManager(linearLayout);
//                    adapter = new LRecyclerViewAdapter(topicAdapter);
//                    break;
//                case 2:
//                    newsAdapter = new CollectNewsAdapter(getContext());
//                    newsAdapter.setDataList(listTopicData);
//                    LinearLayoutManager linearLayout2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//                    recycleView.setLayoutManager(linearLayout2);
//                    adapter = new LRecyclerViewAdapter(newsAdapter);
//                    break;
            }
//            recycleView.setAdapter(adapter);
//            adapter.setOnItemClickListener(new OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//                    if (type == 0) {
//                        String title = listEssayData.get(position).getProductTitle();
//                        String url = "";
//                        if (listEssayData.get(position).getCover() != null)
//                            url = listEssayData.get(position).getCover().getPictureUrl();
//                        String productId = listEssayData.get(position).getProductId();
//                        startActivity(new Intent(getActivity(), ProductDetailAct.class)
//                                .putExtra("id", productId)
//                                .putExtra("title", title)
//                                .putExtra("url", url));
//                    }
//                    if (type == 1) {
//                        NRCommunityTopicItem item = listDonateData.get(position);
//                        if (NRApplication.getUserInfo() == null) {
//                            Toaster.toast(getContext(), "请先登录！");
//                            startActivity(new Intent(getContext(), LoginActivity.class));
//                        } else
//                            startActivity(new Intent(getContext(), CommunityTopicDetailAct.class)
//                                    .putExtra("data", item));
//                    } else {
//                        String title = "";
//                        String url = ServiceFactory.API_BASE_URL + NRConfig.NORMAL_JUMP + listTopicData.get(position).getContentId();
//                        String secondTitle = listTopicData.get(position).getContentTitle();
//                        String icon = "";
//                        if (listTopicData.get(position).getCover() != null)
//                            icon = listTopicData.get(position).getCover().getPictureUrl();
//                        startActivity(new Intent(getContext(), WebViewAct.class)
//                                .putExtra("title", title)
//                                .putExtra("secondTitle", secondTitle)
//                                .putExtra("shareIcon", icon)
//                                .putExtra("url", url));
//                    }
//                }
//            });
            setRefreshListener();
            getNetData();

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

    private int pageNum = 1;
    private int M_PAGE_SIZE = 20;//总共的页数
    private int totalPage = 1;

    private void setRefreshListener() {
//        recycleView.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                onRefreshData();
//            }
//        });
//        recycleView.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                if (pageNum < totalPage) {
//                    onLoadData();
//                } else {
//                    recycleView.setNoMore(true);
//                }
//            }
//        });
//        recycleView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
//            @Override
//            public void reload() {
//                getNetData();
//            }
//        });
//        adapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                //TODO 这里处理每个点击事件
//                Intent it = null;
//                if (type == 0) {
//                    String url = "";
//                    if (listEssayData.get(position).getCover() != null)
//                        url = listEssayData.get(position).getCover().getPictureUrl();
//                    it = new Intent(getContext(), ProductDetailAct.class);
//                    it.putExtra("title", listEssayData.get(position).getProductTitle());
//                    it.putExtra("id", listEssayData.get(position).getProductId());
//                    it.putExtra("url", url);
//                } else if (type == 1) {
//                    NRCommunityTopicItem data = listDonateData.get(position);
//                    it = new Intent(getContext(), CommunityTopicDetailAct.class);
//                    it.putExtra("data", data);
//                } else if (type == 2) {
//                    NRNewsItem item = listTopicData.get(position);
//                    it = new Intent(getContext(), WebViewAct.class);
//                    it.putExtra("url", ServiceFactory.API_BASE_URL + NRConfig.NORMAL_JUMP + item.getContentId());
//                    it.putExtra("title", getTitles(item.getContentClassify()));
//                }
//                startActivity(it);
//            }
//
//        });
//
//        View emptyView = rootView.findViewById(R.id.empty_view);
//        recycleView.setEmptyView(emptyView);
//        recycleView.setLoadMoreEnabled(true);
//
//        //设置底部加载颜色
//        recycleView.setFooterViewColor(R.color.colorAccent, R.color.black, android.R.color.white);
//        //设置底部加载文字提示
//        recycleView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
//
//        recycleView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
//        recycleView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
    }

    private String getTitles(String contentClassify) {
        String s = "那然资讯";
        if (contentClassify.equals("HYDT"))
            s = "行业动态";
        else if (contentClassify.equals("JKSH"))
            s = "健康生活";
        else if (contentClassify.equals("QWFB"))
            s = "权威发布";
        else if (contentClassify.equals("ZDGZ"))
            s = "制度规则";
        return s;
    }

    public void onRefreshData() {
        pageNum = 1;
        getNetData();
    }

    public void onLoadData() {
        pageNum++;
        getNetData();
    }

    private void getNetData() {
        if (type == 0) {
            getEssayData();
        } else if (type == 1) {
//            getTopicData();
        } else if (type == 2) {
            getNewsData();
        }
    }

    private void getNewsData() {
//        LoadDialog.show(getContext());
//        Map<String, Object> map = new HashMap<>();
//        map.put("userId", MApplication.getAccessToken());
//        map.put("contentType", "NEWS");
//        map.put("page", pageNum);
//        map.put("row", M_PAGE_SIZE);
//        NRClient.getMineCollectNews(map, new ResultCallback<Result<NRNewsData>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                LoadDialog.dismiss(getContext());
//                Utils.showErrorToast(getContext(), throwable);
//                setViewNewsData(null);
//            }
//
//            @Override
//            public void onSuccess(Result<NRNewsData> result) {
//                LoadDialog.dismiss(getContext());
//                if (result != null && result.getData() != null
//                        && result.getData().getDataPage() != null)
//                    totalPage = result.getData().getDataPage().getMaxPage();
//                setViewNewsData(result.getData().getDataPage());
//            }
//        });
    }

//    private void setViewNewsData(NRBaseData<NRNewsItem> datapage) {
//        if (pageNum == 1)
//            listTopicData.clear();
//        if (datapage != null && datapage.getList() != null)
//            listTopicData.addAll(datapage.getList());
//        newsAdapter.setDataList(listTopicData);
//        notifyData();
//    }

//    private void getTopicData() {
//        LoadDialog.show(getContext());
//        Map<String, Object> map = new HashMap<>();
//        map.put("userId", NRApplication.getUserID());
//        map.put("contentType", "TOPIC");
//        map.put("page", pageNum);
//        map.put("row", M_PAGE_SIZE);
//        NRClient.getMineCollectTopic(map, new ResultCallback<Result<NRMineTopic>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                LoadDialog.dismiss(getContext());
//                Utils.showErrorToast(getContext(), throwable);
//                setViewTopicData(null);
//            }
//
//            @Override
//            public void onSuccess(Result<NRMineTopic> result) {
//                LoadDialog.dismiss(getContext());
//                if (result != null && result.getData() != null
//                        && result.getData().getDatapage() != null)
//                    totalPage = result.getData().getDatapage().getMaxPage();
//                setViewTopicData(result.getData().getDatapage());
//            }
//        });
//    }

//    private void setViewTopicData(NRBaseData<NRCommunityTopicItem> datapage) {
//        if (pageNum == 1)
//            listDonateData.clear();
//        if (datapage != null && datapage.getList() != null)
//            listDonateData.addAll(datapage.getList());
//        topicAdapter.setDataList(listDonateData);
//        notifyData();
//    }

    private void getEssayData() {
        LoadDialog.show(getContext());
        Map<String, Object> map = new HashMap<>();
        map.put("userId", MApplication.getAccessToken());
        map.put("contentType", "PRODUCT");
        map.put("page", pageNum);
        map.put("row", M_PAGE_SIZE);
//        NRClient.getMineCollectProduct(map, new ResultCallback<Result<CollectEssayItem>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                LoadDialog.dismiss(getContext());
//                Utils.showErrorToast(getContext(), throwable);
//                setViewProductData(null);
//            }
//
//            @Override
//            public void onSuccess(Result<CollectEssayItem> result) {
//                LoadDialog.dismiss(getContext());
//                if (result != null && result.getData() != null
//                        && result.getData().getDatapage() != null)
//                    totalPage = result.getData().getDatapage().getMaxPage();
//                setViewProductData(result.getData().getDatapage());
//            }
//        });
    }

    private void setViewProductData(CollectEssayItem datapage) {
        if (pageNum == 1)
            listEssayData.clear();
//        if (datapage != null && datapage.getList() != null)
//            listEssayData.addAll(datapage.getList());
        essayAdapter.setList(listEssayData);
        notifyData();
    }

    private void notifyData() {
//        recycleView.refreshComplete(M_PAGE_SIZE);
//        adapter.notifyDataSetChanged();
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

}
