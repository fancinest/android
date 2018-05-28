package com.narancommunity.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.OrderEntity;

import java.util.List;

/**
 * Writer：fancy on 2017/5/9 10:59
 * Email：120760202@qq.com
 * FileName :
 */

public class WantListAdapter extends EasyRecyclerAdapter<OrderEntity> {
    boolean isLimited = false;
    MeItemInterface meItemInterface;

    public WantListAdapter(Context context, List<OrderEntity> list) {
        super(context, list);
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_want_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final OrderEntity item = getList().get(position);
        hold.tv_content.setText(Utils.getValue(item.getAccountNike() + ""));
        String url = item.getAccountImg();
        Utils.setImgF(mContext, url, hold.iv_pic);
        hold.ln_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meItemInterface.OnItemClick(position);
            }
        });
        if ("SUCCESS".equals(item.getApplyStatus())) {
            hold.iv_status.setVisibility(View.VISIBLE);
        } else if (!"SUCCESS".equals(item.getApplyStatus()))
            hold.iv_status.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_content;
        SelectableRoundedImageView iv_pic;
        ImageView iv_status;
        RelativeLayout ln_parent;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_pic = itemView.findViewById(R.id.iv_icon);
            tv_content = itemView.findViewById(R.id.tv_desc);
            iv_status = itemView.findViewById(R.id.iv_status);
            ln_parent = itemView.findViewById(R.id.ln_parent);
        }
    }
}
