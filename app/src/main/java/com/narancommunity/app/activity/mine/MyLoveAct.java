package com.narancommunity.app.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2018/5/10 10:15
 * Email：120760202@qq.com
 * FileName :
 */

public class MyLoveAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.slide_tab)
    SlidingTabLayout slideTab;
    @BindView(R.id.vp)
    ViewPager vp;

    private String[] mTitles = {"    等待中    ", "    进行中    ", "    已完成    "};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    int mPosition = 0;//当前页

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_attend);
        ButterKnife.bind(this);
        toolbar.setTitle("我的爱心");
        setBar(toolbar);

        MyLoveFragment one = MyLoveFragment.newInstance();
        one.setType(0);
        MyLoveFragment two = MyLoveFragment.newInstance();
        two.setType(1);
        MyLoveFragment three = MyLoveFragment.newInstance();
        three.setType(2);
        Collections.addAll(mFragments, one, two, three);

        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
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
}
