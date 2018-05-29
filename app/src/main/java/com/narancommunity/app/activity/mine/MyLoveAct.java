package com.narancommunity.app.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2018/5/10 10:15
 * Email：120760202@qq.com
 * FileName :
 */

public class MyLoveAct extends BaseActivity implements OnTabSelectListener {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.slide_tab)
    SlidingTabLayout slideTab;
    @BindView(R.id.vp)
    ViewPager vp;

    private String[] mTitles = {"    等待中    ", "    进行中    ", "    已完成    "};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    int mPosition = 0;//当前页
    MyPagerAdapter adapter;

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

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        slideTab.setViewPager(vp);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
    public void onTabSelect(int position) {
        mPosition = position;
    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("fancy", "执行了1次");
        adapter.getItem(mPosition).onActivityResult(requestCode, resultCode, data);
    }
}
