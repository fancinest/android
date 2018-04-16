package com.narancommunity.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.activity.fragment.FindBookSonFragment;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.Toaster;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2018/4/11 15:08
 * Email：120760202@qq.com
 * FileName : 爱心书架 {@link BookCommunityAct}
 */

public class AixinBookAct extends BaseActivity implements OnTabSelectListener {
    @BindView(R.id.slide_tab)
    SlidingTabLayout slideTab;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.iv_release)
    ImageView ivRelease;

    private String[] mTitles = {"全部书籍", "教育教科", "文学小说", "人文社科", "童书绘本", "成功励志", "生活艺术", "金融经管", "其他书籍"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_aixin_book);////跟书香社区一个布局
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("爱心书架");

        FindBookSonFragment all = FindBookSonFragment.newInstance();
        all.setType(0);
        FindBookSonFragment tech = FindBookSonFragment.newInstance();
        tech.setType(1);
        FindBookSonFragment novel = FindBookSonFragment.newInstance();
        novel.setType(2);
        FindBookSonFragment humanity = FindBookSonFragment.newInstance();
        humanity.setType(3);
        FindBookSonFragment graphic = FindBookSonFragment.newInstance();
        graphic.setType(4);
        FindBookSonFragment success = FindBookSonFragment.newInstance();
        success.setType(5);
        FindBookSonFragment art = FindBookSonFragment.newInstance();
        art.setType(6);
        FindBookSonFragment financial = FindBookSonFragment.newInstance();
        financial.setType(7);
        FindBookSonFragment others = FindBookSonFragment.newInstance();
        others.setType(8);
        Collections.addAll(mFragments, all, tech, novel, humanity, graphic
                , success, art, financial, others);

        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        slideTab.setOnTabSelectListener(this);
        slideTab.setViewPager(vp);

        ivRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                /**
                 * 先判断是否已经实名认证
                 * {@link AuthoriseFirstAct}
                 */
                startActivity(new Intent(getContext(), DonateBookAct.class));
            }
        });
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
            startActivity(new Intent(getContext(), SearchBookAct.class));
        }
        return super.onOptionsItemSelected(item);
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

    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }
}
