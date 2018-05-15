package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.narancommunity.app.MApplication;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.EditAddressAct;
import com.narancommunity.app.common.LoadDialog;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.AddressEntity;
import com.narancommunity.app.net.NRClient;
import com.narancommunity.app.net.Result;
import com.narancommunity.app.net.ResultCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Writer：fancy on 2017/5/9 10:59
 * Email：120760202@qq.com
 * FileName :
 */

public class MyAddressAdapter extends EasyRecyclerAdapter<AddressEntity> {
    boolean isLimited = false;
    MeItemInterface meItemInterface;

    public MyAddressAdapter(Context context, List<AddressEntity> list) {
        super(context, list);
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_address, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final AddressEntity item = getList().get(position);
        hold.tvName.setText("收货人："+Utils.getValue(item.getMailName()) + "");
        hold.tvAddress.setText("收货地址："+Utils.getValue(item.getProvince() + Utils.getValue(item.getCity())
                + Utils.getValue(item.getCounty()) + Utils.getValue(item.getMailAddress()) + ""));
        hold.tvTel.setText(Utils.getValue(item.getMailPhone() + ""));
        hold.ctvDefault.setChecked(item.isDefault());
        if (item.isDefault())
            hold.ctvDefault.setText("默认地址");
        else
            hold.ctvDefault.setText("设为默认");
        hold.ctvDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyDefault(position, view);

            }
        });
        hold.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meItemInterface.OnDelClick(position);
            }
        });
        hold.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), EditAddressAct.class)
                        .putExtra("flag", 1)
                        .putExtra("address", getList().get(position)));
            }
        });
//        Utils.setImgF(mContext, url, hold.iv_pic);
        hold.lnParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, SortSearchAct.class)
//                        .putExtra("type", Utils.getValue(item.getClassifyName())));
                meItemInterface.OnItemClick(position);
            }
        });
    }

    private void verifyDefault(final int position, final View view) {
        LoadDialog.show(getContext(), "");
        Map<String, Object> map = new HashMap<>();
        map.put("mailId", getList().get(position).getMailId());
        map.put("accessToken", MApplication.getAccessToken(getContext()));
        NRClient.setDefaultAddress(map, new ResultCallback<Result<Void>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LoadDialog.dismiss(getContext());
                Utils.showErrorToast(getContext(), throwable);
            }

            @Override
            public void onSuccess(Result<Void> result) {
                LoadDialog.dismiss(getContext());
                CheckedTextView checkedTextView = (CheckedTextView) view;
                checkedTextView.toggle();
                AddressEntity entity = getList().get(position);
                entity.setDefault(!entity.isDefault());
                for (int i = 0; i < getList().size(); i++) {
                    if (i != position)
                        getList().get(i).setDefault(false);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvTel;
        TextView tvAddress;
        CheckedTextView ctvDefault;
        TextView tvEdit;
        TextView tvDel;
        LinearLayout lnParent;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTel = itemView.findViewById(R.id.tv_tel);
            tvAddress = itemView.findViewById(R.id.tv_address);
            ctvDefault = itemView.findViewById(R.id.ctv_default);
            tvEdit = itemView.findViewById(R.id.tv_edit);
            tvDel = itemView.findViewById(R.id.tv_del);
            lnParent = itemView.findViewById(R.id.ln_parent);
        }
    }
}
