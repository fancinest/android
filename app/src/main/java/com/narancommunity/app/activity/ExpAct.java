package com.narancommunity.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/1/18 19:25
 * Email：120760202@qq.com
 * FileName :
 */

public class ExpAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.tv_exp)
    TextView tvExp;
    @BindView(R.id.tv_loves)
    TextView tvLoves;
    @BindView(R.id.btn_complete)
    Button btnComplete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_exp);
        ButterKnife.bind(this);
        toolbar.setTitle("捐赠达成");
        setBar(toolbar);
    }

    @OnClick(R.id.btn_complete)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        MApplication.finishAllActivity();
        super.onDestroy();
    }
}
