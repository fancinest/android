package com.narancommunity.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.BannerPagerAdapter;
import com.narancommunity.app.adapter.BookListAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.IconPageIndicator;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.BannerData;
import com.narancommunity.app.entity.NewsData;
import com.narancommunity.app.entity.Publicitys;
import com.narancommunity.app.entity.RecData;
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
 * Writer：fancy on 2018/4/10 14:41
 * Email：120760202@qq.com
 * FileName : 爱心书屋
 */
public class BookHouseAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.ln_find)
    LinearLayout lnFind;
    @BindView(R.id.ln_rank)
    LinearLayout lnRank;
    @BindView(R.id.ln_community)
    LinearLayout lnCommunity;
    @BindView(R.id.ln_summary)
    LinearLayout lnSummary;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.marqueeView)
    MarqueeView marqueeView;
    @BindView(R.id.banner_pager)
    ViewPager mBannerPager;
    @BindView(R.id.banner_indicator)
    IconPageIndicator mBannerIndicator;
    @BindView(R.id.ln_hot_switch)
    LinearLayout lnHotSwitch;
    @BindView(R.id.ln_rec_switch)
    LinearLayout lnRecSwitch;
    @BindView(R.id.recyclerView_hot)
    RecyclerView recyclerViewHot;
    @BindView(R.id.recyclerView_rec)
    RecyclerView recyclerViewRec;

    List<Publicitys> listBannerData = new ArrayList<>();
    BookListAdapter adapterHot;
    BookListAdapter adapterRec;
    List<RecEntity> listHot = new ArrayList<>();
    List<RecEntity> listRec = new ArrayList<>();

    BannerPagerAdapter mBannerPagerAdapter;

    private static final int BANNER_CHG_PEROID = 3000;

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

    int pageNumHot = 1, pageNumRec = 1;
    int maxPageHot = 1, maxPageRec = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_book_house);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("爱心书屋");
        setView();

        getData();
    }

    private void getData() {
        getMarqueen();
        getBanner();
        getHotData(false);
        getRecData(false);
    }

    private void getHotData(boolean isShowProgress) {
        if (isShowProgress)
            LoadDialog.show(getContext(), "请稍后！");
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNumHot);
        map.put("pageSize", 6);
        NRClient.getHouseHotRec(map, new ResultCallback<Result<RecData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<RecData> result) {
                LoadDialog.dismiss(getContext());
                setHot(result.getData());
            }
        });
    }

    private void setHot(RecData data) {
        listHot.clear();
        if (data != null && data.getOrders().size() > 0) {
            listHot.addAll(data.getOrders());
            maxPageHot = data.getTotalPageNum();
            adapterHot.notifyDataSetChanged();
        }
    }

    private void getRecData(boolean isShowProgress) {
        if (isShowProgress)
            LoadDialog.show(getContext(), "请稍后！");
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNumRec);
        map.put("pageSize", 6);
        NRClient.getHouseBookRec(map, new ResultCallback<Result<RecData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<RecData> result) {
                LoadDialog.dismiss(getContext());
                setBook(result.getData());
            }
        });
    }

    private void setBook(RecData data) {
        listRec.clear();
        if (data != null && data.getOrders().size() > 0) {
            listRec.addAll(data.getOrders());
            maxPageRec = data.getTotalPageNum();
            adapterRec.notifyDataSetChanged();
        }
    }


    private void setView() {
        GridLayoutManager linearLayoutHot = new GridLayoutManager(getContext(), 3);
        linearLayoutHot.setOrientation(GridLayoutManager.VERTICAL);
        GridLayoutManager linearLayoutRec = new GridLayoutManager(getContext(), 3);
        linearLayoutRec.setOrientation(GridLayoutManager.VERTICAL);
        recyclerViewHot.setLayoutManager(linearLayoutHot);
        recyclerViewRec.setLayoutManager(linearLayoutRec);

        adapterHot = new BookListAdapter(getContext(), listHot);
        adapterHot.setListener(new com.narancommunity.app.MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                startActivity(new Intent(getContext(), BookDetailAct.class)
                        .putExtra("bookId", listHot.get(position).getOrderId()));
            }

            @Override
            public void OnDelClick(int position) {

            }
        });
        recyclerViewHot.setAdapter(adapterHot);
        recyclerViewHot.setNestedScrollingEnabled(false);

        adapterRec = new BookListAdapter(getContext(), listRec);
        adapterRec.setListener(new com.narancommunity.app.MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                startActivity(new Intent(getContext(), BookDetailAct.class)
                        .putExtra("bookId", listRec.get(position).getOrderId()));
            }

            @Override
            public void OnDelClick(int position) {

            }
        });
        recyclerViewRec.setAdapter(adapterRec);
        recyclerViewRec.setNestedScrollingEnabled(false);

        mBannerPagerAdapter = new BannerPagerAdapter(getContext(), listBannerData);
        mBannerPager.setAdapter(mBannerPagerAdapter);
        mBannerPager.postDelayed(mBannerChgRunnable, BANNER_CHG_PEROID);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getMarqueen() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", 1);
        map.put("pageSize", 10);
        NRClient.getBookTopLinesList(map, new ResultCallback<Result<NewsData>>() {
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
        map.put("publicityType", "BOOK");
        map.put("pageNum", 1);
        map.put("pageSize", 10);
        NRClient.getBannerHouseList(map, new ResultCallback<Result<BannerData>>() {
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
        marqueeView.startWithList(info);
        // 在代码里设置自己的动画
        marqueeView.startWithList(info, R.anim.anim_bottom_in, R.anim.anim_top_out);

    }

    private List<String> revertData(List<TopLines> data) {
        List<String> list = new ArrayList<>();
        for (TopLines info : data) {
            list.add(info.getToplineTitle());
        }
        return list;
    }

    private void setBannerData(List<Publicitys> data) {
        listBannerData.clear();
        if (data != null) {
            listBannerData.addAll(data);
            mBannerPagerAdapter.notifyDataSetChanged();
            mBannerIndicator.setViewPager(mBannerPager);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setIcon(R.mipmap.topnav_btn_sousuo);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
//            Toaster.toast(getContext(), "准备搜索");
            startActivity(new Intent(getContext(), SearchBookAct.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.ln_find, R.id.ln_rank, R.id.ln_community, R.id.ln_summary, R.id.ln_hot_switch, R.id.ln_rec_switch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ln_find:
                startActivity(new Intent(getContext(), AixinBookAct.class));
                break;
            case R.id.ln_rank:
                startActivity(new Intent(getContext(), RangeBookAct.class));
                break;
            case R.id.ln_community:
                startActivity(new Intent(getContext(), BookCommunityAct.class));
                break;
            case R.id.ln_summary:
                startActivity(new Intent(getContext(), ShuzhaiAct.class));
                break;
            case R.id.ln_hot_switch:
                pageNumHot++;
                if (pageNumHot < maxPageHot)
                    getHotData(true);
                else {
                    pageNumHot = 1;
                    getHotData(true);
                }
                break;
            case R.id.ln_rec_switch:
                pageNumRec++;
                if (pageNumRec < maxPageRec)
                    getRecData(true);
                else {
                    pageNumRec = 1;
                    getRecData(true);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBannerPager != null) mBannerPager.removeCallbacks(mBannerChgRunnable);
    }
}
