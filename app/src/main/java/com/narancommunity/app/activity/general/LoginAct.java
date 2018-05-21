package com.narancommunity.app.activity.general;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.common.DBHelper;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.UserInfo;
import com.narancommunity.app.net.AppConstants;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.NRConfig;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
import com.narancommunity.app.net.ServiceFactory;
import com.snappydb.DB;
import com.snappydb.SnappydbException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/1/5 10:50
 * Email：120760202@qq.com
 * FileName :
 */

public class LoginAct extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.ctv_is_agree)
    CheckedTextView ctvIsAgree;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.center)
    View center;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.tv_sina)
    TextView tvSina;
    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    @BindView(R.id.email_login_form)
    LinearLayout emailLoginForm;
    @BindView(R.id.login_form)
    ScrollView loginForm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_login);
        ButterKnife.bind(this);
        MApplication.putActivity("login", LoginAct.this);
    }

    @OnClick({R.id.tv_login, R.id.tv_reset, R.id.tv_register, R.id.tv_qq,
            R.id.tv_sina, R.id.tv_wechat, R.id.tv_agreement, R.id.ctv_is_agree
            , R.id.tv_get_code})
    public void onViewClicked(View view) {
        String phone = etPhone.getText().toString();
        switch (view.getId()) {
            case R.id.tv_login:
                if (Utils.isEmpty(phone)) {
                    Toaster.toast(this, "请输入手机号码");
                    etPhone.requestFocus();
                    return;
                }
                if (phone.length() != 11) {
                    Toaster.toast(this, "手机号码输入错误！");
                    etPhone.requestFocus();
                    return;
                }
                String code = etVerifyCode.getText().toString();
                if (Utils.isEmpty(code)) {
                    Toaster.toast(this, "请输入验证码");
                    etVerifyCode.requestFocus();
                    return;
                }
                doLogin(phone, code);
                break;
            case R.id.tv_qq:
                break;
            case R.id.tv_sina:
                break;
            case R.id.tv_wechat:
                break;
            case R.id.tv_agreement:
                startActivity(new Intent(getContext(), AgreementAct.class)
                        .putExtra("url", ServiceFactory.API_BASE_URL + NRConfig.URL_PROTOCOL));
                break;
            case R.id.ctv_is_agree:
                ctvIsAgree.setChecked(!ctvIsAgree.isChecked());
                break;
            case R.id.tv_get_code:
                if (Utils.isEmpty(phone)) {
                    Toaster.toast(this, "请输入手机号码");
                    return;
                }
                if (phone.length() != 11) {
                    Toaster.toast(this, "手机号码输入错误！");
                    etPhone.requestFocus();
                    return;
                }
                doGetCode(phone);
                break;
        }
    }

    private void doLogin(String phone, String code) {
        LoadDialog.show(this, "正在为您登录...");
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("verifyCode", code);
        map.put("deviceNo", Utils.getDeviceID(getContext()));
        NRClient.login(map, new ResultCallback<Result<UserInfo>>() {
            @Override
            public void onSuccess(Result<UserInfo> result) {
                LoadDialog.dismiss(LoginAct.this);
                Toaster.toast(LoginAct.this, "登录成功！");
                saveUserInfo(result.getData());
                MApplication.finishAllActivity();
                finish();
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(LoginAct.this);
                Utils.showErrorToast(getContext(), throwable);
            }
        });
    }

    private void doGetCode(String phone) {
        LoadDialog.show(this);
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("isTest", MApplication.isTest);
        NRClient.getVerifyCode(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                mSendVerificationCodeCountDownTimer.start();
                Toaster.toast(getContext(), "请注意查收！");
                if (!MApplication.isRelease)
                    etVerifyCode.setText("" + Utils.getValue(result.getMsg()));
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
            if (tvGetCode != null) {
                tvGetCode.setEnabled(false);
                tvGetCode.setBackgroundResource(R.drawable.round_corner_grey);
                tvGetCode.setText(
                        getString(R.string.send_verification_code_countdown,
                                "" + millisUntilFinished / 1000));
            }
        }

        @Override
        public void onFinish() {
            if (tvGetCode != null) {
                tvGetCode.setBackgroundResource(R.drawable.round_corner_green);
                tvGetCode.setEnabled(true);
                tvGetCode.setText(R.string.send_verification_code);
            }
        }
    };

    /**
     * 保存用户信息
     *
     * @param data
     */
    private void saveUserInfo(UserInfo data) {
        try {
            DB snappyDb =
                    DBHelper.getDB(this);
            snappyDb.put(AppConstants.USER_INFO, data);
            snappyDb.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
        }
    }
}
