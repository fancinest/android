package com.narancommunity.app.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2018/5/26 13:36
 * Email：120760202@qq.com
 * FileName :
 */
public class AuthoriseSuccessAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_authorise_success);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("实名认证成功");
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
}
