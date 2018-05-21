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
import com.narancommunity.app.adapter.base.ListBaseAdapter;
import com.narancommunity.app.adapter.base.SuperViewHolder;
import com.narancommunity.app.common.Utils;
import com.narancommunity.app.entity.CompanyEntity;
import com.narancommunity.app.entity.RecEntity;

import butterknife.BindView;

/**
 * Writer：fancy on 2017/8/30 17:17
 * Email：120760202@qq.com
 * FileName :
 */
public class LoveSearchAdapter extends ListBaseAdapter<CompanyEntity> {
    OnItemClickListener listener;
    boolean isSearch = false;

    public LoveSearchAdapter(Context context) {
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
        return R.layout.item_love_search;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        if (mDataList.size() <= 0)
            return;
        final CompanyEntity entity = mDataList.get(position);
        if (entity == null)
            return;

        LinearLayout lnItem = holder.getView(R.id.ln_item);
        SelectableRoundedImageView ivLogo = holder.getView(R.id.iv_logo);

        TextView tvDesc = holder.getView(R.id.tv_desc);
        TextView tvName = holder.getView(R.id.tv_name);

        tvName.setText(Utils.getValue(entity.getCompanyName()));
        tvDesc.setText(Utils.getValue(entity.getCompanyContent()));

        if (!"".equals(Utils.getValue(entity.getCompanyImg()))) {
            Utils.setImgF(mContext, entity.getCompanyImg(), ivLogo);
        } else {
            Utils.setImgF(mContext, R.mipmap.bg, ivLogo);
        }
        lnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
    }
}
