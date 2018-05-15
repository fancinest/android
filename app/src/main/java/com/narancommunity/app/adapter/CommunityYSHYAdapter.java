package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.BookSHHZAct;
import com.narancommunity.app.activity.index.BookYSHYDetailAct;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.YSHYEntity;

import java.util.List;

/**
 * Writer：fancy on 2017/5/9 10:59
 * Email：120760202@qq.com
 * FileName : 以书会友和书荒互动适配器
 */

public class CommunityYSHYAdapter extends EasyRecyclerAdapter<YSHYEntity> {
    boolean isLimited = false;
    MeItemInterface meItemInterface;
    boolean isYSHY = true;
    boolean isMyCenter = false;

    public CommunityYSHYAdapter(Context context, List<YSHYEntity> list) {
        super(context, list);
    }

    public void setIsMyFunction(boolean isMyCenter) {
        this.isMyCenter = isMyCenter;
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    public void setTag(boolean isYSHY) {
        this.isYSHY = isYSHY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bookcommunity_son, parent, false);
        return new MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final YSHYEntity item = getList().get(position);
        if (!isMyCenter) {
            String url = Utils.getValue(item.getAccountImg());
            if (!"".equals(url)) {
                Utils.setImgF(mContext, url, hold.ivIcon);
            } else
                Utils.setImgF(mContext, R.mipmap.zw_morentouxiang, hold.ivIcon);
            hold.lnTop.setVisibility(View.VISIBLE);
            hold.tvName.setText(Utils.getValue(item.getAccountNike()));
            hold.tvTimes.setText(Utils.dateDiff(Utils.stringTimeToMillion(item.getCreateTime())) + "");
            hold.lnFunction.setVisibility(View.VISIBLE);
            hold.tvContent.setTextColor(mContext.getResources().getColor(R.color.black));
            hold.tvTitle.setVisibility(View.GONE);
        } else {
            hold.tvTitle.setVisibility(View.VISIBLE);
            hold.tvTitle.setText("" + Utils.getValue(item.getContentTitle()));
            hold.lnFunction.setVisibility(View.GONE);
            hold.lnTop.setVisibility(View.GONE);
            hold.tvContent.setTextColor(mContext.getResources().getColor(R.color.color_999999));
        }

        String pic = Utils.getValue(item.getContentImg());
        if (!"".equals(pic)) {
            if (pic.contains(",")) {
                pic = pic.split(",", -1)[0];
            }
            Utils.setImgF(mContext, pic, hold.ivImg);
            hold.ivImg.setVisibility(View.VISIBLE);
        } else if ("".equals(pic)) {
            hold.ivImg.setVisibility(View.GONE);
            Utils.setImgF(mContext, R.mipmap.zw_morentouxiang, hold.ivImg);
        }
        hold.tvContent.setText(Utils.getValue(item.getContentBody()));
        hold.tvComment.setText(Utils.getValue(item.getCommentTimes()) + "");
        hold.tvLike.setText(Utils.getValue(item.getLikeTimes()) + "");
        hold.tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meItemInterface.OnItemClick(position);
            }
        });
        hold.tvLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meItemInterface.OnDelClick(position);
            }
        });
        hold.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isYSHY)
                    mContext.startActivity(new Intent(mContext, BookYSHYDetailAct.class)
                            .putExtra("data", item));
                else
                    mContext.startActivity(new Intent(mContext, BookSHHZAct.class)
                            .putExtra("data", item));
            }
        });
        hold.ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isYSHY)
                    mContext.startActivity(new Intent(mContext, BookYSHYDetailAct.class)
                            .putExtra("data", item));
                else
                    mContext.startActivity(new Intent(mContext, BookSHHZAct.class)
                            .putExtra("data", item));

            }
        });
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        SelectableRoundedImageView ivIcon;
        TextView tvName;
        TextView tvTimes;
        TextView tvContent;
        TextView tvComment;
        TextView tvLike;
        ImageView ivImg;
        TextView tvTitle;
        LinearLayout lnTop;
        LinearLayout lnFunction;


        public MyViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTimes = itemView.findViewById(R.id.tv_times);
            tvComment = itemView.findViewById(R.id.tv_comment);
            tvLike = itemView.findViewById(R.id.tv_like);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivImg = itemView.findViewById(R.id.iv_img);
            lnTop = itemView.findViewById(R.id.ln_top);
            lnFunction = itemView.findViewById(R.id.ln_function);
            itemView.setTag(this);
        }
    }
}
