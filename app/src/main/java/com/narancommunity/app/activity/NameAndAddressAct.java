package com.narancommunity.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.net.NRClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/1/9 11:44
 * Email：120760202@qq.com
 * FileName :
 */

public class NameAndAddressAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_sure)
    Button btnSure;

    boolean isName = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_name_address);
        ButterKnife.bind(this);
        setBar(toolbar);
        isName = getIntent().getBooleanExtra("isName", true);
        toolbar.setTitle(isName ? "填写姓名" : "填写地址");
        etContent.setHint(isName ? "请输入姓名" : "请填写详细地址(只需填写区域后方详细地址即可)");
        etContent.setMinHeight(isName ? Utils.dip2px(getContext(), 40) : Utils.dip2px(getContext(), 80));
    }

    @OnClick(R.id.btn_sure)
    public void onViewClicked() {
        if (isName) {
        } else {

        }
    }
}
