package com.narancommunity.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.narancommunity.app.MApplication;
import com.narancommunity.app.MainActivity;
import com.narancommunity.app.R;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.net.AppConstants;
import com.narancommunity.app.common.DBHelper;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.NRConfig;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.UserInfo;
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
 * Writer：fancy on 2017/3/8 16:43
 * Email：120760202@qq.com
 * FileName :
 */

public class ForgetAct extends AppCompatActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.et_repeat_psd)
    EditText etRepeatPsd;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.ctv_is_agree)
    CheckedTextView ctvIsAgree;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.til_psd)
    TextInputLayout tilPsd;
    @BindView(R.id.til_repeat_psd)
    TextInputLayout tilRepeatPsd;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;

    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.transparent));
//            window.setNavigationBarColor(getResources().getColor(R.color.transparent));
//        }
        setContentView(R.layout.act_register);
        ButterKnife.bind(this);

        tvTitle.setText(getString(R.string.action_forget_psd_two));
        tvRegister.setText(getString(R.string.submit));

        tvAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvAgreement.getPaint().setAntiAlias(true);
        tilPsd.setHint(getString(R.string.type_new_psd));
        tilRepeatPsd.setHint(getString(R.string.repead_new_psd));
    }

    @OnClick({R.id.iv_close, R.id.tv_register, R.id.tv_agreement, R.id.ctv_is_agree, R.id.tv_get_code})
    public void onClick(View view) {
        String phone = etPhone.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_register:
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
                if (Utils.isEmpty(etPsd.getText().toString())) {
                    Toaster.toast(this, "请输入密码");
                    etPsd.requestFocus();
                    return;
                }
                if (etPsd.getText().toString().length() < 6) {
                    Toaster.toast(this, "密码长度需大于6");
                    etPsd.requestFocus();
                    return;
                }
                if (Utils.isEmpty(etRepeatPsd.getText().toString())) {
                    Toaster.toast(this, "请输入重复密码");
                    etRepeatPsd.requestFocus();
                    return;
                }
                if (!etPsd.getText().toString().equals(etRepeatPsd.getText().toString())) {
                    Toaster.toast(this, "两次密码设置不一致！");
                    etPsd.requestFocus();
                    return;
                }
                if (Utils.isEmpty(etVerifyCode.getText().toString())) {
                    Toaster.toast(this, "请输入验证码");
                    etVerifyCode.requestFocus();
                    return;
                }
                if (ctvIsAgree.isChecked()) {
                    doForgetPsd();
                } else {
                    Toaster.toast(this, "请先同意服务协定!");
                    return;
                }
                break;
            case R.id.tv_agreement:
                startActivity(new Intent(ForgetAct.this, AgreementAct.class)
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
            case R.id.iv_close:
                finish();
                break;
        }
    }

    Activity getContext() {
        return this;
    }

    private void doLogin(String phone, String psd) {
        LoadDialog.show(this, "正在为您登录...");
        String pass = Utils.MD5(psd);
        Map<String, Object> map = new HashMap<>();
        map.put("username", phone);
        map.put("password", pass);
        NRClient.login(map, new ResultCallback<Result<UserInfo>>() {
            @Override
            public void onSuccess(Result<UserInfo> result) {
                LoadDialog.dismiss(ForgetAct.this);
                Toaster.toast(ForgetAct.this, "登录成功！");
                saveUserInfo(result.getData());
                startActivity(new Intent(ForgetAct.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(ForgetAct.this);
                Utils.showErrorToast(getContext(), throwable);
            }
        });
    }

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

    private void doForgetPsd() {
        LoadDialog.show(this);
        final String phone = etPhone.getText().toString();
        final String psd = etPsd.getText().toString();
        String pass = Utils.MD5(psd);
        String code = etVerifyCode.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("userPhone", phone);
        map.put("password", pass);
        map.put("code", code);
        NRClient.forgetPsd(map, new ResultCallback<Result<String>>() {
            @Override
            public void onSuccess(Result<String> result) {
                LoadDialog.dismiss(getContext());
                Toaster.toast(getContext(), "操作成功！");
                doLogin(phone, psd);
            }

            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }
        });
    }

    private void doGetCode(String phone) {
        LoadDialog.show(this);
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("exist", "forgetpw");
        map.put("doSendSMS", MApplication.isRelease);
        NRClient.getVerifyCode(map, new ResultCallback<Result<Object>>() {
            @Override
            public void onSuccess(Result<Object> result) {
                LoadDialog.dismiss(ForgetAct.this);
                mSendVerificationCodeCountDownTimer.start();
                if (!MApplication.isRelease)
                    etVerifyCode.setText(result.getData() + "");
                Toaster.toast(ForgetAct.this, "请注意查收！");
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
}
