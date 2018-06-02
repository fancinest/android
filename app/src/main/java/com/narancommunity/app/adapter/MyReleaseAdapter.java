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
import com.narancommunity.app.activity.index.book.BookSHHZAct;
import com.narancommunity.app.activity.index.book.BookYSHYDetailAct;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.common.adapter.EasyRecyclerAdapter;
import com.narancommunity.app.entity.YSHYEntity;

import java.util.List;

/**
 * Writer：fancy on 2017/5/9 10:59
 * Email：120760202@qq.com
 * FileName : 我发布过的
 */

public class MyReleaseAdapter extends EasyRecyclerAdapter<YSHYEntity> {
    boolean isLimited = false;
    MeItemInterface meItemInterface;
    boolean isYSHY = true;

    public MyReleaseAdapter(Context context, List<YSHYEntity> list) {
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
        View convertView;
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_my_release, parent, false);
        return new MyViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder hold = (MyViewHolder) (holder);

        final YSHYEntity item = getList().get(position);
        String url = Utils.getValue(item.getAccountImg());
        if (!"".equals(url)) {
            Utils.setImgF(mContext, url, hold.ivIcon);
        } else
            Utils.setImgF(mContext, R.mipmap.bitmap_shequliebiao, hold.ivIcon);
        hold.tvContent.setTextColor(mContext.getResources().getColor(R.color.black));
        hold.tvTitle.setVisibility(View.GONE);
        hold.tvTitle.setVisibility(View.VISIBLE);
        hold.tvTitle.setText("" + Utils.getValue(item.getContentTitle()));
        hold.tvContent.setTextColor(mContext.getResources().getColor(R.color.color_999999));

        String pic = Utils.getValue(item.getContentImg());
        if (!"".equals(pic)) {
            if (pic.contains(",")) {
                pic = pic.split(",", -1)[0];
            }
            Utils.setImgF(mContext, pic, hold.ivImg);
            hold.ivImg.setVisibility(View.VISIBLE);
        } else if ("".equals(pic)) {
            hold.ivImg.setVisibility(View.GONE);
            Utils.setImgF(mContext, R.mipmap.bitmap_shequliebiao, hold.ivImg);
        }
        hold.tvContent.setText(Utils.getValue(item.getContentBody()));
        hold.lnParent.setOnClickListener(new View.OnClickListener() {
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
        TextView tvContent;
        ImageView ivImg;
        TextView tvTitle;
        LinearLayout lnParent;
        TextView tvComplete;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvContent = itemView.findViewById(R.id.tv_content);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvComplete = itemView.findViewById(R.id.tv_complete);
            lnParent = itemView.findViewById(R.id.ln_parent);
            itemView.setTag(this);
        }
    }
}
