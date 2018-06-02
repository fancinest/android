package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.book.BookDetailAct;
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.RecEntity;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :
 */
public class FindLatestAdapter extends ListBaseAdapter<RecEntity> {
    OnItemClickListener listener;
    boolean isSearch = false;
    boolean isCollect;

    public FindLatestAdapter(Context context) {
        super(context);
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void isSearch(boolean isSearch) {
        this.isSearch = isSearch;
    }

    public void isCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_find_latest;
    }

    @Override
    public int getItemCount() {
        if (isSearch) {
            return super.getItemCount();
        } else {
            if (super.getItemCount() > 8)
                return 8;
            else
                return super.getItemCount();
        }
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        if (mDataList.size() <= 0)
            return;
        final RecEntity entity = mDataList.get(position);
        if (entity == null)
            return;


        LinearLayout lnDontate = holder.getView(R.id.ln_dontate);
        LinearLayout lnScore = holder.getView(R.id.ln_score);
        CardView cardView = holder.getView(R.id.card_parent);
        ImageView ivImg = holder.getView(R.id.iv_img);

        ImageView ivDonater = holder.getView(R.id.iv_donater);

        TextView tvDonater = holder.getView(R.id.tv_donater);
        TextView tvName = holder.getView(R.id.tv_book_name);
        TextView tvWriter = holder.getView(R.id.tv_writer);
//        RelativeLayout rlTop = holder.getView(R.id.rl_top);
//        TextView tv = holder.getView(R.id.tv);
        TextView tvScore = holder.getView(R.id.tv_score);
        RatingBar rating = holder.getView(R.id.rating);
        TextView tvDesc = holder.getView(R.id.tv_desc);
        TextView tvDistance = holder.getView(R.id.tv_distance);

        tvName.setText(Utils.getValue(entity.getOrderTitle()));
        tvWriter.setText(Utils.getValue(entity.getOrderAuthor()));
        tvDesc.setText(Utils.getValue(entity.getOrderContent()));
        String address = Utils.getValue(entity.getProvince()) + Utils.getValue(entity.getCity()) + Utils.getValue(entity.getCounty());
        if (address.equals(""))
            address = "未知";
        tvDistance.setText(address);

        if (!"".equals(Utils.getValue(entity.getOrderImgs()))) {
            Utils.setImgF(mContext, entity.getOrderImgs(), ivImg);
        } else {
            Utils.setImgF(mContext, R.mipmap.bitmap_book, ivImg);
        }

        if (isSearch) {
            tvWriter.setVisibility(View.GONE);
            lnScore.setVisibility(View.GONE);
        } else {
            tvScore.setText(Utils.getValue(entity.getAverage()) + "");
            rating.setRating(Float.parseFloat(Utils.getValue(entity.getAverage())) / 2);
        }
        if (isCollect) {
            lnScore.setVisibility(View.GONE);
            lnDontate.setVisibility(View.VISIBLE);
            tvDistance.setVisibility(View.GONE);
            tvDonater.setText("" + Utils.getValue(entity.getInitiatorNike()));
            String donateUrl = Utils.getValue(entity.getInitiatorImg()) + "";
            if (donateUrl.equals("")) {
                Utils.setImgF(mContext, donateUrl, ivDonater);
            } else Utils.setImgF(mContext, R.mipmap.zw_morentouxiang, ivDonater);
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener.onItemClick(position);
                mContext.startActivity(new Intent(mContext, BookDetailAct.class)
                        .putExtra("bookId", entity.getOrderId()));
            }
        });
    }
}
