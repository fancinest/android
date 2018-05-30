package com.narancommunity.app.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Writer：fancy on 2018/5/28 16:21
 * Email：120760202@qq.com
 * FileName :
 */
public class SearchAddressAct extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_address);
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
