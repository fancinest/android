package com.narancommunity.app.activity.general;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/1/29 15:25
 * Email：120760202@qq.com
 * FileName :
 */

public class AppFeedbackAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_release)
    Button btnRelease;
    @BindView(R.id.ln_success)
    LinearLayout lnSuccess;

    boolean isSuccess = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_app_feedback);
        ButterKnife.bind(this);

        toolbar.setTitle("意见反馈");
        setBar(toolbar);
    }

    @OnClick(R.id.btn_release)
    public void onViewClicked() {
        if (!isSuccess) {
            LoadDialog.show(getContext(), "提交中...");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isSuccess = true;
                    LoadDialog.dismiss(getContext());
                    toolbar.setTitle("提交成功");
                    btnRelease.setText("完成");
                    lnSuccess.setVisibility(View.VISIBLE);
                    etContent.setVisibility(View.GONE);
                }
            }, 1000);
        } else finish();
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
