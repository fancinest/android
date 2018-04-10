package com.narancommunity.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
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
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;
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
    @BindView(R.id.et_psd)
    EditText etPsd;
    @BindView(R.id.tv_login)
    TextView tvLogin;
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

    @OnClick({R.id.tv_login, R.id.tv_reset, R.id.tv_register, R.id.tv_qq, R.id.tv_sina, R.id.tv_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                String phone = etPhone.getText().toString();
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
                String psd = etPsd.getText().toString();
                if (Utils.isEmpty(psd)) {
                    Toaster.toast(this, "请输入密码");
                    etPsd.requestFocus();
                    return;
                }
                if (psd.length() < 6) {
                    Toaster.toast(this, "密码长度需大于6");
                    etPsd.requestFocus();
                    return;
                }
                doLogin(phone, psd);
                break;
            case R.id.tv_reset:
                startActivity(new Intent(getContext(), ForgetAct.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(getContext(), RegisterAct.class));
                break;
            case R.id.tv_qq:
                break;
            case R.id.tv_sina:
                break;
            case R.id.tv_wechat:
                break;
        }
    }

    private void doLogin(String phone, String psd) {
        LoadDialog.show(this, "正在为您登录...");
        String pass = Utils.MD5(psd);
        Map<String, Object> map = new HashMap<>();
        map.put("accountNum", phone);
        map.put("password", pass);
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

    /**
     * 保存用户信息
     *
     * @param data
     */
    private void saveUserInfo(UserInfo data) {
//                UserInfo info = new UserInfo();
//                info.setAccessToken(result.getData().get("accessToken") + "");
//                info.setAccountId(result.getData().get("accountId") + "");
//                info.setNickName(result.getData().get("nickName") + "");
//                info.setPhone(result.getData().get("phone") + "");

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
