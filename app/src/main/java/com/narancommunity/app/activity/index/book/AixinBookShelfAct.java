package com.narancommunity.app.activity.index.book;

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
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.fragment.BookSortSonFragment;
import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.activity.index.DonateBookAct;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.entity.BookSortEntity;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2018/4/11 15:08
 * Email：120760202@qq.com
 * FileName : 爱心书架 {@link BookCommunityAct}
 */

public class AixinBookShelfAct extends BaseActivity implements OnTabSelectListener {
    @BindView(R.id.slide_tab)
    SlidingTabLayout slideTab;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.iv_release)
    ImageView ivRelease;

    //    private String[] mTitles = {"全部书籍", "教育教科", "文学小说", "人文社科", "童书绘本", "成功励志", "生活艺术", "金融经管", "其他书籍"};
//    String[] realType = new String[]{"", "BOOK_EDUCATION", "BOOK_NOVEL", "BOOK_HUMANITY", "BOOK_CHILD", "BOOK_SUCCESS", "BOOK_LIFE", "BOOK_FINANCE", "BOOK_OTHER"};
    String[] mTitles;
    String[] realType;

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_aixin_book);////跟书香社区一个布局
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("爱心书架");
        List<BookSortEntity> list = (List<BookSortEntity>) getIntent().getSerializableExtra("sort");

        mTitles = new String[list.size()];
        realType = new String[list.size()];
//        mTitles = getTitles(list);
//        realType = getRealType(list);
        for (int i = 0; i < list.size(); i++) {
            BookSortSonFragment son = BookSortSonFragment.newInstance();
            mTitles[i] = list.get(i).getClassifyName();
            realType[i] = list.get(i).getClassifyValue();
            son.setType(i, list.get(i).getClassifyValue());
            mFragments.add(son);
        }

//        BookSortSonFragment all = BookSortSonFragment.newInstance();
//        all.setType(0, realType[0]);
//        BookSortSonFragment tech = BookSortSonFragment.newInstance();
//        tech.setType(1, realType[1]);
//        BookSortSonFragment novel = BookSortSonFragment.newInstance();
//        novel.setType(2, realType[2]);
//        BookSortSonFragment humanity = BookSortSonFragment.newInstance();
//        humanity.setType(3, realType[3]);
//        BookSortSonFragment graphic = BookSortSonFragment.newInstance();
//        graphic.setType(4, realType[4]);
//        BookSortSonFragment success = BookSortSonFragment.newInstance();
//        success.setType(5, realType[5]);
//        BookSortSonFragment art = BookSortSonFragment.newInstance();
//        art.setType(6, realType[6]);
//        BookSortSonFragment financial = BookSortSonFragment.newInstance();
//        financial.setType(7, realType[7]);
//        BookSortSonFragment others = BookSortSonFragment.newInstance();
//        others.setType(8, realType[8]);
//        Collections.addAll(mFragments, all, tech, novel, humanity, graphic
//                , success, art, financial, others);

        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        slideTab.setOnTabSelectListener(this);
        slideTab.setViewPager(vp);

        ivRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state = MApplication.getAuthorisedState(getContext());
                if (state.equals(MApplication.AUTH_INITIAL)) {
                    showPopView(ivRelease, "分享赠送陌生人\n实名认证更安全");
                } else if (state.equals(MApplication.AUTH_FAIL)) {
                    showPopView(ivRelease, "您的实名认证失败，无法操作，是否重新提交？");
                } else if (state.equals(MApplication.AUTH_SUCCESS)) {
                    startActivity(new Intent(getContext(), DonateBookAct.class));
                } else if (state.equals(MApplication.AUTH_GOING)) {
                    Toaster.toastLong(getContext(), "您尚未通过实名认证,请等待认证完成再操作！");
                }
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
}
