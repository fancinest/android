package com.narancommunity.app.activity.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.general.AuthoriseFirstAct;
import com.narancommunity.app.activity.general.LoginAct;
import com.narancommunity.app.adapter.MeFunctionAdapter;
import com.narancommunity.app.common.ItemDecoration.GridItemDecoration;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.MeFunctionEntity;
import com.narancommunity.app.entity.UserInfo;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2017/12/19 14:41
 * Email：120760202@qq.com
 * FileName : 首页片段
 */

public class MeFragment extends Fragment {
    @BindView(R.id.tv_coin)
    TextView tvCoin;
    @BindView(R.id.iv_icon)
    SelectableRoundedImageView ivIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_intro)
    TextView tvIntro;
    @BindView(R.id.tv_now)
    TextView tvNow;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.ln_exp)
    LinearLayout lnExp;
    @BindView(R.id.pb_exp)
    ProgressBar pbExp;
    @BindView(R.id.tv_love)
    TextView tvLove;
    @BindView(R.id.ln_love)
    LinearLayout lnLove;
    @BindView(R.id.pb_love)
    ProgressBar pbLove;
    @BindView(R.id.tv_authorise)
    TextView tvAuthorise;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    MeFunctionAdapter adapter;
    List<MeFunctionEntity> list;

    public static MeFragment newInstance() {

        MeFragment fragment = new MeFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_me, container, false);

        ButterKnife.bind(this, rootView);
        setList();
        setAdapter();
        return rootView;
    }

    private void setView() {
        UserInfo userInfo = MApplication.getUserInfo(getContext());
        if (userInfo != null) {
            String url = Utils.getValue(userInfo.getPhoto());
            if (!"".equals(url))
                Utils.setImgF(getContext(), url, ivIcon);
            else Utils.setImgF(getContext(), R.mipmap.zw_morentouxiang, ivIcon);
            tvName.setText(Utils.getValue(userInfo.getNickName()) + "");
            tvIntro.setText(Utils.getValue(userInfo.getRemark()) + "");

            tvId.setText("心愿ID：" + userInfo.getAccountId());
            Integer now = Utils.getValue(userInfo.getNowExperience());
            tvNow.setText("" + now);
            Integer top = Utils.getValue(userInfo.getTopExperience());
            Integer love = Utils.getValue(userInfo.getNowLove());
            tvLove.setText("" + love);
            if (top != 0) {
                tvAll.setText("/" + top);
                int percent = (int) getPercent(now, top);
                pbExp.setProgress(percent);
            } else pbExp.setProgress(0);
        } else Utils.setImgF(getContext(), R.mipmap.zw_morentouxiang, ivIcon);

        if (MApplication.getUserInfo(getContext()) != null &&
                MApplication.getUserInfo(getContext()).getCertificationType().equals("SUCCESS")) {
            tvAuthorise.setText("申请认证慈善使者");
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_renzhengcishanshizhe);
            tvAuthorise.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//                tvAuthorise.setBackgroundColor(getResources().getColor(R.color.login_gray));
        } else if (MApplication.getUserInfo(getContext()) != null &&
                MApplication.getUserInfo(getContext()).getCertificationType().equals("FAIL")) {
            tvAuthorise.setText("申请实名认证失败");
            tvAuthorise.setTextColor(getResources().getColor(R.color.appRed));
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_weirenzheng);
            tvAuthorise.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        } else if (MApplication.getUserInfo(getContext()) != null &&
                (MApplication.getUserInfo(getContext()).getCertificationType().equals("INITIAL") ||
                        MApplication.getUserInfo(getContext()).getCertificationType().equals("GOING"))) {
            tvAuthorise.setText("申请实名认证中");
            tvAuthorise.setTextColor(getResources().getColor(R.color.green_tag));
            Drawable drawable = getResources().getDrawable(R.mipmap.icon_renzhengzhong);
            tvAuthorise.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }
    }

    private float getPercent(Integer now, Integer top) {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        return Float.parseFloat(numberFormat.format((float) now / (float) top * 100));
    }

    String[] names = new String[]{"我的签到", "我的收藏", "我的参与", "我的爱心", "我的心愿", "我的发布",
            "我的地址", "联系客服", "关于那然"};

    private void setList() {

        list = new ArrayList<>();
        int[] ids = new int[]{R.mipmap.wode_btn_wodeqiandao, R.mipmap.wode_btn_wodeshoucang, R.mipmap.wode_btn_wocangyude,
                R.mipmap.wode_btn_wosongchude, R.mipmap.wode_btn_woshoudaode, R.mipmap.wode_btn_wodefabu,
                R.mipmap.wode_btn_wodedizhi, R.mipmap.wode_btn_lianxikefu, R.mipmap.wode_btn_guanyunaran,};
        MeFunctionEntity entity;

        for (int i = 0; i < 9; i++) {
            entity = new MeFunctionEntity();
            entity.setId(ids[i]);
            entity.setName(names[i]);
            ;
            list.add(entity);
        }
    }

    private void setAdapter() {
        adapter = new MeFunctionAdapter(getContext(), list);

        GridItemDecoration divider = new GridItemDecoration.Builder(getContext())
                .setVertical(R.dimen.sort_divider_vertical)
                .setHorizontal(R.dimen.sort_divider_horizontal)
                .setColorResource(R.color.color_dddddd)
                .build();
        GridLayoutManager gridlayout = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridlayout);
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        adapter.setListener(new com.narancommunity.app.MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                switchTo(position);
            }

            @Override
            public void OnDelClick(int position) {

            }
        });
    }

    //    String[] names = new String[]{"我的签到", "我的收藏", "我的参与", "我的爱心", "我的心愿", "我的发布",
//            "我的地址", "联系客服", "关于那然"};
    private void switchTo(int position) {
        String token = MApplication.getAccessToken();
        switch (position) {
            case 0://我的签到
                if (token.equals("")) {
                    startActivity(new Intent(getContext(), LoginAct.class));
                    Toaster.toast(getContext(), "请先登录！");
                    return;
                }
                startActivity(new Intent(getContext(), MySignAct.class));
                break;
            case 1://我的收藏
                if (token.equals("")) {
                    startActivity(new Intent(getContext(), LoginAct.class));
                    Toaster.toast(getContext(), "请先登录！");
                    return;
                }
                startActivity(new Intent(getContext(), MyCollectionAct.class));
                break;
            case 2://我的参与
                if (token.equals("")) {
                    startActivity(new Intent(getContext(), LoginAct.class));
                    Toaster.toast(getContext(), "请先登录！");
                    return;
                }
                startActivity(new Intent(getContext(), MyAttendNewAct.class));
                break;
            case 3://我的爱心
                if (token.equals("")) {
                    startActivity(new Intent(getContext(), LoginAct.class));
                    Toaster.toast(getContext(), "请先登录！");
                    return;
                }
                startActivity(new Intent(getContext(), MyLoveAct.class));
                break;
            case 4://我的心愿
                if (token.equals("")) {
                    startActivity(new Intent(getContext(), LoginAct.class));
                    Toaster.toast(getContext(), "请先登录！");
                    return;
                }
                startActivity(new Intent(getContext(), MyWishAct.class));
                break;
            case 5://我的发布
                if (token.equals("")) {
                    startActivity(new Intent(getContext(), LoginAct.class));
                    Toaster.toast(getContext(), "请先登录！");
                    return;
                }
                startActivity(new Intent(getContext(), MyReleaseAct.class));
                break;
            case 6://我的地址
                if (token.equals("")) {
                    startActivity(new Intent(getContext(), LoginAct.class));
                    Toaster.toast(getContext(), "请先登录！");
                    return;
                }
                startActivity(new Intent(getContext(), AddressAct.class));
                break;
            case 7://联系客服
                break;
            case 8://关于那然
                break;

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i("fancy", "MeFragment");
        if (!hidden) {
            setView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.iv_msg, R.id.iv_set, R.id.tv_authorise, R.id.iv_icon})
    public void onViewClicked(View view) {
        String token = MApplication.getAccessToken();
        switch (view.getId()) {
            case R.id.iv_msg:
                if (token.equals("")) {
                    startActivity(new Intent(getContext(), LoginAct.class));
                    Toaster.toast(getContext(), "请先登录！");
                    return;
                }
                startActivity(new Intent(getContext(), MsgAct.class));
                break;
            case R.id.iv_set:
                startActivity(new Intent(getContext(), SettingAct.class));
                break;
            case R.id.tv_authorise:
                if (token.equals("")) {
                    startActivity(new Intent(getContext(), LoginAct.class));
                    Toaster.toast(getContext(), "请先登录！");
                    return;
                }
                startActivity(new Intent(getContext(), AuthoriseFirstAct.class));
                break;
            case R.id.iv_icon:
                if (token.equals("")) {
                    startActivity(new Intent(getContext(), LoginAct.class));
                    Toaster.toast(getContext(), "请先登录！");
                    return;
                }
                startActivity(new Intent(getContext(), MyInfoAct.class));
                break;
        }
    }
}
