package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.BookHelpDetailAct;
import com.narancommunity.app.activity.BookYSHYDetailAct;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.BookCommunityEntity;

import java.util.List;

/**
 * Writer：fancy on 2017/5/9 10:59
 * Email：120760202@qq.com
 * FileName : 书评 item
 */

public class CommunityYSHYAdapter extends EasyRecyclerAdapter<BookCommunityEntity> {
    boolean isLimited = false;
    MeItemInterface meItemInterface;
    boolean isYSHY = true;

    public CommunityYSHYAdapter(Context context, List<BookCommunityEntity> list) {
        super(context, list);
    }

    public void setListener(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    public void setTag(boolean isYSHY) {
        this.isYSHY = isYSHY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bookcommunity_son, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final BookCommunityEntity item = getList().get(position);
        String url = item.getUrl();
        if (!"".equals(url)) {
            Utils.setImgF(mContext, url, hold.ivIcon);
        } else {
            Utils.setImgF(mContext, R.mipmap.zw_morentouxiang, hold.ivIcon);
        }
        String pic = item.getPic();
        if (!"".equals(pic)) {
            Utils.setImgF(mContext, pic, hold.ivImg);
        } else {
            Utils.setImgF(mContext, R.mipmap.zw_morentouxiang, hold.ivImg);
        }
        hold.tvName.setText(Utils.getValue(item.getName()));
        hold.tvContent.setText(Utils.getValue(item.getContent()));
        hold.tvTimes.setText(Utils.dateDiff(Utils.stringTimeToMillion(item.getCreateTime())) + "");
        hold.tvComment.setText(Utils.getValue(item.getCount()) + "");
        hold.tvLike.setText(Utils.getValue(item.getLikes()) + "");
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
                    mContext.startActivity(new Intent(mContext, BookYSHYDetailAct.class));
                else
                    mContext.startActivity(new Intent(mContext, BookHelpDetailAct.class));
            }
        });
        hold.ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isYSHY)
                    mContext.startActivity(new Intent(mContext, BookYSHYDetailAct.class));
                else
                    mContext.startActivity(new Intent(mContext, BookHelpDetailAct.class));

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

        public MyViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_icon);
            tvName = itemView.findViewById(R.id.tv_name);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTimes = itemView.findViewById(R.id.tv_times);
            tvComment = itemView.findViewById(R.id.tv_comment);
            tvLike = itemView.findViewById(R.id.tv_like);
            ivImg = itemView.findViewById(R.id.iv_img);
        }
    }
}
