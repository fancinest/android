package com.narancommunity.app.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2018/5/26 15:00
 * Email：120760202@qq.com
 * FileName :
 */
public class PushInfoAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_push_info);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("推送消息");
    }
}
