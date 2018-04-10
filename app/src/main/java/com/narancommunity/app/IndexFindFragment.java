package com.narancommunity.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.narancommunity.app.activity.FindFourAct;
import com.narancommunity.app.activity.RangeAct;
import com.narancommunity.app.activity.WallListAct;
import com.narancommunity.app.activity.WishDetailAct;
import com.narancommunity.app.adapter.WallListAdapter;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.entity.Banner;
import com.narancommunity.app.entity.MOrders;
import com.narancommunity.app.entity.WallListData;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.sunfusheng.marqueeview.MarqueeView;
import com.sunfusheng.marqueeview.Utils;
import com.zone.zbanner.ViewPagerCircle;
import com.zone.zbanner.indicator.IndicatorView;
import com.zone.zbanner.indicator.type.LineIndicator;
import com.zone.zbanner.indicator.type.abstarct.ShapeIndicator;
import com.zone.zbanner.simpleadapter.PagerAdapterCircle_Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2017/12/20 10:35
 * Email：120760202@qq.com
 * FileName :
 */

public class IndexFindFragment extends Fragment {
    TextView tvDetail;
    MarqueeView marqueeView;
    @BindView(R.id.tv_rec)
    TextView tvRec;
    @BindView(R.id.tv_assistant)
    TextView tvAssistant;
    @BindView(R.id.tv_topic)
    TextView tvTopic;
    @BindView(R.id.tv_range)
    TextView tvRange;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
//    @BindView(R.id.swipe_refresh)
//    SwipeRefreshLayout swipeRefresh;

    private ViewPagerCircle viewPager;
    private IndicatorView indicatorView;
    PagerAdapterCircle_Image mviewPager;
    private ShapeIndicator lineIndicator;
    WallListAdapter adapter;

    List<MOrders> listData = new ArrayList<>();
    int pageSize = 10, pageNum = 1;
    List<Banner> listBanner;

    public static IndexFindFragment newInstance() {

        IndexFindFragment fragment = new IndexFindFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listBanner = new ArrayList<>();
        Banner mbanner;
        for (int i = 0; i < 5; i++) {
            mbanner = new Banner();
            mbanner.setUrl("http://img.sccnn.com/bimg/337/43776.jpg");
            listBanner.add(mbanner);
        }
    }

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_index_find, container, false);
            ButterKnife.bind(this, rootView);

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void setView() {
        setMarqueen();
        setBanner();
        setLatest();
        getData();
    }

    private void setLatest() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new WallListAdapter(getContext(), listData, 1);
        adapter.setListener(new com.narancommunity.app.MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                startActivity(new Intent(getContext(), WishDetailAct.class).putExtra("orderId", listData.get(position)
                        .getOrderId()));
            }

            @Override
            public void OnDelClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void setBanner() {
        viewPager = rootView.findViewById(R.id.pager);
        indicatorView = rootView.findViewById(R.id.indicatorView);

        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(40);
//        viewPager.setPageTransformer(true, null);
        mviewPager = new PagerAdapterCircle_Image(getContext(), listBanner, true) {
            @Override
            public void setImage(ImageView iv, int position) {
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(getActivity()).load(listBanner.get(position).getUrl()).centerCrop()
                        .placeholder(R.mipmap.banner)
                        .error(R.mipmap.banner).dontAnimate().into(iv);
            }
        };
        viewPager.setAdapter(mviewPager);

        indicatorView.setViewPager(viewPager);
        lineIndicator = new LineIndicator(Utils.dip2px(getContext(), 30), Utils.dip2px(getContext(), 2));
//        BaseIndicator baseIndicator = new BaseIndicator(Utils.dip2px(getContext(), 30), Utils.dip2px(getContext(), 2)) {
//            @Override
//            public Bitmap getDefaultBitmap(int position) {
//                @SuppressLint("ResourceType") InputStream is = getResources().openRawResource(R.mipmap.group_3);
//                Bitmap mBitmap = BitmapFactory.decodeStream(is);
//                return mBitmap;
//            }
//
//            @Override
//            public Bitmap getSelectedBitmap(int position) {
//                @SuppressLint("ResourceType") InputStream is = getResources().openRawResource(R.mipmap.group_7);
//                Bitmap mBitmap = BitmapFactory.decodeStream(is);
//                return mBitmap;
//            }
//        };
//        baseIndicator.setIndicatorView();
//        IndicatorView indicatorView = new IndicatorView(getContext());
//        indicatorView.setIndicator(baseIndicator);
//        lineIndicator.setIndicatorView(indicatorView);
        lineIndicator.setBetweenMargin(Utils.dip2px(getContext(), 8));
        indicatorView.setIndicator(lineIndicator);

//        lineIndicator = new LineIndicator(Utils.dip2px(getContext(),20),Utils.dip2px(getContext(),2)).setShapeEntity
//                (new ShapeIndicator.ShapeEntity().setHaveFillColor(true),
//                        new ShapeIndicator.ShapeEntity().setStrokeWidthHalf(1.5F).setFillColor(Color.WHITE).setHaveStrokeColor(true));

        viewPager.openTimeCircle();
    }

    private void setMarqueen() {
        marqueeView = rootView.findViewById(R.id.marqueeView);

        final List<String> info = new ArrayList<>();
        info.add("大家好，我是fancy。");
        info.add("欢迎大家关注我哦！");
        info.add("新浪微博：fancinest");
//        marqueeView.setNotices(info);
        marqueeView.startWithList(info);

        tvDetail = rootView.findViewById(R.id.tv_detail);
        tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "" + info.get(marqueeView.getPosition()), Toast.LENGTH_SHORT).show();
            }
        });
        // 在代码里设置自己的动画
        marqueeView.startWithList(info, R.anim.anim_bottom_in, R.anim.anim_top_out);
    }

    @Override
    public void onStart() {
        super.onStart();
//        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
//        marqueeView.stopFlipping();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        NRClient.getDonateWall(map, new ResultCallback<Result<WallListData>>() {
            @Override
            public void onSuccess(Result<WallListData> result) {
                setList(result.getData());
            }

            @Override
            public void onFailure(Throwable throwable) {
                com.narancommunity.app.common.Utils.showErrorToast(getContext(), throwable);
            }
        });
    }

    private void setList(WallListData data) {
        if (data != null && data.getOrders() != null && data.getOrders().size() > 0) {
            listData.clear();
            adapter.clear();
            listData.addAll(data.getOrders());
            adapter.setList(listData);
            adapter.notifyDataSetChanged();
        }
        LoadDialog.dismiss(getContext());
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

    @OnClick({R.id.tv_rec, R.id.tv_assistant, R.id.tv_topic, R.id.tv_range, R.id.ln_make_wish, R.id.ln_donate})
    public void onViewClicked(View view) {
        Intent it = new Intent(getContext(), FindFourAct.class);
        switch (view.getId()) {
            case R.id.tv_rec:
                it.putExtra("tag", 0);
                startActivity(it);
                break;
            case R.id.tv_assistant:
                it.putExtra("tag", 1);
                startActivity(it);
                break;
            case R.id.tv_topic:
                it.putExtra("tag", 2);
                startActivity(it);
                break;
            case R.id.tv_range:
                Intent intent = new Intent(getContext(), RangeAct.class);
                startActivity(intent);
                break;
            case R.id.ln_make_wish:
                Intent wishIntent = new Intent(getContext(), WallListAct.class);
                wishIntent.putExtra("isWish", true);
                startActivity(wishIntent);
                break;
            case R.id.ln_donate:
                Intent donateIntent = new Intent(getContext(), WallListAct.class);
                donateIntent.putExtra("isWish", false);
                startActivity(donateIntent);
                break;
        }
    }
}
