package com.narancommunity.app.activity.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.fragment.RangeChengjiuFragment;
import com.narancommunity.app.activity.fragment.RangeDaRenFragment;
import com.narancommunity.app.activity.fragment.RangeRangeFragment;
import com.narancommunity.app.common.CenteredToolbar;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2017/12/27 11:39
 * Email：120760202@qq.com
 * FileName :
 */
public class RangeAct extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.slide_tab)
    SlidingTabLayout slideTab;
    @BindView(R.id.vp)
    ViewPager vp;

    private String[] mTitles = {"公益达人", "等级", "成就"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    RangeDaRenFragment firstFrag = RangeDaRenFragment.newInstance();
    RangeRangeFragment secondFrag = RangeRangeFragment.newInstance();
    RangeChengjiuFragment thirdFrag = RangeChengjiuFragment.newInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_range);
        ButterKnife.bind(this);

        setBar(toolbar);
        toolbar.setTitle("公益榜");

        mFragments.add(firstFrag);
        mFragments.add(secondFrag);
        mFragments.add(thirdFrag);

        Collections.addAll(mFragments);
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        slideTab.setOnTabSelectListener(this);
        slideTab.setViewPager(vp);

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


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }
}
