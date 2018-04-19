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
import com.narancommunity.app.activity.BookDetailAct;
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.BookListEntity;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :
 */
public class FindLatestAdapter extends ListBaseAdapter<BookListEntity> {
    OnItemClickListener listener;
    boolean isSearch = false;

    public FindLatestAdapter(Context context) {
        super(context);
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void isSearch(boolean isSearch) {
        this.isSearch = isSearch;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_find_latest;
    }

    @Override
    public int getItemCount() {
        if (super.getItemCount() > 8)
            return 8;
        else
            return super.getItemCount();
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        if (mDataList.size() <= 0)
            return;
        final BookListEntity entity = mDataList.get(position);
        if (entity == null)
            return;

        LinearLayout lnScore = holder.getView(R.id.ln_score);
        CardView cardView = holder.getView(R.id.card_parent);
        ImageView ivImg = holder.getView(R.id.iv_img);
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
        tvDistance.setText(Utils.getValue("20"));

        if (!"".equals(Utils.getValue(entity.getOrderImgs()))) {
            Utils.setImgF(mContext, entity.getOrderImgs(), ivImg);
        } else {
            Utils.setImgF(mContext, R.mipmap.bg, ivImg);
        }

        if (isSearch) {
            tvWriter.setVisibility(View.GONE);
            lnScore.setVisibility(View.GONE);
        } else {
            tvScore.setText(Utils.getValue(entity.getAverage()) + "");
            rating.setRating(Float.parseFloat(Utils.getValue(entity.getAverage())) / 2);
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
