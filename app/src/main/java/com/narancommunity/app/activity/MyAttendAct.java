//package com.narancommunity.app.activity;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//
//import com.flyco.tablayout.SlidingTabLayout;
//import com.flyco.tablayout.listener.OnTabSelectListener;
//import com.narancommunity.app.BaseActivity;
//import com.narancommunity.app.MyParticipateFragment;
//import com.narancommunity.app.MApplication;
//import com.narancommunity.app.R;
//import com.narancommunity.app.common.CenteredToolbar;
//import com.narancommunity.app.common.LoadDialog;
//import com.narancommunity.app.common.Utils;
//import com.narancommunity.app.entity.AssistantEntity;
//import com.narancommunity.app.net.NRClient;
//import com.narancommunity.app.net.Result;
//import com.narancommunity.app.net.ResultCallback;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Writer：fancy on 2018/5/3 10:51
// * Email：120760202@qq.com
// * FileName :
// */
//
//public class MyAttendAct extends BaseActivity implements OnTabSelectListener {
//
//    @BindView(R.id.slide_tab)
//    SlidingTabLayout slideTab;
//    @BindView(R.id.vp)
//    ViewPager vp;
//    @BindView(R.id.toolbar)
//    CenteredToolbar toolbar;
//
//    private String[] mTitles = {"   援助任务   ", "   公益活动   "};
//    private ArrayList<Fragment> mFragments = new ArrayList<>();
//    MyParticipateFragment secondFrag = MyParticipateFragment.newInstance();
//    MyParticipateFragment thirdFrag = MyParticipateFragment.newInstance();
//
//    int pageSize = 5;
//    int pageNum = 1;
//    private int TOTAL_PAGE = 1;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_normal_list_view_with_swipe_refresh);
//        ButterKnife.bind(this);
//        setBar(toolbar);
//        toolbar.setTitle("我的参与");
//
//        secondFrag.setTag(0);
//        thirdFrag.setTag(1);
//        mFragments.add(secondFrag);
//        mFragments.add(thirdFrag);
//
//        Collections.addAll(mFragments);
//        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//        slideTab.setOnTabSelectListener(this);
//        slideTab.setViewPager(vp);
//    }
//
//    private class MyPagerAdapter extends FragmentPagerAdapter {
//
//        public MyPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragments.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mTitles[position];
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragments.get(position);
//        }
//
//    }
//
//    @Override
//    public void onTabSelect(int position) {
//
//    }
//
//    @Override
//    public void onTabReselect(int position) {
//
//    }
//}
