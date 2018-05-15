package com.narancommunity.app.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2018/5/2 12:00
 * Email：120760202@qq.com
 * FileName :
 */

public class MySignAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sign);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("我的签到");

    }
}
