package com.narancommunity.app.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.activity.general.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.mine.AddressAct;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.AddressEntity;
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
 * Writer：fancy on 2018/1/18 17:27
 * Email：120760202@qq.com
 * FileName :准备发货
 */

public class StartFahuoAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.tv_select_address)
    TextView tvSelectAddress;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.ln_name)
    LinearLayout lnName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ln_address)
    LinearLayout lnAddress;
    @BindView(R.id.et_num)
    EditText etNum;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.btn_go)
    Button btnGo;

    Integer applyId;
    boolean isWish = false;
    Integer orderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_start_fahuo);
        ButterKnife.bind(this);
        setBar(toolbar);
        toolbar.setTitle("确认信息");
        lnAddress.setVisibility(View.GONE);
        tvSelectAddress.setVisibility(View.VISIBLE);
        applyId = getIntent().getIntExtra("applyId", 0);
        isWish = getIntent().getBooleanExtra("isWish", false);
        orderId = getIntent().getIntExtra("orderId",0);

        MApplication.putActivity("fahuo", getContext());
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

    @OnClick({R.id.tv_select_address, R.id.ln_address, R.id.iv_scan, R.id.btn_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_address:
            case R.id.ln_address:
                Intent its = new Intent(getContext(), AddressAct.class);
                its.putExtra("isForResult", true);
                startActivityForResult(its, 2000);
                break;
            case R.id.iv_scan:
                Toaster.toast(getContext(), "暂不支持该功能！");
                break;
            case R.id.btn_go:
                if (mAddress == null) {
                    Toaster.toast(getContext(), "请先选择地址！");
                    return;
                }
                if (etNum.getText().toString().equals("")) {
                    etNum.requestFocus();
                    Toaster.toast(getContext(), "请先输入运单号！");
                    return;
                }
                LoadDialog.show(getContext(), "确认中...");
                if (isWish) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("accessToken", MApplication.getAccessToken());
                    map.put("orderId", orderId);
                    map.put("mailCode", etNum.getText().toString());
                    NRClient.WishSendConfirm(map, new ResultCallback<Result<Void>>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            LoadDialog.dismiss(getContext());
                            Utils.showErrorToast(getContext(), throwable);
                        }

                        @Override
                        public void onSuccess(Result<Void> result) {
                            LoadDialog.dismiss(getContext());
                            startActivity(new Intent(getContext(), ExpAct.class));
                        }
                    });
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("accessToken", MApplication.getAccessToken());
                    map.put("applyId", applyId);
                    map.put("mailCode", etNum.getText().toString());
                    NRClient.donateWishConfirm(map, new ResultCallback<Result<Void>>() {
                        @Override
                        public void onFailure(Throwable throwable) {
                            LoadDialog.dismiss(getContext());
                            Utils.showErrorToast(getContext(), throwable);
                        }

                        @Override
                        public void onSuccess(Result<Void> result) {
                            LoadDialog.dismiss(getContext());
                            startActivity(new Intent(getContext(), ExpAct.class));
                        }
                    });
                }
                break;
        }
    }

    AddressEntity mAddress = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000 && data != null) {
            AddressEntity address = (AddressEntity) data.getSerializableExtra("address");
            mAddress = address;
            setAddress(address);
        }
    }

    private void setAddress(AddressEntity address) {
        tvSelectAddress.setVisibility(View.GONE);
        lnAddress.setVisibility(View.VISIBLE);
        tvName.setText("收货人：" + Utils.getValue(address.getMailName()) + "");
        tvTel.setText(Utils.getValue(address.getMailPhone()) + "");
        tvAddress.setText("收货地址：" + Utils.getValue(address.getProvince() + address.getCity() + address.getCounty()
                + address.getMailAddress()) + "");
    }
}
