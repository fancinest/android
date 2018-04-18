package com.narancommunity.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.narancommunity.app.entity.BannerItem;
import com.narancommunity.app.entity.BookEntity;
import com.narancommunity.app.entity.NewsData;
import com.narancommunity.app.entity.Publicitys;
import com.narancommunity.app.entity.RecData;
import com.narancommunity.app.entity.TopLines;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/4/10 14:41
 * Email：120760202@qq.com
 * FileName :
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
    List<BookEntity> listHot = new ArrayList<>();
    List<BookEntity> listRec = new ArrayList<>();

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_book_house);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("爱心书屋");
        setData();
        setView();

        getData();
    }

    private void getData() {
        getMarqueen();
        getBanner();
        getHotData();
        getRecData();
    }

    private void getHotData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", 6);
        map.put("pageSize", 1);
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
        
    }

    private void getRecData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", 1);
        map.put("pageSize", 10);
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
                Toaster.toast(getContext(), "准备跳转");
                startActivity(new Intent(getContext(), BookDetailAct.class));
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
                Toaster.toast(getContext(), "准备跳转");
                startActivity(new Intent(getContext(), BookDetailAct.class));
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

    private void setData() {
        String[] arr = new String[]{"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523521822134&di=8f8ad66862e43356d2f611c75e837232&imgtype=0&src=http%3A%2F%2Fs9.sinaimg.cn%2Fmw690%2F001oghI3gy6RP8blcDK18%26690"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523521840435&di=ccc40b13bce701c1243aa33e7ac8ccbf&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fbaike%2Fw%253D268%253Bg%253D0%2Fsign%3Df7c629d8b9014a90813e41bb914c5e2f%2Fe61190ef76c6a7ef27ca3e4cfdfaaf51f2de66e8.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523521857113&di=7c3fcec13a02545b16adf8064a80ab3f&imgtype=0&src=http%3A%2F%2Fimage.xinmin.cn%2F2016%2F10%2F31%2F1477875471441.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523521876495&di=5e2f1c14077b7fd644a085d531bafe47&imgtype=0&src=http%3A%2F%2Fimg13.360buyimg.com%2FpopWaterMark%2Fjfs%2Ft481%2F15%2F977914362%2F15133%2F32d03cb1%2F54a1175aN3785b77e.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523539651530&di=076380697cd9b09ecc6bd9f7b8cca7e7&imgtype=0&src=http%3A%2F%2Fp4.qhimg.com%2Ft0126b72b37e2c48a1c.jpg"
                , "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523522043547&di=2b57cbb4ffac6d93fd4044374ff73297&imgtype=0&src=http%3A%2F%2Fimg34.ddimg.cn%2F24%2F8%2F486114-1_o.jpg"};

        Random random = new Random();
        BookEntity entity;
        for (int i = 0; i < 6; i++) {
            entity = new BookEntity();
            entity.setName("三国志");
            int position = random.nextInt(6);
            Log.i("fancy", "random = " + position);
            entity.setUrl(arr[position] + "");
            listHot.add(entity);
            listRec.add(entity);
        }
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
                break;
            case R.id.ln_hot_switch:
                break;
            case R.id.ln_rec_switch:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBannerPager != null) mBannerPager.removeCallbacks(mBannerChgRunnable);
    }
}
