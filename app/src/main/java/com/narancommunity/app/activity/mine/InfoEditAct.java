package com.narancommunity.app.activity.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.UserInfo;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2017/3/20 10:37
 * Email：120760202@qq.com
 * FileName : 详情编辑页面
 */

public class InfoEditAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.ln_normal)
    LinearLayout lnNormal;
    @BindView(R.id.et_sign)
    EditText etSign;
    @BindView(R.id.ln_edit)
    LinearLayout lnEdit;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    int type = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_info_edit);
        ButterKnife.bind(this);
        setBar(toolbar);

        toolbar.setTitle(getString(R.string.info_edit));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setElevation(0);
        type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            toolbar.setTitle("个人资料");
            lnEdit.setVisibility(View.GONE);
            etContent.setHint("输入您的昵称");
            lnNormal.setVisibility(View.VISIBLE);
        } else {
            toolbar.setTitle("编写个性签名");
            lnEdit.setVisibility(View.VISIBLE);
            lnNormal.setVisibility(View.GONE);
        }
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etContent.getText().toString();
                String contentSign = etSign.getText().toString();
                Map<String, Object> map = new HashMap<>();
                if (type == 0) {
                    if (content.equals("")) {
                        etContent.requestFocus();
                        Toaster.toast(getContext(), "请输入内容！");
                    }
                    map.put("nickName", content);
                } else {
                    if (contentSign.equals("")) {
                        etSign.requestFocus();
                        Toaster.toast(getContext(), "请输入内容！");
                    }
                    map.put("remark", contentSign);
                }
                updateInfo(map);
            }
        });
    }

    private void updateInfo(final Map<String, Object> map) {
        map.put("accessToken", MApplication.getAccessToken());
        LoadDialog.show(getContext());
        NRClient.saveUserInfo(map, new ResultCallback<Result<UserInfo>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Utils.showErrorToast(getContext(), throwable);
                LoadDialog.dismiss(getContext());
            }

            @Override
            public void onSuccess(Result<UserInfo> result) {
                LoadDialog.dismiss(getContext());
                MApplication.setUserInfo(result.getData());
                finish();
            }
        });
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }
}
