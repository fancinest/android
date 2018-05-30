package com.narancommunity.app.activity.general;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.Toaster;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Writer：fancy on 2018/5/28 13:55
 * Email：120760202@qq.com
 * FileName :
 */
public class AddIDNumAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_info_edit);
        ButterKnife.bind(this);
        setBar(toolbar);
        final int type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            toolbar.setTitle("输入身份证");
            etContent.setHint("输入身份证");
        } else if (type == 0) {
            toolbar.setTitle("输入姓名");
            etContent.setHint("输入姓名");
        }
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                if (type == 0) {
                    String name = etContent.getText().toString();
                    if (name.equals("")) {
                        Toaster.toast(getContext(), "请输入姓名");
                        return;
                    }
                    it.putExtra("name", etContent.getText().toString());
                    setResult(12, it);
                } else if (type == 1) {
                    String num = etContent.getText().toString();
                    if (num.equals("")) {
                        Toaster.toast(getContext(), "请输入身份证号码!");
                        return;
                    } else if (etContent.getText().toString().length() != 18) {
                        etContent.requestFocus();
                        Toaster.toastLong(getContext(), "身份证号码长度为18位哦");
                        return;
                    }
                    it.putExtra("idNum", etContent.getText().toString());
                    setResult(11, it);
                }
                finish();
            }
        });
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
