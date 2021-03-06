package com.narancommunity.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.narancommunity.app.activity.index.book.AixinBookHouseAct;
import com.narancommunity.app.activity.index.FindFourAct;
import com.narancommunity.app.activity.index.IndexSearchAct;
import com.narancommunity.app.activity.index.RangeAct;
import com.narancommunity.app.adapter.BannerPagerAdapter;
import com.narancommunity.app.adapter.FindLatestAdapter;
import com.narancommunity.app.adapter.FindSortAdapter;
import com.narancommunity.app.adapter.OnItemClickListener;
import com.narancommunity.app.common.IconPageIndicator;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.ObservableScrollView;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.BannerData;
import com.narancommunity.app.entity.BookListData;
import com.narancommunity.app.entity.NewsData;
import com.narancommunity.app.entity.Publicitys;
import com.narancommunity.app.entity.RecEntity;
import com.narancommunity.app.entity.TopLines;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/4/2 14:21
 * Email：120760202@qq.com
 * FileName : 新版首页
 */

public class IndexFragment extends Fragment {
    @BindView(R.id.banner_pager)
    ViewPager mBannerPager;
    @BindView(R.id.banner_indicator)
    IconPageIndicator mBannerIndicator;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.ln_top)
    RelativeLayout top;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.ln_week)
    LinearLayout lnWeek;
    @BindView(R.id.ln_assistant)
    LinearLayout lnAssistant;
    @BindView(R.id.ln_topic)
    LinearLayout lnTopic;
    @BindView(R.id.ln_rank)
    LinearLayout lnRank;
    @BindView(R.id.ln_book)
    LinearLayout lnBook;
    @BindView(R.id.recyclerView_sort)
    RecyclerView recyclerViewSort;
    @BindView(R.id.recyclerView_new)
    RecyclerView recyclerViewNew;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.view_bg)
    View viewBg;
    @BindView(R.id.tv_bottom)
    TextView tvBottom;
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;

    BannerPagerAdapter mBannerPagerAdapter;
    FindSortAdapter sortAdapter;
    FindLatestAdapter latestAdapter;
    private static final int BANNER_CHG_PEROID = 3000;

    List<Publicitys> listBannerData = new ArrayList<>();
    List<RecEntity> listBookData = new ArrayList<>();

    private Runnable mBannerChgRunnable = new Runnable() {

        @Override
        public void run() {
            if (mBannerPager != null && mBannerPagerAdapter != null
                    && mBannerPagerAdapter.getCount() != 0) {
                mBannerPager.setCurrentItem((mBannerPager.getCurrentItem() + 1)
                        % mBannerPagerAdapter.getCount());
                mBannerPager.removeCallbacks(this);
                mBannerPager.postDelayed(this, BANNER_CHG_PEROID);
            }
        }
    };

    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart("Main");
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd("Main");
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
            rootView = inflater.inflate(R.layout.fragment_index_new, container, false);
            ButterKnife.bind(this, rootView);

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) top.getLayoutParams();
                linearParams.height = Utils.dip2px(getContext(), 48);
                top.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
                top.setPadding(0, 0, 0, 0);
            }

            mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getData();
                            mRefreshLayout.setRefreshing(false);
                        }
                    }, 1000);
                }
            });
            //往下移动80个单位
            mRefreshLayout.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics()));

            if (scrollView != null) {
                scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
//                        200
                        int y = scrollView.getScrollY();
                        if (y >= 300) {
                            if (viewBg.getAlpha() < 1) {
                                viewBg.setAlpha(1);
                                viewBg.setBackgroundResource(R.drawable.bookhouse_top_gradient);
                                tvSearch.setBackgroundResource(R.drawable.round_corner_color_search_lighter);
                                tvSearch.setHintTextColor(getResources().getColor(R.color.white));
                                Drawable drawable = getResources().getDrawable(R.mipmap.topnav_btn_sousuo_white);
                                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), (int) (drawable.getMinimumHeight()));
                                tvSearch.setCompoundDrawables(drawable, null, null, null);
                                viewBg.setAlpha(1);
                            }
                        } else {
                            float alpha = (float) y / 300;
                            viewBg.setAlpha(alpha);
                            tvSearch.setBackgroundResource(R.drawable.round_corner_color_search);
                            tvSearch.setHintTextColor(getResources().getColor(R.color.appBlue));
                            Drawable drawable = getResources().getDrawable(R.mipmap.topnav_btn_sousuo_blue);
                            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), (int) (drawable.getMinimumHeight()));
                            tvSearch.setCompoundDrawables(drawable, null, null, null);
                        }

                    }
                });
            }
            scrollView.scrollTo(0, 0);
            viewBg.setBackgroundColor(getResources().getColor(R.color.appBlue));
            viewBg.setAlpha(1);

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBannerPagerAdapter = new BannerPagerAdapter(getContext(), listBannerData);
        mBannerPager.setAdapter(mBannerPagerAdapter);
        mBannerPager.postDelayed(mBannerChgRunnable, BANNER_CHG_PEROID);

        setSort();
        setAdapter();
        getData();
    }

    private void setAdapter() {
        LinearLayoutManager lm_latest = new LinearLayoutManager(getContext());
        lm_latest.setOrientation(LinearLayoutManager.VERTICAL);
        latestAdapter = new FindLatestAdapter(getContext());
        latestAdapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerViewNew.setLayoutManager(lm_latest);
        recyclerViewNew.setAdapter(latestAdapter);
        recyclerViewNew.setNestedScrollingEnabled(false);
        latestAdapter.setDataList(listBookData);
        latestAdapter.notifyDataSetChanged();
    }

    private void getData() {
        //1.获取轮播图，2获取那然快报，3获取最新发布的
        getBanner();
        getMarqueen();
        getLatest();
    }

    private void getLatest() {
        Map<String, Object> map = new HashMap<>();
        map.put("commodityType", "");
        map.put("pageNum", 1);
        map.put("pageSize", 10);
        NRClient.getBookList(map, new ResultCallback<Result<BookListData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<BookListData> result) {
                LoadDialog.dismiss(getContext());
                setLatest(result.getData());
            }
        });
    }

    private void getMarqueen() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", 1);
        map.put("pageSize", 10);
        NRClient.getNewsList(map, new ResultCallback<Result<NewsData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<NewsData> result) {
                LoadDialog.dismiss(getContext());
                setMarqueen(result.getData());
            }
        });
    }

    private void getBanner() {
        Map<String, Object> map = new HashMap<>();
        map.put("publicityType", "HOME");
        map.put("pageNum", 1);
        map.put("pageSize", 10);
        NRClient.getBannerList(map, new ResultCallback<Result<BannerData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<BannerData> result) {
                LoadDialog.dismiss(getContext());
                if (result.getData() != null && result.getData().getTotalCount() > 0)
                    setBannerData(result.getData().getPublicitys());
                else
                    Toaster.toast(getContext(), "没有轮播图！");
            }
        });
    }

    private void setMarqueen(NewsData data) {
        List<TopLines> list = new ArrayList<>();
        if (data != null && data.getTotalCount() > 0) {
            list.addAll(data.getToplines());
        } else
            Toaster.toast(getContext(), "没有快报！");
        List<String> info = revertData(list);
        marqueeView = rootView.findViewById(R.id.marqueeView);
        marqueeView.startWithList(info);
        // 在代码里设置自己的动画
        marqueeView.startWithList(info, R.anim.anim_bottom_in, R.anim.anim_top_out);

    }

    private List<String> revertData(List<TopLines> data) {
        List<String> list = new ArrayList<>();
        for (TopLines info : data) {
            list.add(info.getToplineContent());
        }
        return list;
    }

    private void setLatest(BookListData data) {
        listBookData.clear();
        if (data != null && data.getOrders() != null && data.getTotalCount() > 0) {
            listBookData.addAll(data.getOrders());
            tvBottom.setVisibility(View.VISIBLE);
        } else {
            tvBottom.setVisibility(View.GONE);
            Toaster.toast(getContext(), "没有发布！");
        }
        latestAdapter.setDataList(listBookData);
        latestAdapter.notifyDataSetChanged();
    }

    private void setSort() {
        final LinearLayoutManager lm_sort = new LinearLayoutManager(getContext());
        lm_sort.setOrientation(LinearLayoutManager.HORIZONTAL);
        sortAdapter = new FindSortAdapter(getContext());
        sortAdapter.setListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showNoPrompt();
            }
        });
        recyclerViewSort.setLayoutManager(lm_sort);
        recyclerViewSort.setAdapter(sortAdapter);
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        sortAdapter.setDataList(list);
        sortAdapter.notifyDataSetChanged();
    }

    PopupWindow mPop;

    private void showNoPrompt() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.pop_soon_up, null);

        if (mPop == null) {
            mPop = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mPop.setFocusable(true);
            mPop.setOutsideTouchable(true);
            mPop.setBackgroundDrawable(new BitmapDrawable());

            v.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mPop.dismiss();
                }
            });
        }
        if (mPop != null && !mPop.isShowing())
            mPop.showAtLocation(lnBook, Gravity.CENTER, 0, 0);
    }

    private void setBannerData(List<Publicitys> data) {
        listBannerData.clear();
        if (data != null) {
            listBannerData.addAll(data);
            mBannerPagerAdapter.notifyDataSetChanged();
            mBannerIndicator.setViewPager(mBannerPager);
        }
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBannerPager != null) mBannerPager.removeCallbacks(mBannerChgRunnable);
    }

    @OnClick({R.id.ln_week, R.id.ln_assistant, R.id.ln_topic, R.id.ln_rank, R.id.ln_book, R.id.tv_search})
    public void onViewClicked(View view) {
        Intent it = new Intent(getContext(), FindFourAct.class);
        switch (view.getId()) {
            case R.id.tv_search:
                startActivity(new Intent(getContext(), IndexSearchAct.class));
                break;
            case R.id.ln_book:
                Intent itBook = new Intent(getContext(), AixinBookHouseAct.class);
                itBook.putExtra("tag", 0);
                startActivity(itBook);
                break;
            case R.id.ln_week:
                it.putExtra("tag", 0);
                startActivity(it);
                break;
            case R.id.ln_assistant:
                it.putExtra("tag", 1);
                startActivity(it);
                break;
            case R.id.ln_topic:
                it.putExtra("tag", 2);
                startActivity(it);
                break;
            case R.id.ln_rank:
                Intent intent = new Intent(getContext(), RangeAct.class);
                startActivity(intent);
                break;
        }
    }
}
