package com.narancommunity.app.activity.general;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/5/28 11:52
 * Email：120760202@qq.com
 * FileName :
 */
public class VerifyMobileAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.btn_go)
    Button btnGo;

    String mobile = "";
    String code = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_verify_mobile);
        ButterKnife.bind(this);
        toolbar.setTitle("手机号验证");
        setBar(toolbar);
        etMobile.setMaxEms(11);
    }

    @OnClick({R.id.btn_get_code, R.id.btn_go})
    public void onViewClicked(View view) {
        mobile = etMobile.getText().toString();
        String code = etCode.getText().toString();
        switch (view.getId()) {
            case R.id.btn_get_code:
                if (!"".equals(mobile))
                    getCode(mobile);
                else if (mobile.length() != 11) {
                    Toaster.toast(getContext(), "请输入正确的手机号");
                } else {
                    Toaster.toast(getContext(), "请输入手机号");
                    etMobile.requestFocus();
                    return;
                }
                break;
            case R.id.btn_go:
                if ("".equals(mobile)) {
                    Toaster.toast(getContext(), "请输入手机号");
                    etMobile.requestFocus();
                    return;
                } else if (mobile.length() != 11) {
                    etMobile.requestFocus();
                    Toaster.toast(getContext(), "请输入正确的手机号");
                    return;
                }
                if (code.equals("")) {
                    Toaster.toast(getContext(), "请输入验证码");
                    etCode.requestFocus();
                    return;
                }
                Intent it = new Intent();
                it.putExtra("mobile", mobile);
                it.putExtra("code", code);
                setResult(11, it);
                finish();
                break;
        }
    }

    private void getCode(String mobile) {
        LoadDialog.show(this);
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("isTest", true);
        NRClient.getAuthoriseCode(map, new ResultCallback<Result<String>>() {
            @Override
            public void onSuccess(Result<String> result) {
                LoadDialog.dismiss(getContext());
                Toaster.toast(getContext(), "请注意查收验证码！");
                mSendVerificationCodeCountDownTimer.start();
                if (!MApplication.isRelease)
                    etCode.setText("" + result.getMsg());
                code = result.getMsg();
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }
        });
    }

    private CountDownTimer mSendVerificationCodeCountDownTimer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            if (btnGetCode != null) {
                btnGetCode.setEnabled(false);
                btnGetCode.setBackgroundResource(R.drawable.round_corner_grey);
                btnGetCode.setText(
                        getString(R.string.send_verification_code_countdown,
                                "" + millisUntilFinished / 1000));
            }
        }

        @Override
        public void onFinish() {
            if (btnGetCode != null) {
                btnGetCode.setBackgroundResource(R.drawable.round_corner_green);
                btnGetCode.setEnabled(true);
                btnGetCode.setText(R.string.send_verification_code);
            }
        }
    };

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
