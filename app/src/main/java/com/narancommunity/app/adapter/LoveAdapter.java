package com.narancommunity.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.joooonho.SelectableRoundedImageView;
import com.narancommunity.app.R;
import com.narancommunity.app.activity.index.BookDetailAct;
import com.narancommunity.app.activity.love.CompanyDetailAct;
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.CompanyEntity;
import com.narancommunity.app.entity.RecEntity;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :
 */
public class LoveAdapter extends ListBaseAdapter<CompanyEntity> {
    OnItemClickListener listener;
    boolean isSearch = false;
    boolean isCollect;

    public LoveAdapter(Context context) {
        super(context);
    }


    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

//    public void isSearch(boolean isSearch) {
//        this.isSearch = isSearch;
//    }
//
//    public void isCollect(boolean isCollect) {
//        this.isCollect = isCollect;
//    }

    @Override
    public int getLayoutId() {
        return R.layout.item_love;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        final CompanyEntity entity = mDataList.get(position);
        if (entity == null)
            return;
        SelectableRoundedImageView ivImg = holder.getView(R.id.iv_logo);

        TextView tvTitle = holder.getView(R.id.tv_title);
        TextView tvName = holder.getView(R.id.tv_name);
        CardView cardParent = holder.getView(R.id.card_parent);

        tvName.setText(Utils.getValue(entity.getCompanyName()) + "");
        tvTitle.setText(Utils.getValue(entity.getCompanyType()) + "");

        if (!"".equals(Utils.getValue(entity.getCompanyImg()))) {
            Utils.setImgF(mContext, entity.getCompanyImg(), ivImg);
        } else {
            Utils.setImgF(mContext, R.mipmap.bg, ivImg);
        }

        cardParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener.onItemClick(position);
                mContext.startActivity(new Intent(mContext, CompanyDetailAct.class)
                        .putExtra("id", entity.getCompanyId()));
            }
        });
    }
}
