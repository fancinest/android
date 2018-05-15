package com.narancommunity.app.activity.index;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.general.AuthoriseFirstAct;
import com.narancommunity.app.activity.fragment.CommunitySonFragment;
import com.narancommunity.app.common.CenteredToolbar;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/4/13 15:14
 * Email：120760202@qq.com
 * FileName :  书香社区 {@link AixinBookAct}
 */

public class BookCommunityAct extends BaseActivity implements OnTabSelectListener {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.slide_tab)
    SlidingTabLayout slideTab;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.iv_release)
    ImageView ivRelease;

    private String[] mTitles = {"    以书会友    ", "    书荒互助    "};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    int mPosition = 0;//当前页

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_aixin_book);//跟爱心书屋一个布局
        ButterKnife.bind(this);
        toolbar.setTitle("书香社区");
        setBar(toolbar);
        ivRelease.setImageResource(R.mipmap.side_fabu);

        CommunitySonFragment one = CommunitySonFragment.newInstance();
        one.setType(0);
        CommunitySonFragment two = CommunitySonFragment.newInstance();
        two.setType(1);
        Collections.addAll(mFragments, one, two);

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
//                startActivity(new Intent(getContext(), DonateBookAct.class));
                if (mPosition == 1)
                    showPopView(ivRelease);
                else
                    startActivity(new Intent(getContext(), NeedBookAct.class)
                            .putExtra("tag", 2));
            }
        });
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

    PopupWindow mPop;

    private void showPopView(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.pop_release_two, null);

        if (mPop == null) {
            mPop = new PopupWindow(v, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mPop.setFocusable(true);
            mPop.setOutsideTouchable(true);
            mPop.setBackgroundDrawable(new BitmapDrawable());
            ImageView ivRelease = v.findViewById(R.id.iv_release);
            ImageView ivFind = v.findViewById(R.id.iv_find_book);

            ivRelease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mPop.dismiss();
                    startActivity(new Intent(getContext(), NeedBookAct.class)
                            .putExtra("tag", 1));
                }
            });
            ivFind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPop.dismiss();
                    startActivity(new Intent(getContext(), NeedBookAct.class)
                            .putExtra("tag", 0));
                }
            });
        }
        if (mPop != null && !mPop.isShowing())
            mPop.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    @OnClick(R.id.iv_release)
    public void onViewClicked() {
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
