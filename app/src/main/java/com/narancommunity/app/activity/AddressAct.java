package com.narancommunity.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.narancommunity.app.BaseActivity;
import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.adapter.MyAddressAdapter;
import com.narancommunity.app.common.CenteredToolbar;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Toaster;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.Address;
import com.narancommunity.app.entity.AddressEntity;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Writer：fancy on 2018/1/3 14:15
 * Email：120760202@qq.com
 * FileName :
 */

public class AddressAct extends BaseActivity {

    List<AddressEntity> list = new ArrayList<>();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_add)
    Button btnAdd;

    MyAddressAdapter addressAdapter;
    @BindView(R.id.toolbar)
    CenteredToolbar toolbar;

    boolean isForResult = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_address);
        ButterKnife.bind(this);

        toolbar.setTitle("收货地址");
        isForResult = getIntent().getBooleanExtra("isForResult",false);
        setBar(toolbar);
        initView();
    }

    private void getList() {
        LoadDialog.show(this, "");
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", 1);
        map.put("pageSize", 30);
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        NRClient.getAddressList(map, new ResultCallback<Result<Address>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Address> result) {
                LoadDialog.dismiss(getContext());
                if (list != null && list.size() > 0) {
                    list.clear();
                    addressAdapter.clear();
                }
                list = result.getData().getMails();
                addressAdapter.setList(list);
                addressAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayout);

        addressAdapter = new MyAddressAdapter(getContext(), list);
        addressAdapter.setListener(new MeItemInterface() {
            @Override
            public void OnItemClick(int position) {
                if(isForResult){
                    Intent it = new Intent();
                    it.putExtra("address", list.get(position));
                    setResult(2000, it);
                    finish();
                }
            }

            @Override
            public void OnDelClick(int position) {
                delAddress(position);
            }
        });
        recyclerView.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();
    }

    private void delAddress(final int position) {
        LoadDialog.show(this, "删除中......");
        Map<String, Object> map = new HashMap<>();
        map.put("mailId", list.get(position).getMailId());
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        NRClient.delAddress(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                list.remove(position);
                if (list.size() == 1)
                    list.get(0).setDefault(true);
                addressAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        getContext().startActivity(new Intent(getContext(), EditAddressAct.class)
                .putExtra("flag", 0));
    }
}
