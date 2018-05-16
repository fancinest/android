package com.narancommunity.app.activity.love;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.PetFragment;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.BookCommunityAct;
import com.narancommunity.app.activity.mine.MeFragment;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.BookListData;
import com.narancommunity.app.entity.CompanyEntity;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2018/5/16 11:57
 * Email：120760202@qq.com
 * FileName :
 */

public class CompanyDetailAct extends BaseActivity {
    Integer id;
    @BindView(R.id.rl_bg)
    ImageView rlBg;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.iv_logo)
    SelectableRoundedImageView ivLogo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_man)
    TextView tvMan;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.card_parent)
    CardView cardParent;
    @BindView(R.id.home_tab)
    RadioButton homeTab;
    @BindView(R.id.tab_rdo_grp)
    RadioGroup tabRdoGrp;
    @BindView(R.id.view)
    View view;
    //    @BindView(R.id.viewPager)
//    ViewPager viewPager;
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    boolean isWhiteIcon = true;

    private LoveIntroduceFragment mLove = LoveIntroduceFragment.newInstance();
    private LoveFootPrintFragment mFootprint = LoveFootPrintFragment.newInstance();
    private LoveIntroduceFragment mHonour = LoveIntroduceFragment.newInstance();
    private final Fragment[] TABS_FRAGMENT = new Fragment[]{
            mLove,
            mFootprint,
            mHonour
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_company_detail);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", 0);

        setScroll();
        setBar(toolbar);
        toolbar.setTitle("");
        toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));

        setFragment();
        getData();
    }

    private void setFragment() {
        homeTab.setChecked(true);
        tabRdoGrp.setOnCheckedChangeListener(mOnTabCheckedChangeListenernew);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.content_frame, mLove)
                .add(R.id.content_frame, mFootprint)
                .add(R.id.content_frame, mHonour)
                .hide(mFootprint)
                .hide(mHonour)
                .commit();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", id);
        NRClient.getOrgDetail(map, new ResultCallback<Result<CompanyEntity>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<CompanyEntity> result) {
                LoadDialog.dismiss(getContext());
                setDetail(result.getData());
            }
        });
    }

    private void setDetail(CompanyEntity data) {
        String logo = Utils.getValue(data.getCompanyImg());
        if (!logo.equals(""))
            Utils.setImgF(getContext(), logo, ivLogo);
        else
            Utils.setImgF(getContext(), R.mipmap.zw_morentouxiang, ivLogo);
        tvName.setText("" + Utils.getValue(data.getCompanyName()));
//        tvDesc.setText("" + Utils.getValue(data.getCompanyContent()));
        tvMan.setText("" + Utils.getValue(data.getCharger()));
        tvDate.setText("" + Utils.getValue(data.getEstablishTime()));
        tvType.setText("" + Utils.getValue(data.getCompanyType()));
        tvTel.setText("" + Utils.getValue(data.getPhone()));
        tvAddress.setText("" + Utils.getValue(data.getProvince()) + Utils.getValue(data.getCity()) + Utils.getValue(data.getCounty()));
        mLove.setView(""+Utils.getValue(data.getCompanyContent()));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setScroll() {
        if (scrollView != null) {
            scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    int y = scrollView.getScrollY();
                    if (y >= 100) {
                        isWhiteIcon = false;
                        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_gray_24dp);
                        invalidateOptionsMenu();
                    } else {
                        isWhiteIcon = true;
                        toolbar.setNavigationIcon(R.mipmap.nav_back);
                        invalidateOptionsMenu();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isWhiteIcon)
            menu.findItem(R.id.action_share).setIcon(R.mipmap.nav_fengxiang);
        else menu.findItem(R.id.action_share).setIcon(R.mipmap.shuzhai_fengxiang_blue1);
        return super.onPrepareOptionsMenu(menu);
    }

//    private class MyPagerAdapter extends FragmentPagerAdapter {
//
//        public MyPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public int getCount() {
//            return TABS_FRAGMENT.length;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return "";
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return TABS_FRAGMENT[position];
//        }
//
//    }

    /**
     * 底部Tab选择监听
     */
    private RadioGroup.OnCheckedChangeListener mOnTabCheckedChangeListenernew
            = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

            RadioButton checkedRdoBtn = (RadioButton) radioGroup.findViewById(checkedId);
            checkedRdoBtn.playSoundEffect(SoundEffectConstants.CLICK);
            int checkedPosition = radioGroup.indexOfChild(checkedRdoBtn);
            if (0 <= checkedPosition && checkedPosition < TABS_FRAGMENT.length) {
                if (checkedPosition != 2)
                    showFragment(checkedPosition);
            }
        }
    };

    private void showFragment(int checkedPosition) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        for (int i = 0, len = TABS_FRAGMENT.length; i < len; i++) {
            if (checkedPosition != i) {
                ft.hide(TABS_FRAGMENT[i]);
            }
        }
        ft.show(TABS_FRAGMENT[checkedPosition]).commit();
    }

}
