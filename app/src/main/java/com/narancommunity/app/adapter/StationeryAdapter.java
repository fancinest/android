package com.narancommunity.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.MeFunctionEntity;
import com.narancommunity.app.entity.StationeryEntity;

import java.util.List;

/**
 * Writer：fancy on 2017/5/9 10:59
 * Email：120760202@qq.com
 * FileName :
 */

public class StationeryAdapter extends EasyRecyclerAdapter<StationeryEntity> {
    boolean isLimited = false;
    MeItemInterface meItemInterface;

    public StationeryAdapter(Context context, List<StationeryEntity> list) {
        super(context, list);
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    public void setLimited(boolean isLimited) {
        this.isLimited = isLimited;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_stationery, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final StationeryEntity item = getList().get(position);
        String url = item.getStationeryImg();
        hold.ctv.setChecked(item.isChecked());
        Utils.setImgF(mContext, url, hold.iv_pic);
        hold.lnParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meItemInterface.OnItemClick(position);
                verifyOthers(position);
            }
        });
    }

    private void verifyOthers(int position) {
        for (int i = 0; i < getList().size(); i++) {
            getList().get(i).setChecked(false);
        }
        getList().get(position).setChecked(true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (getList().size() < 4)
            return getList().size();
        if (isLimited)
            return 4;
        else
            return getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_pic;
        RelativeLayout lnParent;
        CheckedTextView ctv;

        public MyViewHolder(View itemView) {
            super(itemView);
            ctv = itemView.findViewById(R.id.ctv);
            iv_pic = itemView.findViewById(R.id.iv);
            lnParent = itemView.findViewById(R.id.ln_parent);
        }
    }
}
