package com.narancommunity.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.MeFunctionEntity;
import com.narancommunity.app.entity.RankEntity;

import java.util.List;

/**
 * Writer：fancy on 2017/12/26 15:15
 * Email：120760202@qq.com
 * FileName :
 */

public class BookRangeAdapter extends EasyRecyclerAdapter<RankEntity> {
    int tag;
    MeItemInterface meItemInterface;

    public BookRangeAdapter(Context context, List<RankEntity> list, int tag) {
        super(context, list);
        this.tag = tag;
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_book_range, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final RankEntity item = getList().get(position);
        hold.tvName.setText(Utils.getValue(item.getAccountName()) + "");
        String url = Utils.getValue(item.getAccountImg());
        if (!"".equals(url))
            Utils.setImgF(mContext, url, hold.ivIcon);
        else Utils.setImgF(mContext, R.mipmap.zw_morentouxiang, hold.ivIcon);
        String remark = Utils.getValue(item.getAccountRemark());
        if (!remark.equals(""))
            hold.tvRemark.setText(remark + "");
        else hold.tvRemark.setText("这个人很懒，什么都没留下");
        if (position < 3) {
            if (position == 0)
                hold.ivLevel.setImageResource(R.mipmap.gyb_jinpai);
            else if (position == 1)
                hold.ivLevel.setImageResource(R.mipmap.gyb_yinpai);
            else if (position == 2)
                hold.ivLevel.setImageResource(R.mipmap.gyb_tongpai);
            hold.tvLevel.setVisibility(View.INVISIBLE);
            hold.ivLevel.setVisibility(View.VISIBLE);
        } else {
            hold.ivLevel.setVisibility(View.INVISIBLE);
            hold.tvLevel.setVisibility(View.VISIBLE);
            hold.tvLevel.setText("" + (position + 1));
        }
        hold.tvTimes.setText("" + Utils.getValue(item.getRankScore()));
        hold.tvTimes.setVisibility(View.VISIBLE);
//        if (tag == 0 || tag == 2) {
//            hold.tvGrades.setVisibility(View.GONE);
//            hold.rbStar.setVisibility(View.GONE);
//        } else {
//            hold.tvTimes.setVisibility(View.GONE);
//            hold.tvGrades.setVisibility(View.VISIBLE);
//            hold.rbStar.setVisibility(View.VISIBLE);
//            hold.tvGrades.setText("" + Utils.getValue(item.getRankName()));
//            hold.rbStar.setRating(Utils.getValue(item.getRankScore()) / 2);
//        }
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLevel;
        TextView tvLevel;
        SelectableRoundedImageView ivIcon;
        TextView tvName;
        TextView tvTimes;
        TextView tvRemark;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvLevel = itemView.findViewById(R.id.tv_level);
            ivLevel = itemView.findViewById(R.id.iv_level);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTimes = itemView.findViewById(R.id.tv_times);
            tvRemark = itemView.findViewById(R.id.tv_remark);
        }
    }
}
