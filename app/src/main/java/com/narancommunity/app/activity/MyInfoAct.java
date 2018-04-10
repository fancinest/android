package com.narancommunity.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/1/4 15:45
 * Email：120760202@qq.com
 * FileName :
 */

public class MyInfoAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.iv_head)
    SelectableRoundedImageView ivHead;
    @BindView(R.id.ln_head)
    LinearLayout lnHead;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tr_sex)
    LinearLayout trSex;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tr_birthday)
    LinearLayout trBirthday;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_sign)
    TextView tvSign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_info);
        ButterKnife.bind(this);

        setBar(toolbar);
        toolbar.setTitle("个人资料");
    }

    @OnClick({R.id.iv_head, R.id.tv_sex, R.id.tv_birthday, R.id.tv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                break;
            case R.id.tv_sex:
                break;
            case R.id.tv_birthday:
                break;
            case R.id.tv_address:
                break;
        }
    }
}
