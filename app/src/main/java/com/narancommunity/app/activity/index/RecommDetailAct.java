package com.narancommunity.app.activity.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/5/7 14:06
 * Email：120760202@qq.com
 * FileName :
 */

public class RecommDetailAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_wish_collect)
    TextView tvWishCollect;
    @BindView(R.id.ln_collect)
    LinearLayout lnCollect;
    @BindView(R.id.tv_wish_comment)
    TextView tvWishComment;
    @BindView(R.id.ln_comment)
    LinearLayout lnComment;
    @BindView(R.id.tv_wish_share)
    TextView tvWishShare;
    @BindView(R.id.ln_like)
    LinearLayout lnLike;
    @BindView(R.id.ln_tool)
    LinearLayout lnTool;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_recomm_detail);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("每周推荐");
    }

    @OnClick({R.id.ln_collect, R.id.ln_comment, R.id.ln_like})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ln_collect:
                break;
            case R.id.ln_comment:
                break;
            case R.id.ln_like:
                break;
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
