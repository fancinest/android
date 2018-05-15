package com.narancommunity.app.activity.index;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.R;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.WishDetailEntity;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/1/22 18:54
 * Email：120760202@qq.com
 * FileName : 运输中状态
 */

public class TransitAct extends BaseActivity {
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.btn_complete)
    Button btnComplete;

    int orderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_transit);
        ButterKnife.bind(this);
        toolbar.setTitle("运输中");
        setBar(toolbar);
        orderId = getIntent().getIntExtra("orderId", 0);
        getCode(orderId);
        MApplication.putActivity("", getContext());
    }

    String code;

    private void getCode(int orderId) {
        LoadDialog.show(getContext(), "正在获取订单号...");
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", MApplication.getAccessToken());
        map.put("orderId", orderId);
        NRClient.getWishDetail(map, new ResultCallback<Result<WishDetailEntity>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<WishDetailEntity> result) {
                LoadDialog.dismiss(getContext());
                code = result.getData().getMailCode();
                tvCode.setText("快递运单号：" + code);
            }
        });
    }

    @OnClick({R.id.tv_copy, R.id.btn_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_copy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvCode.getText());
                Toaster.toast(getContext(), "复制成功，您可以自行查询物流信息");
                break;
            case R.id.btn_complete:
                startActivity(new Intent(getContext(), WishFeedBackAct.class)
                        .putExtra("orderId", orderId));
                break;
        }
    }
}
