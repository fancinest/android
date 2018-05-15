package com.narancommunity.app.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MyParticipateFragment;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.fragment.CommunitySonFragment;
import com.narancommunity.app.activity.fragment.MyReleaseFragment;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.BookListData;
import com.narancommunity.app.entity.YSHYData;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2018/5/7 16:22
 * Email：120760202@qq.com
 * FileName :  我的发布（个人中心的）
 */
public class MyReleaseAct extends BaseActivity implements OnTabSelectListener {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.slide_tab)
    SlidingTabLayout slideTab;
    @BindView(R.id.vp)
    ViewPager vp;

    private String[] mTitles = {"   发帖   ", "   求助   "};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    MyReleaseFragment secondFrag = MyReleaseFragment.newInstance();
    MyReleaseFragment thirdFrag = MyReleaseFragment.newInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_attend);
        ButterKnife.bind(this);
        toolbar.setTitle("我的发布");
        setBar(toolbar);

        secondFrag.setType(0);
        thirdFrag.setType(1);
        Collections.addAll(mFragments, secondFrag, thirdFrag);

        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        slideTab.setOnTabSelectListener(this);
        slideTab.setViewPager(vp);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }
}
