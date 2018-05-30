package com.narancommunity.app.activity.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.card.CardPagerAdapter;
import com.narancommunity.app.adapter.card.ShadowTransformer;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.ShuzhaiData;
import com.narancommunity.app.entity.ShuzhaiItem;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2018/4/28 11:37
 * Email：120760202@qq.com
 * FileName :
 */

public class ShuzhaiAct extends BaseActivity {

    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    int pageSize = 5;
    int pageNum = 1;
    private int TOTAL_PAGE = 1;
    List<ShuzhaiItem> listData = new ArrayList<>();

    private boolean isLastPage = false;
    private boolean isDragPage = false;
//    private boolean canJumpPage = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shuzhai);
        ButterKnife.bind(this);
        toolbar.setTitle("书摘");
        setBar(toolbar);
        setView();
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void setView() {
        mCardAdapter = new CardPagerAdapter(getContext());
        mCardAdapter.setData(listData);
        mCardShadowTransformer = new ShadowTransformer(viewPager, mCardAdapter);
        viewPager.setAdapter(mCardAdapter);
        viewPager.setPageTransformer(false, mCardShadowTransformer);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 在屏幕滚动过程中不断被调用
             * @param position
             * @param positionOffset   是当前页面滑动比例，如果页面向右翻动，这个值不断变大，最后在趋近1的情况后突变为0。如果页面向左翻动，这个值不断变小，最后变为0
             * @param positionOffsetPixels   是当前页面滑动像素，变化情况和positionOffset一致
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (isLastPage && isDragPage && positionOffsetPixels == 0) {   //当前页是最后一页，并且是拖动状态，并且像素偏移量为0
//                    if (canJumpPage) {
//                        canJumpPage = false;
                    if (pageNum <= TOTAL_PAGE) {
                        getData();
                    } else
                        Toaster.toast(getContext(), "已无更多数据");
//                    }
                }
            }

            /**
             * 这个方法有一个参数position，代表哪个页面被选中
             * @param position    当前页的索引
             */
            @Override
            public void onPageSelected(int position) {
                isLastPage = position == listData.size() - 1;

            }

            /**
             * 在手指操作屏幕的时候发生变化
             * @param state   有三个值：0（END）,1(PRESS) , 2(UP) 。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

                isDragPage = state == 1;

            }
        });
        mCardAdapter.setListener(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                likeIt(position);
            }

            @Override
            public void OnDelClick(int position) {
                showShareBoard();
            }
        });
    }

    private void likeIt(int position) {
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("contentId", listData.get(position).getContentId());
        NRClient.likeEssay(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                Toaster.toast(getContext(), "点赞成功");
            }
        });
    }

    void getData() {
        LoadDialog.show(getContext());
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        NRClient.getShuzhaiList(map, new ResultCallback<Result<ShuzhaiData>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<ShuzhaiData> result) {
                LoadDialog.dismiss(getContext());
                setData(result.getData());
            }
        });
    }

    private void setData(ShuzhaiData data) {
        if (data == null) {
            return;
        }
//        if (pageNum == 1)
//            listData.clear();
        if (data.getContents() != null && data.getContents().size() > 0) {
            listData.addAll(data.getContents());
            mCardAdapter.setData(listData);
//            pageNum++;
        }
        mCardAdapter.notifyDataSetChanged();
    }
}
